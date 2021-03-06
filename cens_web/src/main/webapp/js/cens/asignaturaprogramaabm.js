var asignaturaPrograma={
	 estadoMap :{"LISTO":true,"NUEVO":true,"ASIGNADO":false,"RECHAZADO":false,"APROBADO":false,"CAMBIOS":false} 	
};
jQuery(document).ready(function () {
	if(!isNaN(pageId())){
		startSpinner();
		$.ajax({
			url: pagePath+"/api/asignatura/"+asignaturaId+"/programa/"+pageId(),
			type: "GET",	    	    
			contentType :'application/json',
			dataType: "json",    
			success : function(result){
				$('#id').val(result.id);
				$('#nombre').val(result.nombre);
				$('#descripcion').val(result.descripcion);
				$('#cantCartillas').val(result.cantCartillas);
				if(!asignaturaPrograma.estadoMap[result.estadoRevisionType]){
					$("#editable").show();
					$("#cantCartillas").spinner("destroy");
					$(".programaDiv input[type='text'],.programaDiv textarea, .programaDiv select,.programaDiv ui-spinner-button").attr("disabled",true);
					$($(".footerForm >button").children()[0]).attr("disabled",true).addClass("ui-state-disabled");
					
				}
				
				if(result.programaAdjunto!=null){
					$('#programaAdjuntado').toggle();
					$('#fileUpPrograma').toggle();
					$('#downloadPrograma').prop("download",result.programaAdjunto);
					$('#downloadPrograma').prop("href",pagePath+"/api/asignatura/"+asignaturaId+"/programa/"+result.id+"/archivo");					
					
					$('#downloadPrograma').html('Descargar el programa \"'+result.programaAdjunto+'\"');
					link_text_file_remove =  $("#eliminarProgramaAdjunto");
    				link_text_file_remove.on("click",function(){
    					$( '#borrarPrograma').dialog('open');
    				});
    				link_text_file_remove.toggle();
    				
				}
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
		$('#comentariosTitulo').toggle();
		$('#accordion').comment({
		       title: 'Comentarios',
		       update_notification: true,
		       url_get: pagePath+'/api/comentario/comments/list',
		       url_input: pagePath+'/api/comentario/comments/list',
		       url_delete: pagePath+'/api/comentario/comments/list',
		       url_open_attachment: pagePath+'/api/comentario/comments/list/{id}/attachment',
		       url_remove_attachment: pagePath+'/api/comentario/comments/list/{id}/attachment',
		       arguments:{tipoType:"PROGRAMA",tipoId:pageId(),usuarioId:profesorId,usuarioTipo:"PROFESOR"},
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
	}else{
		$('#btnEliminarPrograma').prop("disabled",true);
	}
	$("#cantCartillas").spinner({
	    min : 1,
	    max : 12,   
	    showOn : 'both',
	    numberFormat: "n",    
	    	
	});
	$('#cantCartillas').val(1);
	$("#cantCartillas").focus(function(a) {
		  $( this ).parent().addClass('spanFocus');
	});
	$("#cantCartillas").focusout(function(a) {
			val = $('#cantCartillas').val();
			if(isNaN(val)){
				$('#cantCartillas').val(new Date().getFullYear());
			}else{
				if(parseInt(val)>$("#cantCartillas").spinner("option","max") || parseInt(val)< $("#cantCartillas").spinner("option","min")){
					$('#cantCartillas').val(new Date().getFullYear());
				}
			}
		  $( this ).parent().removeClass('spanFocus');
	});
	

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
	
   
   $( "#guardarPrograma" ).dialog({
		autoOpen: false,
		width: 400,
		modal:true,
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
   
   $( "#borrarPrograma" ).dialog({
		autoOpen: false,
		width: 400,
		modal:true,
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
						    	$('#programaAdjuntado').toggle();
						    	$('#fileUpPrograma').toggle();
						    	$('#borrarPrograma').dialog("close");
						    },
						    error: function(value){
						    	$("#borrarPrograma").dialog( "close" );	
						    	 errorData = errorConverter(value);
									if(errorData.errorDto != undefined){
										cargarMensaje(errorData);
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

});

$(function () {
	 
    $('#fileupload').fileupload({
    	
    	  url:null,
    	  
          done: function (e, data) {
              alert("done"); 
          },
          
          // The regular expression for allowed file types, matches
          // against either file type or file name:
          //acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,          
          // The maximum allowed file size in bytes:
          maxFileSize: 10000000, // 10 MB
          // The minimum allowed file size in bytes:
          minFileSize: undefined, // No minimal file size
          // The limit of files to be uploaded:
          maxNumberOfFiles: 1,
          
          add: function (e, data) {
        	  closeAllErrors()
        	  $('#fileUploadName').val("");
              $('#fileUploadUsed').val("false");
             
              if(!(/(\.|\/)(xlsx|xls|doc|docx|ppt|pptx|pps|ppsx|pdf)$/i).test(data.files[0].name)){
            	  addError('fileupload',"Tipo no Soportado");
            	  return false;
              }
              $('#fileUploadName').val(data.files[0].name);
              $('#fileUploadUsed').val("true");
             
              $("#btnGuardarPrograma").off();
              $("#btnGuardarPrograma").click(function () {
            	  data.process().done(function () {            		  
            		  data.submit();
                  });
              })              
            
          },
          submit: function(event,data){
        	  var formData = new FormData();
        	  	formData.append("file",data.files[0]);
        	  	if($('#nombre').val().length===0){
        	  		addError('nombre',"Nombre requerido");
        	  		return false;
        	  	}     	  	

        	  	formData.append('properties', new Blob([cargarData()], { type: "application/json" }));
        	  
        	  	var post =$('#id').length == 0;

        	  $.ajax({
        	    url:  post ? pagePath+"/api/asignatura/"+asignaturaId+"/programa" : (pagePath+"/api/asignatura/"+asignaturaId+"/programa/"+$('#id').val()),
        	    type:  "POST",//post si o si sino no funciona
        	    data: formData,
        	    processData: false,  // tell jQuery not to process the data
        	    contentType: false,   // tell jQuery not to set contentType
        	    xhr: function() {  // Custom XMLHttpRequest
                    var myXhr = $.ajaxSettings.xhr();
                    if(myXhr.upload){ // Check if upload property exists
                        myXhr.upload.addEventListener('progress',progress, false); // For handling the progress of the upload
                    }
                    return myXhr;
                },
        	    success : function(result){
        	    	$('#cancelar').trigger("click");        	    
           		 
        	    },
                error: function(value){
                	stopSpinner();
                	 $( "#progressbar" ).progressbar( "option", "value", 0 );
            		 $(".progress-label").text( "" );
            		 $("#guardarPrograma").dialog("close");
            		 errorData = errorConverter(value);
         			if(errorData.errorDto != undefined && value.errorDto){
         				alert(errorConverter(value).message);
         			}else{
         				 alert("Se produjo un error el servidor");
         			}
                }
        	  });        	  
          },          
          send: function(e,data){
        	  return false;
        	  
          },
          processData: false,
          contentType: false,
          cache: false,
          autoUpload: false,
   
          dropZone: $('body') 
    }).on('always', function (e, data) {
        var currentFile = data.files[data.index];
        if (data.files.error && currentFile.error) {
          // there was an error, do something about it
          console.log(currentFile.error);
        }
      });        
    });

function progress (data) {
    var progress = parseInt(data.loaded / data.total * 100, 10);
    $( "#progressbar" ).progressbar( "option", "value", progress );
};

function guardarPrograma(){
	if( $('#fileUploadUsed').val()==="true"){
		 $( "#progressbar" ).progressbar( "option", "value", 0 );
		 $(".progress-label").text( "" );		
		$("#guardarPrograma").dialog("open");
	}else{
		guardarSinArchivo();
	}
};

function guardarSinArchivo(){
	if($('#nombre').val().length===0){
  		addError('nombre',"Nombre requerido");
  		return false;
  	}   
 	var post =$('#id').length == 0;
  	
  $.ajax({
    url:  post ? pagePath+"/api/asignatura/"+asignaturaId+"/programanf" : (pagePath+"/api/asignatura/"+asignaturaId+"/programanf/"+$('#id').val()),
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

function cargarData(){	
	var model = {
  			nombre: $('#nombre').val().length===0 ? null : $('#nombre').val(),
  			cantCartillas:  $('#cantCartillas').val().length===0 ? null : $('#cantCartillas').val(),
  			descripcion: $('#descripcion').val().length===0 ? null : $('#descripcion').val(),
  			profesorId : profesorId
  			
  		};
	
	var post =$('#id').length == 0;
  	if(!post){
  		model.id = $('#id').val().length===0 ? null : $('#id').val();
  	}
  	return JSON.stringify(model);
}


