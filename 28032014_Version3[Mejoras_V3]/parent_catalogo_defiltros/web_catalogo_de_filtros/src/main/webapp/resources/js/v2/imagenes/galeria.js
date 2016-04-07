var url = "filtros/listfotos";

jQuery(document).ready(function() {
	wipeOutGrid();
});
	 
function loadImgs(page) {
	$.blockUI({ message: '<div class="loading ui-state-default ui-state-active loadingpopup">Cargando...</div>' });
	var urlCompleta = url + getFiltros() + "&rows=15&page="+page; 
	
	wipeOutGrid();
	$.ajax({
        type: "GET",
        url: urlCompleta,
        success: function(data){
        	$('#notFound').hide();
        	if(data != null &&  data.rows.length > 0){
        		cargarPaginador(data.total);
        		for(var i = 0; i < data.rows.length; i++) {
        			addImage(data.rows[i].foto, data.rows[i].codigoCorto,data.rows[i].id);
        			if(i%20 == 0) {
    					buildGrid();
    				}
        		}
        		buildGrid();
        		$.unblockUI();
        		
        	}else{
        		cleanPagination();
        		$('#items').html('');
           		$('#notFound').show();
        		$.unblockUI();
        	}	
        },
       
    });
	buildGrid();
	
	};
	


function cargarPaginador(newTotalPages){

        $('#pagination').twbsPagination({
        	
	       totalPages: newTotalPages,
	       visiblePages: 5,
	       onPageClick: function (event, page) {
	      	loadImgs(page);
	       }
	   });

	 
} 

function loadImages(page){
	   cleanPagination();
       loadImgs(page);

}

function cleanPagination(){
	  $('#pagination').empty();
      $('#pagination').removeData('twbs-pagination');
      $('#pagination').unbind('page');
}

function wipeOutGrid() {
	$('#items').html('');
	
}



function buildGrid() {
	$('#items').gridify({items : 5});
}

function addImage(imageName, id,entityId) {
	$('#items').append('<div><img onload="buildGrid()" id=\"'+id+'\"src=\"img/'+imageName +"\"style=\"width: 200px!important; padding-top:15px; height: auto;!important; float: left;\" title=\""+id+"\"></img><span><a style=\"color:#2a6496; margin-left:-130px\" href='detalle/"+entityId+"'>"+id+"</a></span></div>");

}

