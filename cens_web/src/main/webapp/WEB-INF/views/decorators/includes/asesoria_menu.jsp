<%@ page session="false" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %> 


<ul class="menu">

    <li>
        <a href="<%=request.getContextPath() %>/usuarioList">Administraci&oacute;n de usuarios</a>
<!--         <ul> -->
<%--             <li><a href="<%=request.getContextPath() %>/asesoresList">Gesti&oacute;n de Asesores</a></li> --%>
<%--             <li><a href="<%=request.getContextPath() %>/enteList.jsp">Gesti&oacute;n de Profesores</a></li> --%>
<%--            	<li><a href="<%=request.getContextPath() %>/usuarios">Gesti&oacute;n de Preceptores</a></li>            --%>
<!--         </ul> -->
    </li>

	<li>
        <a href="#">Gesti&oacute;n de Expedientes</a>
		<ul>
			<li><a href="<%=request.getContextPath() %>/expedienteList">Gesti&oacute;n de Expedientes</a></li>

		</ul>
	</li>


</ul>