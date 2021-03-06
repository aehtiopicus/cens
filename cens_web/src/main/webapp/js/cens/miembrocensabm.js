jQuery(document).ready(function () {
		

	if(!isNaN(pageId())){
	$.ajax({
		url: pagePath+"/api/miembro/"+pageId(),
		type: "GET",
		contentType :'application/json',
		dataType: "json",		
		success: function(data){
			$('#id').val(data.id);
			$('#usuarioid').val(data.usuario.id);
			$('#fechaNac').datepicker( "setDate", new Date(new Date(data.fechaNac).getTime()+3*(60*60*1000) ));//val(convertDate(data.fechaNac));
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
			errorData = errorConverter(data);
			if(errorData.errorDto != undefined && data.errorDto){
				alert(errorConverter(data).message);
			}else{
				 alert("Se produjo un error el servidor");
			}
		}
	});
	}
	$( "#remAsignaturas" ).dialog({
		autoOpen: false,
		width: 400,
		buttons: [
			{
				text: "Ok",
				click: function() {
					$( this ).dialog( "close" );
					deleteAsignaturas();
				}
			},
			{
				text: "Cancelar",
				click: function() {
					$( this ).dialog( "close" );
					$('#profesorId').val("");
				}
			}
		]
	});
});


function submitMiembro(){

	var post =$('#id').length == 0;
	if(checkForm(post)){		
	$.ajax({
		  type: post ? "POST" : "PUT",
		  url: post? pagePath+"/api/miembro" :(pagePath+"/api/miembro"+"/"+ $('#id').val()),
		  data: prepareData(post),
		  dataType:"json",
		  contentType:"application/json", 
		  success: function(value){
			 $('#cancelar').trigger("onclick");
		  },
		  error:function(value){
			  dataError = errorConverter(value);
			  if(dataError.errorDto != undefined && dataError.errorDto){
				  var asignatura = false;
				  
				  for(var key in dataError.errors) {
						if(key==="profesorId"){
							$('#profesorId').val(dataError.errors[key]);
							asignatura = true;
							break;
						}					
					}
				  if(asignatura){
					  $("#remAsignaturas").dialog("open");
				  }else{
					  if(validationError(dataError)){
				  		alert(dataError.message );
			  	  	  }else{
			  	  		alert("Se produjo un error el servidor");
			  	  	}
				  }
			  }
		  }
		  
		});
	}
}	


function prepareData(post){
		var tipo =[];
		$.each($('#perfilList li input') ,function(index,val) {
			  if($(val).prop("checked"))
			  {
				  tipo.push({
					  perfilType:($(val).prop('value'))
					  }); 
			  }
			});
		
		var usuario={
				id:($("#usuarioid").length == 0 ? null : $("#usuarioid").val()),
				username:$('#username').length ? $('#username').val() : null,
				perfil:tipo
				};		
		
		var miembroCens ={
				id:($("#id").length == 0 ? null : $("#id").val()),
				nombre:$('#nombre').val(),
				apellido:$('#apellido').val(),
				dni:$('#dni').val(),
				fechaNac:$('#fechaNac').datepicker("getDate").toISOString(),
				usuario:usuario
		};
		if(post){
			miembroCens = [miembroCens];
		}
		return JSON.stringify(miembroCens);
}


function checkForm(post){
	var error = true;
	if(!checkDate($('#fechaNac').val())){
		addError('fechaNac',"Fecha invalida");
		error = false;
	}
	
	
	return error;
 
}

function deleteAsignaturas(){	
	

	$.ajax({
		type:"DELETE",
		url:pagePath+"/api/profesor/"+$('#profesorId').val()+"/removerasignaturas",
		contentType :'application/json',
		dataType:"json",
		success: function(data){
			submitMiembro();
		},
		error: function(data){			
			var errorData =errorConverter(data);
			if(errorData.errorDto===false){
				alert("Se produjo un error el servidor")
			}
		}								

		}
		
	);
	
} 

