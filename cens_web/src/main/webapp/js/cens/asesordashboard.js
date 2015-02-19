$(document).ajaxStart(function() {
	startSpinner();
});
$(document).ajaxStop(function() {
	stopSpinner();
	$('.censmaterias').slickSetOption('respondeTo', '', true);
});

jQuery(document).ready(function () {
carruselIds = [];
	$.ajax({
		type:"GET",
		url:pagePath+"/api/asesor/"+asesorId+"/dashboard",
		contentType :'application/json',
		dataType:"json",
		success: function(data){			
			cargarDatos(data);
			loadPorlet();
			loadCarrousel();			
		},
		error: function(data){						
			location.href=pagePath+"/errors/"+data.error().status;
		}								

		}
		
	);
		 
	 
});

function cargarDatos(data){	
			
	currentDiv =($('#yearContent').append('<div class="censaccordion"></div>')).children();
	
	if(data.cursoDto!=null){
		$.each(data.cursoDto,function(index,value){		
		
			currentDiv=datosCurso(currentDiv,value);
			if(value.asignaturas!=null){
				$.each(value.asignaturas,function(index,asignatura){	
					datosAsignatura(value,currentDiv,asignatura);						
				});
			}
		});
	}
}

function datosCurso(currentDiv,value){
	var title = '<h3 class="subtitulo">{cursoName}</h3>';
	currentDiv.append(title.replace("{cursoName}","ASIGNATURAS DEL CURSO <span class='cursoFont'>"+(value.nombre.toUpperCase()+" ("+value.yearCurso+")</span>")));
	currentDiv = currentDiv.append("<div id='curso"+value.id+"' class='curso'></div>");
	 $('#curso'+value.id).append("<div class='censmaterias' id='porletcontainer"+value.id+"'></div>");
	 return currentDiv;
}

function datosAsignatura(value,currentDiv,asignatura){
	var divPorlet = '<div class="portlet" id="{id}"></div>';
	var divPorletHeader =  '<div class="portlet-header">{name}</div>';
	var divPorletContet =  '<div class="portlet-content"></div>';
	
	var list ='<ul>{profesor}{programa}{material}</ul>';		
	list = list.replace('{profesor}',datosProfesor(value,asignatura.profe,asignatura.profeSuplente));
	list = list.replace('{programa}',datosPrograma(value,asignatura));
	list = list.replace('{material}',datosMaterial(value,asignatura));
	currentPorlet =$("#porletcontainer"+value.id).append(divPorlet.replace("{id}","asignatura"+asignatura.id));
	currentPorlet = $("#asignatura"+asignatura.id).append(divPorletHeader.replace("{name}",asignatura.nombre.toUpperCase()));
	currentPorlet = currentPorlet.append(divPorletContet);
	currentPorlet = $(currentPorlet.children()[1]).append(list.replace("{id}",asignatura.id).replace("{id}",asignatura.id).replace("{id}",asignatura.id));
	
}	

function datosPrograma(value,asignatura){
	var itemPrograma='<li>{link}</li>';
	var itemLink = '<a href="'+pagePath+'/mvc/asesor/'+asesorId+'/asignatura/{id}/programa{existente}{nombreAsignatura}">{text}</a>';
	var itemText ='Programa: <span class="estadoMaterial {subClass}">({estado})</span>';
	if(asignatura.programa!=null && estadoRevision(asignatura.programa)){
	
			itemLink = itemLink.replace("{existente}","/"+asignatura.programa.id).replace("{nombreAsignatura}","?asignatura="+asignatura.nombre.toUpperCase()+" ("+value.nombre+" - "+value.yearCurso+")&estado="+asignatura.programa.estadoRevisionType);
			itemText = itemText.replace("{estado}",asignatura.programa.estadoRevisionType).replace("{subClass}",asignatura.programa.estadoRevisionType.toLowerCase());
		}else{
			if(asignatura.programa!=null){
				itemText = itemText.replace("{estado}",asignatura.programa.estadoRevisionType).replace("{subClass}",asignatura.programa.estadoRevisionType.toLowerCase());
			}else{
				itemText = itemText.replace("{estado}","No Existe").replace("{subClass}","inexistente");
			}
			itemLink= "{text}";
		}
	
	return itemPrograma.replace("{link}",itemLink.replace("{text}",itemText));
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

function datosProfesor(value,profe,suplente){
	var itemProfesor='<li>{nombreProfe}: <span class="estadoMaterial {subClass}">{profesor}</span></li>';
	itemProfesor = itemProfesor.replace("{nombreProfe}","Profesor");
	if(suplente!==undefined && suplente!=null){		
		return itemProfesor.replace("{profesor}",suplente.apellido.toUpperCase()+", ("+suplente.dni+")").replace("{subClass}","nuevo");
	}else if(profe !==undefined && profe!=null){
		return itemProfesor.replace("{profesor}",profe.apellido.toUpperCase()+", ("+profe.dni+")").replace("{subClass}","nuevo");
	}else{
		return itemProfesor.replace("{profesor}","No Asignado").replace("{subClass}","rechazado");
	}
}

function estadoRevision(programa){
	if(programa.estadoRevisionType === "LISTO" ||programa.estadoRevisionType === "ASIGNADO" ||programa.estadoRevisionType === "RECHAZADO" ||programa.estadoRevisionType === "ACEPTADO" || programa.estadoRevisionType ==="CAMBIOS"){
		return true;
	}
	return false;
}