function errorConverter(data){
	return JSON.parse(data.responseText);
}

function errorDivs(errorDiv,field,value){	
	if(checkDivNoExist(errorDiv)){
		var template ='<div id="{replaceDivId}"><label for="{replaceFor}" class="ui-state-error">{replace}</label><label class="ui-state-error-img" onclick="closeError(\'{replaceDivId}\')"></label></div>';		
		return template.replace('{replace}',value).replace('{replaceFor}',field).replace('{replaceDivId}',errorDiv).replace('{replaceDivId}',errorDiv);	
	}
}

function addError(field,value){
	var errorDiv = field+"ErrorDiv";
	$('#'+field).parent().append(errorDivs(errorDiv,field,value));
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
