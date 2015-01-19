<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="includes/includes.jsp"></jsp:include>
    </head>
    <body>
        <jsp:include page="includes/header.jsp"></jsp:include>
        <div class="container">
            <div class="row">
                <div class="col-md-8">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3>Priorizaci&oacute;n de Marcas de Filtros</h3>
                        </div>
                        <div class="panel-body">
                            <form:form method="POST" commandName="listaMarcaFiltroPrioridadDTO">
                                <table class="table table-hover table-condensed">
                                    <thead>
                                        <tr>
                                            <th>Marca</th>
                                            <th>Prioridad</th>
                                        </tr>                        
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${listaMarcaFiltroPrioridadDTO.marcaFiltroPrioridadDTOs}" var="marcaFiltroPrioridad" varStatus="status">
                                            <tr>
                                                <td>${marcaFiltroPrioridad.nombreMarca}</td>
                                                <td>
                                                    <form:hidden path="marcaFiltroPrioridadDTOs[${status.index}].id"></form:hidden>
                                                    <form:hidden path="marcaFiltroPrioridadDTOs[${status.index}].codMarca"></form:hidden>
                                                    <form:hidden path="marcaFiltroPrioridadDTOs[${status.index}].nombreMarca"></form:hidden>
                                                    <form:input path="marcaFiltroPrioridadDTOs[${status.index}].prioridad" class="form-control input-sm" type="number"></form:input>
                                                    <div>
                                                        <form:errors path="marcaFiltroPrioridadDTOs[${status.index}].prioridad" class="alert-dismissable alert-danger" /> 
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" id="submitButton" class="btn btn-default has-spinner">
                                            <span class="spinner">
                                                <span class="glyphicon glyphicon-spin glyphicon-refresh"></span>
                                            </span>
                                            Actualizar prioridades
                                        </button>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--        <div class="modal fade bs-example-modal-sm" id="pleaseWaitDialog" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-sm">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3>Actualizando... Por favor aguarde.</h3>
                            </div>
                            <div class="modal-body">
                                <div class="progress-bar progress-striped active">
                                    <div class="bar" style="width: 100%;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>-->
        <script type="text/javascript">
            $(function(){
                $('#submitButton').click(function() {
                    $(this).toggleClass('active');
                });
            });
        </script>
    </body>
</html>
