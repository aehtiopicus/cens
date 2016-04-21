package com.aehtiopicus.cens.service.cens;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.SchedulerJobs;
import com.aehtiopicus.cens.utils.CensException;
import com.google.common.collect.ImmutableMap;

@Service
public class SchedulerServiceImpl implements SchedulerService {

	private static final String SCHEDULER_UN_READ_JOB = "#{schedulerProperties['un_read_job']}";

	private static final String SCHEDULER_GENERAL_NOTIFICATION_JOB = "#{schedulerProperties['general_notification_job']}";

	private static final String SCHEDULER_TOKEN_FB = "#{schedulerProperties['token_fb']}";

	private static final String SCHEDULER_EDITION_TIME = "#{schedulerProperties['tiempo_edicion']}";

	private static final String SCHEDULER_UN_READ_JOB_NAME = "un_read_job";
	private static final String SCHEDULER_GENERAL_NOTIFICATION_JOB_NAME = "general_notification_job";
	private static final String SCHEDULER_TOKEN_FB_NAME = "token_fb";
	private static final String SCHEDULER_EDITION_TIME_NAME = "tiempo_edicion";

	private static final String SCHEDULER_UN_READ_JOB_ACTIVE = "#{schedulerProperties['un_read_job_activated']}";
	private static final String SCHEDULER_GENERAL_NOTIFICATION_JOB_ACTIVE = "#{schedulerProperties['general_notification_job_activated']}";
	private static final String SCHEDULER_TOKEN_FB_ACTIVE = "#{schedulerProperties['token_fb_activated']}";
	private static final String SCHEDULER_EDITION_ACTIVE = "#{schedulerProperties['tiempo_edicion_activated']}";

	private static final Logger log = LoggerFactory.getLogger(SchedulerServiceImpl.class);

	@Value(SCHEDULER_UN_READ_JOB)
	private String unRead;

	@Value(SCHEDULER_GENERAL_NOTIFICATION_JOB)
	private String generalNotification;

	@Value(SCHEDULER_TOKEN_FB)
	private String fbTokenRefreshExpression;

	@Value(SCHEDULER_EDITION_TIME)
	private String editionTime;

	@Value(SCHEDULER_UN_READ_JOB_ACTIVE)
	private Boolean unReadActive;

	@Value(SCHEDULER_GENERAL_NOTIFICATION_JOB_ACTIVE)
	private Boolean generalNotificationActive;

	@Value(SCHEDULER_TOKEN_FB_ACTIVE)
	private Boolean fbTokenRefreshExpressionActive;

	@Value(SCHEDULER_EDITION_ACTIVE)
	private Boolean editionTimeActive;

	@Autowired
	@Qualifier(SCHEDULER_UN_READ_JOB_NAME)
	private CronTriggerFactoryBean unreadNotificationCron;

	@Autowired
	@Qualifier(SCHEDULER_TOKEN_FB_NAME)
	private CronTriggerFactoryBean facebookTokenCron;

	@Autowired
	@Qualifier(SCHEDULER_GENERAL_NOTIFICATION_JOB_NAME)
	private CronTriggerFactoryBean generalNotificationCron;

	@PersistenceContext
	private EntityManager entityManager;

	private Map<String, String> defaultJobs;
	private Map<String, Boolean> defaultJobsActive;
	private Map<String, CronTriggerFactoryBean> cronTriggersMap;

	@Autowired
	private ApplicationContext appContext;

