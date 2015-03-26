
jQuery(document).ready(function () {
	
	
	var data = new localstorage.ls.notificacionData();
	data.removeIfNeeded();
	if(!data.getNotificacion()){
		$.ajax({
			url: pagePath+"/api/notificacion/miembro",
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
	
	$('#headerUsername').on("click",openDialog);
	
	$('#closeButton').on("click",function(){
		var lsData = new localstorage.ls.notificacionData();
		lsData.remove();				
		var ls = new localstorage.ls.notificacion(JSON.parse(lsData.getNotificacion()).item);
		ls.remove();
	});
	
	 $( "#notificacionDeUsuario" ).dialog({
			autoOpen: false,
			width: 400,
			modal:true,
			buttons: [
				{
					text: "Ok",
					click: function() {						
					}
				},
				{
					text: "Cancelar",
					click: function() {
						$( this ).dialog( "close" );						
					}
				}
			]
		});
		
});

function openDialog(){
	loadNotificationInformation();
	 $( "#notificacionDeUsuario" ).dialog("open");
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
		url: pagePath+"/api/notificacion/miembro/"+callback.miembroId,
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