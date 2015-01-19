$(function () {
    $('#fileupload').fileupload({
        dataType: 'json',
 
        done: function (e, data) {
        	if(data.result[0].success != -1 && data.result[0].success.indexOf("true")!=-1){
        		
        		location.href="../";
        	}else{
        		$('#error .error').html("El archivo "+data.result[0].fileName+" no contiene la informaci&oacute;n requerida");
        		$('#error').show();
        	}
        	 
        	 $('#progress_bar .ui-progress').css(
                     'width',
                     0 + '%'
                 );
        	 $('#progress_bar .ui-progress').hide();
        	 $('.ui-label').hide();

        },
 
        progressall: function (e, data) {
        	$('#error').hide();
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress_bar .ui-progress').show();
            $('#progress_bar .ui-progress').css(
                'width',
                progress + '%'
            );
            if(progress == 100){
            	$('.ui-label').show();
            }
        },
 
        dropZone: $('#dropzone')
    });
    $('#fileuploadvolcado').fileupload({
        dataType: 'json',
 
        done: function (e, data) {
        	$("body").removeClass("loading"); 
        	if(data.result[0].success != -1 && data.result[0].success.indexOf("true")!=-1){
        		
        		location.href="../";
        	}else{
        		$('#error .error').html("El archivo "+data.result[0].fileName+" no contiene la informaci&oacute;n requerida");
        		$('#error').show();
        	}
        	 
        	 $('#progress_bar .ui-progress').css(
                     'width',
                     0 + '%'
                 );
        	 $('#progress_bar .ui-progress').hide();
        	 $('.ui-label').hide();

        },
 
        progressall: function (e, data) {
        	$('#error').hide();
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress_bar .ui-progress').show();
            $('#progress_bar .ui-progress').css(
                'width',
                progress + '%'
            );
            if(progress == 100){
            	$('.ui-label').show();
            	$("body").addClass("loading"); 
            }
        },
 
        dropZone: $('#dropzone')
    });
    
    $('#updateSistemaFileupload').fileupload({
        dataType: 'json',
 
        done: function (e, data) {
            stopSpinner();
        	if(data.result[0].success != -1 && data.result[0].success.indexOf("true")!=-1){
        		
                $('#progress_bar .ui-progress').removeClass('procesing')
        		$('#progressBarLabel').text("Actualización en processo.Reinicio en 2 minutos ");
                
//        		setTimeout(function() {location.href= getCookie('appPath') + "index";},data.result[0].waitTime);
                        startSpinner();
                        $('.spiner_text').html("Reinicio en 2 minutos");
        	}else{
        		$('#error .error').html("El archivo "+data.result[0].fileName+" no contiene la informaci&oacute;n requerida");
        		$('#error').show();
        	
        		$('#progress_bar .ui-progress').css(
                        'width',
                        0 + '%'
                    );
	           	$('#progress_bar .ui-progress').hide();
	           	$('.ui-label').hide();
        	}
        	 
        	 

        },
 
        progressall: function (e, data) {
        	$('#error').hide();
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress_bar .ui-progress').show();
            $('#progress_bar .ui-progress').addClass('procesing')
            $('#progress_bar .ui-progress').css(
                'width',
                progress + '%'
            );
            if(progress == 100){
            	$('.ui-label').show();
                startSpinner();
            }
        },
 
        dropZone: $('#dropzone')
    });
});