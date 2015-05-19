var asesorSocialDashboard = {
        
      
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

asesorSocialDashboard.namespace("social");
asesorSocialDashboard.social.publish = asesorSocialDashboard.makeClass();

asesorSocialDashboard.social.publish.prototype.init = function(){
	
	this.socialPublishData;
	
	this.socialPublishStates = {PUBLICADO:"publicado",NO_PUBLICADO:"no publicado"};
	
	this.initDialog = function(){
		 $("#publishDialog").dialog({
				autoOpen: false,
				width: 400,
				modal : true,
				buttons: [
					{
						text: "Cancelar",
						click: function() {
							$( this ).dialog( "close" );
						}
					}
				]
			});
	}
	this.setSocialPublishData = function(socialPublishData){
		this.socialPublishData = socialPublishData;
	}
	this.openPublishDialog = function(){
		if(socialPublish.socialPublishStates[socialPublish.socialPublishData.socialPostStateType] ==="publicado"){
			$("#publishState").addClass("activo");
			$("#publishState").removeClass("inexistente");
			$("#publishState").html("Publicado");
			
			$( "#publishDelete" ).button({
				  disabled: false
				})
		}else{
			$("#publishState").removeClass("activo");
			$("#publishState").addClass("inexistente");
			$("#publishState").html("No Publicado");
			
			$( "#publishDelete" ).button({
				  disabled: true
				})
		}
		
		$("#publishMessage").val(socialPublish.socialPublishData.message);
		$("#publishSubmit").unbind("click");
		$("#publishSubmit").on("click",function(){
			socialPublish.publishData();
		});
		$("#publishDelete").unbind("click");
		$("#publishDelete").on("click",function(){
			socialPublish.unPublishData();
		});
		 $("#publishDialog").dialog("open");
	}
	
	this.publishData = function(){
		$.ajax({
			
			type:"POST",
			url:pagePath+"/api/social/oauth2?provider=FACEBOOK&comentarioTypeId="+socialPublish.socialPublishData.programaId+"&publishString="+$("#publishMessage").val(),
			contentType :'application/json',
			dataType:"json",
			success: function(data,xhr){			
				 $("#publishDialog").dialog("close");
				 alert($('<div/>').html("Publicaci&oacute;n realizada :"+data.publishEventId).text(),"success");
			},
			error: function(data,status){						
				alert("Error al intentar autenticar");
			}								
	
	});
	}
	this.unPublishData = function(){
		$.ajax({
			
			type:"DELETE",
			url:pagePath+"/api/social/oauth2?provider=FACEBOOK&comentarioTypeId="+socialPublish.socialPublishData.programaId,
			contentType :'application/json',
			dataType:"json",
			success: function(data,xhr){			
				 $("#publishDialog").dialog("close");
				 alert($('<div/>').html(data.message).text(),"success");
			},
			error: function(data){
				
				alert($('<div/>').html(JSON.parse(data.responseText).message).text());
			}								
	
	});
	}
}

$(document).ajaxStart(function() {
	startSpinner();
});
$(document).ajaxStop(function() {
	stopSpinner();
	$('.censmaterias').slickSetOption('respondeTo', '', true);
});

jQuery(document).ready(function () {
carruselIds = [];
	$.ajax({
		type:"GET",
		url:pagePath+"/api/asesor/"+asesorId+"/dashboard",
		contentType :'application/json',
		dataType:"json",
		success: function(data){			
			cargarDatos(data);
			loadPorlet();
			loadCarrousel();			
		},
		error: function(data){						
			location.href=pagePath+"/errors/"+data.error().status;
		}								

		});
	
	socialPublish.initDialog();	 
	 
});

function cargarDatos(data){	
			
	currentDiv =($('#yearContent').append('<div class="censaccordion"></div>')).children();
	
	if(data.cursoDto!=null){
		$.each(data.cursoDto,function(index,value){		
		
			currentDiv=datosCurso(currentDiv,value);
			if(value.asignaturas!=null){
				$.each(value.asignaturas,function(index,asignatura){	
					datosAsignatura(value,currentDiv,asignatura);						
				});
			}
		});
	}
}

function datosCurso(currentDiv,value){
	var title = $('<h3 class="subtitulo"></h3>');
	var titleSpan = $('<span class="cursoFont"></span>');
	titleSpan.html((value.nombre.toUpperCase()+" ("+value.yearCurso+")"));
	title.append("ASIGNATURAS DEL CURSO ");
	title.append(titleSpan);
	currentDiv.append(title);
	currentDiv.append($('<hr class="portletLine">'));
	currentDiv = currentDiv.append("<div id='curso"+value.id+"' class='curso'></div>");
	 $('#curso'+value.id).append("<div class='censmaterias' id='porletcontainer"+value.id+"'></div>");
	 return currentDiv;
}

function datosAsignatura(value,currentDiv,asignatura){
	var divPorlet = '<div class="portlet" id="{id}"></div>';
	var divPorletHeader =  '<div class="portlet-header">{name}</div>';
	var divPorletContet =  '<div class="portlet-content"></div>';
	
	var list ='<ul>{profesor}{programa}{material}</ul>';		
	list = list.replace('{profesor}',datosProfesor(value,asignatura.profe,asignatura.profeSuplente));
	list = list.replace('{programa}',datosPrograma(value,asignatura));
	list = list.replace('{material}',datosMaterial(value,asignatura));
	currentPorlet =$("#porletcontainer"+value.id).append(divPorlet.replace("{id}","asignatura"+asignatura.id));
	currentPorlet = $("#asignatura"+asignatura.id).append(divPorletHeader.replace("{name}",asignatura.nombre.toUpperCase()));
	currentPorlet = currentPorlet.append(divPorletContet);
	currentPorlet = $(currentPorlet.children()[1]).append(list.replace("{id}",asignatura.id).replace("{id}",asignatura.id).replace("{id}",asignatura.id));
	
}	

function datosPrograma(value,asignatura){
	
	var setSocial = false;
	var socialLink = $("<img></img>");
	socialLink.attr("src","/cens_web/css/midasUI-theme/images/fbIcon.png");
	socialLink.addClass("socialLink");
	
	var itemPrograma='<li>{link}</li>';
	var itemLink = '<a href="'+pagePath+'/mvc/asesor/'+asesorId+'/asignatura/{id}/programa{existente}{nombreAsignatura}">{text}</a>';
	var itemText ='Programa: <span class="estadoMaterial {subClass}">({estado})</span>';
	if(asignatura.programa!=null && estadoRevision(asignatura.programa)){
	
			itemLink = itemLink.replace("{existente}","/"+asignatura.programa.id).replace("{nombreAsignatura}","?asignatura="+asignatura.nombre.toUpperCase()+" ("+value.nombre+" - "+value.yearCurso+")&estado="+asignatura.programa.estadoRevisionType);
			itemText = itemText.replace("{estado}",asignatura.programa.estadoRevisionType).replace("{subClass}",asignatura.programa.estadoRevisionType.toLowerCase());
			if(asignatura.programa.estadoRevisionType.toLowerCase() === "aceptado"){
				setSocial = true;
				socialLink.attr("onclick","openSocialShare("+asignatura.programa.id+")");
				};
			
	}else{
			if(asignatura.programa!=null){
				itemText = itemText.replace("{estado}",asignatura.programa.estadoRevisionType).replace("{subClass}",asignatura.programa.estadoRevisionType.toLowerCase());
				
			}else{
				itemText = itemText.replace("{estado}","No Existe").replace("{subClass}","inexistente");
			}
			itemLink= "{text}";
	}
	
	if(setSocial){		
		itemPrograma = itemPrograma.replace("{link}",itemLink.replace("{text}",itemText)+ socialLink.prop('outerHTML'));
		
	}else{
		itemPrograma = itemPrograma.replace("{link}",itemLink.replace("{text}",itemText));
	}
	return itemPrograma;
}
function datosMaterial(value,asignatura){

	var itemMaterial=$('<li></li>');
	itemMaterial.append("Material Did&aacute;ctico: ");	
	if(asignatura.programa!=null && asignatura.programa != undefined && asignatura.programa.materialDidactico != null && asignatura.programa.materialDidactico.length >0){
			$.each(asignatura.programa.materialDidactico.sort(function(a,b){
				  return a.nro-b.nro;
			}), function(index,md){
				if(estadoRevision(md)){
					itemCartillaNumeroLink = $('<a></a>');
					itemMaterialInterno = $('<span></span>');
					itemMaterialInterno.addClass("estadoMaterial");
					itemMaterialInterno.html(md.nro+" ");
					itemMaterialInterno.addClass(md.estadoRevisionType.toLowerCase());
					
					itemCartillaNumeroLink.attr("href",pagePath+"/mvc/asesor/"+asesorId+"/asignatura/"+asignatura.id+"/programa/"+asignatura.programa.id+"/material/"+md.id+"?asignatura="+asignatura.nombre.toUpperCase()+" ("+value.nombre+" - "+value.yearCurso+")&nro="+md.nro+"&estado="+md.estadoRevisionType);
					itemCartillaNumeroLink.append(itemMaterialInterno);
					itemMaterial.append(itemCartillaNumeroLink);
				}
			});
		
	}else{
		itemMaterialInterno = $('<span></span>');
		itemMaterialInterno.addClass("estadoMaterial");
		itemMaterialInterno.html("(No Existe)");
		itemMaterialInterno.addClass("inexistente");
		itemMaterial.append(itemMaterialInterno);
	}
	return itemMaterial.prop('outerHTML');
}

function datosProfesor(value,profe,suplente){
	var itemProfesor='<li>{nombreProfe}: <span class="estadoMaterial {subClass}">{profesor}</span></li>';
	itemProfesor = itemProfesor.replace("{nombreProfe}","Profesor");
	if(suplente!==undefined && suplente!=null){		
		return itemProfesor.replace("{profesor}",suplente.apellido.toUpperCase()+", ("+suplente.dni+")").replace("{subClass}","nuevo");
	}else if(profe !==undefined && profe!=null){
		return itemProfesor.replace("{profesor}",profe.apellido.toUpperCase()+", ("+profe.dni+")").replace("{subClass}","nuevo");
	}else{
		return itemProfesor.replace("{profesor}","No Asignado").replace("{subClass}","rechazado");
	}
}

function estadoRevision(programa){
	if(programa.estadoRevisionType === "LISTO" ||programa.estadoRevisionType === "ASIGNADO" ||programa.estadoRevisionType === "RECHAZADO" ||programa.estadoRevisionType === "ACEPTADO" || programa.estadoRevisionType ==="CAMBIOS"){
		return true;
	}
	return false;
}

function openSocialShare(programaId){
	$.ajax({
		
			type:"GET",
			url:pagePath+"/api/social/oauth2/programa/"+programaId+"?provider=FACEBOOK",
			contentType :'application/json',
			dataType:"json",
			success: function(data,xhr){			
				var socialPublishData = {
						publishEventId : data.publishEventId,
						provider: data.provider,
						programaId: typeof data.programa !== "undefined" ? data.programa.id : programaId,
						message : data.message,
						socialPostStateType: data.socialPostStateType
				}	;
				socialPublish.setSocialPublishData(socialPublishData);
				socialPublish.openPublishDialog();
			},
			error: function(data){						
				location.href=pagePath+"/errors/"+data.error().status;
			}								
	
	});
}