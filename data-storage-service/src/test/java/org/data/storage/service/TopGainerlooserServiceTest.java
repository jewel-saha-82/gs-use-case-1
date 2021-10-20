package org.data.storage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.data.storage.dao.ChartDataDAO;
import org.data.storage.dao.TopGainerLooserDAO;
import org.data.storage.model.ChartData;
import org.data.storage.model.TopGainerLooser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class TopGainerlooserServiceTest {

	@Autowired
	private TopGainerLooserService service;
	
	@MockBean
	private TopGainerLooserDAO repository;
	LocalDate date = LocalDate.now();
	
	@Test
	public void getDataTest() {
		when(repository.getGainerLooser())
		.thenReturn(Stream.of(
				new TopGainerLooser(1,"ABC","Stock1",date,(float) 0.54, (float) 12.0, (float) 250.0,"INR","Available"),
				new TopGainerLooser(1,"XYZ","Stock2",date,(float) 0.54, (float) 12.0, (float) 250.0,"INR","Available"))
				.collect(Collectors.toList()));
		
		assertEquals(2, service.getDatas().size());
	}
	
	@Test
	public void getDataByIdTest() {
		
		when(repository.getData(1))
		.thenReturn( new TopGainerLooser(1,"ABC","Stock1",date,(float) 0.54, (float) 12.0, (float) 250.0,"INR","Available"));
		
		TopGainerLooser topGainerLooser = repository.getData(1);
		
		assertEquals("ABC",topGainerLooser.getSymbol());
	}
	
	@Test
	public void saveDataTest() {
		TopGainerLooser topGainerLooser = new TopGainerLooser(1,"ABC","Stock1",date,(float) 0.54, (float) 12.0, (float) 250.0,"INR","Available");
		service.createOrUpdateData(topGainerLooser);
		verify(repository,times(1)).createOrUpdateData(topGainerLooser);
		
	}
	
	@Test
	public void deleteDataTest() {
		service.deleteData(1);
		verify(repository,times(1)).deleteData(1);
	}

}
