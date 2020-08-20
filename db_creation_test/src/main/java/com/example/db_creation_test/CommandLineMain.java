package com.example.db_creation_test;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Component
public class CommandLineMain implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

        String packageScan = "com.example.db_creation_test.entity";

        String url = "jdbc:postgresql://localhost:5432/slave1";
        this.createEntityManagerFactory(url, "unit1", packageScan).destroy();

        url = "jdbc:postgresql://localhost:5432/slave2";
        this.createEntityManagerFactory(url, "unit2", packageScan).destroy();

//        url = "jdbc:postgresql://localhost:5432/slave3";
//        this.createEntityManagerFactory(url, "unit3", packageScan);


    }

    private LocalContainerEntityManagerFactoryBean createEntityManagerFactory(String url, String persistentUintName, String packageScan) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(this.createDataSource(url));
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(packageScan);
        factory.setPersistenceUnitName(persistentUintName);
        Properties p = new Properties();
        //mapping to spring.jpa.properties.hibernate.dialect in application.yml
        p.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        p.put("hibernate.connection.charSet", "utf-8");
//        p.put("hibernate.show_sql", "false");
        factory.setJpaProperties(p);
//        DynamicDataSourceContextHolder.setDataSourceRouterKey("slave1");
        factory.afterPropertiesSet();
        return factory;


    }


    private DataSource createDataSource(String url) {
        HikariConfig config = new HikariConfig();


        config.setJdbcUrl(url);
        config.setDriverClassName("org.postgresql.Driver");
        config.setUsername("postgres");
        config.setPassword("admin");
//        config.setMinimumIdle(this.minimumConnections);
//        config.setMaximumPoolSize(this.maximumConnections);
//        config.setConnectionTimeout(this.connectionTimeout);
        HikariDataSource ds = new HikariDataSource(config);
        return ds;
    }
}
