<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="shortcut icon" type="image/vnd.microsoft.icon" href="favicon.ico"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>home</title>
    <link rel="icon" href="favicon.ico" type="image/x-icon">
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
    <link href="<c:url value="${pageContext.request.contextPath}/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="${pageContext.request.contextPath}/css/home.css"/>" rel="stylesheet">
</head>
<body>
<jsp:include page="${pageContext.request.contextPath}/navbar.jsp"/>
<div class="container">
    <header>
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8 page-header">
                <div class="modal-header" id="contacts_header">
                    <h1>Contacts</h1>
                </div>
            </div>
            <div class="col-md-2"></div>
        </div>
    </header>
    <section>
        <div class="row">
            <div class="col-md-1"></div>
            <div class="col-md-10">
                <div class="row" id="contacts_list">
                    <div class="row section" id="contacts">
                        <form action="" method="" id="form1">
                            <c:if test="${!empty contacts}">
                                <div class="bars pull-left">
                                    <button id="remove" class="btn btn-danger action action hidden" type="button">
                                        <i class="glyphicon glyphicon-remove"></i>
                                        Delete
                                    </button>
                                </div>
                            </c:if>

                            <div class="bars pull-right">
                                <div class="toolbar">
                                    <div class="container-fluid">
                                        <ul class="nav nav-tabs">
                                            <li><a href="<c:url value='/contacts/create'/>"><i
                                                    class="glyphicon glyphicon-plus glyphicon-plus"></i>New Contact</a>
                                            </li>
                                            <li>
                                                <button class="btn btn-success action hidden" id="compose"
                                                        type="button">
                                                    <i class="glyphicon glyphicon-send"></i>
                                                    Send Email
                                                </button>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            <div class="alert-warning">
                                <c:forEach var="email" items="${badEmails.entrySet()}">
                            <span>
                                <h1>${email.getKey()}</h1> <h1>${email.getValue()}</h1>
                            </span>
                                </c:forEach>
                            </div>

                            <div class="fixed-table">

                                <table class="table table-striped text-center" id="table-contacts">
                                    <thead>
                                    <tr data-index="0">
                                        <th></th>
                                        <th>Full Name</th>
                                        <th>Date of Birth</th>
                                        <th>Address</th>
                                        <th>Current Company</th>
                                        <th class="action"></th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <c:forEach var="contact" items="${contacts}">
                                        <tr data-index="${contact.id}">
                                            <td class="bs-checkbox">
                                                <input data-index="${contact.id}" name="long[]" type="checkbox"
                                                       value="${contact.id}"
                                                       onclick="app.checkChoice(this)">
                                            </td>
                                            <td class="fullName">
                                                <a href="<c:url value='/contacts/${contact.id}/edit'/>">
                                                        ${contact.firstName} ${contact.lastName} ${contact.middleName}
                                                </a>
                                            </td>
                                            <td>${contact.dateOfBirth}</td>
                                            <td>${contact.location} street: ${contact.street} house: ${contact.house}
                                                flat: ${contact.flat}
                                            </td>
                                            <td>${contact.currentJob}</td>
                                            <td class="action">
                                                <a href="<c:url value='/contacts/${contact.id}/edit'/>"><i
                                                        class="glyphicon glyphicon-pencil"></i></a>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    </tbody>

                                </table>

                                <c:if test="${!empty contacts}">
                                <div class="panel-default">
                                    <div class="row">
                                        <ul class="pager">
                                            <c:if test="${!empty paging}">
                                            <li>
                                                <c:if test="${paging.page != 1}">
                                                    <a type="button" href="/contacts?page=${paging.page - 1}&pageSize=${paging.pageSize}" name="prev"
                                                       class="btn btn-sm btn-success  prev">&larr;
                                                        Previous
                                                    </a>
                                                </c:if>
                                            </li>
                                            <li>
                                                <c:if test="${paging.page lt paging.pagesCount}">
                                                <a type="bytton" href="/contacts?page=${paging.page + 1}&pageSize=${paging.pageSize}" name="next"
                                                   class="btn btn-sm btn-success next">
                                                    Next &rarr;</a>
                                                </c:if>
                                            </li>
                                            </c:if>
                                        </ul>
                                    </div>
                                </div>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="col-md-1"></div>
            </div>
        </div>
    </section>
    <footer></footer>
</div>
<script src="<c:url value="${pageContext.request.contextPath}/js/home.js"/>"></script>
</body>
</html>