<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>Create Contact</title>
    <link href="<c:url value="${pageContext.request.contextPath}/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="${pageContext.request.contextPath}/css/create-contact.css"/>" rel="stylesheet">
</head>
<body>
<jsp:include page="${pageContext.request.contextPath}/navbar.jsp"/>
<div class="container">
    <header>
        <div class="row">
            <div class="col-md-1"></div>
            <div class="col-md-10 page-header">
                <c:choose>
                    <c:when test="${empty contact}">
                        <div class="modal-header" id="create_contacts_header">
                            <h1>Create Contacts</h1>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="modal-header" id="edit_contacts_header">
                            <h1>Edit Contacts</h1>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-md-1"></div>
        </div>
    </header>
    <section>
        <div class="row">
            <form id="contact_form" action="" method="post"
                  enctype="multipart/form-data">
                <div class="col-md-1 col-xs-1 col-sm-1"></div>
                <div class="col-md-10 col-sm-10 col-lg-10 col-xs-12">
                    <div class="col-md-2 col-xs-3 col-sm-3">
                        <c:choose>
                            <c:when test="${ !empty contact && !empty contact.photo.name }">
                                <div class="file_upload">
                                    <img class="thumb" alt="contact photo" src="<c:url value="/contacts/${contact.id}/profile-img"/>"
                                         id="contact_photo">
                                    <input type="file" id="photoFile" name="photoFile" accept='image/*'/>
                                    <button type="button"><small>Change photo</small></button>                 </div>
                            </c:when>
                            <c:otherwise>
                                <div class="file_upload">
                                    <img class="thumb" alt="contact photo" src="<c:url value="/images/no-image.png"/>"
                                         id="contact_photo">
                                    <input type="file" id="photoFile" name="photoFile" multiple accept='image/*'/>
                                    <button type="button"><small>Change photo</small></button>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-md-10 col-sm-10 col-xs-12">
                        <div class="hidden" id="contact_id" data-index="${contact.id}"></div>
                        <div class="form-group">
                            <label for="firstName">First name</label>

                            <div class="input-group">
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-user"></span>
                            </span>
                                <input type="text" class="form-control" id="firstName" placeholder="Enter first name"
                                       name="firstName" value="${contact.firstName}" maxlength="20"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastName">Last name</label>

                            <div class="input-group">
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-user"></span>
                            </span>
                                <input type="text" class="form-control" id="lastName"
                                       placeholder="Enter last name" name="lastName"
                                       value="${contact.lastName}" maxlength="20"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="middleName">Middle name</label>

                            <div class="input-group">
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-user"></span>
                            </span>
                                <input type="text" class="form-control" id="middleName"
                                       placeholder="Enter middle name" name="middleName"
                                       value="${contact.middleName}" maxlength="20"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="dateOfBirth">Date of Birth: </label>

                            <div class="input-group">
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-gift"></span>
                            </span>
                                <input type="text" class="form-control" id="dateOfBirth"
                                       value="${contact.dateOfBirth}" name="dateOfBirth"
                                       placeholder="YYYY-MM-DD" maxlength="10"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="gender">Gender: </label>
                            <select id="gender" name="gender" class="form-control">
                                <c:set var="gender" value="${contact.gender}"/>
                                <c:if test="${gender == 'MALE'}">
                                    <option value="MALE" selected>MALE</option>
                                    <option value="FEMALE">FEMALE</option>
                                </c:if>
                                <c:if test="${gender=='FEMALE'}">
                                    <option value="MALE">MALE</option>
                                    <option value="FEMALE" selected>FEMALE</option>
                                </c:if>
                                <c:if test="${gender == null}">
                                    <option value="MALE">MALE</option>
                                    <option value="FEMALE">FEMALE</option>
                                </c:if>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="nationality">Nationality: </label>

                            <div class="input-group">
                                <span class="input-group-addon"></span>
                                <input type="text" class="form-control" id="nationality"
                                       placeholder="Enter nationality" name="nationality"
                                       value="${contact.nationality}" maxlength="20"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="relationship_status">Relationship Status: </label>
                            <select id="relationship_status" name="relationshipStatus" class="form-control">
                                <c:set var="relationshipStatus" value="${contact.relationshipStatus}"/>
                                <c:if test="${relationshipStatus == 'SINGLE'}">
                                    <option value="SINGLE" selected>SINGLE</option>
                                    <option value="MARRIED">MARRIED</option>
                                </c:if>
                                <c:if test="${relationshipStatus=='MARRIED'}">
                                    <option value="SINGLE">SINGLE</option>
                                    <option value="MARRIED" selected>MARRIED</option>
                                </c:if>
                                <c:if test="${relationshipStatus == null}">
                                    <option value="SINGLE">SINGLE</option>
                                    <option value="MARRIED">MARRIED</option>
                                </c:if>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="web_site">Web Site: </label>

                            <div class="input-group">
                                <span class="input-group-addon"></span>
                                <input type="text" class="form-control" id="web_site"
                                       placeholder="Enter Web Site" name="webSite"
                                       value="${contact.webSite}" maxlength="255"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="email">Email: </label>

                            <div class="input-group">
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-envelope"></span>
                            </span>
                                <input type="text" class="form-control" id="email"
                                       placeholder="Enter email" name="email"
                                       value="${contact.email}" maxlength="100"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="job">Current job: </label>

                            <div class="input-group">
                                <span class="input-group-addon"></span>
                                <input type="text" class="form-control" id="job"
                                       placeholder="Enter current job" name="currentJob"
                                       value="${contact.currentJob}"/>
                            </div>
                        </div>

                        <div class="panel panel-success">
                            <div class="panel-heading">Address</div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label for="country">Country: </label>

                                    <div class="input-group">
                                        <span class="input-group-addon"></span>
                                        <input type="text" class="form-control" id="country"
                                               placeholder="Enter Country" name="location.country"
                                               value="${contact.location.country}" maxlength="255"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="city">City: </label>

                                    <div class="input-group">
                                        <span class="input-group-addon"></span>
                                        <input type="text" class="form-control" id="city"
                                               placeholder="Enter City" name="location.city"
                                               value="${contact.location.city}" maxlength="20"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="ZIP">Zip code: </label>

                                    <div class="input-group">
                                        <span class="input-group-addon"></span>
                                        <input type="text" class="form-control" id="ZIP"
                                               placeholder="Enter ZIP" name="location.zipCode"
                                               value="${contact.location.zipCode}" maxlength="15"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="form-inline">
                                        <div class="col-lg-4 col-md-4 col-sm-4 ">
                                            <label for="street">street:</label>
                                            <input type="text" class="form-control" id="street"
                                                   placeholder="Enter street" name="street"
                                                    <c:if test="${!empty contact}">
                                                        value="${contact.street}"
                                                    </c:if>
                                                   maxlength="30"/>
                                        </div>

                                        <div class="col-lg-4 col-md-4 col-sm-4 ">
                                            <label for="street">house:</label>
                                            <input type="text" class="form-control" id="house"
                                                   placeholder="Enter house" name="house"
                                                    <c:if test="${!empty contact}">
                                                        value="${contact.house}"
                                                    </c:if>
                                                   maxlength="7"/>
                                        </div>

                                        <div class="col-lg-4 col-md-4 col-sm-4 ">
                                            <label for="street">flat:</label>
                                            <input type="text" class="form-control" id="flat"
                                                   placeholder="Enter flat" name="flat"
                                                    <c:if test="${!empty contact}">
                                                        value="${contact.flat}"
                                                    </c:if>
                                                   maxlength="5"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row section" id="phones">
                            <div class="panel panel-success">
                                <div class="panel-heading">Phones</div>
                                <div class="panel-body">
                                    <div id="phone_actions" class="actions">
                                        <div class="bars pull-right" id="delete-bar">
                                            <button id="remove-phone" class="btn action btn-danger hidden action"
                                                    type="button">
                                                <i class="glyphicon glyphicon-trash"></i>
                                                Delete
                                            </button>
                                        </div>

                                        <div class="bars pull-right" id="edit-bar">
                                            <button type="button" id="edit_phone"
                                                    class="btn action btn-warning hidden action">
                                                <i class="glyphicon glyphicon-pencil"></i>
                                                Edit
                                            </button>
                                        </div>

                                        <div class="bars pull-right" id="new-phone-bar">
                                            <button class="btn btn-success" id="new_phone" type="button">
                                                <i class="glyphicon glyphicon-plus"></i>
                                                Add
                                            </button>
                                        </div>
                                    </div>
                                </div>


                                <div class="fixed-table text-center">
                                    <table class="table table-striped text-center" id="selectAllPhone">
                                        <thead>
                                        <tr>
                                            <th></th>
                                            <th>Phone Number</th>
                                            <th>Description</th>
                                            <th>Comment</th>
                                        </tr>
                                        </thead>

                                        <tbody id="phone_tbody">
                                        <c:forEach items="${contact.phones}" var="phone">
                                            <tr data-index="${phone.phoneId}">
                                                <td class="bs-checkbox">
                                                    <label>
                                                        <input data-index="${phone.phoneId}" name="btSelectItem"
                                                               type="checkbox"
                                                               value="${phone.phoneId}"
                                                               onclick="appContainer.checkChoice(this)"/>
                                                    </label>
                                                </td>
                                                <td>
                                                    +${phone.countryCode}(${phone.operatorCode})${phone.phoneNumber}</td>
                                                <td>${phone.phoneType}</td>
                                                <td>${phone.comment}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                        <div class="row section" id="attachments">
                            <div class="panel panel-success">
                                <div class="panel-heading">Attachments</div>
                                <div class="panel-body">
                                    <div id="attachment_actions" class="actions">
                                        <div class="bars pull-right" id="attachments-bar-delete">
                                            <button type="button" id="remove-attach"
                                                    class="btn btn-danger action hidden action">
                                                <i class="glyphicon glyphicon-trash"></i>
                                                Delete
                                            </button>
                                        </div>

                                        <div class="bars pull-right" id="attachments-bar-edit">
                                            <button type="button" id="edit_attach"
                                                    class="btn btn-warning action hidden action">
                                                <i class="glyphicon glyphicon-pencil"></i>
                                                Edit
                                            </button>
                                        </div>

                                        <div class="bars pull-right" id="attachments-bar-add">
                                            <button class="btn btn-success" id="new-attach" type="button">
                                                <i class="glyphicon glyphicon-plus"></i>
                                                Add
                                            </button>
                                        </div>
                                    </div>
                                </div>

                                <div class="fixed-table text-center">
                                    <table class="table table-striped text-center" id="selectAllAttach">
                                        <thead>
                                        <tr>
                                            <th></th>
                                            <th>File name</th>
                                            <th>Date of download</th>
                                            <th>Comment</th>
                                        </tr>
                                        </thead>

                                        <tbody id="attach_tbody">
                                        <c:forEach items="${contact.attachments}" var="attachment">
                                            <tr data-index="${attachment.attachmentId}">
                                                <td class="bs-checkbox">
                                                    <label>
                                                        <input data-index="${attachment.attachmentId}"
                                                               name="btSelectItem"
                                                               type="checkbox"
                                                               value="${attachment.attachmentId}"
                                                               onclick="appContainer.checkChoice(this)"/>
                                                    </label>
                                                </td>
                                                <td><a href="/contacts/${contact.id}/attachment/${attachment.attachmentId}">${attachment.fileName}</a></td>
                                                <td>${attachment.dateOfDownload}</td>
                                                <td>${attachment.comment}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="modal-overlay" id="modal_window"
                             aria-hidden="true" role="dialog"
                             aria-labelledby="modal_title">

                            <div class="modal-content" id="modal_holder" role="dialog">

                                <div class="modal-header" style="background-color: #5cb85c">
                                    <h3 id="label-phones">Adding / Editing Phones</h3>
                                </div>

                                <div class="modal-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label for="country_code">Country code: </label>

                                                <div class="input-group">
                                                <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-pencil"></span>
                                                </span>
                                                    <select class="form-control" name="countryCode" id="country_code">
                                                        <option data-countryCode="BY" value="375">Belarus (+375)
                                                        </option>
                                                        <option data-countryCode="RU" value="7">Russia (+7)</option>
                                                        <option data-countryCode="UA" value="380">Ukraine (+380)
                                                        </option>
                                                        <option data-countryCode="LV" value="371">Latvia (+371)
                                                        </option>
                                                        <option data-countryCode="US" value="1">USA (+1)</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="operator_code">Operator code: </label>

                                                <div class="input-group">
                                                <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-pensil"></span>
                                                </span>
                                                    <input type="text" class="form-control"
                                                           id="operator_code"
                                                           name="operatorCode"
                                                           placeholder="Enter Operator Code">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="phone_number">Phone number:</label>

                                                <div class="input-group">
                                                <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-earphone"></span>
                                                </span>
                                                    <input type="text" class="form-control"
                                                           id="phone_number"
                                                           name="phoneNumber"
                                                           placeholder="Enter Phone Number">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="phone_type">Phone type: </label>

                                                <div class="input-group">
                                                <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-phone-alt"></span>
                                                </span>
                                                    <select class="form-control" id="phone_type" name="phoneType">
                                                        <option value="HOME">HOME</option>
                                                        <option value="MOBILE">MOBILE</option>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label for="comment_phone">Comment: </label>

                                                <div class="input-group">
                                                <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-comment"></span>
                                                </span>
                                                    <input type="text" class="form-control"
                                                           id="comment_phone" name="comment"
                                                           placeholder="Enter comment" maxlength="30">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <button class="btn-close" id="modal_close" type="button"
                                        aria-label="close">&times;</button>
                                <button type="button" class="btn bg-primary" id="save_phone">Save</button>
                            </div>
                        </div>
                        <div class="modal-overlay" id="modal_window1" aria-hidden="true" role="dialog"
                             aria-labelledby="modal_title">

                            <div class="modal-content" id="modal_holder1" role="dialog">
                                <div class="modal-header" style="background-color: #5cb85c">
                                    <h3 id="label-attach">Adding / Editing Attachments</h3>
                                </div>

                                <div class="modal-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label for="file_name">File name: </label>

                                                <div class="input-group" id="upload_file">
                                                <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-file"></span>
                                                </span>

                                                    <div>
                                                        <input type="file" class="form-control"
                                                               id="file_name" name="fileName"
                                                               placeholder="Enter file name" maxlength="20">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label for="comment_attach">Comment: </label>

                                                <div class="input-group">
                                                <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-comment"></span>
                                                </span>
                                                    <input type="text" class="form-control"
                                                           id="comment_attach" name="comment"
                                                           placeholder="Enter comment" maxlength="30">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <button class="btn-close" id="modal_close1" type="button" aria-label="close">
                                    &times;
                                </button>
                                <button type="button" class="btn bg-primary" id="save_attach">Save</button>
                            </div>
                        </div>
                        <div class="modal-overlay" id="editModalWindow" aria-hidden="true" role="dialog"
                             aria-labelledby="modal_title">

                            <div class="modal-content" id="editModalHolder" role="dialog">
                                <div class="modal-header" style="background-color: #5cb85c">
                                    <h3 id="editingAttach">Editing Attachments</h3>
                                </div>

                                <div class="modal-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label for="file_name">File name: </label>

                                                <div class="input-group">
                                                <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-file"></span>
                                                </span>

                                                    <div>
                                                        <input type="text" class="form-control"
                                                               id="fileName" name="fileName"
                                                               placeholder="Enter file name" maxlength="20">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label for="comment_attach">Comment: </label>

                                                <div class="input-group">
                                                <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-comment"></span>
                                                </span>
                                                    <input type="text" class="form-control"
                                                           id="attachComment" name="comment"
                                                           placeholder="Enter comment" maxlength="30">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <button class="btn-close" id="btnCloseEdit" type="button" aria-label="close">
                                    &times;
                                </button>
                                <button type="button" class="btn bg-primary" id="btnSaveEdit">Save</button>
                            </div>
                        </div>
                        <div class=" row button-bar text-center">
                            <button type="button" class="btn btn-primary btn-lg" id="send_form">Save</button>
                        </div>

                        <div class="error">
                            <c:forEach var="error" items="${errors}">
                                <div class="alert alert-danger" role="alert">
                                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                    <span class="sr-only">Error:</span>
                                    ${error}
                                </div>
                            </c:forEach>
                        </div>
                    </div>

                </div>

            </form>
        </div>
    </section>

</div>
<footer>

</footer>

<script src="<c:url value="${pageContext.request.contextPath}/js/modalDialog.js"/>"
        type="application/javascript"></script>
<script src="<c:url value="${pageContext.request.contextPath}/js/create-editContact.js"/>"
        type="application/javascript"></script>
</body>

</html>