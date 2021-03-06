package com.fast.family.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.fast.family.datasource.druid.DruidDataSourceProperties;
import com.fast.family.datasource.exception.DynamicDataSourceException;
import com.fast.family.datasource.hikaricp.HikariCPProperties;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author 张顺
 * @version 1.0
 * @created 2018/10/3-11:28
 */
public class DynamicDataSourceFatcory {

    public static DataSource createDateSource(DynamicDataSourceProperties properties){
        if (properties.getDruid() != null){
            return createDruidDataSource(properties.getDruid());
        } else if (properties.getHikaricp() != null){
            return createHikariCPDataSource(properties.getHikaricp());
        }
        throw new DynamicDataSourceException("数据源创建失败");
    }


    private static DataSource createHikariCPDataSource(HikariCPProperties properties){
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setUsername(properties.getUsername());
        hikariDataSource.setPassword(properties.getPassword());
        hikariDataSource.setDriverClassName(properties.getDriverClassName());
        hikariDataSource.setJdbcUrl(properties.getUrl());
        hikariDataSource.setConnectionTimeout(properties.getConnectionTimeout());
        hikariDataSource.setIdleTimeout(properties.getIdleTimeout());
        hikariDataSource.setMaximumPoolSize(properties.getMaximumPoolSize());
        hikariDataSource.setMaxLifetime(properties.getMaxLifetime());
        hikariDataSource.setMinimumIdle(properties.getMinimumIdle());
        hikariDataSource.setInitializationFailTimeout(properties.getInitializationFailTimeout());
        hikariDataSource.setIsolateInternalQueries(properties.isIsolateInternalQueries());
        hikariDataSource.setAllowPoolSuspension(properties.isAllowPoolSuspension());
        hikariDataSource.setReadOnly(properties.isReadOnly());
        hikariDataSource.setRegisterMbeans(properties.isRegisterMbeans());
        hikariDataSource.setValidationTimeout(properties.getValidationTimeout());
        hikariDataSource.setLeakDetectionThreshold(properties.getLeakDetectionThreshold());
        return hikariDataSource;
    }

    private static DataSource createDruidDataSource(DruidDataSourceProperties properties){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(properties.getUrl());
        druidDataSource.setUsername(properties.getUsername());
        druidDataSource.setPassword(properties.getPassword());
        druidDataSource.setDriverClassName(properties.getDriverClassName());
        druidDataSource.setInitialSize(properties.getInitialSize());
        druidDataSource.setMaxActive(properties.getMaxActive());
        druidDataSource.setMinIdle(properties.getMinIdle());
        druidDataSource.setMaxWait(properties.getMaxWait());
        druidDataSource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
        druidDataSource.setMaxEvictableIdleTimeMillis(properties.getMaxEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(properties.getValidationQuery());
        druidDataSource.setValidationQueryTimeout(properties.getValidationQueryTimeout());
        druidDataSource.setTestOnBorrow(properties.isTestOnBorrow());
        druidDataSource.setTestOnReturn(properties.isTestOnReturn());
        druidDataSource.setTestWhileIdle(properties.isTestWhileIdle());
        druidDataSource.setPoolPreparedStatements(properties.isPoolPreparedStatements());
        druidDataSource.setMaxOpenPreparedStatements(properties.getMaxOpenPreparedStatements());
        druidDataSource.setSharePreparedStatements(properties.isSharePreparedStatements());
        druidDataSource.setConnectProperties(properties.getConnectionProperties());
        try {
            druidDataSource.setFilters(properties.getFilters());
            druidDataSource.init();
        } catch (SQLException e) {
            throw new DynamicDataSourceException("创建数据源失败",e);
        }
        return druidDataSource;
    }

}
