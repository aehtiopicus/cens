<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<script>
	var pagePath="<%=request.getContextPath() %>";
		var oauth2;
	</script>	
    <script src="<%=request.getContextPath() %>/js/cens/admin.js"></script>
	
  	<div id="adminContent">
  		<div id="facebook">
  			<fieldset>
  				<div>
  					<label>Autenticar en facebook</label>
  					<button id="fbButton" type="button" class="button">Autenticar</button>
  				</div>
  			</fieldset>
  		</div>
  	</div>
  <div id="loginDialog" class="dialog" title="Autenticaci&oacute;n">
	<p>Autenticaci&oacute;n de Usuario ante sistema externo</p>
	<div>
		<a id="fbLink">Facebook</a>		
	</div>
</div>