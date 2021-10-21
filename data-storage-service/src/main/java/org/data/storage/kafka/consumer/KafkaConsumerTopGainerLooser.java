package org.data.storage.kafka.consumer;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.data.storage.model.ChartData;
import org.data.storage.model.TopGainerLooser;
import org.data.storage.service.ChartDataService;
import org.data.storage.service.TopGainerLooserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerTopGainerLooser {

	@Autowired
	private TopGainerLooserService topGainerLooserService;
	
	@KafkaListener(topics = "top-gainer-top-looser", groupId= "top-gainer-looser-data-consumer", containerFactory = "kafkaListenerContainerFactory" )
	public void consumeMessage(TopGainerLooser topGainerLooser) {
		
			topGainerLooserService.createOrUpdateData(topGainerLooser);
		}
	
}
