var asignaturaId=window.location.pathname.split('/')[2];

jQuery(document).ready(function () {
	$("#cantCartillas").spinner({
	    min : 1,
	    max : 99,   
	    showOn : 'both',
	    //spin: function( event, ui ) {alert(ui);},
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
	

	 var progressbar = $( "#progressbar" ),
     progressLabel = $( ".progress-label" );

   progressbar.progressbar({
     value: 0,
     change: function() {
       progressLabel.text( progressbar.progressbar( "value" ) + "%" );
     },
     complete: function() {
       progressLabel.text( "Complete!" );
     }
   });

});

$(function () {
	 
    $('#fileupload').fileupload({
    	
    	  dataType: 'json',
    	  
          done: function (e, data) {
              $("tr:has(td)").remove();
              $.each(data.result, function (index, file) {
   
                  $("#uploaded-files").append(
                          $('<tr/>')
                          .append($('<td/>').text(file.fileName))
                          .append($('<td/>').text(file.fileSize))
                          .append($('<td/>').text(file.fileType))
                          .append($('<td/>').html("<a href='upload?f="+index+"'>Click</a>"))
                          .append($('<td/>').text("@"+file.twitter))
   
                          )//end $("#uploaded-files").append()
              }); 
          },
   
          progressall: function (e, data) {
              var progress = parseInt(data.loaded / data.total * 100, 10);
              $( "#progressbar" ).progressbar( "option", "value", progress );
          },
   
          dropZone: $('#dropzone') 
    });        
    });


/**
 *
 
        dataType: 'json',
 
        done: function (e, data) {
            $("tr:has(td)").remove();
            $.each(data.result, function (index, file) {
 
                $("#uploaded-files").append(
                        $('<tr/>')
                        .append($('<td/>').text(file.fileName))
                        .append($('<td/>').text(file.fileSize))
                        .append($('<td/>').text(file.fileType))
                        .append($('<td/>').html("<a href='upload?f="+index+"'>Click</a>"))
                        .append($('<td/>').text("@"+file.twitter))
 
                        )//end $("#uploaded-files").append()
            }); 
        },
 
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .bar').css(
                'width',
                progress + '%'
            );
        },
 
        dropZone: $('#dropzone') 
 */
