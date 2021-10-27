package org.chart.data.processing.kafka.producer;

import java.util.concurrent.ExecutionException;

import org.chart.data.processing.model.ChartData;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducerChartData {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
	@Value("${kafka.topic.chart-data}")
	private String topic;
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(final String message) throws InterruptedException, ExecutionException {

		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);

		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			private ChartData chartData;

			@Override
			public void onSuccess(final SendResult<String, String> record) {
				ChartData chartData = jsonToChartData(record);
				this.chartData = chartData;
				logger.info("sent message = {}, with offset = {}", chartData, record.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(final Throwable throwable) {
				logger.error("unable to send message = {}", chartData, throwable);
			}
		});

	}

	private ChartData jsonToChartData(final SendResult<String, String> record) {
		try {
			return objectMapper.readValue(record.getProducerRecord().value(), ChartData.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
