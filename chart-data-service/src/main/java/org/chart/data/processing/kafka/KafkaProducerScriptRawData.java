package org.chart.data.processing.kafka;

import java.util.concurrent.ExecutionException;

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
	private KafkaTemplate<String, RootModel> kafkaTemplate;

	public boolean sendMessage(final RootModel rootModel) throws InterruptedException, ExecutionException {

		BoolWrap boolWrap = new BoolWrap();

		ListenableFuture<SendResult<String, RootModel>> future = kafkaTemplate.send(topic, rootModel);

		future.get();

		future.addCallback(new ListenableFutureCallback<SendResult<String, RootModel>>() {

			private SendResult<String, RootModel> message;

			@Override
			public void onSuccess(final SendResult<String, RootModel> message) {
				this.message = message;
				boolWrap.status = true;
				logger.info("sent message = " + message + ", with offset= " + message.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(final Throwable throwable) {
				boolWrap.status = false;
				logger.error("unable to send message = " + message, throwable);
			}
		});

		return boolWrap.status;
	}

	class BoolWrap {
		boolean status = false;
	}

}
