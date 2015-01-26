$(document).ajaxStart(function() {
	startSpinner();
});
$(document).ajaxStop(function() {
	stopSpinner();
});

jQuery(document).ready(function () {
carruselIds = [];
	$.ajax({
		type:"GET",
		url:pagePath+"/asesor/"+asesorId+"/dashboard",
		contentType :'application/json',
		dataType:"json",
		success: function(data){			
			cargarDatos(data);
			loadPorlet();
			loadCarrousel();
			loadAccordion();
		},
		error: function(data){
			$('#message').addClass('msgError');	
			cargarMensaje(errorConverter(data));						
		}								

		}
		
	);
	
	$(window).bind('resizeEnd', function() {		
		carruselIds.splice(0,carruselIds.length);
		$( ".censaccordion div.ui-accordion-content:hidden").each(function(index,element){ 
			hidencurso = "#"+$('#'+$(element).prop("id")+" .censmaterias").prop("id");
			carruselIds.push(hidencurso);
			});


	});
	
	 $(window).resize(function() {
	        if(this.resizeTO) clearTimeout(this.resizeTO);
	        this.resizeTO = setTimeout(function() {
	            $(this).trigger('resizeEnd');
	        }, 500);
	    });
	
	 setTimeout(function(){$(window).trigger('resizeEnd')  }, 1000);
});
function loadPorlet(){
	$(function() {
	    $( ".column" ).sortable({
	      connectWith: ".column",
	      handle: ".portlet-header",
	      cancel: ".portlet-toggle",
	      placeholder: "portlet-placeholder ui-corner-all"
	    });
	 
	    $( ".portlet" )
	      .addClass( "ui-widget ui-widget-content ui-helper-clearfix ui-corner-all" )
	      .find( ".portlet-header" )
	        .addClass( "ui-widget-header ui-corner-all" )
	        .prepend( "<span class='ui-icon ui-icon-minusthick portlet-toggle'></span>");
	 
	    $( ".portlet-toggle" ).click(function() {
	      var icon = $( this );
	      icon.toggleClass( "ui-icon-minusthick ui-icon-plusthick" );
	      icon.closest( ".portlet" ).find( ".portlet-content" ).toggle();
	    });
	  });
	
	$(function() {
	    $( ".portlet-header" ).each(function(){
	    	$(this).children().remove();
	    
	    });
	});
	
}
function loadAccordion(){
	
	$(function() {
	    var icons = {
	      header: "ui-icon-circle-arrow-e",
	      activeHeader: "ui-icon-circle-arrow-s"
	    };
	    $( ".censaccordion" ).accordion({
	      icons: icons,
	      heightStyle: "panel",
	      collapsible: true,
	      navigation: true,
	      active: false,
	      refresh: function(){
	    	  alert("refesh");
	      },
	      beforeActivate: function(event,ui){
	    	  if(ui.newPanel.length>0 && carruselIds.length>0 ){
	    		  carru = "#"+$('#'+$(ui.newPanel).prop("id")+" .censmaterias").prop("id");
	    		  if($.inArray(carru, carruselIds)!==-1){
	    			  startSpinner();
	    		  	removeCarousel("#"+$('#'+$(ui.newPanel).prop("id")+" .censmaterias").prop("id"));
	    		  }
	    	  }
	      },
	      activate: function( event, ui ) {
	    	  if(ui.newPanel.length>0 && carruselIds.length>0 ){
	    		  carru = "#"+$('#'+$(ui.newPanel).prop("id")+" .censmaterias").prop("id");
	    		  if($.inArray(carru, carruselIds)!==-1){
	    			  carruselIds.splice( $.inArray(carru, carruselIds), 1 );	    		  
	    		  	loadCarrousel(carru);
	    		  	stopSpinner();
	    		  }
	    	  }
	      }
	    });
	    
	  });
}

