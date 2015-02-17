$(document).ajaxStart(function() {
	startSpinner();
});
$(document).ajaxStop(function() {
	stopSpinner();	
});
jQuery(document).ready(function () {
	


	if(!isNaN(pageId())){
		startSpinner();
		$.ajax({
			url: pagePath+"/api/programa/"+programaId+"/material/"+pageId(),
			type: "GET",	    	    
			contentType :'application/json',
			dataType: "json",    
			success : function(result){
				$('#id').val(result.id);
				$('#nombre').val(result.nombre);
				$('#descripcion').val(result.descripcion);
				$('#divisionPeriodoType').val(result.divisionPeriodoType);
				if(result.cartillaAdjunta!=null){
					$('#cartillaAdjuntado').toggle();
					$('#fileUp').toggle();
					$('#downloadCartillaAdjunta').prop("download",result.cartillaAdjunta);
					$('#downloadCartillaAdjunta').prop("href",pagePath+"/api/programa/"+programaId+"/material/"+result.id+"/archivo");					
					
					$('#downloadCartillaAdjunta').html('Descargar la cartilla \"'+result.cartillaAdjunta+'\"');
					link_text_file_remove =  $("#eliminarCartillaAdjunto");
    				link_text_file_remove.on("click",function(){
    					$( '#borrarCartilla').dialog('open');
    				});
    				link_text_file_remove.toggle();
    				
				}
				stopSpinner();
			},
			error: function(value){
				errorData = errorConverter(value);
				if(errorData.errorDto != undefined && errorData.errorDto){
					if(validationError(errorData)){
				  		alert($('<div/>').html(errorData.message).text() );
					}
					
				}else{
					alert("Se produjo un error el servidor");
				}
				stopSpinner();
			}		
		});
	}
	
	 $("#cambiarEstado").dialog({
			autoOpen: false,
			width: 500,
			resizable:false,
			buttons: [
				{
					text: "Ok",
					click: function() {
						$( this ).dialog( "close" );
						dejarComentario();
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
       url_get: pagePath+'/api/comentario/comments/list',
       url_input: pagePath+'/api/comentario/comments/list',
       url_delete: pagePath+'/api/comentario/comments/list',
       url_open_attachment: pagePath+'/api/comentario/comments/list/{id}/attachment',
       url_remove_attachment: pagePath+'/api/comentario/comments/list/{id}/attachment',
       arguments:{tipoType:"MATERIAL",tipoId:materialId,usuarioId:asesorId,usuarioTipo:"ASESOR"},
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
   
   $('#estado').on("change",function(){
	   if($('#estado').val()!==$('#estadoActual').val()){
		   $('#btnGuardar').button("enable");
	   }else{
		   $('#btnGuardar').button("disable");
	   }
   });
});




function cambiarEstado(){
	if($('#estado').val()!==$('#estadoActual').val()){
		$('#cambiarEstadoValue').html('&iquest;Desea cambiar el estado del Material Did&aacute;ctico de '+$("#estadoActual").val()+' a '+$("#estado").val()+'?');
		$('#cambiarEstado').dialog("open");
		
	}
};

function dejarComentario(){
	
	$.ajax({
	    url:  pagePath+"/api/programa/"+programaId+"/material/"+$('#id').val()+"/estado",
	    type: "PUT",
	    data: JSON.stringify({estadoRevisionType: $("#estado").val()}),
	    dataType:"json",
		  contentType:"application/json", 
	    success : function(result){    		 
	    	$('#cancelar').trigger("click");
	    	$('#accordion >div >div >div > form> textarea').val('Cambio de estado del Material Did&aacute;ctico de '+$("#estadoActual").val()+' a '+$("#estado").val());
	    	$($('#accordion >div >div >div > form> archivos >div >button')[1]).trigger("click");
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
	
	

};




