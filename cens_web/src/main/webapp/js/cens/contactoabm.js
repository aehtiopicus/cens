
$(document).ready(function(){
	$('#email').on("blur", emailBlur());
	
		
});

function emailBlur(event){
	if(event.target.value.length == 0 && $('#emailId').val().length != 0 ){
		$.ajax({
			url: pagePath+"/api/miembro/"+miembroId+"/contacto/email/"+$('#emailId').val(),
			method: "DELETE",
			success : function(data){				
			},
			error : function(errorData){
				
			}
		});
	}
	if(checkEmail(event.target.value)){
		closeAllErrors();
		post = $('#emailId').val().length == 0;
		$.ajax({
			url: post ? pagePath+"/api/miembro/"+miembroId+"/contacto/email" : (pagePath+"/api/miembro/"+miembroId+"/contacto/email/"+$('#emailId').val()),
			method: post ? "POST" : "PUT",
			contentType :'application/json',
			dataType: "json",
			data:loadData(event.target.value,post),
			success : function(data){
				
			},
			error : function(errorData){
				
			}
		});
	}else{
		addError("email","Email no v&aacute;lido");
	}
}
function loadData(data,post){
	var img = {id : $('#emailId').val().length ? $('#emailId').val() : null, datoContacto : data, miembro : {id : miembroId} };
	if(post){
		img = [img];
	}
	return JSON.stringify(img);
} 