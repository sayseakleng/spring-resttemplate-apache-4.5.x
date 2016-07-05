package com.kdemo.junit.spring;

import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/testcontext-root.xml")
public class TestRestTemplate {
	private static final Logger logger = LoggerFactory.getLogger(TestRestTemplate.class);
	
	@Autowired
	RestOperations restOperation;
	
	@Test
	public void testGet() throws RestClientException, URISyntaxException {
		String forObject = restOperation.getForObject(new URI("https://github.com/qos-ch/logback-extensions/wiki/Spring"), String.class);
		assertNotNull(forObject);
		logger.debug(forObject);
	}

}
