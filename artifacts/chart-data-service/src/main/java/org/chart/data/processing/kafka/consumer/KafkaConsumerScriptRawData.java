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

	private Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
	private final String topic = "script-raw-data";
	@Autowired
	private KafkaProducerChartData kafkaProducerChartData;
	@Autowired
	private ObjectMapper objectMapper;

	@KafkaListener(topics = topic, groupId = "script-raw-consumer-grp")
	public void consumeMessage(List<ConsumerRecord<String, String>> records) {
		records.stream().forEach(x -> sendMsgToPrdcr(chartDataToJson(createChartData(jsonToRootModel(x)))));
	}

	public RootModel jsonToRootModel(ConsumerRecord<String, String> x) {
		logger.info(x.value());
		try {
			return objectMapper.readValue(x.value(), RootModel.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	private ChartData createChartData(RootModel rm) {
		return ChartData.builder().symbol(rm.getMeta().getSymbol()).stockName(rm.getMeta().getSymbol())
				.date(rm.getValue().getDatetime()).closingPrice(new BigDecimal(rm.getValue().getClose()))
				.currency(rm.getMeta().getCurrency()).build();
	}

	private String chartDataToJson(ChartData chartData) {
		String json = null;
		try {
			json = objectMapper.writeValueAsString(chartData);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		return json;
	}

	private void sendMsgToPrdcr(String json) {
		try {
			kafkaProducerChartData.sendMessage(json);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public String getTopic() {
		return this.topic;
	}
}
