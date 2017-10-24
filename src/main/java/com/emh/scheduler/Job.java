package com.emh.scheduler;

import java.io.File;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.emh.util.Utility;

public class Job extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		File file = new File("C:\\dailyReport");
		if (file.exists()) {
			System.out.println("Starting delete file or folder.");
			Utility.deleteDirectory(file);
		} else {
			System.out.println("Nothing to do......");
		}
	}
}
