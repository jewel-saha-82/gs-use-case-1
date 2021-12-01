package org.api.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;


import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ApiPullServiceApplicationTests {

		@Autowired
		private TestRestTemplate testRestTemplate;

		@Test
		void contextLoads() {
			ResponseEntity<String> forEntity = testRestTemplate.getForEntity("/api.twelvedata.com/stocks", String.class);
			Assert.assertEquals(forEntity.getStatusCode(), HttpStatus.OK);
		}


	}
