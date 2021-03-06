var localstorage = {
        
      
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

localstorage.namespace("ls");
localstorage.ls.notificacion = localstorage.makeClass();

localstorage.ls.notificacion.prototype.init = function(param){
	this.notificacionItem ="user"+param.user;
	this.notificacionExpired = param.expireSec;
	this.notificacionLoader = param.notificacionLoader;
	this.notificacionLoading = false;
	this.miembroId = param.miembroId;
	this.notificationRefreshRequired = false;
	
	this.getNotificacion = function(){
		if(!this.notificacionLoading){
			var data = localStorage.getItem(this.notificacionItem);
			if(data && !this.notificationRefreshRequired){
				return data;
			}else{
				this.clearNotificacion();
				return null;
			}
		}else{
			return null;
		}
		
	}
	this.isUpdating = function(){
		return this.notificacionLoading;
	}
	this.getNotificacionData = function(){
		var item = this.getNotificacion();
		if(item){
			return JSON.parse(item).item;
		}else{
			return null;
		}
		
	}
	
	
	this.clearNotificacion = function(){
		if(!this.notificacionLoading){
			this.remove();
			this.notificacionLoader(this);//el call back tiene que ser en el success del ajax	
			this.notificacionLoading = true;
		}
	}
	
	this.isRefreshRequired = function(){
		var data = this.getNotificacion();
		if(data){
			var date = new Date();
			var diff = Math.ceil((date.getTime() -JSON.parse(data).date)/1000);
			if(this.notificacionExpired< diff){
				this.notificationRefreshRequired = true;
				return true;
			}else{
				return false;
			}
			
		}else{
			this.notificationRefreshRequired = true;
			return true;
		}
	}
	
	this.remove = function(){
		localStorage.removeItem(this.notificacionItem);
	}
	
	this.setRemoveTimeTo = function(sec){
		var nData = this.getNotificacion();
		this.remove();
		
		localStorage.setItem(this.notificacionItem,'{"date":\"'+(new Date().getTime()-(1000*this.notificacionExpired)+(1000*sec))+'\","item":'+JSON.stringify(JSON.parse(nData).item)+'}');
	}
	
	this.setNotificacion = function(ni){
		new localstorage.ls.userProfileData().saveProfile(ni[0].perfilRol);
		localStorage.setItem(this.notificacionItem,'{"date":\"'+new Date().getTime()+'\","item":'+JSON.stringify(ni)+'}');
		this.notificacionLoading = false;
		this.notificationRefreshRequired = false;
	}
}


localstorage.ls.notificacionData = localstorage.makeClass();
localstorage.ls.notificacionData.prototype.init = function(param){
	this.notificacionItem ="notificationData";
	this.notificacionExpired = 120;
	
	this.getNotificacion = function(){
		return localStorage.getItem(this.notificacionItem);
			
	}
	
	this.getNotificacionObject = function(){
		return JSON.parse(this.getNotificacion());
			
	}
	this.setInfinitRenewalTime = function(){
		var newTime =JSON.parse(this.getNotificacion());
		newTime.date=( new Date().getTime()*10);
		newTime.preventNoty = true;
		localStorage.setItem(this.notificacionItem,JSON.stringify(newTime));
	}
	
	this.removeIfNeeded = function(){
		var data = this.getNotificacion();
		if(data){
			var date = new Date();
			var diff = Math.ceil((date.getTime() -JSON.parse(data).date)/1000);
			if(this.notificacionExpired< diff){
				this.remove();
			}
		}
	}
	
	this.remove = function(){
		localStorage.removeItem(this.notificacionItem);
	}
	
	this.setNotificacion = function(ni){
		localStorage.setItem(this.notificacionItem,'{"date":\"'+new Date().getTime()+'\","item":'+JSON.stringify(ni)+'}');
	}
}


localstorage.ls.userProfileData = localstorage.makeClass();
localstorage.ls.userProfileData.prototype.init = function(){
	this.userProfileItem = "userProfileItem";

	this.saveProfile = function(profile){
		localStorage.setItem(this.userProfileItem,'{"date":\"'+new Date().getTime()+'\","item":'+JSON.stringify(profile)+'}');
		$(document).trigger("userProfileItemInitComplete");
	}
	
	this.loadProfile = function(){
		return JSON.parse(localStorage.getItem(this.userProfileItem)).item;
	}
	this.isLoaded = function(){
		return localStorage.getItem(this.userProfileItem)!=null;
	}
}