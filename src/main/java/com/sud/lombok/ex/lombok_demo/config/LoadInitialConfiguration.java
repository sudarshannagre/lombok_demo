package com.sud.lombok.ex.lombok_demo.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sud.lombok.ex.lombok_demo.pojo.OpcoConfiguration;

@Configuration
public class LoadInitialConfiguration implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(LoadInitialConfiguration.class);
	public static List<OpcoConfiguration> opcoConfigList = new ArrayList<>();
	
	public void loadOpcoConfiguration() {
		logger.info("loadOpcoConfiguration() : start at : " + new Date());
		try {
			JsonNode node = new ObjectMapper()
					.readTree(new ClassPathResource("OpcoConfiguration.json").getInputStream()).get("configuration");
			Iterator<JsonNode> it = node.iterator();

			while (it.hasNext()) {
				OpcoConfiguration opcoConfig = new ObjectMapper().readValue(it.next().toString(),
						OpcoConfiguration.class);
				opcoConfigList.add(opcoConfig);
			}
		} catch (IOException e) {
			logger.error("Exception in loadOpcoConfiguration() while reading the json file");
		}finally {
			logger.info("All Initial Opco Configuration loaded successfully !!!");
		}
		logger.info("OpcoList : "+opcoConfigList);
		logger.info("loadOpcoConfiguration() : end at : " + new Date());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		loadOpcoConfiguration();
	}

}
