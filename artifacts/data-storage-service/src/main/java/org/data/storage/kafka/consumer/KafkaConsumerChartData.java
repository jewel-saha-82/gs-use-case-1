package org.data.storage.kafka.consumer;

import java.util.List;

import org.data.storage.model.ChartData;
import org.data.storage.service.ChartDataService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerChartData {
	
	Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
	
	@Value("${kafka.topic.chart-data}")
	private String topic;
	
	@Autowired
	private ChartDataService chartDataService;
	
	@KafkaListener(topics = "chart-data", groupId= "chart-data-consumer1", containerFactory = "kafkaListenerContainerFactoryChartData" )
	public void consumeMessage(List<ChartData> chartDataList) {
		logger.info("Kafka batch dataSize = "+chartDataList.size());
		chartDataService.insertUsingBatch(chartDataList);
	}
}
