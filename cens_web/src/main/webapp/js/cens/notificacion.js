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
				loadNotificationInformation();
				
			},
			error: function(value){
			
				alert("Se produjo un error el servidor");

			}
		});
	}
	
	$('#headerUsername').on("click",openDialog);
	
	$('#closeButton').on("click",function(){
		for(var key in localStorage) {
			localStorage.removeItem(key);
		}	
		
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
	var data = loadNotificationInformation();
	processData(data);
	 $( "#notificacionDeUsuario" ).dialog("open");
}

function processData(data){
	
	estadoActividad(data.item.actividad,data.item.perfilRol);
}

function estadoActividad(actividad,perfil){
	var title = $('<h3 class="subtitulo"></h3>');	
	title.html("Estado de Actividad");
	var estadoActividad = $('<div></div>');	
	
	curso(actividad.curso,perfil);
}

function curso(cursos,perfil){
	$.each(cursos,function(index,curso){
		var titleSpan = $('<span class="cursoFont"></span>');
		titleSpan.html(curso.nombre);
		asigantura(curso,curso.asignatura,perfil);
	});
}

function asignatura(curso,asiganturas){
	
	
}

function loadNotificationInformation(){
	
	var item = JSON.parse(new localstorage.ls.notificacionData().getNotificacion()).item;
	item.notificacionLoader = notificacionLoader;
	if(!ls){
		ls = new localstorage.ls.notificacion(item);
	}
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