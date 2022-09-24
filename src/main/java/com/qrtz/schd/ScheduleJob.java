package com.qrtz.schd;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.support.DefaultExchange;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;


// Job을 정의하는 클래스 이름은 무조건 ScheduleJob 이어야 함
// Job : dependency - quartz // execute() override 필요
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ScheduleJob implements Job {
	
	@Autowired
	private ProducerTemplate template;
	
	@Autowired
	private CamelContext camelContext;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		Exchange exchange = new DefaultExchange(camelContext);
		template.send("direct:" + SendTopicRoute.ROUTEID, exchange);
	}
}