	@PostConstruct
	public void postConstruct() {
		SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
		defaultJobs = ImmutableMap.of(SCHEDULER_UN_READ_JOB_NAME, unRead, SCHEDULER_GENERAL_NOTIFICATION_JOB_NAME,
				generalNotification, SCHEDULER_TOKEN_FB_NAME, fbTokenRefreshExpression, SCHEDULER_EDITION_TIME_NAME,
				editionTime);

		defaultJobsActive = ImmutableMap.of(SCHEDULER_UN_READ_JOB_NAME, unReadActive,
				SCHEDULER_GENERAL_NOTIFICATION_JOB_NAME, generalNotificationActive, SCHEDULER_TOKEN_FB_NAME,
				fbTokenRefreshExpressionActive, SCHEDULER_EDITION_TIME_NAME, editionTimeActive);

		cronTriggersMap = ImmutableMap.of(SCHEDULER_UN_READ_JOB_NAME, unreadNotificationCron,
				SCHEDULER_GENERAL_NOTIFICATION_JOB_NAME, generalNotificationCron, SCHEDULER_TOKEN_FB_NAME,
				facebookTokenCron);

		List<CronTrigger> cronTriggers = new ArrayList<>();

		if (updateCronBeans(SCHEDULER_UN_READ_JOB_NAME)) {
			cronTriggers.add(cronTriggersMap.get(SCHEDULER_UN_READ_JOB_NAME).getObject());
		}
		if (updateCronBeans(SCHEDULER_TOKEN_FB_NAME)) {
			cronTriggers.add(cronTriggersMap.get(SCHEDULER_TOKEN_FB_NAME).getObject());
		}
		if (updateCronBeans(SCHEDULER_GENERAL_NOTIFICATION_JOB_NAME)) {
			cronTriggers.add(cronTriggersMap.get(SCHEDULER_GENERAL_NOTIFICATION_JOB_NAME).getObject());
		}

		schedulerFactory.setTriggers(cronTriggers.toArray(new CronTrigger[cronTriggers.size()]));

		AutowireCapableBeanFactory bf = this.appContext.getAutowireCapableBeanFactory();
		bf.initializeBean(schedulerFactory, "schedulerFactory");
		ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) appContext).getBeanFactory();
		beanFactory.registerSingleton(schedulerFactory.getClass().getCanonicalName(), schedulerFactory);
	}

	private SchedulerFactoryBean getSchedulerFactory() {
		return appContext.getBean(SchedulerFactoryBean.class);
	}

	private boolean updateCronBeans(String beanName) {
		try {
			String cronExp = findCronExpressionForCronJob(beanName);
			((CronTriggerImpl) appContext.getBean(beanName)).setCronExpression(cronExp);

			cronTriggersMap.get(beanName).setCronExpression(cronExp);
			cronTriggersMap.get(beanName).afterPropertiesSet();
			return true;
		} catch (BeansException e) {
			log.error("bean error", e);
		} catch (ParseException e) {
			log.error("bean parse error", e);
		} catch (CensException e) {
		}
		return false;
	}

	@Override
	public void unScheduleJob(String jobName) throws CensException {
		SchedulerFactoryBean schedulerFactory = getSchedulerFactory();
		try {
			schedulerFactory.stop();
			Scheduler scheduler = schedulerFactory.getScheduler();
			scheduler.unscheduleJob(new TriggerKey(jobName));
		} catch (Exception e) {
			log.error("Not able to unschedule job " + jobName, e);
			throw new CensException("Not able to unschedule job " + jobName);
		} finally {
			if (!schedulerFactory.isRunning()) {
				schedulerFactory.start();
			}
		}
	}

	@Override
	public String findCronExpressionForCronJob(String jobName) throws CensException {
		try {
			SchedulerJobs job = entityManager.createNamedQuery(SchedulerJobs.ONE_SCHEDULER, SchedulerJobs.class)
					.setParameter(SchedulerJobs.ONE_SCHEDULER_FIRST_PARAM, jobName).getSingleResult();
			if (job.isJobModify() && !job.isEnabled()) {
				throw new CensException();
			}
			if (!job.isEnabled()) {
				throw new Exception();
			}
			return job.toString();
		} catch (CensException e) {
			throw e;
		} catch (Exception e) {
			log.info("error loading schedule job");
			if (defaultJobsActive.get(jobName)) {
				return defaultJobs.get(jobName);
			} else {
				throw new CensException("disabled job");
			}
		}
	}

	@Override
	public void reScheduleJobs() throws CensException {
		List<SchedulerJobs> jobList = listSchedulers(true);
		if (CollectionUtils.isNotEmpty(jobList)) {
			for (SchedulerJobs job : jobList) {
				try {
					reScheduleJob(job);
				} catch (CensException e) {
					log.error("jobs not updated", e);
				}
			}
		}
	}

	@Override
	public void reScheduleJob(SchedulerJobs job) throws CensException {
		if (job.isEnabled()) {
			try {

				Scheduler scheduler = getSchedulerFactory().getScheduler();
				TriggerKey triggerKey = new TriggerKey(job.getJobName());
				CronTriggerImpl trigger = (CronTriggerImpl) scheduler.getTrigger(triggerKey);
				trigger.setCronExpression(new CronExpression(job.toString()));
				scheduler.rescheduleJob(triggerKey, trigger);

			} catch (Exception e) {
				throw new CensException("Cannot update Job " + job.getJobName(), e);
			}
		}
	}

	@Override
	@Transactional(rollbackOn = CensException.class)
	public SchedulerJobs updateAndScheduleJob(SchedulerJobs jobs) throws CensException {
		try {
			jobs.setJobModify(true);
			entityManager.merge(jobs);
			reScheduleJob(jobs);
			return jobs;
		} catch (Exception e) {
			log.error("unable to save", e);
			throw new CensException("No se pudo actualiar el job");
		}
	}

	@Override
	public void scheduleJobs(SchedulerJobs job) throws CensException {
		if (job.isEnabled()) {
			SchedulerFactoryBean schedulerFactory = getSchedulerFactory();
			try {

				schedulerFactory.stop();
				Scheduler scheduler = schedulerFactory.getScheduler();
				TriggerKey triggerKey = new TriggerKey(job.getJobName());
				if (scheduler.getTrigger(triggerKey) != null) {
					this.reScheduleJob(job);
				} else {
					updateCronBeans(job.getJobName());
					java.lang.reflect.Field f = (cronTriggersMap.get(job.getJobName())).getClass()
							.getDeclaredField("jobDetail");
					f.setAccessible(true);
					schedulerFactory.getScheduler().scheduleJob(
							(JobDetail) f.get(cronTriggersMap.get(job.getJobName())),
							cronTriggersMap.get(job.getJobName()).getObject());
				}
			} catch (CensException e) {
				throw e;
			} catch (Exception e) {
				throw new CensException("Cannot update Job " + job.getJobName(), e);
			} finally {
				if (!schedulerFactory.isRunning()) {
					schedulerFactory.start();
				}
			}
		}
	}

	@Override
	public List<SchedulerJobs> listSchedulers(boolean enabled) {
		return entityManager.createNamedQuery(enabled ? SchedulerJobs.ALL_SCHEDULERS : SchedulerJobs.ACTIVE_SCHEDULERS,
				SchedulerJobs.class).getResultList();
	}

	@Override
	@Transactional
	public List<SchedulerJobs> listAllSchedulers() {
		List<SchedulerJobs> schedulerJobs = listSchedulers(true);
		if (schedulerJobs.size() < defaultJobs.size()) {
			loadDefaultValues();
			schedulerJobs = listSchedulers(true);
		}
		return schedulerJobs;

	}

	@Override
	@Transactional
	public void loadDefaultValues() {
		Collection<SchedulerJobs> jobs = listSchedulers(false);
		if (CollectionUtils.isNotEmpty(jobs)) {
			for (Entry<String, String> entry : defaultJobs.entrySet()) {
				boolean found = false;
				for (SchedulerJobs job : jobs) {
					if (job.getJobName().equals(entry.getKey())) {
						found = true;
						updateEntry(job, entry.getValue(), defaultJobsActive.get(job.getJobName()));
						break;
					}
				}
				if (!found) {
					createNewEntry(entry.getKey(), entry.getValue());
				}
			}
		} else {
			for (Entry<String, String> entry : defaultJobs.entrySet()) {
				createNewEntry(entry.getKey(), entry.getValue());
			}
		}
	}

	private SchedulerJobs generateSchedulerJobsFromFromCronExpression(SchedulerJobs jobs, String jobName, String cron) {
		SchedulerJobs job = jobs == null ? new SchedulerJobs() : jobs;
		job.setJobName(jobName);
		String cronArray[] = cron.split(" ");
		job.setSec(cronArray[0]);
		job.setMin(cronArray[1]);
		job.setHour(cronArray[2]);
		job.setDay(cronArray[3]);
		job.setMonth(cronArray[4]);
		job.setJobModify(true);
		return job;
	}

	private SchedulerJobs generateSchedulerJobsFromFromCronExpression(String jobName, String cron) {
		return generateSchedulerJobsFromFromCronExpression(null, jobName, cron);
	}

	private void createNewEntry(String jobName, String cron) {
		SchedulerJobs job = generateSchedulerJobsFromFromCronExpression(jobName, cron);
		entityManager.persist(job);
	}

	private void updateEntry(SchedulerJobs job, String cron, boolean enabled) {
		job.setEnabled(enabled);
		job = generateSchedulerJobsFromFromCronExpression(job, job.getJobName(), cron);
		entityManager.merge(job);

	}

	@Override
	@Transactional(rollbackOn = CensException.class)
	public SchedulerJobs updateAndToggleScheduleJob(Long id, Boolean enabled) throws CensException {
		try {
			SchedulerJobs job = entityManager.find(SchedulerJobs.class, id);
			if (job == null) {
				throw new CensException("Job no encontrado " + id);
			}
			job.setJobModify(true);
			job.setEnabled(enabled);
			entityManager.merge(job);
			if(enabled){
				scheduleJobs(job);
			}else{
				unScheduleJob(job.getJobName());
			}			
			return job;
		} catch (CensException e) {
			throw e;
		} catch (Exception e) {
			log.error("unable to update scheduler " + id, e);
			throw new CensException("Nose puede activar/desactivar el job");
		}
	}

}
