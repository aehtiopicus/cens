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
		url:pagePath+"/profesor/"+profesorId+"/curso/asignatura",
		contentType :'application/json',
		dataType:"json",
		success: function(data){			
			cargarDatos(data);
			loadPorlet();
			loadCarrousel();
		},
		error: function(data){
			$('#message').addClass('msgError');	
			cargarMensaje(errorConverter(data));						
		}								

		}
		
	);
	
	
});



function cargarDatos(data){	
			
	currentDiv =($('#yearContent').append('<div class="censaccordion"></div>')).children();
	
	$.each(data.cursoAsignatura,function(index,value){		
		
		currentDiv=datosCurso(currentDiv,value);
		
		$.each(value.asignaturasDelCursoDto,function(index,asignatura){	
			datosAsignatura(value,currentDiv,asignatura);						
		});
	});
}

function datosCurso(currentDiv,value){
	var title = '<h3 class="subtitulo">{cursoName}</h3>';
	currentDiv.append(title.replace("{cursoName}","ASIGNATURAS DEL CURSO <span class='cursoFont'>"+(value.nombre.toUpperCase()+"("+value.yearCurso+")</span>")));
	currentDiv = currentDiv.append("<div id='curso"+value.id+"' class='curso'></div>");
	 $('#curso'+value.id).append("<div class='censmaterias' id='porletcontainer"+value.id+"'></div>");
	 return currentDiv;
}

function datosAsignatura(value,currentDiv,asignatura){
	var divPorlet = '<div class="portlet" id="{id}"></div>';
	var divPorletHeader =  '<div class="portlet-header">{name}</div>';
	var divPorletContet =  '<div class="portlet-content"></div>';
	
	var list ='<ul>{programa}{cartillas}{sugerencias}';	
	var itemCartillas='<li><a href="'+pagePath+'/mvc/asignatura/{id}/material">Material Did&aacute;ctico (No Existe)</a></li>';
	var itemSugerencias='<li><a href="'+pagePath+'/mvc/asignatura/{id}/sugerencias">Sugerencias (No Existe)</a></li></ul>';	
	list = list.replace('{programa}',datosPrograma(value,asignatura)).replace('{cartillas}',itemCartillas).replace('{sugerencias}',itemSugerencias);
	
	currentPorlet =$("#porletcontainer"+value.id).append(divPorlet.replace("{id}","asignatura"+asignatura.id));
	currentPorlet = $("#asignatura"+asignatura.id).append(divPorletHeader.replace("{name}",asignatura.nombre.toUpperCase()));
	currentPorlet = currentPorlet.append(divPorletContet);
	currentPorlet = $(currentPorlet.children()[1]).append(list.replace("{id}",asignatura.id).replace("{id}",asignatura.id).replace("{id}",asignatura.id));
	
}	

function datosPrograma(value,asignatura){
	var itemPrograma='<li><a href="'+pagePath+'/mvc/asignatura/{id}/programa{existente}{nombreAsignatura}">Programa <span class="estadoMaterial {subClass}">({estado})</span></a></li>';
	if(asignatura.programa!==null){
		if(asignatura.programa.estadoRevisionType === "NUEVO" || asignatura.programa.estadoRevisionType ==="LISTO"){
			return itemPrograma.replace("{existente}","/"+asignatura.programa.id).replace("{estado}",asignatura.programa.estadoRevisionType).replace("{subClass}",asignatura.programa.estadoRevisionType.toLowerCase()).replace("{nombreAsignatura}","?asignatura="+asignatura.nombre.toUpperCase()+" ("+value.nombre+" - "+value.yearCurso+")");
		}else{
			return itemPrograma.replace("{existente}","/"+asignatura.programa.id).replace("{estado}",asignatura.programa.estadoRevisionType).replace("{subClass}",asignatura.programa.estadoRevisionType.toLowerCase()).replace("{nombreAsignatura}","?asignatura="+asignatura.nombre.toUpperCase()+" ("+value.nombre+" - "+value.yearCurso+")&disabled=true");
		}
	}else{
		return itemPrograma.replace("{existente}","").replace("{estado}","No Existe").replace("{subClass}","inexistente").replace("{nombreAsignatura}","?asignatura="+asignatura.nombre.toUpperCase()+" ("+value.nombre.toLowerCase()+" - "+value.yearCurso+")");
	}
}