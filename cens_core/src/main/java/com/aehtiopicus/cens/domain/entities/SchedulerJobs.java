package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="cens_scheduler_jobs")
@NamedQueries({
	@NamedQuery(name=SchedulerJobs.ALL_SCHEDULERS, query="SELECT s FROM SchedulerJobs s"),
	@NamedQuery(name=SchedulerJobs.ACTIVE_SCHEDULERS, query="SELECT s FROM SchedulerJobs s WHERE s.jobModify = true"),
	@NamedQuery(name=SchedulerJobs.ONE_SCHEDULER, query = "SELECT s FROM SchedulerJobs s WHERE s.jobName = :jobName")
})

public class SchedulerJobs implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6133788110773819011L;
	
	public static final String ACTIVE_SCHEDULERS = "find.schedulers";
	public static final String ALL_SCHEDULERS = "find.active.schedulers";
	public static final String ONE_SCHEDULER = "find.one.schedulers";
	public static final String ONE_SCHEDULER_FIRST_PARAM = "jobName";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="job_name")
	private String jobName = "*";
	
	@Column(name="job_sec")
	private String sec = "*";
	
	@Column(name="job_min")
	private String min = "*";
	
	@Column(name="job_hour")
	private String hour = "*";
	
	@Column(name="job_day")
	private String day = "*";
	
	@Column(name="job_month")
	private String month = "*";
	
	@Column(name="job_enabled",columnDefinition="boolean default false")
	private boolean enabled = false;
	
	@Column (name="job_modify",columnDefinition="boolean default false")
	private boolean jobModify = false;
	
	@Column(name="job_real_name")
	private String realName;
	
	@Column(name="job_description")
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getSec() {
		return sec;
	}

	public void setSec(String sec) {
		this.sec = sec;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isJobModify() {
		return jobModify;
	}

	public void setJobModify(boolean jobModify) {
		this.jobModify = jobModify;
	}
	
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString(){
		return this.sec+" "+this.min+" "+this.hour+" "+this.day+" "+this.month+" ?";
	}
	
}
