package com.qrtz.schd;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleService {
	
	// Scheduler : quartz dependency 추가
	@Autowired
	Scheduler scheduler;
	
	public void manage(ScheduleCommand schedule) throws SchedulerException {
		
		ScheduleOperation operation = schedule.getScheduleOperation();
		
		if(operation.equals(ScheduleOperation.RUN) || operation.equals(ScheduleOperation.RUN_ONCE)) {
			run(schedule);
			System.out.println("Schedule Successfully Runnned ...");
		} else if (operation.equals(ScheduleOperation.PAUSE)){
			pause(schedule);
			System.out.println("Schedule Successfully Paused ...");
		}
	}

	private void run(ScheduleCommand schedule) throws SchedulerException {

		String scheduleId = schedule.getScheduleId();
		String scheduleGroup = schedule.getScheduleGroup();
		String cron = schedule.getCron();
		String scheduleDescription = schedule.getScheduleDescription();
		
		JobKey jobKey = JobKey.jobKey(scheduleId, scheduleGroup);
		
		JobDetail jobDetail = buildJob(schedule);
		Trigger trigger = buildTrigger(schedule);
				
		if(scheduler.checkExists(jobKey)) {
			scheduler.deleteJob(jobKey);
		} 
		
		scheduler.scheduleJob(jobDetail, trigger);
	}
	
	private void pause(ScheduleCommand schedule) throws SchedulerException {
		
		String scheduleId = schedule.getScheduleId();
		String scheduleGroup = schedule.getScheduleGroup();

		TriggerKey triggerKey = TriggerKey.triggerKey(scheduleId, scheduleGroup);
		Trigger trigger = scheduler.getTrigger(triggerKey);
		
		if(trigger == null) {
			return;
		} else {
			scheduler.unscheduleJob(triggerKey);
		}
	}
	
	private Trigger buildTrigger(ScheduleCommand schedule) {
		
		String scheduleId = schedule.getScheduleId();
		String scheduleGroup = schedule.getScheduleGroup();
		String cron = schedule.getCron();
		String scheduleDescription = schedule.getScheduleDescription();
		ScheduleOperation scheduleOperation = schedule.getScheduleOperation();
		
		if(scheduleOperation.equals(ScheduleOperation.RUN)) {

			// TriggerBuilder, CronScheduleBuilder : dependency - quartz
			Trigger trigger = TriggerBuilder.newTrigger()
									.withIdentity(scheduleId, scheduleGroup)
									.withDescription(scheduleDescription)
									.withSchedule(CronScheduleBuilder.cronSchedule(cron))
									.build();
			return trigger;
			
		} else if(scheduleOperation.equals(ScheduleOperation.RUN_ONCE)){
			// 수정 필요
			SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
										.withIdentity(scheduleId, scheduleGroup)
										.withDescription(scheduleDescription)
										.build();
			return trigger;
		}
		return null;
	}
	
	private JobDetail buildJob(ScheduleCommand schedule) {
		
		String scheduleId = schedule.getScheduleId();
		String scheduleGroup = schedule.getScheduleGroup();
		String scheduleDescription = schedule.getScheduleDescription();
		
		
		// JobDetail : dependency - quartz
		JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class)
							.withIdentity(scheduleId, scheduleGroup)
							.withDescription(scheduleDescription)
							.build();
		return jobDetail;
	}
}