
$(document).ajaxStop(function() {
	stopSpinner();
	$('.censmaterias').slickSetOption('respondeTo', '', true);
});

var alumnodashboard = {
        
      
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

alumnodashboard.namespace("al");
alumnodashboard.al.db = localstorage.makeClass();

alumnodashboard.al.db.prototype.init = function(param){
		
this.alumnoId =	new localstorage.ls.userProfileData().loadProfile().perfilId;

this.cargarDashboard = function(){
	var self = this;
	$.ajax({
		type:"GET",
		url:pagePath+"/api/alumno/"+self.alumnoId+"/dashboard",
		contentType :'application/json',
		dataType:"json",
		beforeSend: function(xhr){
			startSpinner();
		},
		success: function(data){			
			self.cargarDatos(data);
			loadPorlet();
			loadCarrousel();			
		},
		error: function(data){						
			location.href=pagePath+"/errors/"+data.error().status;
		}								

		}
		
	);
}


this.cargarDatos = function(data){
	self = this;
	
	var currentDiv = $("<div></div>");
	currentDiv.addClass("censaccordion");
	$('#yearContent').append(currentDiv);
	
	if(data.cursos!=null){
		$.each(data.cursos,function(index,value){		
		
			currentDiv=self.datosCurso(currentDiv,value);
			if(value.asignaturas!=null){
				$.each(value.asignaturas,function(index,asignatura){	
					self.datosAsignatura(value,currentDiv,asignatura);						
				});
			}
		});
	}
}

this.datosCurso = function(currentDiv,value){
	var title = $('<h3></h3>');
	title.addClass("subtitulo");
	
	var titleSpan = $('<span></span>');
	titleSpan.addClass("cursoFont");	
	titleSpan.html((value.nombre.toUpperCase()+" ("+value.yearCurso+")"));
	title.append("ASIGNATURAS DEL CURSO ");
	title.append(titleSpan);
	currentDiv.append(title);
	
	var hr = $("<hr></hr>");
	hr.addClass("portletLine");	
	currentDiv.append(hr);
	
	var divCursoId = $("<div></div>");
	divCursoId.addClass("curso");
	divCursoId.attr("id",value.id)
	currentDiv = currentDiv.append(divCursoId);
	
	var divPorletContainer = $("<div></div>");
	divPorletContainer.addClass("censmaterias");
	divPorletContainer.attr("id","porletcontainer"+value.id);
	
	divCursoId.append(divPorletContainer);
	 return currentDiv;
}

this.datosAsignatura = function(value,currentDiv,asignatura){

	var divPorlet = $('<div></div>');
	divPorlet.addClass("portlet");
	divPorlet.attr("id","asignatura"+asignatura.id);
	
	var divPorletHeader =  $('<div></div>');
	divPorletHeader.addClass("portlet-header");
	divPorletHeader.append(asignatura.nombre.toUpperCase());
	
	var divPorletContet =  $('<div></div>');
	divPorletContet.addClass("portlet-content");
	
	var list =$('<ul></ul>');		
	
	list = list.append(this.datosProfesor(value,asignatura.profesor,asignatura.profesorSuplente));
	list = list.append(this.datosPrograma(value,asignatura));
	list = list.append(datosMaterial(value,asignatura));
	currentPorlet =$("#porletcontainer"+value.id).append(divPorlet);
	currentPorlet = $("#asignatura"+asignatura.id).append(divPorletHeader);
	currentPorlet = currentPorlet.append(divPorletContet);
	currentPorlet = $(currentPorlet.children()[1]).append(list);
}

this.datosProfesor = function(value,profe,suplente){
	var itemProfesor=$('<li></li>');
	itemProfesor.append("Profesor:");
	
	var profesorSpan = $("<span></span>");
	profesorSpan.addClass("estadoMaterial");
	
	if(typeof suplente!==undefined && suplente!=null){		
		profesorSpan.append(suplente.miembroCens.apellido.toUpperCase()+", ("+suplente.miembroCens.dni+")");
		profesorSpan.addClass("nuevo");
		
	}else if(profe !==undefined && profe!=null){		
		profesorSpan.append(profe.miembroCens.apellido.toUpperCase()+", ("+profe.miembroCens.dni+")");
		profesorSpan.addClass("nuevo");
	}else{
		profesorSpan.append("No Asignado");
		profesorSpan.addClass("rechazado");		
	}
	itemProfesor.append(profesorSpan);
	return itemProfesor;
}

this.datosPrograma = function(value,asignatura){
	var itemPrograma=$('<li></li>');
	var itemLink = $("<a></a>");
	var itemLinkSpan = $("<span></span>");
	itemLinkSpan.addClass("estadoMaterial");
	if(typeof asignatura.programaDto !== "undefined" && typeof asignatura.programaDto.id !== "undefined"){
		itemLink.attr("href",pagePath+"/api/asignatura/"+asignatura.id+"/programa/"+asignatura.programaDto.id+"/archivo");
		itemLinkSpan.addClass(asignatura.programaDto.estadoRevisionType.toLowerCase());
		itemLinkSpan.append(asignatura.programaDto.estadoRevisionType);
	}else{
		itemLinkSpan.addClass("inexistente");
		itemLinkSpan.append("Inexistente");
	}
	itemLink.append("Programa: ");
	itemLink.append(itemLinkSpan);
	
	itemPrograma.append(itemLink);
		
	
	
	return itemPrograma;
}
}





function datosMaterial(value,asignatura){

	var itemMaterial=$('<li></li>');
	itemMaterial.append("Material Did&aacute;ctico: ");	
	if(asignatura.programa!=null && asignatura.programa != undefined && asignatura.programa.materialDidactico != null && asignatura.programa.materialDidactico.length >0){
			$.each(asignatura.programa.materialDidactico.sort(function(a,b){
				  return a.nro-b.nro;
			}), function(index,md){
				if(estadoRevision(md)){
					itemCartillaNumeroLink = $('<a></a>');
					itemMaterialInterno = $('<span></span>');
					itemMaterialInterno.addClass("estadoMaterial");
					itemMaterialInterno.html(md.nro+" ");
					itemMaterialInterno.addClass(md.estadoRevisionType.toLowerCase());
					
					itemCartillaNumeroLink.attr("href",pagePath+"/mvc/asesor/"+asesorId+"/asignatura/"+asignatura.id+"/programa/"+asignatura.programa.id+"/material/"+md.id+"?asignatura="+asignatura.nombre.toUpperCase()+" ("+value.nombre+" - "+value.yearCurso+")&nro="+md.nro+"&estado="+md.estadoRevisionType);
					itemCartillaNumeroLink.append(itemMaterialInterno);
					itemMaterial.append(itemCartillaNumeroLink);
				}
			});
		
	}else{
		itemMaterialInterno = $('<span></span>');
		itemMaterialInterno.addClass("estadoMaterial");
		itemMaterialInterno.html("(No Existe)");
		itemMaterialInterno.addClass("inexistente");
		itemMaterial.append(itemMaterialInterno);
	}
	return itemMaterial.prop('outerHTML');
}



function estadoRevision(programa){
	if(programa.estadoRevisionType === "LISTO" ||programa.estadoRevisionType === "ASIGNADO" ||programa.estadoRevisionType === "RECHAZADO" ||programa.estadoRevisionType === "ACEPTADO" || programa.estadoRevisionType ==="CAMBIOS"){
		return true;
	}
	return false;
}