function removeCarousel(materias){
	if(materias === null || materias === undefined){
		materias  = ".censmaterias";
	}
	$(materias).unslick();
}
function loadCarrousel(materias){
	if(materias === null || materias === undefined){
		materias  = ".censmaterias";
	}
	$(materias).slick({
		  infinite: true,
		  arrows: true,
		  dots: true,
		  slidesToShow: 5,
		  slidesToScroll: 5,
		  responsive: [
		               	{
    					breakpoint: 1200,
    					settings: {
    						slidesToShow: 4,
      						slidesToScroll: 4,
      						infinite: true,
      						dots: true
    						}
  						},
		               {
		                 breakpoint: 900,
		                 settings: {
		                   slidesToShow: 3,
		                   slidesToScroll: 3,
		                   infinite: true,
		                   dots: true
		                 }
		               },
		               {
		                 breakpoint: 690,
		                 settings: {
		                   slidesToShow: 2,
		                   slidesToScroll: 2
		                 }
		               },
		               {
		                 breakpoint: 550,
		                 settings: {
		                   slidesToShow: 1,
		                   slidesToScroll: 1
		                 }
		               }
		             ]
		});
	
}

function cargarDatos(data){	
			
	currentDiv =($('#yearContent').append('<div class="censaccordion"></div>')).children();
	
	if(data.cursoDto!==null){
		$.each(data.cursoDto,function(index,value){		
		
			currentDiv=datosCurso(currentDiv,value);
			if(value.asignaturas!==null){
				$.each(value.asignaturas,function(index,asignatura){	
					datosAsignatura(value,currentDiv,asignatura);						
				});
			}
		});
	}
}

function datosCurso(currentDiv,value){
	var title = '<h3>{cursoName}</h3>';
	currentDiv.append(title.replace("{cursoName}","ASIGNATURAS DEL CURSO <span class='cursoFont'>"+(value.nombre.toUpperCase()+" ("+value.yearCurso+")</span>")));
	currentDiv = currentDiv.append("<div id='curso"+value.id+"'></div>");
	 $('#curso'+value.id).append("<div class='censmaterias' id='porletcontainer"+value.id+"'></div>");
	 return currentDiv;
}

function datosAsignatura(value,currentDiv,asignatura){
	var divPorlet = '<div class="portlet" id="{id}"></div>';
	var divPorletHeader =  '<a = href="'+pagePath+'/mvc/asesor/'+asesorId+'/asignatura/'+asignatura.id+'?asignatura='+asignatura.nombre.toUpperCase()+' ('+value.nombre.toLowerCase()+' - '+value.yearCurso+')"><div class="portlet-header">{name}</div></a>';
	var divPorletContet =  '<div class="portlet-content"></div>';
	
	var list ='<ul>{profesor}{suplente}</ul>';		
	list = list.replace('{profesor}',datosProfesor(value,asignatura.profe,false)).replace('{suplente}',datosProfesor(value,asignatura.profeSuplente,true));
	
	currentPorlet =$("#porletcontainer"+value.id).append(divPorlet.replace("{id}","asignatura"+asignatura.id));
	currentPorlet = $("#asignatura"+asignatura.id).append(divPorletHeader.replace("{name}",asignatura.nombre.toUpperCase()));
	currentPorlet = currentPorlet.append(divPorletContet);
	currentPorlet = $(currentPorlet.children()[1]).append(list.replace("{id}",asignatura.id).replace("{id}",asignatura.id).replace("{id}",asignatura.id));
	
}	

function datosProfesor(value,profe,suplente){
	var itemProfesor='<li>{nombreProfe}: <span class="estadoMaterial {subClass}">{profesor}</span></li>';
	itemProfesor = itemProfesor.replace("{nombreProfe}",suplente ? "Suplente" :"Profesor");
	if(profe!==null){		
		return itemProfesor.replace("{profesor}",profe.apellido.toUpperCase()+", ("+profe.dni+")").replace("{subClass}","nuevo");
	}else{
		return itemProfesor.replace("{profesor}","No Asignado").replace("{subClass}","rechazado");
	}
}