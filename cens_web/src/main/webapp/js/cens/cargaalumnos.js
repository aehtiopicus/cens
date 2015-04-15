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
		var f = event.target.files[0]
		 var reader = new FileReader();
		    var name = f.name;
		    reader.onload = function(e) {
		      var data = e.target.result;

		      /* if binary string, read with type 'binary' */
		      var workbook = XLS.read(data, {type: 'binary'});

		      /* DO SOMETHING WITH workbook HERE */
		      cargaMasivaAlumnos.jsonFormer(workbook);
		    };
		    reader.readAsBinaryString(f);
	})	;
	
	this.jsonFormer = function(workbook) {
		var result = {};
		var name = "index";
		workbook.SheetNames.forEach(function(sheetName) {
			var xlsData = XLS.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
			if(xlsData.length > 0){
				result[name] = xlsData;
			}
		});
		$(cargaMasivaAlumnos.fileSelector).val('');
		return result;
	}
	
	
}


