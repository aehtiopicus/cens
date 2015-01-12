jQuery(document).ready(function () {
	
	

	$(function() {
		   $(".hasdatepicker").datepicker({
		      dateFormat: "yy-mm-dd"
		   });
		});
	if(!isNaN((parseInt(window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1))))){
	$.ajax({
		url: "../miembro/"+parseInt(window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1)),
		type: "GET",
		contentType :'application/json',
		dataType: "json",		
		success: function(data){					
			
		},
		error: function(data){
			alert("error: (" + data.status + ") " + data.statusText);
		}
	});
	}
});



function submitMiembro(){

	var tipo =[];
	$.each($('#perfilList li input') ,function(index,val) {
		  if($(val).prop("checked"))
		  {
			  tipo.push('{"perfilType":"'+($(val).prop('value'))+'"}'); 
		  }
		});
	
	var usuario='{"username":"'+$('#username').val()+'","password":"'+$('#password').val()+'","passwordConfirm":"'+$('#passwordConfirm').val()+'","perfil":['+tipo+']}';
	var miembroCens = '[{"nombre":"'+$('#nombre').val()+'","apellido":"'+$('#apellido').val()+'","dni":"'+$('#dni').val()+'","fechaNac":"'+$('#fechaNac').val().replace("/","-")+'","usuario":'+usuario+'}]';
	
	$.ajax({
		  type: "POST",
		  url: "miembro",
		  data: miembroCens,
		  dataType:"application/json",
		  contentType:"application/json", 
		  success: function(value){
			  
		  },
		  error:function(value){
			  
		  }
		  
		});
	
}