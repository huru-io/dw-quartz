package io.huru.dwquartz.jobs;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.lifecycle.Managed;

public class QuartzManager implements Managed {

	private static Logger logger = LoggerFactory.getLogger(QuartzManager.class);

	private Scheduler scheduler;
	private final Set<JobDefinition> jobDefinitions;
	
	@Inject
	public QuartzManager(Set<JobDefinition> jobs) {
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
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jd.getCronExpression());
            Trigger trigger = TriggerBuilder.newTrigger().withSchedule(scheduleBuilder).build();
            JobBuilder jobBuilder = JobBuilder.newJob(jd.getJobClass()).withDescription(jd.getDescription()).usingJobData(jd.getJobData()).withIdentity(jd.getIdentity());
            scheduler.scheduleJob(jobBuilder.build(), trigger);
		}
	}

}