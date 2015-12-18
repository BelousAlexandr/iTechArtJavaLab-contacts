<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>Search Contact</title>
    <link rel="icon" href="favicon.ico" type="image/x-icon">
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
    <link href="<c:url value="${pageContext.request.contextPath}/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="${pageContext.request.contextPath}/css/search-contacts.css"/>" rel="stylesheet">
</head>
<body>
<jsp:include page="${pageContext.request.contextPath}/navbar.jsp" />
<div class="container">
    <header>
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8 page-header">
                <div class="modal-header" id="search_contacts_header">
                    <h1>Search Contacts</h1>
                </div>
            </div>
            <div class="col-md-2"></div>
        </div>

    </header>
    <section>
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <div class="well well-sm">
                    <form id="filter_form" name="filter_form" action="" method="GET">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label for="first_name">First Name</label>

                                            <div class="input-group">
                                        <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span>
                                        </span>
                                                <input type="text" class="form-control" id="first_name"
                                                       placeholder="Enter First name"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="last_name">Last name</label>

                                            <div class="input-group">
                                        <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span>
                                        </span>
                                                <input type="text" class="form-control" id="last_name"
                                                       placeholder="Enter Last name"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="middle_name">Middle name: </label>

                                            <div class="input-group">
                                        <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span>
                                        </span>
                                                <input type="text" class="form-control" id="middle_name"
                                                       placeholder="Enter Middle name"/>
                                            </div>
                                        </div>
                                        <div class="age" id="age">
                                            <label for="age">Age: </label>

                                            <div class="form-inline">
                                                <label for="from_birthday">From: </label>

                                                <div class="input-group">
                                                <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-gift"></span>
                                                </span>

                                                    <input type="date" class="form-control" id="from_birthday"
                                                           placeholder="From:"/>
                                                </div>

                                                <div class="input-group"> -</div>
                                                <label for="to_birthday">To: </label>

                                                <div class="input-group">
                                                <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-gift"></span>
                                                </span>
                                                    <input type="date" class="form-control" id="to_birthday"
                                                           placeholder="To: "/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="gender" id="gender">
                                            <label for="gender">Gender: </label>

                                            <div class="form-group">
                                                <input type="radio" name="gender" value="MALE" title="Male"/>
                                                MALE
                                                <input type="radio" name="gender" value="FEMALE" title="Female"/>
                                                FEMALE
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="nationality">Nationality: </label>
                                            <input type="text" class="form-control" id="nationality"
                                                   placeholder="Enter nationality"/>
                                        </div>

                                        <div class="form-group">
                                            <label for="relationship_status">Relationship status: </label>
                                            <select id="relationship_status"
                                                    class="form-control">
                                                <option value="0" class="active">Choose a status</option>
                                                <option value="SINGLE">Single</option>
                                                <option value="MARRIED">Married</option>
                                            </select>
                                        </div>

                                        <h3><label>Address:</label></h3>

                                        <div class="form-group">
                                            <div class="form-group">
                                                <label for="country">Country: </label>
                                                <input type="text" class="form-control" id="country"
                                                       placeholder="Enter Country"/>
                                            </div>

                                        </div>
                                        <div class="form-group">
                                            <div class="form-group">
                                                <label for="city">City: </label>
                                                <input type="text" class="form-control" id="city"
                                                       placeholder="Enter City"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="text-center">
                            <button type="button" class="btn btn-primary" id="search_contacts">Search</button>
                            <a href="/contacts">
                                <button type="button" class="btn btn-primary">Cansel</button>
                            </a>
                        </div>
                    </form>
                </div>
            </div>
            <div class="col-md-2"></div>
        </div>
    </section>
</div>
<footer></footer>
<script src="${pageContext.request.contextPath}/js/search-contacts.js" type="application/javascript"></script>
</body>
</html>