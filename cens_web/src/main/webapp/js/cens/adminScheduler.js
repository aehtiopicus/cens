(function () {
	'use strict';
	cens.namespace("scheduler");	
	cens.scheduler =  (function () {
		var url = pagePath+"/api/scheduler";
		var _config = {							
			url : url,
			urlPut : url+"/",
			urlActivate : url+"/{id}/update"			
		}
				
		var createInstance = function () {		
			return publicAttribs;
		};		
		
					
		/**
		 * Call service to retrieve stored information
		 * @param category the category that is going to be retrieve. This attribute should match at least a category defined in the service. The attribute can fully match an attribute as well or just a subCategory
		 * @param callBack the call back function that will be invoke after getting server response. Its parameter will be whatever is returned in the service or false if the service call fails
		 */
		var _getSchedulerData = function(callBack){
			
			var promise = $.ajax({
				type:"GET",
				url:_config.url,
				dataType:"json",
				beforeSend: function(xhr){
					startSpinner();
				}
			});
			
			promise.always(function () {
				stopSpinner();
			}).done(function (data, textStatus, jqXHR) {				
				callBack(new cens.xhrResponse(false,data,textStatus,"success").response);
			}).fail(function (jqXHR, textStatus, errorThrown) {
				callBack(new cens.xhrResponse(true,jqXHR.responseJSON,textStatus,"").response);
			});
			
		};
		
		
			
		/**
		 * Save service call
		 * @param attributeName the service attribute name
		 * @param attributeData represent an optional parameter that will contain the attribute data.
		 * @param callBack an optional function to handle success or failure response.
		 */
		var _putSchedulerData = function(cronJob,callBack){
			
			var promise = $.ajax({
				        url: _config.urlPut+cronJob.id,
				        type: 'PUT',
				        dataType:'json' ,
				        contentType :'application/json',
				        data : JSON.stringify(cronJob),
				        beforeSend: function(xhr){
							startSpinner();
						}
			    });
			promise.always(function () {
				stopSpinner();
			}).done(function (data, textStatus, jqXHR) {				
				callBack(new cens.xhrResponse(false,data,textStatus,"success").response);
			}).fail(function (jqXHR, textStatus, errorThrown) {
				callBack(new cens.xhrResponse(true,jqXHR.responseJSON,textStatus,"").response);
			});
			
		};
		
		var _toggleScheduler = function(jobId,enabled,callBack){
			
			var promise = $.ajax({
				        url: _config.urlActivate.replace("{id}",jobId),
				        type: 'PUT',
				        dataType:'json' ,
				        contentType :'application/json',
				        data : JSON.stringify(enabled),
				        beforeSend: function(xhr){
							startSpinner();
						}
			    });
			promise.always(function () {
				stopSpinner();
			}).done(function (data, textStatus, jqXHR) {				
				callBack(new cens.xhrResponse(false,data,textStatus,"success").response);
			}).fail(function (jqXHR, textStatus, errorThrown) {
				callBack(new cens.xhrResponse(true,jqXHR.responseJSON,textStatus,"").response);
			});
			
		};
		
		var _restoreData = function(callBack){
			var promise = $.ajax({
				type:"PATCH",
				url:_config.url,
				dataType:"json",
				beforeSend: function(xhr){
					startSpinner();
				}
			});
			
			promise.always(function () {
				stopSpinner();
			}).done(function (data, textStatus, jqXHR) {				
				callBack(new cens.xhrResponse(false,data,textStatus,"success").response);
			}).fail(function (jqXHR, textStatus, errorThrown) {
				callBack(new cens.xhrResponse(true,jqXHR.responseJSON,textStatus,"").response);
			});
		}
		


		var publicAttribs =  {
			/**
			 * @param category the userData category. if it is null the default category nuDrive will be used.
			 */
			getData : function(callBack){														
					_getSchedulerData(callBack);							
			},					
			putSchedulerData : function(cronObject,callBack){
				
				if(cronObject){
					_putSchedulerData(cronObject,callBack);
				}
			},
			toggleActivated : function(jobId,enabled,callBack){
				_toggleScheduler(jobId,enabled,callBack);
			},
			
			restoreDefault : function(callBack){
				_restoreData(callBack);
			}

		};
		
		// Return object with static attributes
		return {
			/**
			 * 
			 * Get the current singleton instance.
			 * 
			 * @returns {Object} The singleton instance
			 */
			getInstance: function () {
				if(!_config.instance ){
					_config.instance = createInstance();
				}
				return _config.instance;
			}
			
		};
	})();
}());

cens.namespace("schedulerPanel");
cens.schedulerPanel = cens.makeClass();

cens.schedulerPanel.prototype.init = function(params){
	this.schedulerAccess = cens.scheduler.getInstance();
	$("#"+params.restoreButtonId).on("click",this.restoreDefault.bind(this));
};

cens.schedulerPanel.prototype.getInfo = function(){
	this.schedulerAccess.getData((function(xhrResponse){
		if(xhrResponse.fail){
			alert(xhrResponse.body.message,messageType.error);
		}else{
			xhrResponse.body.forEach((function(value,index){
				new cens.schedulerDiv(value,this.schedulerAccess );			
				
			}).bind(this));
			$("div[id^='scheduler_'] button[id^='actualizar_']").button("option", "disabled", true );
		}
	}).bind(this));
} 

cens.schedulerPanel.prototype.restoreDefault = function(){
	this.schedulerAccess.restoreDefault((function(data){
		if(data.fail){
			alert(xhrResponse.body.message,messageType.error);
		}else{
			$("#schedulerDiv div.schedulers").remove();
			this.getInfo();
		}
	}).bind(this));
}

