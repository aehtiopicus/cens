jQuery(document).ready(function () {
	startSpinner();		
	$.ajax({
		url: pagePath+"/api/miembro/"+miembroId,
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
			
			perfiles="";
			$.each($(data.usuario.perfil),function(index,val){					
				perfiles = perfiles +val.perfilType+", ";
								 
			});
			$('#perfilUsuario').html( perfiles.substring(0,perfiles.length-2));
			stopSpinner();
			
		},
		error: function(data){
			stopSpinner();
			errorData = errorConverter(data);
			if(errorData.errorDto != undefined && data.errorDto){
				alert(errorConverter(data).message);
			}else{
				 alert("Se produjo un error el servidor");
			}
		}
	});
	
	$( "#cambiarPass" ).on("click",function(){
		$( "#cambiarPassword" ).dialog("open");
	});
	$( "#cambiarPassword" ).dialog({
		autoOpen: false,
		width: 450,
		buttons: [
			{
				text: "Guardar",
				click: function() {
					cambiarPass(function(){
						$( "#cambiarPassword" ).dialog( "close" );
					});
					deleteAsignaturas();
				}
			},
			{
				text: "Cancelar",
				click: function() {
					$( this ).dialog( "close" );					
				}
			}
		]
	});
});

function cambiarPass(cambiarAsignatura){
	cambiarAsignatura();
	
}

function submitMiembro(){
	
	if(checkForm(post)){		
	$.ajax({
		  type: "PUT",
		  url: pagePath+"/api/miembro"+"/"+ $('#id').val(),
		  data: prepareData(),
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

cambiar aca para ver si sale del pass o no
function prepareData(){
		var tipo =[];
		$.each($('#perfilList li input') ,function(index,val) {
			  if($(val).prop("checked"))
			  {
				  tipo.push('{"perfilType":"'+($(val).prop('value'))+'"}'); 
			  }
			});
		
		var usuario='{"id":'+($("#usuarioid").length == 0 ? null : $("#usuarioid").val())+',"username":"'+$('#username').val()+'","password":"'+$('#password').val()+'","passwordConfirm":"'+$('#passwordConfirm').val()+'","perfil":['+tipo+']}';		
		var postData = '[{post}]';
		var miembroCens ='{"id":'+($("#id").length == 0 ? null : $("#id").val())+',"nombre":"'+$('#nombre').val()+'","apellido":"'+$('#apellido').val()+'","dni":"'+$('#dni').val()+'","fechaNac":"'+$('#fechaNac').datepicker("getDate").toISOString()+'","usuario":'+usuario+'}';
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
		
	return error;
 
}



