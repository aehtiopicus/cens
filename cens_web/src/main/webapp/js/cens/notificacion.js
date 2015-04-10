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
				$(document).trigger("userImg");
			},
			error: function(value,xhr){
				
				alert("Se produjo un error el servidor");

			}
		});
	}else{
		$(document).trigger("userImg");
		setCantNotificacion();
	}
	
	$('#notificacionOpen').on("click",function(){
		openDialog("noti");
	});
	$('#seguimientoOpen').on("click",function(){
		openDialog("segui");
	});
		
	
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

function openDialog(noti){
	
	
	$('#cerrarIam').trigger("click");
	loadNotificationInformation(processNotificacionData,noti);		 		
	
}


function loadNotificationInformation(processNotificacionData,noti){
	loadLs();
		if(ls.isRefreshRequired()){
			ls.getNotificacionData();
			setTimeout(loadNotificationInformation,1000,processNotificacionData,noti);
		
		}else{									
			setCantNotificacion();
			if( typeof processNotificacionData === 'function'){
				processNotificacionData(ls.getNotificacionData(),noti);
			}else{
				return ls.getNotificacionData();
			}
		}
	
	
}

function loadLs(){
	var lsData = new localstorage.ls.notificacionData().getNotificacion();
	if(lsData){
		var item = JSON.parse(lsData).item;
		item.notificacionLoader = notificacionLoader;
		if(!ls){
			ls = new localstorage.ls.notificacion(item);
		}
	}else{
		location.href = location.href;
	}
}

function setCantNotificacion(){
	loadLs();
	var data = ls.getNotificacionData();
	
	enableBubble(data[0],"notCant",true)
	if(data.length == 2){
		$('#seguimientoOpen').show();
		enableBubble(data[1],"notCantNoLeida",false)	
	}else{
		$('#seguimientoOpen').hide();
	}
	
}

function enableBubble(data,bubble,noti){
	if(data.cantidadNotificaciones){
		$("#"+bubble).html(data.cantidadNotificaciones);
		if(noti){
			$("#"+bubble+"1").html(data.cantidadNotificaciones);
		}
		$("#"+bubble).show();
	}else{
		if(noti){
			$("#"+bubble+"1").html("0");
		}
		$("#"+bubble).html("0");
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

function removerNotificacionesPorPrograma(programaId,isPrograma,noti){
	var datas =ls.getNotificacionData();
	
	
	
		var data = datas[noti.isNoti ? 0 : 1];
		
		
		if(noti.isNoti){
			if(typeof data.actividad !== "undefined"){
				var cantActual= decreaseCounter(data.actividad,programaId,isPrograma);
				data.actividad = removeProgramaMaterial(data.actividad,programaId,isPrograma);
				data.cantidadNotificaciones = data.cantidadNotificaciones - cantActual;				
			}
			if(typeof data.comentario !== "undefined"){
				var cantActual= decreaseCounter(data.comentario,programaId,isPrograma);
				data.comentario = removeProgramaMaterial(data.comentario,programaId,isPrograma);
				data.cantidadNotificaciones = data.cantidadNotificaciones - cantActual;				
			}
		}else{
			if(noti.dataType){
				var cantActual= decreaseCounter(data.actividad,programaId,isPrograma);
				data.actividad = removeProgramaMaterial(data.actividad,programaId,isPrograma);
				data.cantidadNotificaciones = data.cantidadNotificaciones - cantActual;	
			}else{
				var cantActual= decreaseCounter(data.comentario,programaId,isPrograma);
				data.comentario = removeProgramaMaterial(data.comentario,programaId,isPrograma);
				data.cantidadNotificaciones = data.cantidadNotificaciones - cantActual;	
			} 
		}
		datas[noti.isNoti ? 0 : 1] = data;
	
	
	ls.setNotificacion(datas);
}

function removeProgramaMaterial(data,programaId,isPrograma){
	
	var cursoIndex = [];
	
	$.each(data.curso,function(index,value){
		asignaturaIndex = [];
		$.each(value.asignatura,function(index2,value2){
			programaIndex = [];
			$.each(value2.programa,function(index3,value3){
				if(value3.id == programaId){
							
					if(typeof value3.material !== "undefined" && !isPrograma){						
						$.each(value3.material,function(index4,value4){							
							delete value3.material;							
						});					
						isPrograma = true; // convierto a programa para eliminar el programa
						value3.cantidadComnetarios = -1;
					}													
					
					if(typeof value3.cantidadComnetarios !== "undefined" && isPrograma){
						if(typeof value3.material === "undefined" || value3.material.length == 0){
							programaIndex.push(value3);
						}else{
							delete value3.cantidadComnetarios;
						}
						
					}
				}
			});
			$.each(programaIndex,function(pIndex,pi){
				value2.programa.splice(value2.programa.indexOf(pi),1);
			});
			
			if(value2.programa.length == 0){
				delete value2.programa;
				asignaturaIndex.push(value2);
			}						
		});
		
		$.each(asignaturaIndex,function(pIndex,pi){
			value.asignatura.splice(value.asignatura.indexOf(pi),1);
		});
		
		if(value.asignatura.length == 0){
			delete value.asignatura;
			cursoIndex.push(value);
		}
				
	});
	
	$.each(cursoIndex,function(pIndex,pi){
		data.curso.splice(data.curso.indexOf(pi),1);
	});
	
	if(data.curso == 0){
		return undefined;	
	}
	
	return data;
}
function decreaseCounter(data,programaId,isPrograma){
	var count = 0;
	$.each(data.curso,function(index,value){
		$.each(value.asignatura,function(index2,value2){			
			$.each(value2.programa,function(index3,value3){
				if(value3.id == programaId){
					
					if(typeof value3.cantidadComnetarios !== "undefined" && isPrograma){						
						count = count + value3.cantidadComnetarios;
					}
					if(typeof value3.material !== "undefined" && !isPrograma){						
						$.each(value3.material,function(index4,value4){
							count = count +  value4.cantidadComnetarios;
						})
					}
				}
			});			
		});
		
	});
	return count;
}

