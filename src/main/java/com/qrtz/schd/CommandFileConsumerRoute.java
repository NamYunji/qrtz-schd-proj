package com.qrtz.schd;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



// @Component : dependency - spring-boot-starter
// RouteBuilder: dependency - camel-spring-boot-starter
// EndpointRouteBuilder : dependency - camel-endpointdsl
@Component
public class CommandFileConsumerRoute extends EndpointRouteBuilder {

	@Autowired
	private CommandProperties props;
	
	@Autowired
	private ScheduleService service;
	
	@Autowired
	private ScheduleCommand command;
	
	@Override
	public void configure() throws Exception {
	
		from(file(props.getScheduleFile()).delete(true))
		// json : dependency - camel-jackson
		.unmarshal().json(JsonLibrary.Jackson, ScheduleCommand.class)
		.process(e -> {
			command = (ScheduleCommand) e.getIn().getBody();
			service.manage(command);
		});
	}
}
