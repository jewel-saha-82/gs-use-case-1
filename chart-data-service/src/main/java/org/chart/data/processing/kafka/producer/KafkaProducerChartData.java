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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducerChartData {

	Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	@Value("${kafka.topic.chart-data}")
	private String topic;

	@Autowired
	private KafkaTemplate<String, ChartData> kafkaTemplate2;

	public void sendMessage(final ChartData chartData) throws InterruptedException, ExecutionException {

		ListenableFuture<SendResult<String, ChartData>> future = kafkaTemplate2.send(topic, chartData);

		future.addCallback(new ListenableFutureCallback<SendResult<String, ChartData>>() {

			private SendResult<String, ChartData> message;

			@Override
			public void onSuccess(final SendResult<String, ChartData> message) {
				this.message = message;
				logger.info("sent message = " + message + ", with offset= " + message.getRecordMetadata().offset());
				// logger.info("Chart data producer thread = {}", Thread.currentThread());
			}

			@Override
			public void onFailure(final Throwable throwable) {
				logger.error("unable to send message = " + message, throwable);
				// logger.info("Chart data producer thread = {}", Thread.currentThread());
			}
		});

	}

}
