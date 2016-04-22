package com.aehtiopicus.cens.scheduler;

import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

public class SchedulerDefaultBean {

	private String name;
	private String realName;
	private String cronExp;
	private Boolean active;
	private String description;
	private CronTriggerFactoryBean cronTrigger;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getCronExp() {
		return cronExp;
	}
	public void setCronExp(String cronExp) {
		this.cronExp = cronExp;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public CronTriggerFactoryBean getCronTrigger() {
		return cronTrigger;
	}
	public void setCronTrigger(CronTriggerFactoryBean cronTrigger) {
		this.cronTrigger = cronTrigger;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public SchedulerDefaultBean(String name, String realName,String cronExp, Boolean active,String description) {
		super();
		this.name = name;
		this.realName = realName;
		this.cronExp = cronExp;
		this.active = active;
		this.description = description;
	}
	
	
	
}
