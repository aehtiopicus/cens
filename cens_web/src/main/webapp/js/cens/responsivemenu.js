var responsiveheader = {
        
     deviceScreenMap : {"devicescreen":true, "'devicescreen'":true, '"devicescreen"':true},
     
     namespace: function(ns) {
        var parts = ns.split("."),
            object = this,
            i, len;

        for (i=0, len=parts.length; i < len; i++) {
            if (!object[parts[i]]) {
                object[parts[i]] = {};
            }
            object = object[parts[i]];
        }
        return object;
    },

    makeClass: function(){
        return function(args){
            if ( this instanceof arguments.callee ) {
                if ( typeof this.init == "function" ){
                    this.init.apply( this, args != null && args.callee ? args : arguments );
                }
            } 
            else {
                return new arguments.callee( arguments );
            }
        };
    },

    makeRandomNamespace: function(ns){
        var randomNs = "";
        var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for( var i=0; i < 20; i++ )
            randomNs += possible.charAt(Math.floor(Math.random() * possible.length));

        if(ns != null){
            randomNs = ns+'.'+randomNs;
        }
        return image.namespace(randomNs);
    }
};

responsiveheader.namespace("rh");
responsiveheader.rh.responsive = responsiveheader.makeClass();

responsiveheader.rh.responsive.prototype.init = function(){
	
	this.responsiveEventResponsive = "width-1";	
	this.isInResponsiveMode = false;		

	this.responsiveChangeListener = function(){
		document.body.addEventListener("webkitAnimationEnd", this.adaptMenu, false);		
		$("#closeResponsiveMenu").on("click",function(){
			$("#hmMenu").trigger("click");
		});
		
		$("#hmMenu").click(function() {
				document.getElementById("responsiveMenu").height =$("document").height();
				
			  $( "#responsiveMenu" ).animate({
			    width: "toggle"
			  }, 500, function() {
				
			  });
			});
	}	
	
	this.adaptMenu = function (event){
		
	  	if (event.animationName ===responsiveMenu.responsiveEventResponsive  && typeof responsiveheader.deviceScreenMap[window.getComputedStyle(document.body,':after').getPropertyValue('content')] !== "undefined") {
	  		if(!responsiveMenu.isInResponsiveMode){	  			
	  			responsiveMenu.adaptMenuEnable();	  	  		 				  				  			
	  		}
	  	} else {
	  		
	  		if(responsiveMenu.isInResponsiveMode && !(typeof responsiveheader.deviceScreenMap[window.getComputedStyle(document.body,':after').getPropertyValue('content')] !== "undefined")){
	  			responsiveMenu.isInResponsiveMode = false;	  			
	  			$( "#responsiveMenu" ).hide();
	  		}
	  	}
		 
	}
	
	/**Deprecated way to call resolution change*/
	this.pingCompleteResponsiveCheck = function(){

		if(typeof responsiveheader.deviceScreenMap[window.getComputedStyle(document.body,':after').getPropertyValue('content')] !== "undefined"){
			event = document.createEvent("HTMLEvents");
		    event.initEvent("webkitAnimationEnd", true, true);
		    event.animationName = "width-1";
		    document.body.dispatchEvent(event)
		    this.isInResponsiveMode = true;
		}
		
	}
	
	this.adaptMenuEnable = function(){
		responsiveMenu.isInResponsiveMode = true;
		//clean
		this.cleanMenu();
		//agregar un item por cada elemento
		this.addMenus($(".menu"),false);
	}
	
	this.addMenus = function(ulItem,subMenu){
		
		ulItem = $(ulItem);
		$.each(ulItem.children(),function(index,value){
			var liMenu = $("<li></li>");			
			var aItem = $("<a></a>");
			if(subMenu){
				aItem.addClass("responsive-sub-menu");
			}
			liMenu.append(aItem);
			var outerA = $(value).children()[0];
			aItem.attr("href",outerA.href)
			aItem.append(outerA.innerText);
			$("#responsiveMenuList").append(liMenu);
			if($(value).children()[1] !== "undefined"){
				responsiveMenu.addMenus($(value).children()[1],true);
			}
			
		});
	}
	
	this.cleanMenu = function (){
		first = 0;
		$.each($("#responsiveMenuList").children(),function(index,childItem){
			if(first != 0){
				childItem.remove();
			}else{
				first ++;
			}
		});		
	}

}