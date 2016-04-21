var marginWidthGrid = 20;
var marginHeightGrid = 370;
var marginHeightGridFull = 345;
var responsiveMenu = new responsiveheader.rh.responsive();
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
	

			$('#iam-img').attr("src",pagePath+"/api/miembro/"+JSON.parse(data.getNotificacion()).item.miembroId+"/picture");//fix me 	    	
			$('#iam-img').css("max-width","70px");
			$('#iam-img').css("max-height","67px");
			$('#cerrarIam').on("click",function(){
				$("#iam").hide();
				 $('#closeButton').show();
			});
			$('#headerImg').on("click",function(){openUser(JSON.parse(data.getNotificacion()).item.user);});
			$('#notCant1').on("click",function(){openUser(JSON.parse(data.getNotificacion()).item.user);});
			$(document).mouseup(function (e)
					{
					    var container = $("#iam");

					    if (!container.is(e.target) // if the target of the click isn't the container...
					        && container.has(e.target).length === 0) // ... nor a descendant of the container
					    {
					        container.hide();
					        $('#closeButton').show();
					    }
					});
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
	    
	    function openUser(user){
	    	$('#iam').show();
	    	$('#closeButton').hide();
	    }
	    