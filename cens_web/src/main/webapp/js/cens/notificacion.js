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
	
	loadNotificationInformation(processData);		 		
	 
}

function processData(data){
	var notificaciones = $('<div></div>');
	if(data.actividad){
		notificaciones.append(estadoActividad(data.actividad,data.perfilRol));
	}
	if(data.comentario){
		notificaciones.append(comentario(data.comentario,data.perfilRol));
	}
	$('#notificacionDeUsuario').append(notificaciones);
	$( "#notificacionDeUsuario" ).dialog("open");
}

function estadoActividad(actividad,perfil){
	var title = $('<h3></h3>');	
	title.html("Estado de Actividad");
	var estadoActividad = $('<div></div>');	
	estadoActividad.append(title);
	estadoActividad.append(curso(actividad.curso,perfil,true));
	return estadoActividad;
}

function comentario(comentario,perfil){
	var title = $('<h3 ></h3>');	
	title.html("Comenterarios realizados");
	var comentarios = $('<div></div>');
	comentarios.append(title);
	
	comentarios.append(curso(comentario.curso,perfil,false));
	return comentarios;
}

function curso(cursos,perfil,actividad){

		
	var cursosDiv = $('<div></div>');	
	
	
	$.each(cursos,function(index,curso){
		
		itemCursoDiv=$('<div></div>');
		itemCursotitle = $('<h3 class="subtitulo"></h3>');	
		itemCursotitle.html("Curso "+curso.nombre.toUpperCase());
		itemCursoDiv.append(itemCursotitle);
		
		$.each(curso.asignatura,function(index,a){
			itemCursoDiv.append(asignatura(curso,a,perfil.perfilType ==="ASESOR",perfil.perfilId,actividad));
		});
		
		cursosDiv.append(itemCursoDiv);
		
	});
	return cursosDiv;
}

function asignatura(curso,asiganturas,asesor,perfilId,actividad){

		
		var linksPrograma = [];
		var linksMaterial = [];
		$.each(asiganturas.programa, function(index,p){
			var pLink = programa(p,curso,asignatura,asesor,perfilId,actividad);
			if(pLink){
				linksPrograma.push(pLink);
			}
			if(typeof p.material !== "undefined"){
				$.each(p.material,function(index,m){
					linksMaterial.push(material(m,p,curso,asignatura,asesor,perfilId,actividad));
				})
			}
				
			
		})
		
		itemAsignaturaDiv=$('<div></div>');
		itemAsignaturatitle = $('<h3 class="subtitulo"></h3>');	
		itemAsignaturatitle.html("Asignatura "+asiganturas.nombre.toUpperCase());
		itemAsignaturaDiv.append(itemAsignaturatitle);
		
		if(linksPrograma.length>0){
			itemAsignaturaDiv.append(resourceItem(linksPrograma,true,actividad));
		}
		
		if(linksMaterial.length>0){
			itemAsignaturaDiv.append(resourceItem(linksMaterial,false));
		}

	

	return itemAsignaturaDiv;
	
}

function resourceItem(linksPrograma,programa,actividad){
	itemAsignaturaProgramaDiv=$('<div></div>');
	itemAsignaturaProrgamaTitle = $('<h3 class="subtitulo"></h3>');
	itemAsignaturaProrgamaTitle.html(programa ? "Programa" : "Material");
	
	itemAsignaturaProgramaDiv.append(itemAsignaturaProrgamaTitle);
	
	itemAsignaturaProgramaUl = $('<ul></ul>')
	
	$.each(linksPrograma,function(index,link){
		itemProgramaLi = $('<li></li>');
		
		itemPrograma = $('<a></a>');
		itemPrograma.attr("href",link.url);
		itemProgramaName=$('<span></span>');
		estado = actividad ? (" Estado: "+link.actividad) : '';
		itemProgramaName.html(link.nombre.toUpperCase()+", Fecha: "+link.fechaCreado+" Nro: "+link.cantidadComnetarios+ estado);
		itemPrograma.append(itemProgramaName);
		
		itemProgramaLi.append(itemPrograma);
		
		itemAsignaturaProgramaUl.append(itemProgramaLi);
		
	});
	
	itemAsignaturaProgramaDiv.append(itemAsignaturaProgramaUl);
	return itemAsignaturaProgramaDiv;
}

function programa(programa,curso,asignatura,asesor,perfilId,actividad){
	if(typeof programa.fechaCreado !== "undefined"){
		var p = new Object();
		p.fechaCreado = programa.fechaCreado;
		p.cantidadComnetarios = programa.cantidadComnetarios;
		p.nombre = programa.nombre;
		if(actividad){
			p.actividad=programa.estadoRevision;
		}
		if(asesor){			
			p.url = pagePath+"/mvc/asesor/"+perfilId+"/asignatura/"+asignatura.id+"/programa/"+programa.id+"?asignatura="+asignatura.nombre+" ("+curso.nombre.replace("(","- ").replace(",","").replace(")","")+")&estado="+programa.estadoRevision;			
		}else{
			p.url = pagePath+"/mvc/asignatura/"+asignatura.id+"/programa/"+programa.id+"?asignatura="+asignatura.nombre+" ("+curso.nombre.replace("(","- ").replace(",","").replace(")","")+")";
			if(programa.estadoRevision === "NUEVO" || programa.estadoRevision ==="LISTO" || programa.estadoRevision ==="CAMBIOS" || programa.estadoRevision ==="RECHAZADO"){
				p.url = p.url+"&disabled=true";
			}
		}
		return p;
	}
	return null;
}

function material(material,programa,curso,asignatura,asesor,perfilId,actividad){

	var p = new Object();
	p.fechaCreado = material.fechaCreado;
	p.cantidadComnetarios = material.cantidadComnetarios;
	p.nombre = material.nombre;
	p.estado = material.estadoRevision;
	if(actividad){
		p.actividad=material.estadoRevision;
	}
	if(asesor){			
		p.url = pagePath+"/mvc/asesor/"+perfilId+"/asignatura/"+asignatura.id+"/programa/"+programa.id+"/material/"+material.id+"?asignatura="+asignatura.nombre+" ("+curso.nombre.replace("(","- ").replace(",","").replace(")","")+")&nro"+material.nro+"&estado="+material.estadoRevision;			
	}else{
		p.url = pagePath+"/mvc/programa/"+programa.id+"/materialABM/"+material.id+"?asignatura="+asignatura.nombre+" ("+curso.nombre.replace("(","- ").replace(",","").replace(")","")+")&nro"+material.nro;
		if(material.estadoRevision === "NUEVO" || material.estadoRevision ==="LISTO" || material.estadoRevision ==="CAMBIOS" || material.estadoRevision ==="RECHAZADO"){
			p.url = p.url+"&disabled=true";
		}
	}
	return p;
	
}

function loadNotificationInformation(processData){
	
	var item = JSON.parse(new localstorage.ls.notificacionData().getNotificacion()).item;
	item.notificacionLoader = notificacionLoader;
	if(!ls){
		ls = new localstorage.ls.notificacion(item);
	}
	if(ls.isRefreshRequired()){
		ls.getNotificacionData();
		setTimeout(loadNotificationInformation,1000,processData);
		
	}else{
		if( typeof processData === 'function'){
			processData(ls.getNotificacionData());
		}else{
			return ls.getNotificacionData();
		}
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