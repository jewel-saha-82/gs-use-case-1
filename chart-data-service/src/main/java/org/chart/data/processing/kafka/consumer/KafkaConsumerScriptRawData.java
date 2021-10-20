package org.chart.data.processing.kafka.consumer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

import org.chart.data.processing.kafka.producer.KafkaProducerChartData;
import org.chart.data.processing.model.ChartData;
import org.chart.data.processing.model.RootModel;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaConsumerScriptRawData {
	
	Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	// @Value("${kafka.topic.chart-data}")
	private final String topic = "script-raw-data";
	
	@Autowired
	private KafkaProducerChartData kafkaProducerChartData;

	@KafkaListener(topics = topic, groupId = "chart-data-consumer-grp")
	public void consumeMessage(final RootModel rootModel) throws InterruptedException, ExecutionException {
		
		logger.info("Consumer thread = {}", Thread.currentThread());
		
		System.out.println("Consumed message: " + rootModel);
		
		ChartData chartData = 
				ChartData
					.builder()
					.symbol(rootModel.getMeta().getSymbol())
					.stockName(rootModel.getMeta().getSymbol())
					.date(LocalDate.parse(rootModel.getValue().getDatetime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
					.closingPrice(new BigDecimal(rootModel.getValue().getClose()))
					.currency(rootModel.getMeta().getCurrency())
					.build();
		
		kafkaProducerChartData.sendMessage(chartData);
	}
}
