package org.chart.data.processing.kafka.consumer;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.chart.data.processing.kafka.producer.KafkaProducerChartData;
import org.chart.data.processing.model.ChartData;
import org.chart.data.processing.model.RootModel;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaConsumerScriptRawData {

	Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	private final String topic = "script-raw-data";

	@Autowired
	private KafkaProducerChartData kafkaProducerChartData;

	@KafkaListener(topics = topic, groupId = "script-raw-consumer-grp")
	public void consumeMessage(List<ConsumerRecord<String, String>> records) {

		records.stream().forEach(x -> {

			System.out.println(x.value());
			ObjectMapper mapper = new ObjectMapper();
			RootModel rootModel = null;
			try {
				rootModel = mapper.readValue(x.value(), RootModel.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			System.out.println("Consumed message: " + rootModel);

			ChartData chartData = ChartData.builder().symbol(rootModel.getMeta().getSymbol())
					.stockName(rootModel.getMeta().getSymbol()).date(rootModel.getValue().getDatetime())
					.closingPrice(new BigDecimal(rootModel.getValue().getClose()))
					.currency(rootModel.getMeta().getCurrency()).build();

			String json = null;
			try {
				json = mapper.writeValueAsString(chartData);
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}

			try {
				kafkaProducerChartData.sendMessage(json);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}

		});
	}
}
