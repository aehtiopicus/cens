package com.aehtiopicus.cens.service.cens;

import java.text.ParseException;
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
import org.springframework.context.ApplicationContext;
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

	private static final Logger log = LoggerFactory.getLogger(SchedulerServiceImpl.class);

	@Value(SCHEDULER_UN_READ_JOB)
	private String unRead;

	@Value(SCHEDULER_GENERAL_NOTIFICATION_JOB)
	private String generalNotification;

	@Value(SCHEDULER_TOKEN_FB)
	private String fbTokenRefreshExpression;

	@Value(SCHEDULER_EDITION_TIME)
	private String editionTime;

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

	@Autowired
	private ApplicationContext appContext;

	private SchedulerFactoryBean schedulerFactory;

	@PostConstruct
	public void postConstruct() {
		defaultJobs = ImmutableMap.of(SCHEDULER_UN_READ_JOB_NAME, unRead, SCHEDULER_GENERAL_NOTIFICATION_JOB_NAME,
				generalNotification, SCHEDULER_TOKEN_FB_NAME, fbTokenRefreshExpression, SCHEDULER_EDITION_TIME_NAME,
				editionTime);
		schedulerFactory = new SchedulerFactoryBean();

		updateCronBeans(SCHEDULER_UN_READ_JOB_NAME);
		updateCronBeans(SCHEDULER_TOKEN_FB_NAME);
		updateCronBeans(SCHEDULER_GENERAL_NOTIFICATION_JOB_NAME);

		schedulerFactory.setTriggers(generalNotificationCron.getObject(), unreadNotificationCron.getObject(),
				facebookTokenCron.getObject());
		AutowireCapableBeanFactory bf = this.appContext.getAutowireCapableBeanFactory();
		bf.initializeBean(schedulerFactory, "schedulerFactory");
	}

	private void updateCronBeans(String beanName) {
		try {
			((CronTriggerImpl) appContext.getBean(beanName)).setCronExpression(findCronExpressionForCronJob(beanName));
		} catch (BeansException e) {
			log.error("bean error",e);
		} catch (ParseException e) {
			log.error("bean parse error",e);

		}
	}

	@Override
	public String findCronExpressionForCronJob(String jobName) {
		try {
			SchedulerJobs job = entityManager.createNamedQuery(SchedulerJobs.ONE_SCHEDULER, SchedulerJobs.class)
					.setParameter(SchedulerJobs.ONE_SCHEDULER_FIRST_PARAM, jobName).getSingleResult();
			return job.toString();
		} catch (Exception e) {
			log.info("error loading schedule job", e);
			return defaultJobs.get(jobName);
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
		try {
			if (job.isEnabled()) {
				Scheduler scheduler = schedulerFactory.getScheduler();
				TriggerKey triggerKey = new TriggerKey(job.getJobName());
				CronTriggerImpl trigger = (CronTriggerImpl) scheduler.getTrigger(triggerKey);
				trigger.setCronExpression(new CronExpression(job.toString()));
				scheduler.rescheduleJob(triggerKey, trigger);
			}
		} catch (Exception e) {
			throw new CensException("Cannot update Job " + job.getJobName(), e);
		}
	}

	@Override
	public List<SchedulerJobs> listSchedulers(boolean enabled) {
		return entityManager.createNamedQuery(enabled ? SchedulerJobs.ALL_SCHEDULERS : SchedulerJobs.ACTIVE_SCHEDULERS,
				SchedulerJobs.class).getResultList();
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
						updateEntry(job, entry.getValue());
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

	private void createNewEntry(String jobName, String cron) {
		SchedulerJobs job = new SchedulerJobs();
		job.setJobName(jobName);
		String cronArray[] = cron.split(" ");
		job.setSec(cronArray[0]);
		job.setMin(cronArray[1]);
		job.setHour(cronArray[2]);
		job.setDay(cronArray[3]);
		job.setMonth(cronArray[4]);
		entityManager.persist(job);
	}

	private void updateEntry(SchedulerJobs job, String cron) {
		job.setEnabled(false);
		String cronArray[] = cron.split(" ");
		job.setSec(cronArray[0]);
		job.setMin(cronArray[1]);
		job.setHour(cronArray[2]);
		job.setDay(cronArray[3]);
		job.setMonth(cronArray[4]);
		entityManager.merge(job);

	}

}
