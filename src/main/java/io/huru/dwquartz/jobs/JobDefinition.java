package io.huru.dwquartz.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.Trigger;

public interface JobDefinition {

	public Trigger getTrigger();
	
	public JobDataMap getJobData();
	
	public Class<? extends Job> getJobClass();

	public String getDescription();

	public String getIdentity();

	public String getCronExpression();
	
}
