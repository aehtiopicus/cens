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
			minWidth: 700,
			maxWidth: 700,
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
		     var result = cargaMasivaAlumnos.jsonFormer(workbook);
		     if(result != null){		    	
		    	 if(!cargaMasivaAlumnos.checkResult(result)){
		    	 	alert("Formato de achivo incorrecto. Descargue la plantilla");
		     	}else{	
		    	 alumnoResult = new alumnos.al.alumnos(result);
		    	 alumnoResult.aramarAlumnos();
		     	}
		     }
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
			}else{
				result = null;
				alert("No hay datos en la lista");
			}
		});
		$(cargaMasivaAlumnos.fileSelector).val('');
		return result;
	}
	
	this.checkResult = function(result){
		if(typeof result.index[0].apellido === "undefined" || typeof result.index[0].dni === "undefined" || typeof result.index[0].nombre === "undefined" ||typeof result.index[0].nac === "undefined" ){
			return false;
		}
		return true;
	}
	
	
}

alumnos.namespace("al");
alumnos.al.alumnos = alumnos.makeClass();

alumnos.al.alumnos.prototype.init = function(param){
	this.alumnosRaw =[];
	this.alumnos = [];
	var i = 1;
	
	var self = this;
	
	$.each( param.index,function(index,value){
		value.id = i
		self.alumnosRaw.push(value);
		i++;
	});
	this.aramarAlumnos = function(){
		var self = this;
		$.each(this.alumnosRaw,function(index,value){
			self.alumnos.push(self.armarAlumno(value));
		});
	}
	
	this.armarAlumno = function(alumnoRaw){
		var alumnoNew = {
				apellido:null,dni:null,fechaNac:null,id:null,nombre:null,usuario:null
		};
		var usuarioNew = {
				id:null,username:null,perfil:[{perfilType:"ALUMNO"}]
		}
		
		alumnoNew.apellido = alumnoRaw.apellido;
		alumnoNew.dni = alumnoRaw.dni;
		alumnoNew.nombre = alumnoRaw.nombre;
		alumnoNew.fechaNac = new Date(alumnoRaw.nac).toISOString();
		
		usuarioNew.username = alumnoNew.nombre.substring(0,1)+ alumnoNew.apellido.substring(0,1)+alumnoNew.dni;
		
		alumnoNew.usuario = usuarioNew;
		
		return alumnoNew;
		
	}
}


