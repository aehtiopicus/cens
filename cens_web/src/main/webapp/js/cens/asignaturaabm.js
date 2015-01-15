jQuery(document).ready(function () {

	$( "#profesor" ).autocomplete({     
		   delay: 500,
		   minLength: 4,
		   source : function(request,response){
			   cargarDatos('profesor',request.term,'profesor',response);
		   },
		  
		   select : function(event,ui){
			   $( "#profesor" ).val(ui.item.label);		
			   $( "#profesorId" ).val(ui.item.value);
			   $( "#profesorName" ).val(ui.item.label);
			return false;
			},
			change : function(event,ui){
				if(ui.item != null){
					$( "#profesor" ).val(ui.item.label);	
					$( "#profesorId" ).val(ui.item.value);
					$( "#profesorName" ).val(ui.item.label);
				}else{
					if($( "#profesor" ).val().length==0){
						$( "#profesorId" ).val("");
						$( "#profesorName" ).val("");
					}else{
						$( "#profesor" ).val($( "#profesorName" ).val());						
					}
				}
			return false;
			}
	    });


if(!isNaN((parseInt(window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1))))){
	$.ajax({
		url: pagePath+"/curso/"+parseInt(window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1)),
		type: "GET",
		contentType :'application/json',
		dataType: "json",		
		success: function(data){
			$('#id').val(data.id);
			$('#nombre').val(data.nombre);
			$('#yearCurso').val(data.yearCurso);
		},
		error: function(data){
			errorData = errorConverter(value);
			if(errorData.errorDto != undefined && value.errorDto){
				alert(errorConverter(value).message);
			}else{
				 alert("Se produjo un error el servidor");
			}
		}
	});
	}

});

function cargarDatos(field,value,url,response){
	$.ajax({
		url: pagePath+"/"+url,
		type: "GET",
		contentType :'application/json',
		dataType: "json",
		data:{ requestData:prepareJsonRequestData(url,value)},
		success: function(data){		
			response( assembleAutocompleteJson(data,url));
		},
		error: function(errorData){
			errorData = errorConverter(errorData);
			if(errorData.errorDto != undefined && errorData.errorDto){
				addError(field,errorData.message);
			}else{
				alert("Se produjo un error el servidor");
			}
		}
	});	
}

function prepareJsonRequestData(url,value){
	var request = {"page": 1,"row": 10,"sord":"asc","filters":{}};
	if(url === "profesor"){
		request.filters = {"data":value};
	}else if(url === "curso"){
		request.filters = {"nombre":value};
	}
	return JSON.stringify(request);
}

function assembleAutocompleteJson(data,url){
	
	var fieldData = [];

		$.each(data.rows,function(index2,value2){
				if(url ==="profesor"){
					fieldData.push({"value":value2.id, "label" : value2.miembroCens.apellido+", "+value2.miembroCens.nombre+" ("+value2.miembroCens.dni+")"});
				}else if(url ==="curso"){
					fieldData.push({"value":value2.id, "label" : value2.nombre+" ("+value2.yearCurso+")"});
				}
			});

	return fieldData;
}


function submitCurso(url){

	var post =$('#id').length == 0;
			
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
function validationError (error){
	if(error.errorDto != undefined && error.errorDto){
		for(var key in error.errors) {
			addError(key,error.errors[key]);
		}
		return true;
	}
	return false;
}

function prepareData(post){
				
		var curso='{"id":'+($("#id").length == 0 ? null : $("#id").val())+',"nombre":"'+$('#nombre').val()+'","yearCurso":"'+$('#yearCurso').val()+'"}';		
		var postData = '[{post}]';		
		if(post){
			curso = postData.replace('{post}',curso);
		}
		return curso;
}

