var responsiveheader = {
        
      
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
	}
	
	this.adaptMenu = function (event){
	  	if (event.animationName ===responsiveMenu.responsiveEventResponsive  && window.getComputedStyle(document.body,':after').getPropertyValue('content') === "devicescreen") {
	  		if(!responsiveMenu.isInResponsiveMode){	  			
	  			responsiveMenu.adaptMenuEnable();	  
	  			
	  			$("#hmMenu").unbind("click");
	  			
	  			$("#hmMenu").click(function() {
	  			  $( "#responsiveMenu" ).animate({
	  			    width: "toggle"
	  			  }, 500, function() {
	  			    // Animation complete.
	  			  });
	  			});
	  		}
	  	} else {
	  		
	  		if(responsiveMenu.isInResponsiveMode && !(window.getComputedStyle(document.body,':after').getPropertyValue('content') === "devicescreen")){
	  			responsiveMenu.isInResponsiveMode = false;
	  			 $( "#responsiveMenu" ).hide();
	  		}
	  	}
		 
	}
	
	/**Deprecated way to call resolution change*/
	this.pingCompleteResponsiveCheck = function(){
//		if(window.getComputedStyle(document.body,':after').getPropertyValue('content') === "devicescreen"){
//			event = document.createEvent("HTMLEvents");
//		    event.initEvent("webkitAnimationEnd", true, true);
//		    event.animationName = "width-1";
//		    document.body.dispatchEvent(event)
//		    this.isInResponsiveMode = true;
//		}
		
	}
	
	this.adaptMenuEnable = function(){
		responsiveMenu.isInResponsiveMode = true;
	}

}