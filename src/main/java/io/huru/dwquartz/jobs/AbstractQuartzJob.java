package io.huru.dwquartz.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

public abstract class AbstractQuartzJob implements Job {

	@SuppressWarnings("unchecked")
	protected <T> T getFromContext(String key, JobExecutionContext context) {
		
		T t = (T) context.getMergedJobDataMap().get(key);
		if (t == null){
			throw new IllegalArgumentException("Could not find data n context with key " + key);
		}
		
		return t;
		
	}
	
	protected boolean existInContext(String key, JobExecutionContext context){
		return context.getMergedJobDataMap().containsKey(key);
	}

}