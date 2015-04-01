 var marginWidthGrid = 20;
 	    var marginHeightGrid = 370;
 	    var marginHeightGridFull = 345;

 	    $(document).ready(function() {
				$( ".button" ).button();
				
				$( ".hasdatepicker" ).datepicker({
					inline: true,
					yearRange: "-100:+0",
					changeMonth: true,
				    changeYear: true,
				    dateFormat: "dd-mm-yy"
				});
				
				$( ".menu" ).menu({
	                position: {at: "left bottom"}
	            });
		        
				$( ".chosen-select" ).chosen({
					allow_single_deselect: true
				});
				
		        $(".entero").numeric();
		        $(".decimal").numeric(","); 
		        $(".decimalConPunto").numeric("."); 
		        
 	    });
 	    $(document).bind("userImg",function(){
 	    	var data = new localstorage.ls.notificacionData(); 	    	 	    	
 	    	$('#headerImg').attr("src",pagePath+"/api/miembro/"+JSON.parse(data.getNotificacion()).item.miembroId+"/picture");//fix me 	    	
			$('#headerImg').css("width","50px");
 	    });
 	   function deleteAllCookies() {
	        var cookies = document.cookie.split(";");

	        for (var i = 0; i < cookies.length; i++) {
	        	var cookie = cookies[i];
	        	var eqPos = cookie.indexOf("=");
	        	var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
	        	document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
	        }
	    }
	    
	    
	    function logout(){
	    	deleteAllCookies();
	    	window.location = mainPagePath+'/logout';
	    }
	    
	    jQuery(window).load(function () {
	       $('#mainPanel').show();
	       
	    });
	    