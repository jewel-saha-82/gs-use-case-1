package org.data.storage.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.data.storage.properties.AppProperties;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {
	
	@Autowired
	AppProperties appProperties;
	

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(appProperties.getPACKAGES_TO_SCAN());
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new  DriverManagerDataSource();
		dataSource.setDriverClassName(appProperties.getDRIVER());
		dataSource.setUrl(appProperties.getURL());
		dataSource.setUsername(appProperties.getUSERNAME());
		dataSource.setPassword(appProperties.getPASSWORD());
		return dataSource;
	}

	private final Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.put(Environment.DIALECT, appProperties.getDIALECT());
		hibernateProperties.put(Environment.SHOW_SQL, appProperties.getSHOW_SQL());
		hibernateProperties.put(Environment.HBM2DDL_AUTO, appProperties.getHBM2DDL_AUTO());
		hibernateProperties.put(Environment.STATEMENT_BATCH_SIZE, appProperties.getBATCH_SIZE());
		hibernateProperties.put(Environment.ORDER_INSERTS, appProperties.getORDER_INSERT());
		hibernateProperties.put(Environment.BATCH_VERSIONED_DATA, true);
		hibernateProperties.put(Environment.GENERATE_STATISTICS, appProperties.getStats());
		return hibernateProperties;
	}
}
