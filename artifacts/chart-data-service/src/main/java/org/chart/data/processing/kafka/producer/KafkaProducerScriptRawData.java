package org.chart.data.processing.kafka.producer;

import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.chart.data.processing.model.RootModel;
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
public class KafkaProducerScriptRawData {

	Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	@Value("${kafka.topic.script-raw-data}")
	private String topic;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(final String message) throws InterruptedException, ExecutionException {

		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);

		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			private RootModel rootModel;

			@Override
			public void onSuccess(final SendResult<String, String> record) {
				ObjectMapper mapper = new ObjectMapper();
				RootModel rootModel = null;
				try {
					rootModel = mapper.readValue(record.getProducerRecord().value(), RootModel.class);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				this.rootModel = rootModel;
				logger.info("sent message = {}, with offset = {}", rootModel, record.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(final Throwable throwable) {
				logger.error("unable to send message = {}", rootModel, throwable);
			}
		});
	}

}
