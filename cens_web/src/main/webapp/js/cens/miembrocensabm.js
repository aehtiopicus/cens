jQuery(document).ready(function () {
		

	$(function() {
		   $(".hasdatepicker").datepicker({
		      dateFormat: "yy-mm-dd"
		   });
		});
	if(!isNaN((parseInt(window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1))))){
	$.ajax({
		url: pagePath+"/miembro/"+parseInt(window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1)),
		type: "GET",
		contentType :'application/json',
		dataType: "json",		
		success: function(data){
			$('#id').val(data.id);
			$('#usuarioid').val(data.usuario.id);
			$('#fechaNac').val(convertDate(data.fechaNac));
			$('#username').val(data.usuario.username);
			$('#nombre').val(data.nombre);
			$('#apellido').val(data.apellido);
			$('#dni').val(data.dni);
			$.each($('#perfilList li input') ,function(index,val) {
				$.each($(data.usuario.perfil),function(index2,val2){					
					 if($(val).val()===val2.perfilType)
					  {
						 $(val).prop("checked",true);
					  }
								 
				});
			});
		},
		error: function(data){
			errorData = errorConverter(value);
			if(errorData.errorDto != undefined){
				alert(errorConverter(value).message);
			}else{
				 alert("Se produjo un error el servidor");
			}
		}
	});
	}
});

function convertDate(value){
	var a =(new Date(value));
	date = a.getDate().toString();
	date =date.length == 1 ? 0+date : date;
	return a.getFullYear()+"-"+(a.getMonth()+1)+"-"+ date;
}

function submitMiembro(url){

	var post =$('#id').length == 0;
	if(checkForm(post)){		
	$.ajax({
		  type: post ? "POST" : "PUT",
		  url: post? url :(url+"/"+ $('#id').val()),
		  data: prepareData(post),
		  dataType:"json",
		  contentType:"application/json", 
		  success: function(value){
			 $('#cancelar').trigger("onclick");
		  },
		  error:function(value){
			  if(validationError(errorConverter(value))){
				  alert(errorConverter(value).message );
			  }else{
				  alert("Se produjo un error el servidor");
			  }
		  }
		  
		});
	}
}	
function validationError (error){
	if(error.errorDto != undefined){
		for(var key in error.errors) {
			addError(key,error.errors[key]);
		}
		return true;
	}
	return false;
}

function prepareData(post){
		var tipo =[];
		$.each($('#perfilList li input') ,function(index,val) {
			  if($(val).prop("checked"))
			  {
				  tipo.push('{"perfilType":"'+($(val).prop('value'))+'"}'); 
			  }
			});
		
		var usuario='{"id":'+($("#usuarioid").length == 0 ? null : $("#usuarioid").val())+',"username":"'+$('#username').val()+'","password":"'+$('#password').val()+'","passwordConfirm":"'+$('#passwordConfirm').val()+'","perfil":['+tipo+']}';		
		var postData = '[{post}]';
		var miembroCens ='{"id":'+($("#id").length == 0 ? null : $("#id").val())+',"nombre":"'+$('#nombre').val()+'","apellido":"'+$('#apellido').val()+'","dni":"'+$('#dni').val()+'","fechaNac":"'+$('#fechaNac').val().replace("/","-")+'","usuario":'+usuario+'}';
		if(post){
			miembroCens = postData.replace('{post}',miembroCens);
		}
		return miembroCens;
}


function checkForm(post){
	var error = true;
	if(!checkDate($('#fechaNac').val())){
		addError('fechaNac',"Fecha invalida");
		error = false;
	}
	
	if(post && (($('#password').val().length !=$('#passwordConfirm').val().length) || ($('#password').val()!==$('#passwordConfirm').val()))){
		addError('password',"Contraseña invalida");
		error = false;
	}
	return error;
 
}

