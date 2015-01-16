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
	
	$( "#profesorSuplente" ).autocomplete({     
		   delay: 500,
		   minLength: 4,
		   source : function(request,response){
			   cargarDatos('profesorSuplente',request.term,'profesor',response);
		   },
		  
		   select : function(event,ui){
			   $( "#profesorSuplente" ).val(ui.item.label);		
			   $( "#profesorSuplenteId" ).val(ui.item.value);
			   $( "#profesorSuplenteName" ).val(ui.item.label);
			return false;
			},
			change : function(event,ui){
				if(ui.item != null){
					$( "#profesorSuplente" ).val(ui.item.label);	
					$( "#profesorSuplenteId" ).val(ui.item.value);
					$( "#profesorSuplenteName" ).val(ui.item.label);
				}else{
					if($( "#profesorSuplente" ).val().length==0){
						$( "#profesorSuplenteId" ).val("");
						$( "#profesorSuplenteName" ).val("");
					}else{
						$( "#profesorSuplente" ).val($( "#profesorSuplenteName" ).val());						
					}
				}
			return false;
			}
	    });
	
	$( "#curso" ).autocomplete({     
		   delay: 500,
		   minLength: 4,
		   source : function(request,response){
			   cargarDatos('curso',request.term,'curso',response);
		   },
		  
		   select : function(event,ui){
			   $( "#curso" ).val(ui.item.label);		
			   $( "#cursoId" ).val(ui.item.value);
			   $( "#cursoName" ).val(ui.item.label);
			return false;
			},
			change : function(event,ui){
				if(ui.item != null){
					$( "#curso" ).val(ui.item.label);	
					$( "#cursoId" ).val(ui.item.value);
					$( "#cursoName" ).val(ui.item.label);
				}else{
					if($( "#curso" ).val().length==0){
						$( "#cursoId" ).val("");
						$( "#cursoName" ).val("");
					}else{
						$( "#curso" ).val($( "#cursoName" ).val());						
					}
				}
			return false;
			}
	    });


if(!isNaN((parseInt(window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1))))){
	$.ajax({
		url: pagePath+"/asignatura/"+parseInt(window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1)),
		type: "GET",
		contentType :'application/json',
		dataType: "json",		
		success: function(data){
			$('#id').val(data.id);
			$('#nombre').val(data.nombre);
			$('#modalidad').val(data.modalidad);
			$('#horarios').val(data.horarios);
			if(data.profesor != null){
				$('#profesor').val(data.profesor.miembroCens.apellido+", "+data.profesor.miembroCens.nombre+" ("+data.profesor.miembroCens.dni+")");
				$('#profesorId').val(data.profesor.id);
				$('#profesorName').val(data.profesor.miembroCens.apellido+", "+data.profesor.miembroCens.nombre+" ("+data.profesor.miembroCens.dni+")");
			}
			if(data.profesorSuplente != null){
				$('#profesorSuplente').val(data.profesorSuplente.miembroCens.apellido+", "+data.profesorSuplente.miembroCens.nombre+" ("+data.profesorSuplente.miembroCens.dni+")");
				$('#profesorSuplenteId').val(data.profesorSuplente.id);
				$('#profesorSuplenteName').val(data.profesor.miembroCens.apellido+", "+data.profesorSuplente.miembroCens.nombre+" ("+data.profesorSuplente.miembroCens.dni+")");
			}
			$('#curso').val(data.curso.nombre+" ("+data.curso.yearCurso+")");
			$('#cursoId').val(data.curso.id);
			$('#cursoName').val(data.curso.nombre+" ("+data.curso.yearCurso+")");
			$('#vigente').prop("checked",data.vigente);
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
		data:{ requestData:prepareJsonRequestData(field,url,value)},
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

function prepareJsonRequestData(field,url,value){
	var request = {"page": 1,"row": 10,"sord":"asc","filters":{}};
	if(url === "profesor"){
		request.filters = {"data":value};
		if(field==="profesorSuplente" && $('#profesorId').val().length>0){
			request.filters.profesor = $('#profesorId').val();
		}
		if(field==="profesor" && $('#profesorSuplenteId').val().length>0){
			request.filters.profesor = $('#profesorSuplenteId').val();
		}
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


function submitAsignatura(url){

	var post =$('#id').length == 0;
			
	$.ajax({
		  type: post ? "POST" : "PUT",
		  url: post? url :(url+"/"+ $('#id').val()),
		  data: JSON.stringify(prepareData(post)),
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


function prepareData(post){
		var profesor = {id:$('#profesorId').val()};
		var profesorSuplente = {id:$('#profesorSuplenteId').val()};
		var curso = {id:$('#cursoId').val()}
		var asignatura = {id: $("#id").val() == undefined ? null : $("#id").val(), nombre : $('#nombre').val(), modalidad : $('#modalidad').val(), horarios : $('#horarios').val(), vigente:$('#vigente').prop("checked")}
		asignatura.profesor = profesor;
		asignatura.profesorSuplente = profesorSuplente;
		asignatura.curso = curso;
		
			
		if(post){
			return [asignatura];
		}
		return asignatura;
}

