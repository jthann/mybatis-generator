package com.jd.panda.dao.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DatabaseCluster extends AbstractRoutingDataSource {

    private String dbInUse;

    public DatabaseCluster() {
    }

    protected Object determineCurrentLookupKey() {
        return this.dbInUse;
    }

    public void setDbInUse(String dbInUse) {
        this.dbInUse = dbInUse;
    }
}