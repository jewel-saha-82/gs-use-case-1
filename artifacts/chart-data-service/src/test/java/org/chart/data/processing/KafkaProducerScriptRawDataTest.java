package org.chart.data.processing;

import java.util.concurrent.ExecutionException;

import org.chart.data.processing.kafka.producer.KafkaProducerScriptRawData;
import org.chart.data.processing.model.MetaModel;
import org.chart.data.processing.model.RootModel;
import org.chart.data.processing.model.ValuesModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class KafkaProducerScriptRawDataTest {

	@Autowired
	private KafkaProducerScriptRawData kafkaProducer;

	@Test
	@DisplayName(value = "Test for kafka producer for script-raw-data")
	void producerTest() throws InterruptedException, ExecutionException {

		// given
		MetaModel metaModel = new MetaModel("AAPL", "1day", "USD", "America/New_York", "NASDAQ", "Common Stock");
		ValuesModel valuesModel = new ValuesModel("2021-10-11", "142.27000", "144.81000", "141.81000", "142.81000",
				"63012662");
		RootModel KafkaRootModel = new RootModel(metaModel, valuesModel, "ok");

		// when
		//for (int i = 0; i < 100; i++)
			//kafkaProducer.sendMessage(KafkaRootModel);

		// then
	}
}
