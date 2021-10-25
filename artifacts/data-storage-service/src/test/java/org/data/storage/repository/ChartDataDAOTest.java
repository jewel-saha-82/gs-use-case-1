package org.data.storage.repository;

import java.time.LocalDate;

import org.data.storage.config.HibernateConfigTest;
import org.data.storage.dao.ChartDataDAO;
import org.data.storage.model.ChartData;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {HibernateConfigTest.class})
@ActiveProfiles("DaoTest")
class ChartDataDaoTest {

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	ChartDataDAO chartDataDAO;
	
	LocalDate date = LocalDate.now();
	
	@Test
	void SaveDataTest() {
		ChartData chartData = new ChartData(1,"ABC","Stock1",date, (float) 250.0,"INR");
		
	}

}
