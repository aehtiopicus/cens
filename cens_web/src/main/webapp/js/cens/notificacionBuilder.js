var notificationUsers = {"ASESOR":true, "PROFESOR":true};
function processNotificacionData(datas,noti){
	
	var data = datas[0];
	if(data.perfilRol && typeof notificationUsers[data.perfilRol.perfilType] !== "undefined"){
		
		if(noti === "noti"){
			$("#notificacionDeUsuarioData").show();
			$("#seguimientoActvidad").hide();
			$('#notificacionDeUsuario').dialog('option', 'title', 'Notificaciones');	
			
			processNotificacionReal(data);
		}
		else{
			$("#seguimientoActvidad").show();
			$("#notificacionDeUsuarioData").hide();
			$('#notificacionDeUsuario').dialog('option', 'title', "Seguimiento de Actividad");
		 processSeguimientoData(datas[1]);
		}
	}else{
		
		$("#seguimientoActvidad").hide();
	
	}
	
	
}
function processNotificacionReal(data){
	var notificaciones = $('<div class="notificacion"></div>');
	if(data.actividad || data.comentario || data.tiempoEdicion){
				
		var notificacion = new Object();
		notificacion.notificacion = true;
		
		var cnNotificacion = new censNotificaciones.cn.notificacion(notificacion);
		
		
		if(data.actividad){
			notificaciones.append(cnNotificacion.estadoActividad(data.actividad,data.perfilRol));
		}
		
		if(data.comentario){
			notificaciones.append(cnNotificacion.comentario(data.comentario,data.perfilRol));
		}
		
		if(data.tiempoEdicion){
			notificaciones.append(cnNotificacion.tiempoEdicion(data.tiempoEdicion,data.perfilRol));
		}
	
	}else{
		var title = $('<h3 class="header"></h3>');	
		title.html("No se registran notificaciones");
		var noData = $('<div></div>');	
		noData.append(title);
		notificaciones.append(noData);
	}
	$('#notificacionDeUsuarioData').html("");
	$('#notificacionDeUsuarioData').append(notificaciones);
	$( "#notificacionDeUsuario" ).dialog("open");
}
function processSeguimientoData(data){
	var notificaciones = $('<div class="notificacion"></div>');
	
	var notificacion = new Object();
	notificacion.notificacion = false;
	
	var cnSeguimiento = new censNotificaciones.cn.notificacion(notificacion);
	
	if(data.actividad || data.comentario){
		if(data.actividad){
			notificaciones.append(cnSeguimiento.estadoActividad(data.actividad,data.perfilRol));
		}
		if(data.comentario){
			notificaciones.append(cnSeguimiento.comentario(data.comentario,data.perfilRol));
		}
	}else{
		var title = $('<h3 class="header"></h3>');	
		title.html("No se registran notificaciones");
		var noData = $('<div></div>');	
		noData.append(title);
		notificaciones.append(noData);
	}
	$('#seguimientoActvidad').html("");
	$('#seguimientoActvidad').append(notificaciones);
	$( "#notificacionDeUsuario" ).dialog("open");
}


var censNotificaciones = {
        
	      
	     namespace: function(ns) {
	        var parts = ns.split("."),
	            object = this,
	            i, len;

	        for (i=0, len=parts.length; i < len; i++) {
	            if (!object[parts[i]]) {
	                object[parts[i]] = {};
	            }
	            object = object[parts[i]];
	        }
	        return object;
	    },

	    makeClass: function(){
	        return function(args){
	            if ( this instanceof arguments.callee ) {
	                if ( typeof this.init == "function" ){
	                    this.init.apply( this, args != null && args.callee ? args : arguments );
	                }
	            } 
	            else {
	                return new arguments.callee( arguments );
	            }
	        };
	    },

	    makeRandomNamespace: function(ns){
	        var randomNs = "";
	        var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	        for( var i=0; i < 20; i++ )
	            randomNs += possible.charAt(Math.floor(Math.random() * possible.length));

	        if(ns != null){
	            randomNs = ns+'.'+randomNs;
	        }
	        return image.namespace(randomNs);
	    }
};

censNotificaciones.namespace("cn");
censNotificaciones.cn.notificacion = censNotificaciones.makeClass();

