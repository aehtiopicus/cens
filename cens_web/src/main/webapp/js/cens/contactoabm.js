$(document).ajaxStart(function() {
	startSpinner();
});
$(document).ajaxStop(function() {
	stopSpinner();	
});

$(document).ready(function(){
	$('#email').on("blur", emailBlur);
	$.ajax({
		url: pagePath+"/api/miembro/"+miembroId+"/contacto/email",
		method: "GET",
		contentType:"application/json",
		success : function(data){
			if(data && data.length){
				$.each(data,function(index,value){
					$('#email').val(value.datoContacto);
					$('#emailOriginal').val(value.datoContacto);
					$('#emailId').val(value.id);
				});
			}
		}
	});
		
});

function emailBlur(event){
	if(event.target.value.length == 0 && $('#emailId').val().length != 0 ){
		$.ajax({
			url: pagePath+"/api/miembro/"+miembroId+"/contacto/email/"+$('#emailId').val(),
			method: "DELETE",
			success : function(data){		
				$('#emailId').val("");
				$('#emailOriginal').val("");
				closeAllErrors();
			},
			error : function(errorData){
				  addError("email","Error al eliminar el email");
			}
		});
	}else{
		if($('#emailOriginal').val()!== event.target.value && checkEmail(event.target.value)){
			closeAllErrors();
			post = $('#emailId').val().length == 0;
			$.ajax({
				url: post ? pagePath+"/api/miembro/"+miembroId+"/contacto/email" : (pagePath+"/api/miembro/"+miembroId+"/contacto/email/"+$('#emailId').val()),
					method: post ? "POST" : "PUT",
					contentType :'application/json',
				dataType: "json",
				data:loadData(event.target.value,post),
				success : function(data){
					
					$('#emailId').val(data.id);
					$('#emailOriginal').val(data.datoContacto);
					closeAllErrors();
				},
				error : function(errorData){
					if(errorData.errorDto != undefined && errorData.errorDto){
						  	if(!validationError(errorData)){					  		
							  addError("email","Error al guardar el email");
						  }
				}
				  
				}
			});
		}else{
			if(event.target.value!==$('#emailOriginal').val()){
				addError("email","Email no v&aacute;lido");
			}else{
				closeAllErrors();
			}
		}
	}
}
function loadData(data,post){
	var img = {
			id : $('#emailId').val().length ? $('#emailId').val() : null,
			datoContacto : data,
			miembroCens : {id : miembroId},
			tipoContacto : 'MAIL'
			};
	if(post){
		img = [img];
	}
	return JSON.stringify(img);
} 