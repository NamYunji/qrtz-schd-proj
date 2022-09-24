package com.qrtz.schd;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SendTopicRoute extends EndpointRouteBuilder {
	
	public static final String ROUTEID = "SendTopicRoute";

	@Override
	public void configure() throws Exception {
		
		from(direct(ROUTEID)).routeId(ROUTEID)
		.process(exchange -> {
			System.out.println(exchange.getIn().getBody());
			
			Map<String, Integer> priceMap = new HashMap<>();
			priceMap.put("Samsung", 1800000);
			priceMap.put("LG", 1600000);
			priceMap.put("HP", 1200000);
			
			exchange.getIn().setBody(priceMap);
		})
		.to(kafka("item-price"))
		.log("Successfully Send to Kafka Topic [item-price].");
		
	}
}
