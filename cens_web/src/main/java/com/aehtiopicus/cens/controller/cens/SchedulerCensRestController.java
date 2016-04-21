package com.aehtiopicus.cens.controller.cens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.dto.cens.SchedulerJobsDto;
import com.aehtiopicus.cens.mapper.cens.SchedulerCensMapper;
import com.aehtiopicus.cens.service.cens.SchedulerService;
import com.aehtiopicus.cens.utils.CensException;

@Controller
@RequestMapping(value=UrlConstant.SCHEDULER)
@Secured("ROLE_ASESOR")
public class SchedulerCensRestController extends AbstractRestController{

	@Autowired
	private SchedulerService schedulerService;
	
	@Autowired
	private SchedulerCensMapper mapper;
	
	@RequestMapping(method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> listAllSchedulers()throws CensException{		
		return new ResponseEntity<>(mapper.convertSchedulerJobListToDtoList(schedulerService.listAllSchedulers()),HttpStatus.OK);
	}
	
	@RequestMapping(value=UrlConstant.SCHEDULER_PUT, method=RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateScheduler(@PathVariable("id") Long id, @RequestBody(required=true) SchedulerJobsDto job)throws CensException{				
		return new ResponseEntity<>(schedulerService.updateAndScheduleJob(mapper.convertSchedulerJobsDtoToEntity(job)),HttpStatus.OK);
	}
	
	@RequestMapping(value=UrlConstant.SCHEDULER_PUT_TOGGLE, method=RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> toggleScheduler(@PathVariable("id") Long id,@RequestBody(required=true) Boolean enabled)throws CensException{				
		return new ResponseEntity<>(schedulerService.updateAndToggleScheduleJob(id,enabled),HttpStatus.OK);
	}
}
