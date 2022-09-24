package com.qrtz.schd;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleCommand {

	private String agentId;
	private String scheduleId;
	private String scheduleGroup;
	private ScheduleOperation scheduleOperation;
	private String scheduleDescription;
	private String cron;

}
