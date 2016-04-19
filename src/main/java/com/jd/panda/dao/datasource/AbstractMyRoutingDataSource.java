package com.jd.panda.dao.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 读写分离的数据源<br/>
 * 可配置一个write和多个read-only的数据源,并提供对read-only数据源做负载均衡的接口
 */
public abstract class AbstractMyRoutingDataSource extends AbstractDataSource implements InitializingBean {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    protected static final String READ_ONLY = "read-only";

    private Object masterDataSource;
    private DataSource resolvedMasterDataSource;// 主库
    private List<Object> slaveDataSoures;
    private List<DataSource> resolvedSlaveDataSources;// 从库

    private int readDsSize;// read-only data source的数量,做负载均衡的时候需要
    private DataSourceLookup dataSourceLookup = new JndiDataSourceLookup();

    public void setSlaveDataSoures(List<Object> slaveDataSoures) {
        this.slaveDataSoures = slaveDataSoures;
    }

    public void setMasterDataSource(Object masterDataSource) {
        this.masterDataSource = masterDataSource;
    }

    public int getReadDsSize() {
        return readDsSize;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return determineTargetDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return determineTargetDataSource().getConnection(username, password);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.resolvedMasterDataSource = resolveSpecifiedDataSource(masterDataSource);
        if (this.resolvedMasterDataSource == null) {
            throw new IllegalArgumentException("Property 'masterDataSource' is required");
        }

        if (slaveDataSoures == null || slaveDataSoures.size() <= 0) {
            LOGGER.warn("未配置任何Slave从库数据源,读写全部访问Master库...");
            return;
        }
        resolvedSlaveDataSources = new ArrayList<DataSource>();
        for (Object item : slaveDataSoures) {
            DataSource ds = resolveSpecifiedDataSource(item);
            if (ds != null) {
                resolvedSlaveDataSources.add(ds);
            }
        }
        readDsSize = resolvedSlaveDataSources.size();
    }

    /**
     * 确认目标数据源
     *
     * @return
     */
    protected DataSource determineTargetDataSource() {
        String lookupKey = determineCurrentLookupKey();
        if (!READ_ONLY.equals(lookupKey) || readDsSize <= 0) {
            return getResolvedMasterDataSource();//写操作或者没有配置读库的时候
        } else {
            return loadBalance();
        }
    }

    protected List<DataSource> getResolvedSlaveDataSources() {
        return resolvedSlaveDataSources;
    }

    protected DataSource getResolvedMasterDataSource() {
        LOGGER.debug("ooo Using Master DataSource [{}]", resolvedMasterDataSource.toString());
        return resolvedMasterDataSource;
    }

    /**
     * 获取真实的data source
     *
     * @param dataSource (jndi | real data source)
     * @return
     * @throws IllegalArgumentException
     */
    private DataSource resolveSpecifiedDataSource(Object dataSource) throws IllegalArgumentException {
        DataSource ds = null;
        if (dataSource instanceof DataSource) {
            ds = (DataSource) dataSource;
        } else if (dataSource instanceof String) {
            ds = this.dataSourceLookup.getDataSource((String) dataSource);
        } else {
            throw new IllegalArgumentException(
                    "Illegal data source value - only [javax.sql.DataSource] and String supported: " + dataSource);
        }
        try {
            ds.getConnection().close();//测试数据源连接是否有效
        } catch (Exception e) {
            LOGGER.error("Invalid DataSource:{}", dataSource, e);
            return null;
        }
        return ds;
    }

    protected abstract String determineCurrentLookupKey();

    protected abstract DataSource loadBalance();
}