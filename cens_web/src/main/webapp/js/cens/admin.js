var admindashboard = {
        
      
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

admindashboard.namespace("admin");
admindashboard.admin.oauth = localstorage.makeClass();

admindashboard.admin.oauth.prototype.init = function(){

	$('#fbButton').on("click",function(){
		oauth2.initFacebookOauth();
	});
	
this.initDialog = function(){
	 $("#loginDialog").dialog({
			autoOpen: false,
			width: 400,
			modal : true,
			buttons: [
				{
					text: "Ok",
					click: function() {
						$( this ).dialog( "close" );
					}
				}
			]
		});      
}
this.openDialog = function(){
	 $("#loginDialog").dialog("open");
}
this.initFacebookOauth = function(){
	var self = this;
	$.ajax({
		type:"GET",
		url:pagePath+"/api/social/oauth2?socialType=FACEBOOK",
		contentType :'application/json',
		dataType:"json",
		beforeSend: function(xhr){
			startSpinner();
		},
		success: function(data){			
			stopSpinner();			
			$("#fbLink").unbind("click");
			$("#fbLink").attr("href",data.message);			
			self.openDialog();
		},
		error: function(data){
			stopSpinner();			
			alert(typeof data.errorDto !== "undefined" ? data.errorDto.message : "Error general");
		}								

		}
		
	);
}

}

$(document).ready(function(){
	oauth2 = new admindashboard.admin.oauth();
	oauth2.initDialog();
});
