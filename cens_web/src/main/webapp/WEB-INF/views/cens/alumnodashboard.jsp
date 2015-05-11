<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<script>var pagePath="<%=request.getContextPath() %>"</script>	
    <script src="<%=request.getContextPath() %>/js/cens/alumnodashboard.js"></script>
	
  	<div id="yearContent"></div>
  	<script>


  	$(document).bind("userProfileItemInitComplete",function(){
  		var dashboard = new alumnodashboard.al.db();
  		dashboard.cargarDashboard();
  		$(document).unbind("userProfileItemInitComplete");
  	});

  	if(new localstorage.ls.userProfileData().isLoaded()){
  		$(document).trigger("userProfileItemInitComplete");
  	}
  	</script>
  	