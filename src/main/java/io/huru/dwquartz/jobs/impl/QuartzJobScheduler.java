package io.huru.dwquartz.jobs.impl;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.quartz.JobBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.huru.dwquartz.jobs.JobDefinition;
import io.huru.dwquartz.jobs.JobScheduler;

public class QuartzJobScheduler implements JobScheduler {

	private static Logger logger = LoggerFactory.getLogger(QuartzJobScheduler.class);

	private Scheduler scheduler;
	private final Set<JobDefinition> jobDefinitions;
	
	@Inject
	public QuartzJobScheduler(Set<JobDefinition> jobs) {
		this.jobDefinitions = Collections.unmodifiableSet(jobs);
	}

	@Override
	public void start() throws Exception {
		logger.info("Starting Quartz Manager.");
        scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduleAllJobs();
		logger.info("Started Quartz Manager.");
	}

	@Override
	public void stop() throws Exception {
		scheduler.shutdown();
	}

	private void scheduleAllJobs() throws SchedulerException {
		for (JobDefinition jd : jobDefinitions) {
			scheduleJob(jd);
		}
	}

	@Override
	public void scheduleJob(JobDefinition jd) throws SchedulerException {
        JobBuilder jobBuilder = JobBuilder.newJob(jd.getJobClass()).withDescription(jd.getDescription()).usingJobData(jd.getJobData()).withIdentity(jd.getIdentity());
        scheduler.scheduleJob(jobBuilder.build(), jd.getTrigger());
		logger.info("Scheduled job \"{}\" with trigger \"{}\"", jd.getDescription(), jd.getTrigger());
	}

	@Override
	public void unscheduleJob(JobDefinition jd) throws SchedulerException {
		scheduler.unscheduleJob(jd.getTrigger().getKey());
		logger.info("Unscheduled job \"{}\" with trigger \"{}\"", jd.getDescription(), jd.getTrigger());
	}

	@Override
	public void refreshJobSchedule(JobDefinition jd) throws SchedulerException {
		unscheduleJob(jd);
		scheduleJob(jd);
	}

}
