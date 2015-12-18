<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>404 error page</title>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="<c:url value="${pageContext.request.contextPath}/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="${pageContext.request.contextPath}/css/error-404.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="wrap">
    <div class="content">
        <div class="logo">
            <h1><img src="${pageContext.request.contextPath}/images/logo.png"/></h1>
            <span><img src="${pageContext.request.contextPath}/images/signal.png"/>Oops! The Page you requested was not found!</span>
        </div>
        <div class="buttom">
        </div>
    </div>
    <footer>
        <p class="copy_right">&#169; 2015 Alexandr Belous</p>
    </footer>
</div>

</body>
</html>
