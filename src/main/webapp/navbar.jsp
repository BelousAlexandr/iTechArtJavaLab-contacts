<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-1"></div>
<div class="col-md-10">
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="<c:url value="/contacts"/>">Contacts</a>
            </div>
            <div class="navbar-header pull-right">
                <a class="navbar-brand" href="<c:url value="/contacts/search/"/>">
                    <i class="glyphicon glyphicon-search"></i>
                    Search
                </a>
            </div>
        </div>
    </nav>
</div>

<div class="col-md-1"></div>