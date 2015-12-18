window.onload = function() {
    app.searchContacts = document.getElementById('search_contacts');
    app.searchContacts.addEventListener('click', app.filterContacts);
};
var app = {};

app.filterContacts = function () {
    var firstName = document.getElementById('first_name');
    var lastName = document.getElementById('last_name');
    var middleName = document.getElementById('middle_name');
    var dateFrom = document.getElementById('from_birthday');
    var dateTo = document.getElementById('to_birthday');
    var gender = document.getElementsByName('gender');
    var nationality = document.getElementById('nationality');
    var relationshipStatus = document.getElementById('relationship_status');
    var country = document.getElementById('country');
    var city = document.getElementById('city');
    var href = "?";
    href = href + app.buildHref(firstName, 'firstName=');
    href = href + app.buildHref(lastName, '&lastName=');
    href = href + app.buildHref(middleName, '&middleName=');
    href = href + app.buildHref(dateFrom, '&dateFrom=');
    href = href + app.buildHref(dateTo, '&dateTo=');
    href = href + app.buildHref(app.getGender(gender), '&gender=');
    href = href + app.buildHref(nationality, '&nationality=', href);
    href = href + app.getRelationshipStatus(relationshipStatus, '&relationshipStatus=');
    href = href + app.buildHref(country, '&country=');
    href = href + app.buildHref(city, '&city=');

    if (href !== "?") {
        var location = window.location.href;
        location = location.substring(0, location.length - 1);
        window.location.href = location + href;
    } else {
        console.log("здрасте");
    }
};

app.buildHref = function(element, name) {
    if (element !== null && element !== undefined && element.value !== null && element.value !== "") {
        return name + encodeURIComponent(element.value);
    }
    return "";
};

app.getRelationshipStatus = function(relationship_status, name) {
    if (relationship_status !== null && relationship_status !== undefined &&
        relationship_status.value !== null && relationship_status.value !== "" && relationship_status.value !== '0') {
        return name + encodeURIComponent(relationship_status.value);
    }
    return "";
};

app.getGender = function(gender) {
    for (var i = 0; i < gender.length; i++) {
        if (gender[i].type == "radio" && gender[i].checked) {
            return gender[i];
        }
    }
};