var cookiePage = "";
var cookieRegsXPage = "";

jQuery(document).ready(function () {

	restoreState();
	var apellido = jQuery("#apellido").val();
	
    jQuery("#projectTable").jqGrid({
    		url:pagePath+"/api/asignatura/"+asignaturaId+"/alumno",    		
            datatype: "json",
            contentType :'application/json',
            jsonReader: {
                repeatitems: false,
                id: "id",
                cell: "",
                root: function (obj) { 
                	asignaturaData(obj.extraData);
                	return obj.rows; 
                },
              
            },
            colNames:['Nombre', 'Apellido', 'DNI', '<span class="ui-icon ui-icon-trash"/>'],
            colModel:[              
                {name:'miembroCens.nombre',index:'Nombre',sortable: false},
                {name:'miembroCens.apellido',index:'Apellido',sortable: false},
                {name:'miembroCens.dni',index:'DNI',sortable: false},                                		
                { 	
        			name: 'id',  
        			width: 16,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: deleteCurrencyFmatter
        		}
            ],
            rowList:[5,10,50],
            rowNum:cookieRegsXPage,
            pager: "#pagingDiv",
            page:cookiePage,
            postData:{requestData:function(postData) {            	
                return requestData=JSON.stringify({"page": $("#projectTable").getGridParam("page"),"row": $("#projectTable").getGridParam("rowNum"),"sord": $("#projectTable").getGridParam("sortorder"),"filters":{"perfil":"ALUMNO","data":apellido,"asignaturaId":asignaturaId}});
            }},
            viewrecords: true,
            caption: "Alumnos de la Asignatura",            
            loadComplete: function (data) {
            	$(".ui-pg-selbox").val(cookieRegsXPage);
            	$(".ui-pg-input").val(data.page);
            	setCookie('asignaturaUsuarioPage', $('.ui-pg-input').val());
            	cookiePage = $('.ui-pg-input').val();   
            	return data.alumnos;
            },
            onPaging: function(page){
            	setCookie('asignaturaUsuarioRegXPage', $(".ui-pg-selbox").val());
            	cookieRegsXPage = $(".ui-pg-selbox").val();
            }
        });
        
        $(window).bind('resize', function() {
            $("#projectTable").setGridWidth($(window).width()-marginWidthGrid);
            $("#projectTable").setGridHeight($(window).height()-marginHeightGridFull);
        }).trigger('resize');
         
        
        $( "#desincribirAlumno" ).dialog({
			autoOpen: false,
			width: 400,
			modal:true,
			buttons: [
				{
					text: "Ok",
					click: function() {
						desincribirAlumno();
						$( this ).dialog( "close" );
						
					}
				},
				{
					text: "Cancelar",
					click: function() {
						$( this ).dialog( "close" );
						alumnoIdToRemove = null;
					}
				}
			]
		});    
        fixTable();       
    });
 
 
 function deleteCurrencyFmatter (cellvalue, options, rowObject)
 {
	 var link = $("<a></a>");
	 link.attr("href","javascript:desincribirAlumnoAsignatura("+cellvalue+");");
	 link.attr("title","Eliminar Inscripci&oacute;n");
	 
	 var span = $("<span></span>");
	 span.addClass("ui-icon");
	 span.addClass("ui-icon-trash");
	 
	 link.append(span);	
	 
    return link.prop('outerHTML');
 }
 
 function saveState(){
	 setCookie('asignaturaAlumnoF1', jQuery("#apellido").val());
 }
 
function restoreState(){
	if(isAValidCookie('asignaturaAlumnoF1')){ 
		jQuery("#apellido").val(getCookie('asignaturaAlumnoF1'));
	}
	
	
	if(getCookie('asignaturaUsuarioRegXPage') != ""){
		$(".ui-pg-selbox").val(getCookie('asignaturaUsuarioRegXPage'));
		cookieRegsXPage = getCookie('asignaturaUsuarioRegXPage');
	}else{
		cookieRegsXPage = 5;
	}
	if(getCookie('asignaturaUsuarioPage') != ""){
		$('.ui-pg-input').val(getCookie('asignaturaUsuarioPage'));
		cookiePage = getCookie('asignaturaUsuarioPage');
	}else{
		cookiePage = 1;
	}
}
 
 var timeoutHnd;
 var flAuto = false;
 function doSearch(ev){
 	if(!flAuto)
 		return;

 	if(timeoutHnd)
 		clearTimeout(timeoutHnd)
 	timeoutHnd = setTimeout(gridReload,500)
 }
 
 
 function gridReload(currentPage){

	var apellido = jQuery("#apellido").val();
	var pageNro = 1; 
	if(currentPage != null && currentPage > 1){
		pageNro = currentPage;
	}
	
	jQuery("#projectTable").jqGrid(
           'setGridParam',
           {
        	url:pagePath+"/api/asignatura/"+asignaturaId+"/alumno",
            gridview:true,
            contentType :'application/json',
      		dataType: "json",
      		 postData:{requestData:function(postData) {            	
                 return requestData=JSON.stringify({"page": $("#projectTable").getGridParam("page"),"row": $("#projectTable").getGridParam("rowNum"),"sord": $("#projectTable").getGridParam("sortorder"),"filters":{"perfil":"ALUMNO","data":apellido,"asignaturaId":asignaturaId}});
             }},
      		page:pageNro})
      		.trigger("reloadGrid");
	
	saveState();
}

function calculatePageToLoadAfterDelete(){
	var nroRegistrosInPage = $('.jqgrow ').size();
	var currentPage = $('.ui-pg-input').val();
	

	if(currentPage > 1 && nroRegistrosInPage == 1){
		return currentPage-1;
	}
	
	return currentPage;
}
 
function asignaturaData(asignaturaData){
	var profesor = typeof asignaturaData.profesorSuplente == "undefined" ? profNombre(asignaturaData.profesor) :  profNombre(asignaturaData.profesorSuplente);
	var curso = asignaturaData.curso.nombre.toUpperCase()+" ("+asignaturaData.curso.yearCurso+")";
	$("#projectTable").jqGrid("setCaption","Asignatura "+asignaturaData.nombre+" Curso"+curso+" "+" Profesor: "+profesor);
}

function profNombre(prof){
	return prof.miembroCens.apellido.toUpperCase()+", "+prof.miembroCens.nombre.toUpperCase();
}

var alumnoIdToRemove = null;
function desincribirAlumnoAsignatura(alumnoId){
	alumnoIdToRemove = alumnoId;
	$("#desincribirAlumno").dialog("open");
}

function desincribirAlumno(){
	var alumnoId = alumnoIdToRemove;
	
	var pageToLoad = calculatePageToLoadAfterDelete();
	
	alumnoIdToRemove = null;
	$('#message').removeClass('msgSuccess');
	$('#message').removeClass('msgError');
	
	$.ajax({
		type:"DELETE",
		url:pagePath+"/api/inscripcion/asignatura/"+asignaturaId+"/alumno/"+alumnoId,
		contentType :'application/json',
		dataType:"json",
		success: function(data){
			gridReload(pageToLoad);
			$('#message').addClass('msgSuccess');
			cargarMensaje(data,true);
		},
		error: function(data){
			$('#message').addClass('msgError');	
			cargarMensaje(errorConverter(data));
		}								

		}
		
	);
	
} 
