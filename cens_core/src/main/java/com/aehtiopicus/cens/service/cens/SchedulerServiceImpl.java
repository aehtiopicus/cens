package com.aehtiopicus.cens.service.cens;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.aehtiopicus.cens.domain.entities.SchedulerJobs;
import com.aehtiopicus.cens.scheduler.SchedulerDefaultBean;
import com.aehtiopicus.cens.utils.CensException;


public class SchedulerServiceImpl implements SchedulerService {

	private static final Logger log = LoggerFactory.getLogger(SchedulerServiceImpl.class);


	@PersistenceContext
	private EntityManager entityManager;
	
	private static final String SCHEDULER_UN_READ_JOB_NAME = "un_read_job";
	private static final String SCHEDULER_GENERAL_NOTIFICATION_JOB_NAME = "general_notification_job";
	private static final String SCHEDULER_TOKEN_FB_NAME = "token_fb";
	private static final String SCHEDULER_EDITION_TIME_NAME = "tiempo_edicion";
	
	@Autowired
	@Qualifier(SCHEDULER_UN_READ_JOB_NAME)
	private CronTriggerFactoryBean unreadNotificationCron;

	@Autowired
	@Qualifier(SCHEDULER_TOKEN_FB_NAME)
	private CronTriggerFactoryBean facebookTokenCron;

	@Autowired
	@Qualifier(SCHEDULER_GENERAL_NOTIFICATION_JOB_NAME)
	private CronTriggerFactoryBean generalNotificationCron;
	

	@Autowired
	private ApplicationContext appContext;
	

	private Map<String,SchedulerDefaultBean> defaultProperties;
		

	public void setDefaultProperties(Map<String, SchedulerDefaultBean> defaultProperties) {
		this.defaultProperties = defaultProperties;
	}
	
	public void postConstruct() {
		SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
		defaultProperties.get(SCHEDULER_UN_READ_JOB_NAME).setCronTrigger(unreadNotificationCron);
		defaultProperties.get(SCHEDULER_TOKEN_FB_NAME).setCronTrigger(facebookTokenCron);
		defaultProperties.get(SCHEDULER_GENERAL_NOTIFICATION_JOB_NAME).setCronTrigger(generalNotificationCron);

		List<CronTrigger> cronTriggers = new ArrayList<>();
		
		scheduleIniti(SCHEDULER_UN_READ_JOB_NAME,cronTriggers);
		scheduleIniti(SCHEDULER_TOKEN_FB_NAME,cronTriggers);
		scheduleIniti(SCHEDULER_GENERAL_NOTIFICATION_JOB_NAME,cronTriggers);

		schedulerFactory.setTriggers(cronTriggers.toArray(new CronTrigger[cronTriggers.size()]));

		AutowireCapableBeanFactory bf = this.appContext.getAutowireCapableBeanFactory();
		bf.initializeBean(schedulerFactory, "schedulerFactory");
		ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) appContext).getBeanFactory();
		beanFactory.registerSingleton(schedulerFactory.getClass().getCanonicalName(), schedulerFactory);
	}
	
	private void scheduleIniti(String scheduleJobName,List<CronTrigger> cronTriggers){
		try{
			updateCronBeans(scheduleJobName);
			cronTriggers.add(defaultProperties.get(scheduleJobName).getCronTrigger().getObject());
		}catch(Exception e){	
		}
	}

	private SchedulerFactoryBean getSchedulerFactory() {
		return appContext.getBean(SchedulerFactoryBean.class);
	}

	private void updateCronBeans(String beanName) throws CensException {
		try {
			String cronExp = findCronExpressionForCronJob(beanName);
			((CronTriggerImpl) appContext.getBean(beanName)).setCronExpression(cronExp);

			defaultProperties.get(beanName).getCronTrigger().setCronExpression(cronExp);
			defaultProperties.get(beanName).getCronTrigger().afterPropertiesSet();			
		} catch (BeansException e) {
			log.error("bean error", e);
			throw new CensException("No se puede encontrar el job",e);
		} catch (ParseException e) {
			log.error("bean parse error", e);
			throw new CensException("No se puede convertir el valor del cron",e);
		} catch (CensException e) {
			throw e;
		}		
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
			if (defaultProperties.get(jobName).getActive()) {
				return defaultProperties.get(jobName).getCronExp();
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
					java.lang.reflect.Field f = (defaultProperties.get(job.getJobName()).getCronTrigger()).getClass()
							.getDeclaredField("jobDetail");
					f.setAccessible(true);
					schedulerFactory.getScheduler().scheduleJob(
							(JobDetail) f.get(defaultProperties.get(job.getJobName()).getCronTrigger()),
							defaultProperties.get(job.getJobName()).getCronTrigger().getObject());
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
		if (schedulerJobs.size() < defaultProperties.size()) {
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
			for (Entry<String, SchedulerDefaultBean> entry : defaultProperties.entrySet()) {
				boolean found = false;
				for (SchedulerJobs job : jobs) {
					if (job.getJobName().equals(entry.getKey())) {
						found = true;
						updateEntry(job, entry.getValue(), defaultProperties.get(job.getJobName()).getActive());
						break;
					}
				}
				if (!found) {
					createNewEntry(entry.getValue());
				}
			}
		} else {
			for (Entry<String, SchedulerDefaultBean> entry : defaultProperties.entrySet()) {
				createNewEntry(entry.getValue());
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

	private void createNewEntry(SchedulerDefaultBean bean) {
		SchedulerJobs job = generateSchedulerJobsFromFromCronExpression(bean.getName(), bean.getCronExp());
		job.setDescription(bean.getDescription());
		job.setRealName(bean.getRealName());
		entityManager.persist(job);
	}

	private void updateEntry(SchedulerJobs job, SchedulerDefaultBean bean, boolean enabled) {
		job.setEnabled(enabled);
		job = generateSchedulerJobsFromFromCronExpression(job, job.getJobName(), bean.getCronExp());
		job.setRealName(bean.getRealName());
		job.setDescription(bean.getDescription());
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
