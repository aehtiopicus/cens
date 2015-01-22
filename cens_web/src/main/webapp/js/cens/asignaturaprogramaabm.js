
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
	

	progressbar = $( "#progressbar" ),
    progressLabel = $( ".progress-label" );

   progressbar.progressbar({
     value: 0,
     change: function() {
       progressLabel.text( progressbar.progressbar( "value" ) + "%" );
     },
     complete: function() {
       progressLabel.text( "Carga completa!" );
     }
   });
   
   $( "#guardarPrograma" ).dialog({
		autoOpen: false,
		width: 400,
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
					usuarioIdToRemove = null;
				}
			}
		]
	});

});

$(function () {
	 
    $('#fileupload').fileupload({
    	
    	  dataType: 'json',
    	  
          done: function (e, data) {
              alert("done"); 
          },
          
          add: function (e, data) {
              if (e.isDefaultPrevented()) {
                  return false;
              }
              $('#fileUploadName').val(data.files[0].name);
              $('#fileUploadUsed').val("true");
             
              $("#btnGuardarPrograma").click(function () {
            	  data.process().done(function () {
                      data.submit();
                  });
              })
            
          },
          submit: function(event,data){
        	  var formData = new FormData();
        	  	formData.append("file",data.files[0]);
        	  	
        	  	var model = {
        	  			nombre: $('#nombre').val().length===0 ? null : $('#nombre').val(),
        	  			cantCartillas:  $('#cantCartillas').val().length===0 ? null : $('#cantCartillas').val(),
        	  			descripcion: $('#descripcion').val().length===0 ? null : $('#descripcion').val(),
        	  			profesorId:profesorId
        	  		};
        	  	formData.append('properties', new Blob([JSON.stringify(model)], { type: "application/json" }));
        	
        	  $.ajax({
        	    url: pagePath+"/asignatura/"+asignaturaId+"/programa",
        	    type: "POST",
        	    data: formData,
        	    processData: false,  // tell jQuery not to process the data
        	    contentType: false   // tell jQuery not to set contentType
        	  });
          },
          progressall: function (e, data) {
              var progress = parseInt(data.loaded / data.total * 100, 10);
              $( "#progressbar" ).progressbar( "option", "value", progress );
          },
          processData: false,
          contentType: false,
          cache: false,
          autoUpload: false,
   
          dropZone: $('body') 
    });        
    });

function guardarPrograma(){
	if( $('#fileUploadUsed').val()==="true"){
		$("#guardarPrograma").dialog("open");
	}else{
		
	}
}
