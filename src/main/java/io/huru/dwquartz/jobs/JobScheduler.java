package io.huru.dwquartz.jobs;

import org.quartz.SchedulerException;

import io.dropwizard.lifecycle.Managed;

public interface JobScheduler extends Managed{

	void scheduleJob(JobDefinition jd) throws SchedulerException;
	
	void unscheduleJob(JobDefinition jd) throws SchedulerException;

	void refreshJobSchedule(JobDefinition jd) throws SchedulerException;

}
