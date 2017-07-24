package io.huru.dwquartz.jobs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuartzConfiguration {
	
	private boolean jobsEnabled;

	@JsonProperty("jobs-enabled")
	public boolean isJobsEnabled(){
		return jobsEnabled;
	}

	public void setJobsEnabled(boolean jobsEnabled) {
		this.jobsEnabled = jobsEnabled;
	};

}
