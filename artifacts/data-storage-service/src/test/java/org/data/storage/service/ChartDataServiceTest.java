package org.data.storage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.data.storage.dao.ChartDataDAO;
import org.data.storage.model.ChartData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class ChartDataServiceTest {

	@Autowired
	private ChartDataService service;
	
	@MockBean
	private ChartDataDAO repository;
	LocalDate date = LocalDate.now();
	
	@Test
	public void getChartDataTest() {
		when(repository.getChartData())
		.thenReturn(Stream.of(
				new ChartData(1,"ABC","Stock1",date, (float) 250.0,"INR"),
				new ChartData(1,"XYZ","Stock2",date, (float) 250.0,"INR"))
				.collect(Collectors.toList()));
		
		assertEquals(2, service.getChartDatas().size());
	}
	
	@Test
	public void getChartDataById() {
		
		when(repository.getChartData(1))
		.thenReturn( new ChartData(1,"ABC","Stock1",date, (float) 250.0,"INR"));
		
		ChartData chartData = repository.getChartData(1);
		
		assertEquals("ABC",chartData.getSymbol());
	}
	
	@Test
	public void saveChartData() {
		ChartData chartData = new ChartData(1,"ABC","Stock1",date, (float) 250.0,"INR");
		service.createOrUpdateChartData(chartData);
		verify(repository,times(1)).createOrUpdateChartData(chartData);
		
	}
	
	@Test
	public void deleteUserTest() {
		service.deleteChartData(1);
		verify(repository,times(1)).deleteChartData(1);
	}

}
