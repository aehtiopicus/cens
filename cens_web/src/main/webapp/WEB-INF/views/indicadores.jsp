<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/jqgrid/ui.jqgrid.css">

    <script src="<%=request.getContextPath() %>/js/indicadores.js?v=<%= new Date().getTime()%>"></script>

<div class="centreDiv">
  	
    <div class="floatLeftDiv">
    
		<!--     Filtros -->
		<div class="filtrosDiv">
		<label for="periodo">Periodo 1:</label>
			<select id="periodo1">
			    <c:forEach items="${periodosDto}" var="item">
		           <option value="${item}"> ${item} </option>
		        </c:forEach>
	        </select>
	       	        
	        <label for="periodo">Periodo 2:</label>
			<select id="periodo2">
			    <c:forEach items="${periodosDto}" var="item">
		           <option value="${item}"> ${item} </option>
		        </c:forEach>
	        </select>
           <button class="button searchButton" type="button" onclick="gridReload()" id="submitButton">Comparar periodos</button>
	   </div>	

        
		<!--    Tabla a llenar  -->
        <div>
           <table id="projectTable"></table>
            
        </div>
      
    </div>

</div>




