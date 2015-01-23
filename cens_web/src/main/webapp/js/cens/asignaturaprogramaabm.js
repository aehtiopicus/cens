
jQuery(document).ready(function () {
	$("#cantCartillas").spinner({
	    min : 1,
	    max : 99,   
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
     }
   });
	
   
   $( "#guardarPrograma" ).dialog({
		autoOpen: false,
		width: 400,
		buttons: [
			{
				text: "Ok",
				id:"btnGuardarOk",
				click: function() {
					$('#btnGuardarPrograma').trigger("click");
				}
			},
			{
				text: "Cancelar",
				click: function() {
					$( this ).dialog( "close" );
					$( "#guardarPrograma #fileUploadFailureErrorDiv" ).remove();
					
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
        	  	var model = {
        	  			nombre: $('#nombre').val().length===0 ? null : $('#nombre').val(),
        	  			cantCartillas:  $('#cantCartillas').val().length===0 ? null : $('#cantCartillas').val(),
        	  			descripcion: $('#descripcion').val().length===0 ? null : $('#descripcion').val(),
        	  			profesorId : profesorId		
        	  		};
        	  	var fileInfo ={
        	  			fileName:data.files[0].name,
        	  			fileSize:data.files[0].size,
        	  			fileLastModify:convertDate(data.files[0].lastModified),
        	  			creatorId:profesorId,
        	  			creatorType:'PROFESOR'
        	  	};
        	  	model.fileInfo = fileInfo;
        	  	formData.append('properties', new Blob([JSON.stringify(model)], { type: "application/json" }));
        	
        	  $.ajax({
        	    url: pagePath+"/asignatura/"+asignaturaId+"/programa",
        	    type: "POST",
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
        	    	         	            
        	    },
                error: function(error){
                	 $( "#progressbar" ).progressbar( "option", "value", 0 );
            		 $(".progress-label").text( "" );
                	addError('fileUploadFailure',"Error al guardar el programa");
                	$('#btnGuardarOk').prop("disabled",true)
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
		$('#btnGuardarOk').prop("disabled",false);
		 $( "#progressbar" ).progressbar( "option", "value", 0 );
		 $(".progress-label").text( "" );
		
		$("#guardarPrograma").dialog("open");
	}else{
		
	}
};
