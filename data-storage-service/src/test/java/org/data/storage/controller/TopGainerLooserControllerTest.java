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
import org.data.storage.model.TopGainerLooser;
import org.data.storage.service.ChartDataService;
import org.data.storage.service.TopGainerLooserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TopGainerLooserRestController.class)
class TopGainerLooserControllerTest {

	@MockBean
	private TopGainerLooserService topGainerLooserService;
	
	@Autowired
	MockMvc mockMvc;
	
	LocalDate date = LocalDate.now();

	@Test
	void getChartData() throws Exception {
		Mockito.when(topGainerLooserService.getDatas())
		.thenReturn(Stream.of(
				new TopGainerLooser(1,"ABC","Stock1","date", (float) 12.0, (float) 250.0,"INR","Available"),
				new TopGainerLooser(2,"XYZ","Stock1","date", (float) 12.0, (float) 250.0,"INR","Available"))
				.collect(Collectors.toList()));
		
		mockMvc.perform(get("/gainerlooser"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()").value(2))
		.andExpect(jsonPath("$[0].symbol").value("ABC"))
		.andExpect(jsonPath("$[1].symbol").value("XYZ"))
		.andExpect(jsonPath("$[0].id").value(1))
		.andExpect(jsonPath("$[1].id").value(2));
	}
	
	@Test
	void getChartDataById() throws Exception {
		Mockito.when(topGainerLooserService.getData(anyInt()))
		.thenReturn(new TopGainerLooser(2,"XYZ","Stock1","date", (float) 12.0, (float) 250.0,"INR","Available"));
		
		mockMvc.perform(get("/gainerlooser/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].symbol").value("XYZ"));
	}
	
	@Test
	void getChartData_status404() throws Exception {
		Mockito.when(topGainerLooserService.getData(anyInt())).thenThrow(DataNotFoundException.class);
		
		mockMvc.perform(get("/gainerlooser/1"))
		.andExpect(status().isNotFound());
		
	}
	
	@Test
	public void saveChartData() throws Exception {
		TopGainerLooser topGainerLooser = new TopGainerLooser(2,"XYZ","Stock1","date", (float) 12.0, (float) 250.0,"INR","Available");

        String gainerlooserJson = "{\"symbol\":\"ABC\",\"stock_name\":\"Stock1\",\"date\":\"2021-10-18\",\"change_percentage\":0.54,\"change_price\":12,\"closing_price\":250,\"currency\":\"USD\",\"status\":\"available\"}";
        
		Mockito.when(topGainerLooserService.createOrUpdateData(Mockito.any(TopGainerLooser.class)))
		.thenReturn(topGainerLooser);
		
		mockMvc.perform(post("/gainerlooser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gainerlooserJson))		
		.andExpect(status().isCreated());
				
	}
	
	@Test
	public void deleteUserTest() throws Exception {
			
		topGainerLooserService.deleteData(1);
		verify(topGainerLooserService,times(1)).deleteData(1);
	}
	

}
