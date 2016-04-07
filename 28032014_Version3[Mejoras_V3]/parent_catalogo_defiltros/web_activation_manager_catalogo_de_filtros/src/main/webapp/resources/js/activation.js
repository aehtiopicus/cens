/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function activarVendedor(){
    if(($('#vendedorSelect').val())!=="-1"){
        $('#codigoCliente').val("");
        $('#codigoCliente').prop("disabled",true);
    }else{
        $('#codigoCliente').prop("disabled",false);
    }
}

function activarCliente(){
    if(!$('#codigoCliente').val().trim()){
        
        $('#vendedorSelect').prop("disabled",false);
        
    }else{
        $('#vendedorSelect').val("-1");
        $('#vendedorSelect').prop("disabled",true);
    }
}