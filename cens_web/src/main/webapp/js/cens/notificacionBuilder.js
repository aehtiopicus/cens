function processNotificacionData(data){
	
	if(data.perfilRol && data.perfilRol.perfilType == "ASESOR"){
		$('#tabs ul').show();
		$("#seguimientoActvidad").show();
		 
		 $( "#tabs" ).tabs();
			
		$(document).trigger("seguimientoEnabled");
	}else{
		$('#tabs ul').hide();
		$("#seguimientoActvidad").hide();
	}
	
	var notificaciones = $('<div class="notificacion"></div>');
	if(data.actividiad || data.comentario){
				
		var notificacion = new Object();
		notificacion.notificacion = true;
		
		var cnNotificacion = new censNotificaciones.cn.notificacion(notificacion);
		
		
		if(data.actividad){
			notificaciones.append(cnNotificacion.estadoActividad(data.actividad,data.perfilRol));
		}
		
		if(data.comentario){
			notificaciones.append(cnNotificacion.comentario(data.comentario,data.perfilRol));
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
	
	if(data.actividiad || data.comentario){
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
	title.html("Comenterarios realizados");
	var comentarios = $('<div></div>');
	comentarios.append(title);
	
	comentarios.append(this.crearCurso(comentario.curso,perfil,false));
	return comentarios;
}

this.crearCurso = function(cursos,perfil,actividad){

		
	var cursosDiv = $('<div></div>');	
	
	var self = this;
	
	$.each(cursos,function(index,curso){
		
		itemCursoDiv=$('<div></div>');
		itemCursotitle = $('<h3 class="subtitulo"></h3>');	
		
		itemCursoDiv.append(itemCursotitle);
		
		$.each(curso.asignatura,function(index,a){
			itemCursoDiv.append(self.crearAsignatura(curso,a,perfil.perfilType ==="ASESOR",perfil.perfilId,actividad));
		});
		
		cursosDiv.append(itemCursoDiv);
		
	});
	return cursosDiv;
}

this.crearAsignatura = function(curso,asiganturas,asesor,perfilId,actividad){
	var self = this;
	
	var linksPrograma = [];
	var linksMaterial = [];
	$.each(asiganturas.programa, function(index,p){
		var pLink = self.crearPrograma(p,curso,asiganturas,asesor,perfilId,actividad);
		if(pLink){
			linksPrograma.push(pLink);
		}
		if(typeof p.material !== "undefined"){
			$.each(p.material,function(index,m){
				linksMaterial.push(self.crearMaterial(m,p,curso,asiganturas,asesor,perfilId,actividad));
			})
		}
			
		
	})
	
	itemAsignaturaDiv=$('<div></div>');
	itemHr= $('<hr/>')
	
	itemAsignaturatitle = $('<h3 class="subtitulo curso-asignatura"></h3>');	
	itemAsignaturatitle.html("Curso "+curso.nombre.toUpperCase()+", Asignatura "+asiganturas.nombre.toUpperCase());
	itemAsignaturaDiv.append(itemAsignaturatitle);
	
	if(linksPrograma.length>0){
		itemAsignaturaDiv.append(self.resourceItem(linksPrograma,true,actividad));
	}
	
	if(linksMaterial.length>0){
		itemAsignaturaDiv.append(self.resourceItem(linksMaterial,false));
	}
	itemAsignaturaDiv.append(itemHr);



return itemAsignaturaDiv;

}
this.resourceItem = function(linksPrograma,programa,actividad){
	var self = this;
	itemAsignaturaProgramaDiv=$('<div></div>');
	itemAsignaturaProrgamaTitle = $('<h3 class="subtitulo curso-asignatura '+ (programa ? 'programa' : 'material')+'"></h3>');
	itemAsignaturaProrgamaTitle.html(programa ? "Programa" : "Material");
	
	itemAsignaturaProgramaDiv.append(itemAsignaturaProrgamaTitle);
	
	itemAsignaturaProgramaUl = $('<ul></ul>')
	
	$.each(linksPrograma,function(index,link){
		itemProgramaLi = $('<li></li>');
		
		itemPrograma = $('<a class="vinculos"></a>');
		itemPrograma.attr("href",link.url);		
		itemProgramaName=$('<span></span>');
		estado = actividad ? (" Estado: "+link.actividad) : '';
		if(link.seguimiento){
			var dias = link.diasNotificado > 1 ? "d&iacute;as" : "d&iactue;a";
			itemNotificado = $('<span style="color:red;"></span>')
			itemNotificado.html(link.fechaNotificado+" ("+link.diasNotificado+" "+dias+")");
			itemProgramaName.html(link.nombre.toUpperCase()+", Fecha de Notificaci&oacute;n: ");
			itemProgramaName.append(itemNotificado);
		}else{
			itemProgramaName.html(link.nombre.toUpperCase()+", Fecha: "+link.fechaCreado+" Nro: "+link.cantidadComnetarios+ estado);
		}
		itemPrograma.append(itemProgramaName);
		
		itemProgramaLi.append(itemPrograma);
		
		itemAsignaturaProgramaUl.append(itemProgramaLi);
		
	});
	
	itemAsignaturaProgramaDiv.append(itemAsignaturaProgramaUl);
	return itemAsignaturaProgramaDiv;
}

this.crearPrograma = function(programa,curso,asignatura,asesor,perfilId,actividad){
	if(typeof programa.fechaCreado !== "undefined"){
		var p = new Object();
		p.fechaCreado = programa.fechaCreado;
		p.cantidadComnetarios = programa.cantidadComnetarios;
		p.nombre = programa.nombre;
		if(this.seguimiento){
			p.fechaNotificado= programa.fechaNotificado;
			p.diasNotificado = programa.diasNotificado;
			p.seguimiento = true;
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

this.crearMaterial = function(material,programa,curso,asignatura,asesor,perfilId,actividad){

	var p = new Object();
	p.fechaCreado = material.fechaCreado;
	p.cantidadComnetarios = material.cantidadComnetarios;
	p.nombre = material.nombre;
	p.estado = material.estadoRevision;
	if(this.seguimiento){
		p.fechaNotificado= material.fechaNotificado;
		p.diasNotificado = material.diasNotificado;
		p.seguimiento = true;
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
