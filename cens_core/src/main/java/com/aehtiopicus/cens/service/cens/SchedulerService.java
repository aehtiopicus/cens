package com.aehtiopicus.cens.service.cens;

import java.util.List;

import com.aehtiopicus.cens.domain.entities.SchedulerJobs;
import com.aehtiopicus.cens.utils.CensException;

public interface SchedulerService {

	public List<SchedulerJobs> listSchedulers(boolean enabled);

	public void reScheduleJob(SchedulerJobs job) throws CensException;

	public void loadDefaultValues();

	public void reScheduleJobs() throws CensException;

	public String findCronExpressionForCronJob(String jobName)throws CensException;

	public void unScheduleJob(String jobName) throws CensException;

	public void scheduleJobs(SchedulerJobs job) throws CensException;


}
