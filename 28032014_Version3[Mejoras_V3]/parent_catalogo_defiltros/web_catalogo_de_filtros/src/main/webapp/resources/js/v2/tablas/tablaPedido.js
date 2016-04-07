
jQuery(document).ready(function () {

//	loadHeaderOnStart();
//	restoreState();
    jQuery("#pedido-table").jqGrid({
    		url:"./pedido/actual?codigo=" + getCodigoCliente(),
            datatype: "json",
            shrinktofit: true,
            contentType :'application/json',
            jsonReader: {repeatitems: false, id:"id"},
            colNames:['Código', 'Descripción', 'Cantidad', 'Precio Unitario', 'Precio', ''],
            colModel:[
                {name:'codigo',index:'codigo', sortable: false,align: "center"},
                {name:'desc',index:'desc',sortable: false, align: "center"},    
                {name:'cantidad',index:'cantidad',sortable: false, align: "center"},    
                {name:'precioUnitario', index:'precioUnitario', sortable: false, editable: false, fixed: true, formatter: precioCurrencyFmatter, align: "center"},    
                {name:'precio', index:'precio', sortable: false, editable: false, fixed: true, formatter: precioCurrencyFmatter, align: "center"},                
                {name: 'id', editable: false, sortable: false, fixed: true, formatter: goToDetalleCurrencyFmatter, align: "center"}
            		],
			rowNum: -1, 
            viewrecords: true,
            loadComplete: function (data) {
            	grid = $("#pedido-table");
            	grid.resize();
            	var container = grid.parents('.ui-jqgrid-view'); // find the grid's container
            	if(jQuery(this)[0].rows.length == 1){
            		$(".pedido-button").addClass("disable");
            		if(container.find("#texto-vacio").length == 0){
            			container.find('.ui-jqgrid-bdiv').append("<span id=texto-vacio>No se encontraron resultados.</span>");// insert the empty data text
            		} 
            	} else {
            		container.find('.ui-jqgrid-bdiv #texto-vacio').remove();
            		$(".pedido-button").removeClass("disable");
            	}
            	calculateTotal();
            }
        });
        
        $(window).bind('resize', function() {
            $("#pedido-table").setGridWidth($(window).width() - 3);
            $("#pedido-table").setGridHeight($(window).height()-330);
        }).trigger('resize');
        applicationPath = getCookie("appPath");
    });
 
function LoadComplete(grid)
{
//	if ($("#pedido-table").getGridParam('records') == 0) // are there any records?
	if (grid.getGridParam('records') == 0) // are there any records?
	    DisplayEmptyText(true);
	else
	    DisplayEmptyText(false);
}

function DisplayEmptyText(display)
{
//	var grid = $("#pedido-table");
	var container = $("#pedido-table").parents('.ui-jqgrid-view'); // find the grid's container
	if (display) {
	    container.find('.ui-jqgrid-bdiv').append("<span id=texto-vacio>No se encontraron resultados.</span>"); // insert the empty data text
	}
	else {
		container.find('.ui-jqgrid-bdiv #texto-vacio').remove();
	}
}

function getCodigoCliente(){
	return codigoCliente;
}
	
function goToDetalleCurrencyFmatter (cellvalue, options, rowObject)
{
	var template = "<button class=\"\" type=\"button\" onclick=\"javascript:removeFiltroOfPedido({ENTITY_ID})\" id=\"deleteButton\">"
					+ "Eliminar"
					+ "</button>";
	 
   return template.replace("{ENTITY_ID}",cellvalue);
}

function precioCurrencyFmatter (cellvalue, options, rowObject)
{
	//TODO completar el precio aplicandole el porcentaje de aumento
	 return Math.round(cellvalue * 100) / 100;
}

calculateTotal = function() {
    var totalAmount = 0,
        iAmount=getColumnIndexByName($("#pedido-table"),'precio');
    var i=1, rows = $("#pedido-table")[0].rows, rowsCount = rows.length, row;

    for(;i<rowsCount;i++) {
        row = rows[i];
        totalAmount += Number(getTextFromCell(row.cells[iAmount]));
    }
    $("#total-pedido").text( Math.round(totalAmount * 100) / 100);
};

getColumnIndexByName = function(grid,columnName) {
    var cm = grid.jqGrid('getGridParam','colModel'),i=0,l=cm.length;
    for (; i<l; i++) {
        if (cm[i].name === columnName) {
            return i; // return the index
        }
    }
    return -1;
};

getTextFromCell = function(cellNode) {
    if (cellNode.childNodes[0].nodeName.toUpperCase() === "SELECT") {
        var selOptions = $("option:selected", cellNode);
        if (selOptions.length>0) {
            return selOptions.text();
        }
    }
    return cellNode.childNodes[0].nodeName.toUpperCase() === "INPUT"?
           cellNode.childNodes[0].value:
           cellNode.textContent || cellNode.innerText;
};

//function restoreState(){
//	if(getCookie('usuarioF1') != ""){ 
//		jQuery("#perfil").val(getCookie('usuarioF1'));
//	}
//	if(getCookie('usuarioF2') != ""){
//		jQuery("#apellido").val(getCookie('usuarioF2'));
//	}
//	
//	if(getCookie('usuarioRegXPage') != ""){
//		$(".ui-pg-selbox").val(getCookie('usuarioRegXPage'));
//		cookieRegsXPage = getCookie('usuarioRegXPage');
//	}else{
//		cookieRegsXPage = 5;
//	}
//	if(getCookie('usuarioPage') != ""){
//		$('.ui-pg-input').val(getCookie('usuarioPage'));
//		cookiePage = getCookie('usuarioPage');
//	}else{
//		cookiePage = 1;
//	}
//}

function gridReload(){
	
	jQuery("#pedido-table").jqGrid('setGridParam',{
           	url:"./pedido/actual?codigo=" + getCodigoCliente(),
            gridview:true,
            contentType :'application/json',
      		dataType: "json",
      		page:1,
      		}).trigger("reloadGrid");

}