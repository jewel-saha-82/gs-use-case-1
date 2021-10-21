package org.data.storage.kafka.producer;

import org.data.storage.model.ChartData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerChartData {
	
	@Value("${kafka.topic.chart-data}")
	private String topic;
	
	@Autowired
	private KafkaTemplate<String, ChartData> kafkaTemplate;

}