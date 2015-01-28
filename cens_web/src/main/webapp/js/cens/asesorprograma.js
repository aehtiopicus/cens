
jQuery(document).ready(function () {


	$("#progressbar").progressbar({
     value: 0,
     change: function() {
    	 $(".progress-label").text($("#progressbar").progressbar( "value" ) + "%" );
     },
     complete: function() {
    	 $(".progress-label").text( "Carga completa!" );
    	 startSpinner();
     }
   });
	

   $( "#estadoPrograma" ).dialog({
		autoOpen: false,
		width: 700,			
		modal:true,
		resizable:false,
		buttons: [
			{
				text: "Ok",
				click: function() {
					$('#btnGuardarPrograma').trigger("click");
				}
			},
			{
				text: "Cancelar",
				click: function() {
					$( this ).dialog( "close" );	
					$( "#progressbar" ).progressbar( "option", "value", 0 );
					 $(".progress-label").text( "" );
					
				}
			}
		]
	});
   
   $( "#comentariosPrograma" ).dialog({
		autoOpen: false,
		width: 700,
		modal:true,
		resizable:false,
		buttons: [
			{
				text: "Ok",
				click: function() {
					 $.ajax({
						    url:  $('#downloadPrograma').prop("href"),
						    type: 'DELETE',						    
						    dataType:"json",
							contentType:"application/json", 
						    success : function(result){    		 
								 location.href = location.href;
						    },
						    error: function(value){
						    	$( this ).dialog( "close" );	
						    	 errorData = errorConverter(value);
									if(errorData.errorDto != undefined && value.errorDto){
										alert(errorConverter(value).message);
									}else{
										 alert("Se produjo un error el servidor");
									}
						    }
						  });  
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

   $('#accordion').comment({
       title: 'Comentarios',
       url_get: pagePath+'/comentario/1/comments/list',
       url_input: pagePath+'/comentario/1/comments/list',
       url_delete: pagePath+'/comentario/id/1/comments/delete',
       arguments:{tipoType:"PROGRAMA",tipoId:programaId,usuarioId:asesorId,usuarioTipo:"ASESOR"},
       limit: 10,
       auto_refresh: false,
       refresh: 10000,
       transition: 'slideToggle',
     });
});


function openPrograma(){
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
				$('#programaadjunto').val(result.programaAdjunto);					
				$('#downloadPrograma').prop("download",result.programaAdjunto);
				$('#downloadPrograma').prop("href",pagePath+"/asignatura/"+asignaturaId+"/programa/"+result.id+"/archivo");					
				stopSpinner();
				$('#estadoPrograma').dialog('open');
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
}





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




