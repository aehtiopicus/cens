<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<!DOCTYPE html>  
<html lang="es">  
    <head>  
        <meta charset="utf-8" />  
        
        <title><decorator:title/></title>  
        
       	<style>
			fieldset{
			  border: 1px solid white;
			  width: 390px;
			  margin:auto;
			  padding-top: 100px;
			}
			
			fieldset div {
			    vertical-align: middle;
			    padding-top: 5px;
			    padding-bottom: 0px;
			    font-family:Helvetica Narrow, sans-serif;
			}
			
			.center{
			    text-align: center;
			}
			
			fieldset div label {
			    display: inline-block;
			    *display: inline;
			    zoom:1;
			    font-size: 14px;
			    height: 24px;
			    margin: 0 10px 0 0;
			    padding: 3px 7px;
			    width: 70px;
			    margin-top:5px;
			}
			
			fieldset div input {
			    border: 1px solid #CCCCCC;
			    
			    height: 16px;
			    padding: 7px;
			    width: 181px;
			}
			
			fieldset div input[type=checkbox] {
			    border: 1px solid #CCCCCC;
			    height: 16px;
			    padding: 7px;
			    width: 50px;
			}
			
			fieldset div textarea {
			    border: 1px solid #CCCCCC; 
			    width: 450px;
			    font-family: arial, verdana, ms sans serif;
			}
			
			fieldset div select {
			    border: 1px solid #CCCCCC;
			    height: 32px;
			    padding: 4px;
			    width: 456px;
			}
			
			fieldset div .ui-state-error{
				margin-left: 188px;
				display: inline-block;
			}
			
			fieldset div input:focus, 
			fieldset div select:focus, 
			fieldset div textarea:focus{
			    outline: none !important;
			    border:1px solid blue;
			    box-shadow: 0 0 10px #719ECE;
			}
			
			.footerForm{
			    margin-top: -2px;
			    text-align: right;
			    margin-right: 93px;
			}
			
			.tituloForm,h3 {
				font-size: 15px;
				color: #005577;
				margin-left: 3px;
			}
			
			.tituloForm,h2 {
				color: #005577;
			}
			
			.errorblock {
				color: #ff0000;
				background-color: #ffEEEE;
				padding: 8px;
				text-align: center;
				margin-right: 94px;
				margin-top: 5px;
			}
			
			.button{
				padding: .4em 1em;
				font-weight: bold;
				color: #004499;
				border: 1px solid #ccc;
				background: linear-gradient(to bottom, #FFF 0%, #EEE 99%);
			}
			.button:hover{
				background-color :#004499; 
				color: #FFFFFF;
				border: 1px solid #003366;
				background: linear-gradient(to bottom, #004499 0%, #003366 99%);
			}
		</style>
       
    </head>
      
    <body marginheight="0px" marginwidth="0px" style="margin-top: 0px; margin-left: 0px;"> 
    	<div class="mainPanel">

    	
    		<div class="contentPanel">
    			<!-- INSERTAR BODY -->
    			<decorator:body/>  
    		</div>
    	

    	</div>
    	
    </body>  
</html> 