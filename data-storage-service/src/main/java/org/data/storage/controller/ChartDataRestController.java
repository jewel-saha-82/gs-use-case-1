package org.data.storage.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.data.storage.exception.DataNotFoundException;
import org.data.storage.model.ChartData;
import org.data.storage.service.ChartDataService;

@RestController
public class ChartDataRestController {
	
	@Autowired
	ChartDataService chartDataService;
	
	@GetMapping("/chartdata")
	public ResponseEntity<List<ChartData>> getChartDatas(){
		List<ChartData> chartDataList =  chartDataService.getChartDatas();
		return new ResponseEntity<List<ChartData>>(chartDataList,new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/chartdata/{id}")
	public ResponseEntity<ChartData> getChartData(@PathVariable int id) throws DataNotFoundException {
		ChartData chartData = chartDataService.getChartData(id);
		if(chartData == null) {
			throw new DataNotFoundException("Data with id not found: "+id);
		}
		return new ResponseEntity<ChartData>(chartData, new HttpHeaders(), HttpStatus.OK);
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
	
	@DeleteMapping("chartdata/{id}")
	public ResponseEntity deleteChartData(@PathVariable int id) throws DataNotFoundException {
		ChartData chartData = chartDataService.getChartData(id);
		
		if(chartData==null)
			throw new DataNotFoundException("ChartData id not found: "+id);
		
		chartDataService.deleteChartData(id);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	
}
