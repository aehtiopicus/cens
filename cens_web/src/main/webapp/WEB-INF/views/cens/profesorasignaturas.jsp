<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/jqgrid/ui.jqgrid.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/slick/slick.css"/>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/slick.min.js"></script>
	<script>var pagePath="<%=request.getContextPath() %>"</script>
	<script>var profesorId="${profesorId}"</script>
    <script src="<%=request.getContextPath() %>/js/cens/profesorasignatura.js"></script>

	<h3>Informaci&oacute;n de las Asignaturas</h3>	
  	<div id="yearContent"></div>




