<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>Send Email</title>
    <link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/send-email.css"/>" rel="stylesheet">
</head>
<body>
<jsp:include page="${pageContext.request.contextPath}/navbar.jsp" />
<div class="container">
    <header>
        <div class="col-md-12">
            <div class="modal-header" id="new_message_header">
                <h2>New Message</h2>
            </div>
        </div>
    </header>
    <section>
        <div class="col-md-12">
            <div class="form-area">
                <form role="form" id="new_message" action="/contacts/send" method="POST">
                    <div class="form-group">
                        <div class="input-group" style="height: 37px">

                            <div class="input-group-addon">
                            <span class="glyphicon glyphicon-user"> To:

                            </span>
                            </div>
                            <input type="text" class="form-control" id="emails" name="emails" value="<c:forEach var="email"
                         items="${template.getEmails()}">${email}; </c:forEach>" disabled>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                    <span class="input-group-addon">
                            <span> Theme:</span>
                        </span>
                            <input type="text" class="form-control" id="theme" name="theme"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                        <span class="input-group-addon">
                            <span> Template:</span>
                        </span>
                            <select id="template" class="form-control">
                                <c:forEach var="templateElement" items="${template.getTemplate().entrySet()}"
                                           varStatus="status">
                                    <option value="${status.count}">${templateElement.getKey()}</option>
                                </c:forEach>
                            </select>
                            <c:forEach var="templateElement" items="${template.getTemplate().entrySet()}"
                                       varStatus="status">
                                <input type="hidden" name="templates" value="${templateElement.getValue()}">
                            </c:forEach>
                        </div>
                    </div>

                    <div class="form-group">
                        <textarea class="form-control" type="textarea" id="message" placeholder="Message" rows="12">
                        </textarea>
                    </div>

                    <button type="button" id="btn-send" name="send" class="btn btn-primary">Send</button>
                    <a href="/contacts">
                        <button type="button" name="cancel" class="btn btn-primary ">Cancel</button>
                    </a>
                </form>
            </div>
        </div>
    </section>
    <footer>Belous Alexandr</footer>
</div>
<script src="<c:url value="/js/compose.js"/>" type="application/javascript"></script>
</body>
</html>