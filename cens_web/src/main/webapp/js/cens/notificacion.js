var ls;
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
				$('#headerImg').attr("src",pagePath+"/api/usuario/"+result.userId+"/picture");//fix me
				loadNotificationInformation();
				
			},
			error: function(value,xhr){
				
				alert("Se produjo un error el servidor");

			}
		});
	}
	
	$('#headerUsername').on("click",openDialog);
	
	$('#segAct').on("click",loadSeguimiento);
	
	$('#closeButton').on("click",function(){
		for(var key in localStorage) {
			localStorage.removeItem(key);
		}	
		
	});
	
	 $( "#notificacionDeUsuario" ).dialog({
			autoOpen: false,
			width: 640,
			close : function(){
				 
				if($("#seguimientoActvidad").hasClass("ui-tabs-panel")){
					$( "#tabs" ).tabs("destroy");
				}
			},			
			maxHeight: $(window).height(),
			modal:true,
			buttons: [
				{
					text: "Cerrar",
					click: function() {
						$( this ).dialog( "close" );						
					}
				}
			]
		});
	 $("#notificacionDeUsuario").on("close",function(){alert("d");});
		
});
function loadSeguimiento(){
	
$(document).bind("seguimientoEnabled",function(){
		
		$.ajax({
			url: pagePath+"/api/notificacion/miembro/"+JSON.parse(new localstorage.ls.notificacionData().getNotificacion()).item.miembroId+"/asesor",
			type: "GET",	    	    
			contentType :'application/json',
			dataType: "json",    
			success : function(result){
				processSeguimientoData(result);
			},
			error: function(value,xhr){
				if(xhr === "error"){
					location.href = location.href;
				}
				alert("Se produjo un error el servidor");
				return "error";

			}
		});
	}); 
	
}
function openDialog(){
	
	loadSeguimiento();
	loadNotificationInformation(processNotificacionData);		 		
	 
}


function loadNotificationInformation(processNotificacionData){
	var lsData = new localstorage.ls.notificacionData().getNotificacion();
	if(lsData){
		var item = JSON.parse(new localstorage.ls.notificacionData().getNotificacion()).item;
		item.notificacionLoader = notificacionLoader;
		if(!ls){
			ls = new localstorage.ls.notificacion(item);
		}
		if(ls.isRefreshRequired()){
			ls.getNotificacionData();
			setTimeout(loadNotificationInformation,1000,processNotificacionData);
		
		}else{
			if( typeof processNotificacionData === 'function'){
				processNotificacionData(ls.getNotificacionData());
			}else{
				return ls.getNotificacionData();
			}
		}
	}else{
		location.href = location.href;
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
		error: function(value,xhr){
			if(xhr === "error"){
				location.href = location.href;
			}
			alert("Se produjo un error el servidor");
			return "error";

		}
	});
}