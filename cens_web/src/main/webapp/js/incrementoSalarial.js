jQuery(document).ready(
	function() {
		var clienteId = $('#clienteId').val();
		var dt = $('#dateTime').val();
		
		jQuery("#projectTable").jqGrid(
				{
					url : "incrementosalarialresultgrilla?clienteId=" + clienteId + "&dt=" + dt,
					datatype : "json",
					contentType : 'application/json',
					jsonReader : {
						repeatitems : false,
						id : "clienteId"
					},
					colNames : [ 'Legajo', 'Empleado', 'Estado', 'Fecha Sueldo', 'Básico', 'Presentismo' ],
					colModel : [ {
						name : 'legajo',
						index : 'legajo',
						sortable : false,
						width: 100,
	        			align:"right",
	        			fixed: true
					}, {
						name : 'empleado',
						index : 'empleado',
						sortable : false
					}, {
						name : 'estado',
						index : 'estado',
						sortable : false,
						width: 120,
	        			align:"center",
	        			fixed: true
					}, {
						name : 'fechaSueldo',
						index : 'fechaSueldo',
						sortable : false,
						width: 100,
	        			align:"center",
	        			fixed: true
					}, {
						name : 'sueldoBasico',
						index : 'sueldoBasico',
						sortable : false,
						width: 100,
	        			align:"right",
	        			fixed: true
					}, {
						name : 'sueldoPresentismo',
						index : 'sueldoPresentismo',
						sortable : false,
						width: 100,
	        			align:"right",
	        			fixed: true
					},

					],
					viewrecords : true,
					caption : "Resultado del Incremento aplicado",
					rowNum : 0,
				});

		$(window).bind(
				'resize',
				function() {
					$("#projectTable").setGridWidth(
							$(window).width() - marginWidthGrid);
					$("#projectTable").setGridHeight(
							$(window).height() - marginHeightGrid + 60);
				}).trigger('resize');

	});

var timeoutHnd;
var flAuto = false;
function doSearch(ev) {
	if (!flAuto)
		return;

	if (timeoutHnd)
		clearTimeout(timeoutHnd)
	timeoutHnd = setTimeout(gridReload, 500)
}

function calculatePageToLoadAfterDelete() {
	var nroRegistrosInPage = $('.jqgrow ').size();
	var currentPage = $('.ui-pg-input').val();

	if (currentPage > 1 && nroRegistrosInPage == 1) {
		return currentPage - 1;
	}

	return currentPage;
}
 

