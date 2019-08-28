package com.vinodh.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableScheduling
public class ScheduleConfig {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("reportJob")
    private Job reportJob;

    // second, minute, hour, day of month, month, day(s) of week
    @Scheduled(cron = "0 0 0 * * *")
//    @Scheduled(cron = "0 */1 * * * *") // for every minute. use for testing locally
    public void execute() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        jobLauncher.run(reportJob, new JobParametersBuilder()
                .addDate("startDate", new Date())
                .toJobParameters());
    }


}
