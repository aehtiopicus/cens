function errorConverter(data){
	return JSON.parse(data.responseText);
}

function jsonConverter(data){
	return JSON.parse(data);
}
messageType =  {error:"error",success:"success",info:"info"};
function alert(message,messageTypeData){
	if(typeof messageTypeData === "undefined"){
		$.notify(message,"error",{position:'top'});
	}else{
		$.notify(message,messageType[messageTypeData],{position:'top'});
	}
}
function errorDivs(errorDiv,field,value,dialog){	
	if(checkDivNoExist(errorDiv)){
		var templateDiv = $('<div></div>');
		var templateLabel1 = $('<label class="ui-state-error"></label>');
		var templateLabel2 = $('<label class="ui-state-error-img"></label>');
		if(dialog){
			templateLabel1.addClass("dialog");
			templateLabel2.addClass("dialog");
		}
		templateDiv.attr("id",errorDiv);
		templateLabel1.attr("for",field);
		templateLabel1.html(value);		               
		templateLabel2.on("click",function(){closeError(errorDiv.toString());});
		templateDiv.append(templateLabel1);
		templateDiv.append(templateLabel2);		
		return templateDiv;

	}
}

function addError(field,value,dialog){
	var errorDiv = field+"ErrorDiv";
	getErrorParenter($('#'+field)).append(errorDivs(errorDiv,field,value,dialog));
}

function getErrorParenter(field){
	var parentDiv; 
	if(field.parent().is("div")){
		return field.parent();
	}else{
		return getErrorParenter(field.parent());
	}
}

function validationError (error,dialog){
	if(error.errorDto != undefined && error.errorDto){
		for(var key in error.errors) {
			addError(key,error.errors[key],dialog);
		}
		return true;
	}
	return false;
}
function closeAllErrors(){
	$('.ui-state-error-img').parent().remove();
	
	
}
function closeError(value){
	$('#'+value).remove();
}

function checkDivNoExist(value){
	return $('#'+value).length==0;
}

function checkDate(datevalue){
	  re = /^\d{2}\-\d{2}\-\d{4}$/;	  
	  if(datevalue === '' || !datevalue.match(re)) {	    
	    return false;
	  }
	  return true;
}

function checkEmail(dataValue){
	re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	 if(dataValue === '' || !dataValue.match(re)) {	    
		    return false;
		  }
		  return true;
}

function cargarMensaje(data,ok){
	//cargo mensaje en pantalla
	if(ok){
		alert($('<div/>').html(data.message).text(),"success");
	}else{
		if(data.errorDto != undefined && data.errorDto){
			alert($('<div/>').html(data.message).text());
		}else{
			alert("Se produjo un error en el servidor");
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