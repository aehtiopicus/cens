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

cens.schedulerPanel.prototype.init = function(){
	this.schedulerAccess = cens.scheduler.getInstance();	
	this.schedulerElements = [];
	
};

cens.schedulerPanel.prototype.getInfo = function(){
	this.schedulerAccess.getData(this.loadInfo.bind(this));
} 

cens.schedulerPanel.prototype.loadInfo = function(xhrResponse){
	if(xhrResponse.fail){
		alert(xhrResponse.fail.body,messageType.error);
	}else{
		xhrResponse.body.forEach((function(value,index){
			this.schedulerElements.push(new cens.schedulerDiv(value));
		}).bind(this));
	}
}

cens.namespace("schedulerDiv");
cens.schedulerDiv = cens.makeClass();
cens.schedulerDiv.prototype.init = function(scheduler){
	
	this.schedulerStructure = {
			layout: '<div class="schedulers">'+
			'<h3 class="subtitulo chico" style="text-align: -webkit-left;">Tarea: <span class="estadoToken activo" id="">Activo</span></h3>'+		
			'<div>'+
				'<label>Expresi&oacute;n de tiempo de ejecuci&oacute;n</label>'+
				'<input type="text" id="sec"/>'+
				'<input type="text" id="min"/>'+
				'<input type="text" id="hour"/>'+
				'<input type="text" id="day"/>'+
				'<input type="text" id="mont"/>'+
				'<button class="button right" type="button" id="actualizar">Actualizar</button>'+
				'<button class="button right" type="button" id="activar">Activar</button>'+
			'</div>'+
			'<div>'+
				'<label class="estadoToken activo">bla bla bla bla bla</label>'+
			'</div>'+	
			'</div>',
			
	}
}
