var alumnos = {
        
      
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

alumnos.namespace("al");
alumnos.al.cargamasiva = alumnos.makeClass();

alumnos.al.cargamasiva.prototype.init = function(param){
	this.dialog = '#'+param.dialogName;	
	this.fileSelector = '#'+param.fileSelection;
	var self = this;
		
		$(this.dialog).dialog({
			autoOpen: false,
			width: 400,
			buttons: [
				{
					text: "Ok",
					click: function() {
						$(this).dialog( "close" );
//						self;
					}
				},
				{
					text: "Cancelar",
					click: function() {
						$(this).dialog( "close" );
//						self.;
					}
				}
			]
		}); 	
		
	this.openDialgo = function (){
		$(this.dialog).dialog("open");
	}	
		
	$(this.fileSelector).change(function(event){
		alert(event.target.files[0]);
	})	;
	
	
}