cens.namespace("schedulerDiv");
cens.schedulerDiv = cens.makeClass();
cens.schedulerDiv.prototype.init = function(scheduler,schedulerAccess ){
	var schedulerAccess = schedulerAccess;	
	var assembleCron = function(){
		return scheduler.sec +" "+scheduler.min+" "+scheduler.hour+" "+scheduler.day+" "+scheduler.month+"";
	}
	this.schedulerStructure = {
			layout: '<div class="schedulers">'+
			'<h3 class="subtitulo chico" style="text-align: -webkit-left;">Tarea: <span class="estadoToken activo">'+scheduler.realName+'</span></h3>'+		
			'<div id="scheduler_'+scheduler.id+'">'+
				'<label>Expresi&oacute;n de tiempo de ejecuci&oacute;n</label>'+
				'<input type="text" id="sec_'+scheduler.id+'" value="'+scheduler.sec+'"/>'+
				'<input type="text" id="min_'+scheduler.id+'" value="'+scheduler.min+'"/>'+
				'<input type="text" id="hour_'+scheduler.id+'"value="'+scheduler.hour+'"/>'+
				'<input type="text" id="day_'+scheduler.id+'"value="'+scheduler.day+'"/>'+
				'<input type="text" id="month_'+scheduler.id+'"value="'+scheduler.month+'"/>'+
				'<button class="button right" type="button" id="activar_'+scheduler.id+'">'+(scheduler.enabled ? "Desactivar" : "Activar")+'</button>'+
				'<button class="button right" type="button" id="actualizar_'+scheduler.id+'">Actualizar</button>'+
			'</div>'+
			'<div>'+
				'<label class="estadoToken activo">'+scheduler.description+'</label>'+
			'</div>'+	
			'</div>',
			
	},
	this.scheduler = scheduler;
	$("#schedulerDiv").append(this.schedulerStructure.layout);
	$("#scheduler_"+this.scheduler.id+" button").button();
	$("#sec_"+scheduler.id).on('blur',(function(e,a,b,c){
		if(e.target.value == this.scheduler.sec){
			return;
		}
		var oldExp = this.scheduler.sec;
		this.scheduler.sec = e.target.value;
		if(!checkCronExp(assembleCron())){
			this.scheduler.sec = oldExp;
			$("#actualizar_"+scheduler.id).button("option", "disabled", true );
			$("#sec_"+scheduler.id).val(oldExp);
			e.preventDefault();
			e.stopPropagation();
		}else{
			$("#actualizar_"+scheduler.id).button("option", "disabled", false );
		}
				
	}).bind(this));
	$("#min_"+scheduler.id).on('blur',(function(e){
		if(e.target.value == this.scheduler.min){
			return;
		}
		var oldExp = this.scheduler.min;
		this.scheduler.min = e.target.value;
		if(!checkCronExp(assembleCron())){
			this.scheduler.min = oldExp;
			$("#actualizar_"+scheduler.id).button("option", "disabled", true );
			$("#min_"+scheduler.id).val(oldExp);
			e.preventDefault();
			e.stopPropagation();
		}else{
			$("#actualizar_"+scheduler.id).button("option", "disabled", false );
		}
	}).bind(this));
	$("#hour_"+scheduler.id).on('blur',(function(e){
		if(e.target.value == this.scheduler.hour){
			return;
		}
		var oldExp = this.scheduler.hour;
		this.scheduler.hour = e.target.value;
		if(!checkCronExp(assembleCron())){
			this.scheduler.hour = oldExp;
			$("#actualizar_"+scheduler.id).button("option", "disabled", true );
			$("#hour_"+scheduler.id).val(oldExp);
			e.preventDefault();
			e.stopPropagation();
		}else{
			$("#actualizar_"+scheduler.id).button("option", "disabled", false );
		}
	}).bind(this));
	$("#day_"+scheduler.id).on('blur',(function(e){
		if(e.target.value == this.scheduler.day){
			return;
		}
		var oldExp = this.scheduler.day;
		this.scheduler.day = e.target.value;
		if(!checkCronExp(assembleCron())){
			this.scheduler.day = oldExp;
			$("#actualizar_"+scheduler.id).button("option", "disabled", true );
			$("#day_"+scheduler.id).val(oldExp);
			e.preventDefault();
			e.stopPropagation();
		}else{
			$("#actualizar_"+scheduler.id).button("option", "disabled", false );
		}
	}).bind(this));
	$("#month_"+scheduler.id).on('blur',(function(e){
		if(e.target.value == this.scheduler.month){
			return;
		}
		var oldExp = this.scheduler.month;
		this.scheduler.month = e.target.value;
		if(!checkCronExp(assembleCron())){
			this.scheduler.month = oldExp;
			$("#actualizar_"+scheduler.id).button("option", "disabled", true );
			$("#month_"+scheduler.id).val(oldExp);
			e.preventDefault();
			e.stopPropagation();
		}else{
			$("#actualizar_"+scheduler.id).button("option", "disabled", false );
		}
	}).bind(this));
	
	$("#actualizar_"+scheduler.id).on('click',(function(e){		
		if(checkCronExp(assembleCron())){
			schedulerAccess.putSchedulerData(this.scheduler,function(e){
				if(e.fail){
					alert(e.body.message,messageType.error);
				}
			});
		}
	}).bind(this));
	
	$("#activar_"+scheduler.id).on('click',(function(e){
		schedulerAccess.toggleActivated(this.scheduler.id,!this.scheduler.enabled,(function(e){
			if(e.fail){
				alert(e.body.message,messageType.error);
			}else{
				this.scheduler.enabled = !this.scheduler.enabled;
				$("#activar_"+this.scheduler.id).button("option", "label",((this.scheduler.enabled ? "Desactivar" : "Activar")));
			}
		}).bind(this));
	}).bind(this));

}