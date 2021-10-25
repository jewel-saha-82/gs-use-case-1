package org.data.storage.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.data.storage.exception.DataNotFoundException;
import org.data.storage.model.ChartData;
import org.data.storage.service.ChartDataService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ChartDataRestController.class)
class ChartDataControllerTest {

	@MockBean
	private ChartDataService chartDataService;
	
	@Autowired
	MockMvc mockMvc;
	
	LocalDate date = LocalDate.now();

	@Test
	void getChartData() throws Exception {
		Mockito.when(chartDataService.getChartDatas())
		.thenReturn(Stream.of(
				new ChartData(1,"ABC","Stock1",date, (float) 250.0,"INR"),
				new ChartData(2,"XYZ","Stock2",date, (float) 250.0,"INR"))
				.collect(Collectors.toList()));
		
		mockMvc.perform(get("/chartdata"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()").value(2))
		.andExpect(jsonPath("$[0].symbol").value("ABC"))
		.andExpect(jsonPath("$[1].symbol").value("XYZ"))
		.andExpect(jsonPath("$[0].id").value(1))
		.andExpect(jsonPath("$[1].id").value(2));
	}
	
	@Test
	void getChartDataById() throws Exception {
		Mockito.when(chartDataService.getChartData(anyInt()))
		.thenReturn(new ChartData(1, "ABC", "Stock1", date, (float) 250.0, "INR"));
		
		mockMvc.perform(get("/chartdata/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].symbol").value("ABC"));
	}
	
	@Test
	void getChartData_status404() throws Exception {
		Mockito.when(chartDataService.getChartData(anyInt())).thenThrow(DataNotFoundException.class);
		
		mockMvc.perform(get("/chartdata/1"))
		.andExpect(status().isNotFound());
		
	}
	
	@Test
	public void saveChartData() throws Exception {
		ChartData chartData = new ChartData(1,"ABC","Stock1",date, (float) 250.0,"INR");
        String chartDataJson = "{\"symbol\":\"ABC\",\"stock_name\":\"Stock1\",\"date\":\"2021-10-18\",\"closing_price\":250,\"currency\":\"USD\"}";

		Mockito.when(chartDataService.createOrUpdateChartData(Mockito.any(ChartData.class)))
		.thenReturn(chartData);
		
		mockMvc.perform(post("/chartdata")
				.contentType(MediaType.APPLICATION_JSON)
				.content(chartDataJson))		
		.andExpect(status().isCreated());
				
	}
	
	@Test
	public void deleteUserTest() throws Exception {
			
		chartDataService.deleteChartData(1);
		verify(chartDataService,times(1)).deleteChartData(1);
	}
	

}
