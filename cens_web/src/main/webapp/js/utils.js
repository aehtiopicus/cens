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


