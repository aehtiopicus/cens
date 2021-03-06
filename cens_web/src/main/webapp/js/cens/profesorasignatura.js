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
		url:pagePath+"/api/profesor/"+profesorId+"/curso/asignatura",
		contentType :'application/json',
		dataType:"json",
		success: function(data){			
			cargarDatos(data);
			loadPorlet();
			loadCarrousel();
		},
		error: function(data){
			$('#message').addClass('msgError');			
			cargarMensaje($('<div/>').html(errorConverter(data)).text());						
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
	currentDiv.append($('<hr class="portletLine">'));
	currentDiv = currentDiv.append("<div id='curso"+value.id+"' class='curso'></div>");
	 $('#curso'+value.id).append("<div class='censmaterias' id='porletcontainer"+value.id+"'></div>");
	 return currentDiv;
}

function datosAsignatura(value,currentDiv,asignatura){
	var divPorlet = '<div class="portlet" id="{id}"></div>';
	var divPorletHeader =  '<div class="portlet-header">{name}</div>';
	var divPorletContet =  '<div class="portlet-content"></div>';
	
	var list ='<ul>{programa}{cartillas}';	
	var itemCartillas='<li><a href="'+pagePath+'/mvc/asignatura/{id}/material">Material Did&aacute;ctico (No Existe)</a></li>';
		
	list = list.replace('{programa}',datosPrograma(value,asignatura)).replace('{cartillas}',datosMaterial(value,asignatura));
	
	currentPorlet =$("#porletcontainer"+value.id).append(divPorlet.replace("{id}","asignatura"+asignatura.id));
	currentPorlet = $("#asignatura"+asignatura.id).append(divPorletHeader.replace("{name}",asignatura.nombre.toUpperCase()));
	currentPorlet = currentPorlet.append(divPorletContet);
	currentPorlet = $(currentPorlet.children()[1]).append(list.replace("{id}",asignatura.id).replace("{id}",asignatura.id).replace("{id}",asignatura.id));
	
}	
function datosMaterial(value,asignatura){
	var itemCartillas=$('<li></li>');
	var itemCartillasLink=$('<a></a>');
	itemCartillasLink.html("Material Did&aacute;ctico");
	if(asignatura.programa!==undefined && asignatura.programa!==null && asignatura.programa.estadoRevisionType === "ACEPTADO"){
		itemCartillasLink.attr("href",pagePath+"/mvc/programa/"+asignatura.programa.id+"/material?asignatura="+asignatura.nombre.toUpperCase()+" ("+value.nombre+" - "+value.yearCurso+")");
		itemCartillas.append(itemCartillasLink);
		itemCartillas.append("&nbsp;");
		itemCartillaInnerDiv = $("<div></div>");
		itemCartillas.append(itemCartillaInnerDiv);
		itemCartillaInnerDiv.attr("style","display:inline-block; float:right;");
		if(asignatura.programa.materialDidactico !== null){
			
			for (i = 0; i < asignatura.programa.cantCartillas; i++) {
				itemCartillaNumeroLink = $('<a></a>')			
				itemCartillaNumero=$('<span></span>');
				itemCartillaNumero.addClass("estadoMaterial");
				itemCartillaNumero.html(i+1+" ");
				used=false;
				$.each(asignatura.programa.materialDidactico,function(index,mdValue){
					if(mdValue.nro === i+1){
						itemCartillaNumeroLink.attr("href",pagePath+"/mvc/programa/"+asignatura.programa.id+"/materialABM/"+mdValue.id+"?asignatura="+asignatura.nombre.toUpperCase()+" ("+value.nombre+" - "+value.yearCurso+")&nro="+(i+1));						
						itemCartillaNumero.addClass(mdValue.estadoRevisionType.toLowerCase());
						used=true;
					}					
					
				});
				if(used===false){
					itemCartillaNumero.addClass("inexistente");
					itemCartillaNumeroLink.attr("href",pagePath+"/mvc/programa/"+asignatura.programa.id+"/materialABM?asignatura="+asignatura.nombre.toUpperCase()+" ("+value.nombre+" - "+value.yearCurso+")&nro="+(i+1));
				}
				itemCartillaNumeroLink.append(itemCartillaNumero);
				itemCartillaInnerDiv.append(itemCartillaNumeroLink);
				
			}
			
		}
	}else{
		itemCartillas.append(itemCartillasLink);
	}
	
	return itemCartillas.prop('outerHTML');
	
}
function datosPrograma(value,asignatura){
	var itemPrograma='<li><a href="'+pagePath+'/mvc/asignatura/{id}/programa{existente}{nombreAsignatura}">Programa <div style="display:iniline-block; float:right;"><span class="estadoMaterial {subClass}">({estado})</span></div></a></li>';
	if(asignatura.programa!=undefined && asignatura.programa!==null){
			return itemPrograma.replace("{existente}","/"+asignatura.programa.id).replace("{estado}",asignatura.programa.estadoRevisionType).replace("{subClass}",asignatura.programa.estadoRevisionType.toLowerCase()).replace("{nombreAsignatura}","?asignatura="+asignatura.nombre.toUpperCase()+" ("+value.nombre+" - "+value.yearCurso+")");		
	}else{
		return itemPrograma.replace("{existente}","").replace("{estado}","No Existe").replace("{subClass}","inexistente").replace("{nombreAsignatura}","?asignatura="+asignatura.nombre.toUpperCase()+" ("+value.nombre.toLowerCase()+" - "+value.yearCurso+")");
	}
}