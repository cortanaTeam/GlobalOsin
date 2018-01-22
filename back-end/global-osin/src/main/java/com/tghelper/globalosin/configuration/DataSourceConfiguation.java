package com.tghelper.globalosin.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

/**
 * Created by infamouSs on 1/22/18.
 */
@Configuration
@EnableAutoConfiguration
public class DataSourceConfiguation {
    
    @Value("${db.driver}")
    private String DB_DRIVER;
    
    @Value("${db.password}")
    private String DB_PASSWORD;
    
    @Value("${db.url}")
    private String DB_URL;
    
    @Value("${db.username}")
    private String DB_USERNAME;
    
    @Value("${hibernate.dialect}")
    private String HIBERNATE_DIALECT;
    
    @Value("${hibernate.show_sql}")
    private String HIBERNATE_SHOW_SQL;
    
    @Value("${hibernate.hbm2ddl.auto}")
    private String HIBERNATE_HBM2DDL_AUTO;
    
    @Value("${entitymanager.packagesToScan}")
    private String ENTITYMANAGER_PACKAGES_TO_SCAN;
    
    @Value("${hibernate.c3p0.max_size}")
    private String CONN_POOL_MAX_SIZE;
    
    @Value("${hibernate.c3p0.min_size}")
    private String CONN_POOL_MIN_SIZE;
    
    @Value("${hibernate.c3p0.idle_test_period}")
    private String CONN_POOL_IDLE_PERIOD;
    
    @Value("${hibernate.c3p0.timeout}")
    private String CONN_TIMEOUT;
    
    @Bean
    public ComboPooledDataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource("jupiter");
        
        try {
            dataSource.setDriverClass(DB_DRIVER);
        } catch (PropertyVetoException pve) {
            System.out.println(
                      "Cannot load datasource driver (" + DB_DRIVER + ") : " + pve.getMessage());
            return null;
        }
        dataSource.setJdbcUrl(DB_URL);
        dataSource.setUser(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setMinPoolSize(Integer.parseInt(CONN_POOL_MIN_SIZE));
        dataSource.setMaxPoolSize(Integer.parseInt(CONN_POOL_MAX_SIZE));
        dataSource.setMaxIdleTime(Integer.parseInt(CONN_POOL_IDLE_PERIOD));
        try {
            dataSource.setLoginTimeout(Integer.parseInt(CONN_TIMEOUT));
        } catch (SQLException ex) {
            System.out.println("Connection timeout");
            return null;
        }
        
        return dataSource;
    }
    
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
        hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
        hibernateProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
        sessionFactoryBean.setHibernateProperties(hibernateProperties);
        
        return sessionFactoryBean;
    }
    
    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager =
                  new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
    
}
