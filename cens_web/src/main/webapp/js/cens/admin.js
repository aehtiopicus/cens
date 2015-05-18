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

	$('#fbAutenticar').on("click",function(){
		oauth2.initFacebookOauth();
	});
	
	$('#fbRemover').on("click",function(){
		oauth2.revokeFacebookOauth();
	});
	
this.addPortletHeader = function(){
	var divHeader = $("<div></div>");
	
	var divLabel = $("<label></label>");
	divLabel.addClass("fbIcon-hovered");
	
	divHeader.append(divLabel);
	$("#portletHeaderFb").append( divHeader);
}	
	
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
	 $("#successLoginDialog").dialog({
			autoOpen: false,
			width: 400,
			modal : true,
			buttons: [
				{
					text: "Ok",
					click: function() {
						$("#fbLinkButton").trigger("click");
					}
				}
			]
		});
}
this.openDialog = function(){
	 $("#loginDialog").dialog("open");
}

this.checkFacebookOauth = function(){
	$.ajax({
		type:"GET",
		url:pagePath+"/api/social?socialType=FACEBOOK",
		dataType:"json",
		beforeSend: function(xhr){
			startSpinner();
		},
		success : function(message,xhr,data){
			stopSpinner();
			
			$("#tokenFacebook").removeAttr("class");
			$("#tokenFacebook").addClass("estadoToken "+message.status);
			$("#tokenFacebook").html(message.status.toUpperCase());
			$("#key").val(message.key);
			$("#secret").val(message.secret);
		},
		error : function(error,status){
			stopSpinner();
			$("#tokenFacebook").removeAttr("class");
			$("#tokenFacebook").addClass("estadoToken token-error");
			$("#tokenFacebook").html("ERROR");
			alert("Error general de oauth en Facebook");
		}
	});
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
			if($("#fbLinkButton").length == 0){
				fbButton = $("<button></button>");
				fbButton.addClass("fbIcon");
				fbButton.attr("id","fbLinkButton");
				fbButton.insertAfter($("#fbLink"));
				fbButton.attr("style","float:right; margin-top: -7px;");
			}
			fbButton.unbind("click");
			fbButton.on("click",function(){ window.location = data.message;})
			self.openDialog();
		},
		error: function(data){
			stopSpinner();			
			alert(typeof data.errorDto !== "undefined" ? data.errorDto.message : "Error general");
		}								

		}
		
	);
}

this.oauthCompleted = function(oauthData){
	if(typeof oauthData.oauth_status !== "undefined" && oauthData.oauth_status.length> 0 ){
		if(typeof oauthData.code !== "undefined" && oauthData.code.length> 0 ){
			$.ajax({
				type:"GET",
				url:pagePath+"/api/social/oauth2/FACEBOOK/callback?code="+oauthData.code,
				contentType :'application/json',
				dataType:"json",
				beforeSend: function(xhr){
					startSpinner();					
				},
				success: function(data){			
					stopSpinner();			
					$("#successLoginDialog").dialog("open");
					setTimeout(function(){location.href = location.origin+pagePath+"/mvc/administracion";},2000);
				},
				error: function(data){
					stopSpinner();			
					alert(typeof data.errorDto !== "undefined" ? data.errorDto.message : "Error general");
				}								
				
			}
			
			);
			
		}else if(typeof oauthData.error_code !== "undefined" && oauthData.error_code.length> 0 ){
			alert(oauthData.error_code);
		}
	}
	
}

this.revokeFacebookOauth = function(){
	var self = this;
	$.ajax({
		type:"DELETE",
		url:pagePath+"/api/social?socialType=FACEBOOK",
		contentType :'application/json',
		dataType:"json",
		beforeSend: function(xhr){
			startSpinner();
		},
		success: function(data){			
			stopSpinner();			
			alert(data.message,"info");
			setTimeout(function(){location.href = location.origin+pagePath+"/mvc/administracion";},2000);
		},
		error: function(data){
			stopSpinner();			
			alert("Error al remover el token");
		}								

		}
		
	);
}
}

$(document).ready(function(){
			
	oauth2 = new admindashboard.admin.oauth();
	oauth2.initDialog();
	oauth2.checkFacebookOauth();
	oauth2.oauthCompleted(oauthCompletedData);
});
