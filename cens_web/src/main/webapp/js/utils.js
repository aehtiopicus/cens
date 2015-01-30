function errorConverter(data){
	return JSON.parse(data.responseText);
}

function jsonConverter(data){
	return JSON.parse(data);
}

function errorDivs(errorDiv,field,value){	
	if(checkDivNoExist(errorDiv)){
		var template ='<div id="{replaceDivId}"><label for="{replaceFor}" class="ui-state-error">{replace}</label><label class="ui-state-error-img" onclick="closeError(\'{replaceDivId}\')"></label></div>';		
		return template.replace('{replace}',value).replace('{replaceFor}',field).replace('{replaceDivId}',errorDiv).replace('{replaceDivId}',errorDiv);	
	}
}

function addError(field,value){
	var errorDiv = field+"ErrorDiv";
	getErrorParenter($('#'+field)).append(errorDivs(errorDiv,field,value));
}

function getErrorParenter(field){
	var parentDiv; 
	if(field.parent().is("div")){
		return field.parent();
	}else{
		return getErrorParenter(field.parent());
	}
}

function validationError (error){
	if(error.errorDto != undefined && error.errorDto){
		for(var key in error.errors) {
			addError(key,error.errors[key]);
		}
		return true;
	}
	return false;
}
function closeAllErrors(){
	$('.ui-state-error-img').remove();
	$('.ui-state-error').remove();
	
}
function closeError(value){
	$('#'+value).remove();
}

function checkDivNoExist(value){
	return $('#'+value).length==0;
}

function checkDate(datevalue){
	  re = /^\d{4}\-\d{2}\-\d{2}$/;	  
	  if(datevalue === '' || !datevalue.match(re)) {	    
	    return false;
	  }
	  return true;
}

function cargarMensaje(data,ok){
	//cargo mensaje en pantalla
	if(ok){
		$('#message').text(data.message)
	}else{
		if(data.errorDto != undefined && data.errorDto){
			$('#message').text(data.message)
		}else{
			$('#message').text("Se produjo un error en el servidor");
		}
	}
	setTimeout("$('#message').text('')", 5000);
}

function fixTable(){
	$('table').width('100%');
	$($('.ui-jqgrid-hbox ')[0]).width('100%')
}

function pageId(){
	return (parseInt(window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1)));
}


function startSpinner(){
	$("body").addClass("loading");   
}

function stopSpinner(){
	$("body").removeClass("loading");
}

function convertDate(value){
	var a =(new Date(value));
	date = a.getDate().toString();
	date = date.length == 1 ? 0+date : date;
	month = (a.getMonth()+1).toString();
	month = month.length == 1 ? 0+month : date;
	return a.getFullYear()+"-"+month+"-"+ date;
}

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


function loadCarrousel(){
	
		materias  = ".censmaterias";

	$(materias).slick({
		  infinite: true,
		  arrows: true,
		  dots: true,
		  slidesToShow: 4,
		  slidesToScroll: 4,	
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
		                   slidesToScroll: 2,
		                   infinite: true,
     						dots: true
		                 }
		               },
		               {
		                 breakpoint: 550,
		                 settings: {
		                   slidesToShow: 2,
		                   slidesToScroll: 1,
		                   infinite: true,
     						dots: true
		                 }
		               }
		              
		             ]
		});
	
}

function randomId(){
	return (((1+Math.random())*0x10000)|0).toString(16).substring(1) +"-"+ (((1+Math.random())*0x10000)|0).toString(16).substring(1);
}




/**deprecated**
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


*/