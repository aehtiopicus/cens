
jQuery(document).ready(function () {
	


	if(!isNaN(pageId())){
		startSpinner();
		$.ajax({
			url: pagePath+"/asignatura/"+asignaturaId+"/programa/"+pageId(),
			type: "GET",	    	    
			contentType :'application/json',
			dataType: "json",    
			success : function(result){
				$('#id').val(result.id);
				$('#nombre').val(result.nombre);
				$('#descripcion').val(result.descripcion);
				$('#cantCartillas').val(result.cantCartillas);
				$('#profesor').val(result.profesorData);
				profesorId = result.profesorId;
									
				$('#downloadPrograma').prop("download",result.programaAdjunto);
				$('#downloadPrograma').prop("href",pagePath+"/asignatura/"+asignaturaId+"/programa/"+result.id+"/archivo");
				$('#downloadPrograma').html("Descargar el programa "+result.programaAdjunto);
				stopSpinner();				
			},
			error: function(value){
				errorData = errorConverter(value);
				if(errorData.errorDto != undefined && value.errorDto){
					alert(errorConverter(value).message);
				}else{
					alert("Se produjo un error el servidor");
				}
				stopSpinner();
			}		
		});
	}
	  
  

   $('#accordion').comment({
       title: 'Comentarios',
       url_get: pagePath+'/comentario/comments/list',
       url_input: pagePath+'/comentario/comments/list',
       url_delete: pagePath+'/comentario/comments/list',
       url_open_attachment: pagePath+'/comentario/comments/list/{id}/attachment',
       url_remove_attachment: pagePath+'/comentario/comments/list/{id}/attachment',
       arguments:{tipoType:"PROGRAMA",tipoId:programaId,usuarioId:asesorId,usuarioTipo:"ASESOR"},
       limit: 10,
       auto_refresh: false,
       refresh: 10000,
       transition: 'slideToggle',
       uploadProgress: function(data){    	   
    		     if(parseInt(data.loaded / data.total * 100, 10)<100){
    		    	 if(!$('body').hasClass('loading')){
    		    		 startSpinner();
    		    	 }
    		     }else{
    		    	 stopSpinner();
    		     }    		        		
       }
     });
});









function guardarSinArchivo(){
 	var post =$('#id').length == 0;
  	
  $.ajax({
    url:  post ? pagePath+"/asignatura/"+asignaturaId+"/programanf" : (pagePath+"/asignatura/"+asignaturaId+"/programanf/"+$('#id').val()),
    type: post ? "POST" : "PUT",
    data: cargarData(),
    dataType:"json",
	  contentType:"application/json", 
    success : function(result){    		 
		 $('#cancelar').trigger("click");
    },
    error: function(value){
    	 errorData = errorConverter(value);
			if(errorData.errorDto != undefined && value.errorDto){
				alert(errorConverter(value).message);
			}else{
				 alert("Se produjo un error el servidor");
			}
    }
  });  
}




