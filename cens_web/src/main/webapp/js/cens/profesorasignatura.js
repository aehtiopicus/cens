$(document).ajaxStart(function() {
	startSpinner();
});
$(document).ajaxStop(function() {
	stopSpinner();
});

jQuery(document).ready(function () {

	$.ajax({
		type:"GET",
		url:pagePath+"/profesor/"+profesorId+"/curso/asignatura",
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
	      active: function( event, ui ) {
//	    	  $('.censaccordion').accordion("destroy");
	    	  $('.censaccordion').accordion("refresh");
//	    	  $('.censmaterias').resizeCanvas();
//	    	  if(ui.newPanel.length>0){
//	    		  var width = 0;
//	    		  var itemWidth = 0;
//	    		  $('.slick-track').each(function(a,b){
//	    			  if($(b).width()==0){
////	    				  width = $(b).width();
//	    				  $(b).width($( window ).width());}
////	    				  itemWidth = $($(b).children()[0]).width();
////	    			  }else{
////	    				  $(b).width(width);
////	    				  $(b).children().each(function(index,value){
////	    					  $(value).width(itemWidth);
////	    				  });
////	    			  }
//	    			  });
//	    	  }
	      }
	    });
	    
	  });
}

function loadCarrousel(){
	$('.censmaterias').slick({
		  infinite: true,
		  arrows: true,
		  dots: true,
		  slidesToShow: 4,
		  slidesToScroll: 4,
		  responsive: [
		               {
		                 breakpoint: 1024,
		                 settings: {
		                   slidesToShow: 3,
		                   slidesToScroll: 3,
		                   infinite: true,
		                   dots: true
		                 }
		               },
		               {
		                 breakpoint: 600,
		                 settings: {
		                   slidesToShow: 2,
		                   slidesToScroll: 2
		                 }
		               },
		               {
		                 breakpoint: 480,
		                 settings: {
		                   slidesToShow: 1,
		                   slidesToScroll: 1
		                 }
		               }
		             ]
		});
}

function cargarDatos(data){
	var title = '<h3>{cursoName}</h3>';
	var divMateria = '<div class="censmaterias" id="{cursoId}"></div>';
	var divPorlet = '<div class="portlet" id="{id}"></div>';
	var divPorletHeader =  '<div class="portlet-header">{name}</div>';
	var divPorletContet =  '<div class="portlet-content"></div>';
	var list ='<ul><li><a href=""+pagePath+"/asignatura/{id}/programa">Programa</a></li><li><a href=""+pagePath+"/asignatura/{id}/material">Material Did&aacute;ctico</a></li><li>Sugerencias</li></ul>';
	var currentDiv = null;
	currentDiv =($('#yearContent').append('<div class="censaccordion"></div>')).children();
	$.each(data.cursoAsignatura,function(index,value){		
		currentDiv.append(title.replace("{cursoName}",(value.nombre+"("+value.yearCurso+")")));
		currentDiv = currentDiv.append("<div class='censmaterias' id='curso"+value.id+"'></div>");
		
		$.each(value.asignaturasDelCursoDto,function(index,asignatura){	
			var currentPorlet = null;
			currentPorlet =$("#curso"+value.id).append(divPorlet.replace("{id}","asignatura"+asignatura.id));
			currentPorlet = $("#asignatura"+asignatura.id).append(divPorletHeader.replace("{name}",asignatura.nombre));
			currentPorlet = currentPorlet.append(divPorletContet);
			currentPorlet = $(currentPorlet.children()[1]).append(list.replace("{id}",asignatura.id).replace("{id}",asignatura.id));			
		});
		});
}
 