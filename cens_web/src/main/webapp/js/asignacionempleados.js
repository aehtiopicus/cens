var url = "asignacionempleadosgrilla";
var cookiePage = "";
var cookieRegsXPage = "";

jQuery(document).ready(function () {

	restoreState();
	var filtros = getFiltros();
	
    jQuery("#projectTable").jqGrid({
            url: url + filtros,
            datatype: "json",
            contentType :'application/json',
            jsonReader: {repeatitems: false, id:"relacionLaboralId"},
            colNames:['Legajo','Apellido y Nombre', 'Cliente ','Fecha Inicio', 'Puesto', 'Acci&oacute;n','Cambiar Puesto','Historial'],
            colModel:[
                {name:'legajoEmpleado',index:'legajoEmpleado', sortable: false},
                {name:'nombreEmpleado',index:'nombreEmpleado',sortable: false},
                {name:'razonSocialCliente',index:'razonSocialCliente',sortable: false},
                {name:'fechaInicio',index:'fechaInicio',sortable: false},
                {name:'puestoNombre',index:'puestoNombre',sortable: false},  
                            
                { 	
                	name: 'relacionLaboralId',   
        			width: 70,
        			align:"center",
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: editCurrencyFmatter
        		},
                { 	
        			name: 'relacionLaboralId',   
        			width: 100,
        			align:"center",
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: cambiarPuestoCurrencyFmatter
        		},
        		{ 	
        			name: 'empleadoId',   
        			width: 80,
        			align:"center",
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: historialCurrencyFmatter
        		}
            ],
            rowNum:cookieRegsXPage,
            rowList:[5,10,50],
            pager: "#pagingDiv",
            page:cookiePage,
            viewrecords: true,
            caption: "Asignaci&oacute;n de Empleados",
            loadComplete: function (data) {
            	$(".ui-pg-selbox").val(cookieRegsXPage);
            	$(".ui-pg-input").val(data.page);
            	setCookie('asigEmpleadoPage', $('.ui-pg-input').val());
            	cookiePage = $('.ui-pg-input').val();
            },
            onPaging: function(page){
            	setCookie('asigEmpleadoRegXPage', $(".ui-pg-selbox").val());
            	cookieRegsXPage = $(".ui-pg-selbox").val();
            }
        });
        
        $(window).bind('resize', function() {
            $("#projectTable").setGridWidth($(window).width()-marginWidthGrid);
            $("#projectTable").setGridHeight($(window).height()-marginHeightGridFull+30);
        }).trigger('resize');
        
        
    });
 
function editCurrencyFmatter (cellvalue, options, rowObject) {
	var template1 = "<a href='nuevaasignacion/{EMPLEADO_ID}' title='Asignar'><span class='ui-icon ui-icon-plusthick' style='margin-left: 38%;'/></a>"; 
	var template2 = "<a href='editarasignacion/{ASIGNACION_ID}' title='Desasignar'><span class='ui-icon ui-icon-minusthick' style='margin-left: 38%;'/></a>"; 

	if(cellvalue == null){
		return template1.replace("{EMPLEADO_ID}",rowObject.empleadoId);
	}else{
		
		if(!rowObject.accionDesasignar){return "";}
		return template2.replace("{ASIGNACION_ID}",rowObject.relacionLaboralId);		
	}
}
 
function cambiarPuestoCurrencyFmatter (cellvalue, options, rowObject) {
	if(!rowObject.accionCambiarPuesto){return "";}

	var template1 = "<a href='cambiarpuesto/{ASIGNACION_ID}' title='Cambiar Puesto'><span class='ui-icon ui-icon-check' style='margin-left: 38%;'/></a>";
	
	if(cellvalue == null){
		return "";
	}else{
		return template1.replace("{ASIGNACION_ID}",rowObject.relacionLaboralId);
	}
}
 
function historialCurrencyFmatter(cellvalue, options, rowObject) {
	if(!rowObject.accionHistorial){return "";}

	 var template = "<a href='empleados/historial/{EMPLEADO_ID}' title='Historial'><span class='ui-icon ui-icon-note' style='margin-left: 38%;'/></a>";

	 return template.replace("{EMPLEADO_ID}",rowObject.empleadoId);		
}
 
function saveState(){
	 setCookie('asigEmpleadoF1', jQuery("#cliente").val());
	 setCookie('asigEmpleadoF2', jQuery("#nombre").val());
	 setCookie('asigEmpleadoF3', jQuery("#apellido").val());
}

function restoreState(){
	
	if(isAValidCookie('asigEmpleadoF1')){ 
		jQuery("#cliente").val(getCookie('asigEmpleadoF1'));
	}
	if(isAValidCookie('asigEmpleadoF2') != ""){
		jQuery("#nombre").val(getCookie('asigEmpleadoF2'));
	}
	if(isAValidCookie('asigEmpleadoF3') != ""){
		jQuery("#apellido").val(getCookie('asigEmpleadoF3'));
	}
	
	if(getCookie('asigEmpleadoRegXPage') != ""){
		$(".ui-pg-selbox").val(getCookie('asigEmpleadoRegXPage'));
		cookieRegsXPage = getCookie('asigEmpleadoRegXPage');
	}else{
		cookieRegsXPage = 5;
	}
	if(getCookie('asigEmpleadoPage') != ""){
		$('.ui-pg-input').val(getCookie('asigEmpleadoPage'));
		cookiePage = getCookie('asigEmpleadoPage');
	}else{
		cookiePage = 1;
	}
}

function gridReload(currentPage){

	var filtros = getFiltros();
	
	var pageNro = 1; 
	if(currentPage != null && currentPage > 1){
		pageNro = currentPage;
	}
	
	jQuery("#projectTable").jqGrid(
           'setGridParam',
           {
           	url: url + filtros,
            gridview:true,
            contentType :'application/json',
      		dataType: "json",
      		page:pageNro})
      		.trigger("reloadGrid");
	
	saveState();
}
 
 function getFiltros(){
	 var filtros = "?";
	 filtros += "cliente=" + $("#cliente").val() + "&";
	 filtros += "nombre=" + $("#nombre").val() + "&";
	 filtros += "apellido=" + $("#apellido").val();
	 return filtros;
 }