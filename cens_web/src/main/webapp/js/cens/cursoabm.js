jQuery(document).ready(function () {
$("#yearCurso").spinner({
    min : 1980,
    max : new Date().getFullYear(),   
    showOn : 'both',
    //spin: function( event, ui ) {alert(ui);},
    numberFormat: "n",    
    	
});
$('#yearCurso').val(new Date().getFullYear());
$("#yearCurso").focus(function(a) {
	  $( this ).parent().addClass('spanFocus');
});
$("#yearCurso").focusout(function(a) {
		val = $('#yearCurso').val();
		if(isNaN(val)){
			$('#yearCurso').val(new Date().getFullYear());
		}else{
			if(parseInt(val)>$("#yearCurso").spinner("option","max") || parseInt(val)< $("#yearCurso").spinner("option","min")){
				$('#yearCurso').val(new Date().getFullYear());
			}
		}
	  $( this ).parent().removeClass('spanFocus');
});


if(!isNaN(pageId())){
	$.ajax({
		url: pagePath+"/api/curso/"+pageId(),
		type: "GET",
		contentType :'application/json',
		dataType: "json",		
		success: function(data){
			$('#id').val(data.id);
			$('#nombre').val(data.nombre);
			$('#yearCurso').val(data.yearCurso);
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

});


function submitCurso(){

	var post =$('#id').length == 0;
			
	$.ajax({
		  type: post ? "POST" : "PUT",
		  url: post? pagePath+"/api/curso" :(pagePath+"/api/curso/"+ $('#id').val()),
		  data: prepareData(post),
		  dataType:"json",
		  contentType:"application/json", 
		  success: function(value){
			 $('#cancelar').trigger("onclick");
		  },
		  error:function(value){
			  if(validationError(errorConverter(value))){
				  alert(errorConverter(value).message );
			  }else{
				  alert("Se produjo un error el servidor");
			  }
		  }
		  
		});
	
}	


function prepareData(post){
				
		var curso='{"id":'+($("#id").length == 0 ? null : $("#id").val())+',"nombre":"'+$('#nombre').val()+'","yearCurso":"'+$('#yearCurso').val()+'"}';		
		var postData = '[{post}]';		
		if(post){
			curso = postData.replace('{post}',curso);
		}
		return curso;
}

