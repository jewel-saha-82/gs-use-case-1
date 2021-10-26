package org.data.storage.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.data.storage.exception.DataNotFoundException;
import org.data.storage.model.TopGainerLooser;
import org.data.storage.service.TopGainerLooserService;
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
public class TopGainerLooserRestController {
	
	@Autowired
	TopGainerLooserService topGainerLooserService;
	
	@GetMapping(value = {"/gainerlooser","/gainerlooser/{id}"})
	public ResponseEntity<List<TopGainerLooser>> getGainerLooser(@PathVariable Optional<Integer> id){
		List<TopGainerLooser> dataList;
		if(id.isPresent()) {
			TopGainerLooser topGainerLooser = topGainerLooserService.getData(id.get());
			if(topGainerLooser == null) {
				throw new DataNotFoundException("Data with id not found: "+id.get());
			}
			dataList = Stream.of(topGainerLooser).collect(Collectors.toList());
		} else {
			dataList =  topGainerLooserService.getDatas();		
		}
		return new ResponseEntity<List<TopGainerLooser>>(dataList,new HttpHeaders(), HttpStatus.OK);
	}
	
	@PostMapping("/gainerlooser")
	public ResponseEntity<TopGainerLooser> createData(@RequestBody TopGainerLooser data) {
		final TopGainerLooser createdData = topGainerLooserService.createOrUpdateData(data);
		return new ResponseEntity<TopGainerLooser>(createdData, new HttpHeaders(), HttpStatus.CREATED);
	}
	
	@PutMapping("/gainerlooser")
	public TopGainerLooser updateData(@RequestBody TopGainerLooser data) {
		topGainerLooserService.createOrUpdateData(data);
		return data;
	}
	
	@DeleteMapping("/gainerlooser/{id}")
	public ResponseEntity deleteData(@PathVariable int id) throws DataNotFoundException {
		TopGainerLooser data = topGainerLooserService.getData(id);
		
		if(data==null)
			throw new DataNotFoundException("TopGainerLooser data id not found: "+id);
		
		topGainerLooserService.deleteData(id);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	
}
