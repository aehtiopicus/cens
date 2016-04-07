var asignaturaMaterial={
	 estadoMap :{"LISTO":true,"NUEVO":true,"ASIGNADO":false,"RECHAZADO":false,"APROBADO":false,"CAMBIOS":false} 	
};
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
				if(!asignaturaMaterial.estadoMap[result.estadoRevisionType]){
					$("#editable").show();
					$("#cantCartillas").spinner("destroy");
					$(".materialDiv input[type='text'],.materialDiv textarea, .materialDiv select,.materialDiv ui-spinner-button").attr("disabled",true);
					$($(".footerForm >button").children()[0]).attr("disabled",true).addClass("ui-state-disabled");
					
				}
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
		$('#comentariosTitulo').toggle();
		$('#accordion').comment({
		       title: 'Comentarios',
		       update_notification: true,
		       url_get: pagePath+'/api/comentario/comments/list',
		       url_input: pagePath+'/api/comentario/comments/list',
		       url_delete: pagePath+'/api/comentario/comments/list',
		       url_open_attachment: pagePath+'/api/comentario/comments/list/{id}/attachment',
		       url_remove_attachment: pagePath+'/api/comentario/comments/list/{id}/attachment',
		       arguments:{tipoType:"MATERIAL",tipoId:pageId(),usuarioId:profesorId,usuarioTipo:"PROFESOR"},
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
	}
		

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
	
   
   $( "#guardarCartilla" ).dialog({
		autoOpen: false,
		width: 400,
		modal:true,
		buttons: [
			{
				text: "Ok",
				click: function() {
					$('#btnGuardarCartilla').trigger("click");
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
   
   $( "#borrarCartilla" ).dialog({
		autoOpen: false,
		width: 400,
		modal:true,
		buttons: [
			{
				text: "Ok",
				click: function() {
					 $.ajax({
						    url:  $('#downloadCartillaAdjunta').prop("href"),
						    type: 'DELETE',						    
						    dataType:"json",
							contentType:"application/json", 
						    success : function(result){    		 
						    	$('#cartillaAdjuntado').toggle();
						    	$('#fileUp').toggle();
						    	$('#borrarCartilla').dialog("close");
						    },
						    error: function(value){
						    	$( this ).dialog( "close" );	
						    	 errorData = errorConverter(value);
									if(errorData.errorDto != undefined && errorData.errorDto){
										if(validationError(errorData)){
									  		alert($('<div/>').html(errorData.message).text() );
										}
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
   $('#divisionPeriodoType option:eq(0)').attr("selected","true");
});

$(function () {
	 
    $('#fileupload').fileupload({
    	
    	  url:null,
    	  
          done: function (e, data) {
              alert("listo"); 
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
             
              $("#btnGuardarCartilla").off();
              $("#btnGuardarCartilla").click(function () {
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
        	    url:  post ? pagePath+"/api/programa/"+programaId+"/material" : (pagePath+"/api/programa/"+programaId+"/material/"+$('#id').val()),
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
            		 $("#guardarCartilla").dialog("close");
            		 errorData = errorConverter(value);
         			if(errorData.errorDto != undefined && errorData.errorDto){
         				if(validationError(errorData)){
    				  		alert($('<div/>').html(errorData.message).text() );
    					}
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

function guardarCartilla(){
	if( $('#fileUploadUsed').val()==="true"){
		 $( "#progressbar" ).progressbar( "option", "value", 0 );
		 $(".progress-label").text( "" );		
		$("#guardarCartilla").dialog("open");
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
    url:  post ? pagePath+"/api/programa/"+programaId+"/materialnf" : (pagePath+"/api/programa/"+programaId+"/materialnf/"+$('#id').val()),
    type: post ? "POST" : "PUT",
    data: cargarData(),
    dataType:"json",
	  contentType:"application/json", 
    success : function(result){    		 
		 $('#cancelar').trigger("click");
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
    }
  });  
}

function cargarData(){	
	var model = {
  			nombre: $('#nombre').val().length===0 ? null : $('#nombre').val(),
  			divisionPeriodoType:  $('#divisionPeriodoType').val(),
  			descripcion: $('#descripcion').val().length===0 ? null : $('#descripcion').val(),
  			profesorId : profesorId,
  			programaId : programaId,
  			nro:nro,
  			
  		};
	
	var post =$('#id').length == 0;
  	if(!post){
  		model.id = $('#id').val().length===0 ? null : $('#id').val();
  	}
  	return JSON.stringify(model);
}


