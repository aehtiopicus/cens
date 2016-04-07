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

function changePassword(id){
	startSpinner();
	closeAllErrors();
	$.ajax({
		  type: "POST",
		  url: pagePath+"/api/usuario"+"/"+id+"/changepass",
		  data: prepareData(),
		  dataType:"json",
		  contentType:"application/json", 
		  success: function(value){
			 $('#cancelar').trigger("onclick");			 
			 logout();
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