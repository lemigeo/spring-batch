package com.batch.conf;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.batch.service.IService;
import com.batch.service.ServiceFactory;

@Configuration
public class JobConfig extends DefaultBatchConfigurer {
	
	@Autowired
	private BatchProperties props;
	@Autowired
	private JobBuilderFactory factory;	
	@Autowired
	private ThreadPoolTaskScheduler scheduler;
	
	private IService service;
	private final JobLauncher launcher;		
	
	public JobConfig(JobLauncher launcher) {
		this.launcher = launcher;		
	}
		
	public void setDataSource(DataSource dataSource) {	
	}
	
	@Scheduled(initialDelay=3000, fixedRate=3000)
	private void run() {
		try {
			Job job = getBatchJob();
			if(job == null) {
				throw new Exception("batch service not found");
			}
			launcher.run(job, getJobParameters());
		}
		catch (Exception e) {
			e.printStackTrace();
			scheduler.shutdown();		
		}		
	}
	
	private Job getBatchJob() {
		String jobName = props.getJob().getNames();
		if(service == null) {
			service = ServiceFactory.getService(jobName);
		}
	    return factory.get(jobName)
	    	.incrementer(new RunIdIncrementer())
	        .start(service.step1())
	        .next(service.step2())
	        .next(service.step3())
	        .build();
	}
	
	private JobParameters getJobParameters() {
        Map<String, JobParameter> parameters = new HashMap<>();
        JobParameter parameter = new JobParameter(new Date());
        parameters.put("currentTime", parameter);
        return new JobParameters(parameters);
    }
}
