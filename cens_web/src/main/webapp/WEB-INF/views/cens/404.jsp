<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	<script>var pagePath="<%=request.getContextPath() %>"
		function volver(){
		location.href=pagePath;
	}
	</script>


<div class="centreDiv">
  
    <div class="floatLeftDiv">
			<h3>P&Aacute;gina Solicitada Inexistente</h3>
			
	
	        
	        
	        <button class="button searchButton" type="button" onclick="volver()">Volver</button>
	   </div>	

     
</div>




