package com.jd.dao.datasource;

import org.springframework.core.NamedThreadLocal;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoutingDataSource extends AbstractRoutingDataSource {
    private final static NamedThreadLocal<String> keys = new NamedThreadLocal<String>("RoutingDataSource");
    private AtomicInteger count = new AtomicInteger(0);

    public static void readOnly() {
        keys.set(READ_ONLY);
    }

    public static void clear() {
        keys.remove();
    }

    @Override
    protected String determineCurrentLookupKey() {
        return keys.get();
    }

    @Override
    protected DataSource loadBalance() {
        int index = Math.abs(count.incrementAndGet()) % getReadDsSize();
        List<String> disableSlave = new ArrayList<String>(0);//Constants.getSystemCfgList("ds.slaveIndex.disabled", ",");
        if (disableSlave != null && disableSlave.size() > 0) {//通过参数来限制slave的切换
            int full = 0;
            while (full < getReadDsSize() && disableSlave.contains(String.valueOf(index))) {
                index = Math.abs(count.incrementAndGet()) % getReadDsSize();
                full++;//Slave X DataSource 不可用,重新筛选
            }
            if (full >= getReadDsSize()) {
                LOGGER.debug("所有Slave DataSource均不可用了,自动转发到Master响应!");
                return getResolvedMasterDataSource();
            }
        }
        DataSource dataSource = getResolvedSlaveDataSources().get(index);
        LOGGER.debug("ooo Using Slave {} DataSource [{}]", index, dataSource.toString());
        return dataSource;
    }

}