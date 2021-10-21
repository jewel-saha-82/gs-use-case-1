package org.data.storage.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.data.storage.exception.DataNotFoundException;
import org.data.storage.model.ChartData;
import org.data.storage.service.ChartDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChartDataRestController {
	
	@Autowired
	ChartDataService chartDataService;
	
	@GetMapping(value = {"/chartdata","/chartdata/{id}"})
	public ResponseEntity<List<ChartData>> getChartDatas(@PathVariable Optional<Integer> id){
		List<ChartData> chartDataList;
		if(id.isPresent()) {
			ChartData chartData = chartDataService.getChartData(id.get());
			if(chartData == null) {
				throw new DataNotFoundException("Data with id not found: "+id.get());
			}
			chartDataList = Stream.of(chartData).collect(Collectors.toList());
		} else {
			chartDataList =  chartDataService.getChartDatas();		
		}
		return new ResponseEntity<List<ChartData>>(chartDataList,new HttpHeaders(), HttpStatus.OK);
	}
	
	@PostMapping("/chartdata")
	public ResponseEntity<ChartData> createChartData(@RequestBody ChartData chartData) {
		final ChartData createdData = chartDataService.createOrUpdateChartData(chartData);
		return new ResponseEntity<ChartData>(createdData, new HttpHeaders(), HttpStatus.CREATED);
	}
	
	@PutMapping("/chartdata")
	public ChartData updateChartData(@RequestBody ChartData chartData) {
		chartDataService.createOrUpdateChartData(chartData);
		return chartData;
	}
	
	@DeleteMapping("/chartdata/{id}")
	public ResponseEntity deleteChartData(@PathVariable int id) throws DataNotFoundException {
		ChartData chartData = chartDataService.getChartData(id);
		
		if(chartData==null)
			throw new DataNotFoundException("ChartData id not found: "+id);
		
		chartDataService.deleteChartData(id);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	
}
