var fileUploadData;
function fileUploadAssemble(){
$(function () {
	 
    $('#fileUpload').fileupload({
    	
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
              $('#fileUploadUsed').val("false");
              $('#fileUploadData').val("");
             
              if(!(/(\.|\/)(xlsx|xls|doc|docx|ppt|pptx|pps|ppsx|pdf)$/i).test(data.files[0].name)){
            	 alert("Tipo no Soportado");
            	  return false;
              }              
              $('#fileUploadUsed').val("true");
              fileUploadData = data;                            
            
          },
          submit: function(event,data){
        	  return false;        	          	       	  
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
}
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

function cargarData(){	
	var model = {
  			comentario: $('#nombre').val().length===0 ? null : $('#nombre').val(),
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