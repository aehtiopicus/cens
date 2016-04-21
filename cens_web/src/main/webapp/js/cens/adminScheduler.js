cens.namespace("xhrResponse");
cens.xhrResponse = cens.makeClass();
cens.xhrResponse.prototype.init = function(failure,body,textStatus,message){
	this.response = {
			fail : failure,
			body : body,
			message : message,
			textStatus : textStatus
		};
};
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
