package org.data.storage.kafka.consumer;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.data.storage.model.ChartData;
import org.data.storage.service.ChartDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerChartData {
	
	@Value("${kafka.topic.chart-data}")
	private String topic;
	
	@Autowired
	private ChartDataService chartDataService;
	
	@KafkaListener(topics = "chart-data", groupId= "chart-data-consumer-grp" )
	public void consumeMessage(List<ConsumerRecord<String, ChartData>> records) {
		records.stream().forEach(x->{
			ChartData chartData = x.value();
			chartDataService.createOrUpdateChartData(chartData);
		});
	}
}
