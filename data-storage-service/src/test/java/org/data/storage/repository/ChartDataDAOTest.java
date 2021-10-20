package org.data.storage.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.data.storage.dao.ChartDataDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChartDataDAOTest {

	@Autowired
	ChartDataDAO chartDataDAO;
	
	@Test
	void CreateOrUpdateTest() {
		
	}

}
