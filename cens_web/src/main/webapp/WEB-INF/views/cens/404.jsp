<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	<script>			
		setTimeout(function volver(){location.href="<%=request.getContextPath() %>"}, 5000);
	</script>


<div class="centreDiv">
  
    <div class="statusError">
			<h3>P&aacute;gina Solicitada Inexistente</h3>
			
	
	        
	        
	        <button class="button searchButton" type="button" onclick="location.href='<%=request.getContextPath() %>'">Volver</button>
	   </div>	

     
</div>




