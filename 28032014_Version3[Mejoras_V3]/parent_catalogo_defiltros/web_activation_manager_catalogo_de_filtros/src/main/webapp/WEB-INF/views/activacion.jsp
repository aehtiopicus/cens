<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="includes/includes.jsp"></jsp:include>
        <script src="<%=request.getContextPath()%>/resources/js/activation.js"></script>
    </head>
    <body>
        <jsp:include page="includes/header.jsp"></jsp:include>
        <div class="container">
            <div class="row">
                <div class="col-md-8">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3>Formulario de generaci&oacute;n de serial</h3>
                        </div>
                        <div class="panel-body">

                            <form:form method="POST" commandName="serialDTO" action="/serial.txt"
                                       class="">

                                <div class="form-group">
                                    <form:label path="idEquipo" class="control-label">Identificador de equipo:</form:label>
                                    <div class="">
                                        <form:input path="idEquipo" class="form-control"></form:input>
                                    </div>
                                    <form:errors path="idEquipo" class="errors" />
                                </div>
                                <div class="form-group">
                                    <form:label path="codigoDeCliente" class="control-label">C&oacute;digo de cliente:</form:label>
                                    <div class="">
                                    <form:input id="codigoCliente" path="codigoDeCliente" class="form-control" onblur="activarCliente()"></form:input>
                                    </div>
                                    <form:errors path="codigoDeCliente" class="errors" />
                                </div>
                                <div class="form-group">
                                    <form:label path="codigoDeCliente" class="control-label">Selecci&oacute;n de Vendedor:</form:label>
                                    <div class="">
                                    <form:select id="vendedorSelect" path="vendedorId" class="form-control" onchange="activarVendedor()">
                                        <form:option value="-1" label="Seleccione" />
                                        <form:options items="${vendedoresDto}" itemValue="codigoInternoVendedor" itemLabel="nombreVendedor"/>
                                    </form:select>                                    
                                    </div>
                                    <form:errors path="codigoDeCliente" class="errors" />
                                </div>
                                <div class="form-group">
                                    <form:label path="mesesDeValidez" class="control-label">Meses de Validez:</form:label>
                                    <div class="">
                                        <form:input path="mesesDeValidez" class="form-control" type="number" ></form:input>
                                    </div>
                                    <form:errors path="mesesDeValidez" class="errors" />
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button id="submitButton" type="submit" class="btn btn-default">Generar</button>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="includes/footer.jsp"></jsp:include>
    </body>
</html>
