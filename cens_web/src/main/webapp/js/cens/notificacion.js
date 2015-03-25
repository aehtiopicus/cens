
jQuery(document).ready(function () {
	
	
	var data = new localstorage.ls.notificacionData();
	if(!data.getNotificacion()){
		$.ajax({
			url: pagePath+"/mvc/notificacionData",
			type: "GET",	    	    
			contentType :'application/json',
			dataType: "json",    
			success : function(result){
				data.setNotificacion(result);
				loadNotificationInformation();
				
			},
			error: function(value){
			
				alert("Se produjo un error el servidor");

			}
		});
	}
		
});

function notificationLoder(){
	alert(ls);
}

function loadNotificationInformation(){
	
	var item = JSON.parse(new localstorage.ls.notificacionData().getNotificacion()).item;
	item.notificacionLoader = notificacionLoader;
	var ls = new localstorage.ls.notificacion(item);
	if(ls.isRefreshRequired()){
		ls.getNotificacionData();
		setTimeout(loadNotificationInformation,1000);
		
	}else{
		return ls.getNotificacionData();
	}
	
}

function notificacionLoader(callback){
	
	
	$.ajax({
		url: pagePath+"/mvc/notificacionData",
		type: "GET",	    	    
		contentType :'application/json',
		dataType: "json",    
		success : function(result){
			callback.setNotificacion(result);			
		},
		error: function(value){			
			alert("Se produjo un error el servidor");
			return "error";

		}
	});
}