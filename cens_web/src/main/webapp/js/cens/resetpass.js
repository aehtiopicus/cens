$(document).ready(function(){
	

	$("#reinicio").dialog({
		autoOpen: false,
		width: 350,
		buttons: [			
			{
				text: "Reiniciar",
				click: function() {
					logout();				
				}
			}
		]
	});
});	

function reiniciar(){
	$("#reinicio").dialog("open");
	setTimeout(function(){ logout(); }, 5000);
}
function changePassword(callback,id){
	startSpinner();
	closeAllErrors();
	$.ajax({
		  type: "PUT",
		  url: pagePath+"/api/usuario"+"/"+id+"/change",
		  data: prepareData(),
		  dataType:"json",
		  contentType:"application/json", 
		  success: function(value){
			 $('#cancelar').trigger("onclick");
			 stopSpinner();
			 callback();
		  },
		  error:function(value){
			  dataError = errorConverter(value);
			  stopSpinner();
			  if(dataError.errorDto != undefined && dataError.errorDto){
					if(!validationError(dataError,true)){				  					  	  	  
			  	  		alert("Se produjo un error el servidor");
			  	  	}
				  
			  }else{
		  	  		alert("Se produjo un error el servidor");
		  	  }
			  
		  }
		  
	
	});
}
function prepareData(){
	return JSON.stringify({oldPass:$('#passwordOld').val() , newPass:$('#password').val(), newPassConfirm:$('#passwordNewConfirm').val()});
}