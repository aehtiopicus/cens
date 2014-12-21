var cookiePage = "";
var cookieRegsXPage = "";

jQuery(document).ready(function () {
	
	 restoreState();
	 var cuil = jQuery("#cuil").val();
	 var apellido = jQuery("#apellido").val();
	 var cliente = jQuery("#cliente").val();
	 var estado = jQuery("#estado").val();
     
	 jQuery("#projectTable").jqGrid({
          	url:"empleadosgrilla?cuil="+cuil+"&apellido="+apellido+"&cliente="+cliente+"&estado="+estado,
            datatype: "json",
            contentType :'application/json',
            jsonReader: {repeatitems: false, id:"empleadoId"},
            colNames:['Legajo','Apellido','Nombre', 'DNI','Cliente', 'Fecha Ingreso Novatium','Email Personal','Estado','Sueldo','Vacaciones','Historial','<span class="ui-icon ui-icon-pencil"/>'],
            colModel:[
                {name:'legajo',index:'legajo',sortable: false},
                {name:'apellidos',index:'apellidos',sortable: false},
                {name:'nombres',index:'nombres', sortable: false},
                {name:'dni',index:'dni',sortable: false},
                {name:'clienteNombre',index:'clienteNombre',sortable: false},
                {name:'fechaIngresoNovatium',index:'fechaIngresoNovatium',sortable: false},
                {name:'emailPersonal',index:'emailPersonal',sortable: false}, 
                {name:'estado',index:'estado',sortable: false}, 
        		{ 	
                	align: 'center',
        			name: 'empleadoId',   
        			width: 50,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: sueldoCurrencyFmatter
        		},
        		{ 	
                	align: 'center',
        			name: 'empleadoId',   
        			width: 80,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: vacacionesCurrencyFmatter
        		},
        		{ 	
                	align: 'center',
        			name: 'empleadoId',   
        			width: 80,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: historialCurrencyFmatter
        		},
                { 	
                	align: 'center',
        			name: 'empleadoId',   
        			width: 16,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: editCurrencyFmatter
        		}

            ],
            rowNum:cookieRegsXPage,
            rowList:[5,10,50],
            pager: "#pagingDiv",
            page:cookiePage,
            viewrecords: true,
            caption: "Empleados",
            loadComplete: function (data) {
            	$(".ui-pg-selbox").val(cookieRegsXPage);
            	$(".ui-pg-input").val(data.page);
            	setCookie('empleadoPage', $('.ui-pg-input').val());
            	cookiePage = $('.ui-pg-input').val();
            },
            onPaging: function(page){
            	setCookie('empleadoRegXPage', $(".ui-pg-selbox").val());
            	cookieRegsXPage = $(".ui-pg-selbox").val();
            }
        });
        
        $(window).bind('resize', function() {
            $("#projectTable").setGridWidth($(window).width()-marginWidthGrid);
            $("#projectTable").setGridHeight($(window).height()-marginHeightGridFull);
        }).trigger('resize');
      });
 
 function editCurrencyFmatter (cellvalue, options, rowObject)
 {
	if(!rowObject.accionEditar){return "";}
	
	var template = "<a href='empleado/{ENTITY_ID}' title='Editar'><span class='ui-icon ui-icon-pencil'/></a>";
	 
    return template.replace("{ENTITY_ID}",cellvalue);
 }

 function sueldoCurrencyFmatter(cellvalue, options, rowObject)
 {
	if(!rowObject.accionSueldo){return "";}

	 if(rowObject.estado == 'Actual'){
  		 var template = "<a href='empleados/sueldo/{EMPLEADO_ID}' title='Sueldo'><span class='ui-icon ui-icon-calculator' style='margin-left: 38%;'/></a>";
		 return template.replace("{EMPLEADO_ID}",rowObject.empleadoId);
	 }else{
		 return "";
	 }
			
 }
 
function vacacionesCurrencyFmatter(cellvalue, options, rowObject)
{
	if(!rowObject.accionVacacion){return "";}

	var template = "<a href='empleados/vacaciones/{EMPLEADO_ID}' title='Vacaciones'><span class='ui-icon ui-icon-calendar' style='margin-left: 38%;'/></a>";
	return template.replace("{EMPLEADO_ID}",rowObject.empleadoId);		
}
 
function historialCurrencyFmatter(cellvalue, options, rowObject) {
	if(!rowObject.accionHistorial){return "";}

	var template = "<a href='empleados/historial/{EMPLEADO_ID}' title='Historial'><span class='ui-icon ui-icon-note' style='margin-left: 38%;'/></a>";
	return template.replace("{EMPLEADO_ID}",rowObject.empleadoId);		
}
 
function saveState(){
	 setCookie('empleadoF1', jQuery("#cuil").val());
	 setCookie('empleadoF2', jQuery("#apellido").val());
	 setCookie('empleadoF3', jQuery("#cliente").val());
	 setCookie('empleadoF4', jQuery("#estado").val());
}

function restoreState(){
	if(isAValidCookie('empleadoF1')){ 
		jQuery("#cuil").val(getCookie('empleadoF1'));
	}
	if(isAValidCookie('empleadoF2')){
		jQuery("#apellido").val(getCookie('empleadoF2'));
	}
	if(isAValidCookie('empleadoF3')){
		jQuery("#cliente").val(getCookie('empleadoF3'));
	}
	if(isAValidCookie('empleadoF4')){
		jQuery("#estado").val(getCookie('empleadoF4'));
	}
	
	if(getCookie('empleadoRegXPage') != ""){
		$(".ui-pg-selbox").val(getCookie('empleadoRegXPage'));
		cookieRegsXPage = getCookie('empleadoRegXPage');
	}else{
		cookieRegsXPage = 5;
	}
	if(getCookie('empleadoPage') != ""){
		$('.ui-pg-input').val(getCookie('empleadoPage'));
		cookiePage = getCookie('empleadoPage');
	}else{
		cookiePage = 1;
	}
}
 
 var timeoutHnd;
 var flAuto = false;

 
 function gridReload(currentPage){
	var cuil = jQuery("#cuil").val();
	var apellido = jQuery("#apellido").val();
	var cliente = jQuery("#cliente").val();
	var estado = jQuery("#estado").val();
	
	var pageNro = 1; 
	if(currentPage != null && currentPage > 1){
		pageNro = currentPage;
	}
	
	jQuery("#projectTable").jqGrid(
           'setGridParam',
           {
           	url:"empleadosgrilla?cuil="+cuil+"&apellido="+apellido+"&cliente="+cliente+"&estado="+estado,
            gridview:true,
            contentType :'application/json',
      		dataType: "json",
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

function exportar(){
	var cuil = jQuery("#cuil").val();
	var apellido = jQuery("#apellido").val();
	var cliente = jQuery("#cliente").val();
	var estado = jQuery("#estado").val();
	window.location= window.location.href+'/exportar?apellido='+apellido+'&cliente='+cliente+'&estado='+estado;
	
}

