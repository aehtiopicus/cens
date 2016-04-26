package com.aehtiopicus.cens.mapper.cens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.SchedulerJobs;
import com.aehtiopicus.cens.dto.cens.SchedulerJobsDto;
import com.aehtiopicus.cens.util.Utils;

@Component
public class SchedulerCensMapper {

	public SchedulerJobsDto convertSchedulerJobsToDto(SchedulerJobs job){
		SchedulerJobsDto dto = Utils.getMapper().map(job, SchedulerJobsDto.class);
		return dto;		
	}
	
	public SchedulerJobs convertSchedulerJobsDtoToEntity(SchedulerJobsDto jobDto){
		SchedulerJobs job =Utils.getMapper().map(jobDto, SchedulerJobs.class);
		return job;
		
	}

	public List<SchedulerJobsDto> convertSchedulerJobListToDtoList(
			List<SchedulerJobs> schedulerJobList) {
		List<SchedulerJobsDto> shcedulerJobDtoList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(schedulerJobList))
		for(SchedulerJobs job : schedulerJobList){
			shcedulerJobDtoList.add(convertSchedulerJobsToDto(job));
		}
		Collections.sort(shcedulerJobDtoList, new Comparator<SchedulerJobsDto>() {

			@Override
			public int compare(SchedulerJobsDto o1, SchedulerJobsDto o2) {
				return(int) (o1.getId() - o2.getId());
				
			}
		});
		return shcedulerJobDtoList;
	}
	
	public List<SchedulerJobs> convertSchedulerJobDtoListToEntityList(
			List<SchedulerJobsDto> schedulerJobDtoList) {
		List<SchedulerJobs> schedulerJobList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(schedulerJobDtoList))
		for(SchedulerJobsDto pDto : schedulerJobDtoList){
			schedulerJobList.add(convertSchedulerJobsDtoToEntity(pDto));
		}
		return schedulerJobList;
	}
}
