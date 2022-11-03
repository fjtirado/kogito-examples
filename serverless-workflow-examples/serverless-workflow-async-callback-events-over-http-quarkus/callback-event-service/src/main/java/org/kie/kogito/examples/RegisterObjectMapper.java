package org.kie.kogito.examples;

import javax.enterprise.context.ApplicationScoped;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cloudevents.jackson.JsonFormat;
import io.quarkus.jackson.ObjectMapperCustomizer;

@ApplicationScoped
public class RegisterObjectMapper implements ObjectMapperCustomizer{

	@Override
	public void customize(ObjectMapper objectMapper) {
		objectMapper.registerModule(JsonFormat.getCloudEventJacksonModule());
	}
}