censNotificaciones.cn.notificacion.prototype.init = function(param){
	if(param.notificacion){
		this.notificacion = true;
	}else{
		this.seguimiento = true;
	}
	
this.estadoActividad = function (actividad,perfil){
	var title = $('<h3 class="header"></h3>');	
	title.html("Estado de Actividad");
	var estadoActividad = $('<div></div>');	
	estadoActividad.append(title);
	estadoActividad.append(this.crearCurso(actividad.curso,perfil,true));
	return estadoActividad;
	}

this.comentario = function(comentario,perfil){
	var title = $('<h3 class="header"></h3>');	
	title.html("Comenterarios Realizados");
	var comentarios = $('<div></div>');
	comentarios.append(title);
	
	comentarios.append(this.crearCurso(comentario.curso,perfil,false));
	return comentarios;
}

this.tiempoEdicion = function(comentario,perfil){
	var title = $('<h3 class="header"></h3>');	
	title.html("Tiempo de Edici&oacute;n Vencido");
	var comentarios = $('<div></div>');
	comentarios.append(title);
	
	comentarios.append(this.crearCurso(comentario.curso,perfil,false));
	return comentarios;
}

this.crearCurso = function(cursos,perfil,actividad){

		
	var cursosDiv = $('<div></div>');	
	
	var self = this;
	
	$.each(cursos,function(index,curso){
		if(typeof curso.asignatura !== "undefined" && curso.asignatura.length > 0){
			itemCursoDiv=$('<div></div>');
			itemCursotitle = $('<h3 class="subtitulo"></h3>');	
		
			itemCursoDiv.append(itemCursotitle);		
			$.each(curso.asignatura,function(index,a){
				itemCursoDiv.append(self.crearAsignatura(curso,a,perfil.perfilType ==="ASESOR",perfil.perfilId,actividad));
			});
		
			cursosDiv.append(itemCursoDiv);
		}
	});
	return cursosDiv;
}

this.crearAsignatura = function(curso,asiganturas,asesor,perfilId,actividad){
	var self = this;
	
	var linksPrograma = [];
	var linksMaterial = [];
	if(!asiganturas.isOnlyAsignatura){
		$.each(asiganturas.programa, function(index,p){
			var pLink = self.crearPrograma(p,curso,asiganturas,asesor,perfilId,actividad);
			if(pLink){
				if(typeof pLink.cantidadComnetarios !== "undefined"){
					linksPrograma.push(pLink);
				}
			}
			if(typeof p.material !== "undefined"){
				$.each(p.material,function(index,m){
					linksMaterial.push(self.crearMaterial(m,p,curso,asiganturas,asesor,perfilId,actividad));
				})
			}
		});
	}
	itemAsignaturaDiv=$('<div></div>');
	itemHr= $('<hr/>')
	itemHr.css("width","100%");
	itemAsignaturatitle = $('<h3 class="subtitulo curso-asignatura"></h3>');	
	itemAsignaturatitle.html("Curso "+curso.nombre.toUpperCase()+", Asignatura "+asiganturas.nombre.toUpperCase());
	itemAsignaturaDiv.append(itemAsignaturatitle);
	
	if(linksPrograma.length>0){
		itemAsignaturaDiv.append(self.resourceItem(linksPrograma,true,actividad));
	}
	
	if(linksMaterial.length>0){
		itemAsignaturaDiv.append(self.resourceItem(linksMaterial,false,actividad));
	}
	if(asiganturas.isOnlyAsignatura){
		itemAsignaturaDiv.append(self.resourceItem([this.crearAsignaturaTiempoEdicion(curso,asiganturas,asesor)],false,actividad,true));
	}
	itemAsignaturaDiv.append(itemHr);



return itemAsignaturaDiv;

}
this.resourceItem = function(linksPrograma,programa,actividad,asignatura){
	var self = this;
	itemAsignaturaProgramaDiv=$('<div></div>');
	itemAsignaturaProgramaHeaderDiv = $('<div style="min-height: 36px;"></div>');		
	itemAsignaturaProgramaHeaderDiv.append(this.resourceTitleCreator(programa, linksPrograma,asignatura));
	
	var randomBubbleId = randomId();
	var bubble = $("<h3>?</h3>");
	bubble.addClass("notifybubble");
	
	
	itemAsignaturaProgramaUl = $('<ul></ul>')
	itemAsignaturaProgramaUl.attr("id",randomBubbleId);

	var bubbleCount = 0;
	
	
	
	
	var resourceType = programa ? "Programa: " : "Material: ";
	if(asignatura){
		resourceType = "Asignatura: ";
	}
	
	$.each(linksPrograma,function(index,link){
		itemProgramaLi = $('<li></li>');
		bubbleCount = bubbleCount+link.cantidadComnetarios;
		itemPrograma = $('<a class="vinculos"></a>');
								
		itemProgramaName=$('<span></span>');
		itemProgramaName.addClass("items");
		estado = actividad ? (" Estado: "+link.actividad) : '';
		
		if(link.seguimiento){
			var dias = link.diasNotificado > 1 ? "d&iacute;as" : "d&iactue;a";
			var itemNotificado = $('<span style="color:red;"></span>')
			itemNotificado.html(link.fechaNotificado+" ("+link.diasNotificado+" "+dias+" sin ser visto)");
			itemProgramaName.html("Fecha de Notificaci&oacute;n: ");
			itemProgramaName.append(itemNotificado);		
			
		}else if(!link.tiempoEdicion){
			itemProgramaName.html("Fecha: "+link.fechaCreado+" Nro: "+link.cantidadComnetarios+ estado);
		}else{
			var itemNotificado = $('<span style="color:red;"></span>')
			itemNotificado.html(link.estadoTiempoEdicionFecha+" Estado: "+ link.estadoTiempoEdicion);
			itemProgramaName.html("&Uacute;ltima Fecha de Cambio de Estado: ");
			itemProgramaName.append(itemNotificado);
		}
		itemPrograma.append(itemProgramaName);
		
		itemProgramaLi.append(itemPrograma);
		
		itemAsignaturaProgramaUl.append(itemProgramaLi);
		
	});
	
	bubble.html(bubbleCount);
	itemAsignaturaProgramaHeaderDiv.append(bubble);
	itemAsignaturaProgramaDiv.append(itemAsignaturaProgramaHeaderDiv);
	itemAsignaturaProgramaDiv.append(itemAsignaturaProgramaUl);
	var itemVisualizar = this.visualizarItemCreator(programa,linksPrograma);
	
	
	bubble.on("click",function(){
		itemVisualizar.toggleClass("open");		
		$('#'+randomBubbleId).slideToggle("slow");
	})
	var itemDivContainer = $("<div style='overflow:auto;'></div>");
	
	var itemIgnorar;
	if(linksPrograma[0].seguimiento){
		itemIgnorar = this.ignorarItemCreator(programa);
		bubble.addClass("seguimiento");	
		bubble.on("click",function(){
			itemIgnorar.toggleClass("both");
			itemVisualizar.toggleClass("both");
			itemVisualizar.css("margin-right","7px");
		})
		
		itemIgnorar.on("click",function(){	
			var noti = {
					isNoti:false,
					dataType:actividad,
					programaId: linksPrograma[0].programaId,
					rt: resourceType === "Programa: ",
					tipoId: (typeof linksPrograma[0].materialId === "undefined" ? linksPrograma[0].programaId : linksPrograma[0].materialId ),					
					};
			
			self.ignorarCall(noti);
			
					
		});
		itemDivContainer.append(this.itemActionLink(itemIgnorar));		

	}
	itemDivContainer.append(this.itemActionLink(itemVisualizar));
	itemAsignaturaProgramaDiv.append(itemDivContainer);
	
	return itemAsignaturaProgramaDiv;
}
this.ignorarCall= function(noti){
	var urlFragment =noti.tipoId +"/"+(noti.rt ? "PROGRAMA": "MATERIAL")+"/"+(noti.dataType ? "ACTIVIDAD" :"COMENTARIO");
	
	$.ajax({
		url: pagePath+"/api/notificacion/"+urlFragment,
		type: "PUT",	    	    
		contentType :'application/json',
		dataType: "json",    
		success : function(result){
			removerNotificacionesPorPrograma(noti.programaId,noti.rt,noti);
			$("#seguimientoOpen").trigger("click");
			alert("Notificaciones removidas",messageType.info);
		},
		error: function(value,xhr){
			
			alert("Se produjo un error el servidor");

		}
	})
	
}
this.itemActionLink = function (data){
	itemIgnorarDiv = $("<div></div>");
	itemIgnorarDiv.css("display","inline-block");
	itemIgnorarDiv.css("float","right");
	itemIgnorarDiv.append(data);
	return itemIgnorarDiv;
}
this.ignorarItemCreator = function(programa){

	
	itemIgnorar = $('<h3></h3>');
	itemIgnorar.addClass("subtitulo");
	itemIgnorar.addClass("curso-asignatura");
	itemIgnorar.addClass((programa ? 'programa' : 'material'));
	itemIgnorar.addClass("ignorar");
	itemIgnorar.html("Ignorar");
	itemIgnorar.css("cursor","pointer");
	return itemIgnorar;
	
}

this.visualizarItemCreator = function(programa,linksPrograma){
	
	itemVisualizar = $('<h3></h3>');
	itemVisualizar.addClass("subtitulo");
	itemVisualizar.addClass("curso-asignatura");
	itemVisualizar.addClass((programa ? 'programa' : 'material'));
	itemVisualizar.addClass("ignorar");
	itemVisualizar.html("Visualizar");	
	itemVisualizar.css("cursor","pointer");
	var resourceType = programa ? "Programa: " : "Material: ";
	itemVisualizar.on("click",function(){
		if(!linksPrograma[0].seguimiento){
			var noti = {isNoti:true};
			removerNotificacionesPorPrograma(linksPrograma[0].programaId,resourceType === "Programa: ",noti);
		}
		location.href = linksPrograma[0].url;
		});
	return itemVisualizar;
	
}


this.resourceTitleCreator = function(programa,linksPrograma,asignatura){
	
	var itemAsignaturaProrgamaTitle = $('<h3></h3>');
	itemAsignaturaProrgamaTitle.addClass("subtitulo");
	itemAsignaturaProrgamaTitle.addClass("curso-asignatura");
	itemAsignaturaProrgamaTitle.addClass((programa ? 'programa' : 'material'));
	itemAsignaturaProrgamaTitle.css("display","inline-block");
	var resourceType = programa ? "Programa: " : "Material: ";
	if(asignatura){
		resourceType= "Asignatura: ";
	}
	itemAsignaturaProrgamaTitle.html(resourceType + linksPrograma[0].nombre.toUpperCase());
	

	
	return itemAsignaturaProrgamaTitle;
}

this.crearPrograma = function(programa,curso,asignatura,asesor,perfilId,actividad){
	if(typeof programa.fechaCreado !== "undefined"){
		var p = new Object();
		p.fechaCreado = programa.fechaCreado;
		p.cantidadComnetarios = programa.cantidadComnetarios;
		p.nombre = programa.nombre;
		p.programaId = programa.id;
		if(this.seguimiento){
			p.fechaNotificado= programa.fechaNotificado;
			p.diasNotificado = programa.diasNotificado;
			p.seguimiento = true;
		}
		if(programa.isTiempoEdicion){
			p.estadoTiempoEdicion = programa.estadoRevision;
			p.estadoTiempoEdicionFecha = programa.fechaCreado;
			p.tiempoEdicion = true;
		}
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

this.crearAsignaturaTiempoEdicion = function(curso,asignatura,asesor){
	if(typeof asignatura.fechaCreado !== "undefined"){
		var p = {};
		p.fechaCreado = asignatura.fechaCreado;
		p.cantidadComnetarios = asignatura.cantidadComnetarios;
		p.nombre = asignatura.nombre;
		p.programaId = asignatura.id;

		p.estadoTiempoEdicion = asignatura.estadoRevision;
		p.estadoTiempoEdicionFecha = asignatura.fechaCreado;
		p.tiempoEdicion = true;
		
		if(!asesor){			
			p.url = pagePath+"/mvc/profesor/asignaturaList";			
		}else{
			p.url = pagePath+"/mvc/asesor/dashboard";
		}
		return p;
	}
	return null;
}

this.crearMaterial = function(material,programa,curso,asignatura,asesor,perfilId,actividad){

	var p = new Object();
	p.fechaCreado = material.fechaCreado;
	p.cantidadComnetarios = material.cantidadComnetarios;
	p.nombre = material.nombre;
	p.estado = material.estadoRevision;
	p.programaId = programa.id;
	p.materialId = material.id;
	if(this.seguimiento){
		p.fechaNotificado= material.fechaNotificado;
		p.diasNotificado = material.diasNotificado;
		p.seguimiento = true;
	}
	if(programa.isTiempoEdicion){
		p.estadoTiempoEdicion = material.estadoRevision;
		p.estadoTiempoEdicionFecha = material.fechaCreado;		
		p.tiempoEdicion = true;
	}
	if(actividad){
		p.actividad=material.estadoRevision;
	}
	if(asesor){			
		p.url = pagePath+"/mvc/asesor/"+perfilId+"/asignatura/"+asignatura.id+"/programa/"+programa.id+"/material/"+material.id+"?asignatura="+asignatura.nombre+" ("+curso.nombre.replace("(","- ").replace(",","").replace(")","")+")&nro="+material.nro+"&estado="+material.estadoRevision;			
	}else{
		p.url = pagePath+"/mvc/programa/"+programa.id+"/materialABM/"+material.id+"?asignatura="+asignatura.nombre+" ("+curso.nombre.replace("(","- ").replace(",","").replace(")","")+")&nro="+material.nro;
		if(material.estadoRevision === "NUEVO" || material.estadoRevision ==="LISTO" || material.estadoRevision ==="CAMBIOS" || material.estadoRevision ==="RECHAZADO"){
			p.url = p.url+"&disabled=true";
		}
	}
	return p;
	
}
}	
