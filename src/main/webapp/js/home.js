'use strict';
window.onload = function() {
    app.deleteContacts_btn = document.getElementById('remove');
    if (app.deleteContacts_btn !== null) {
        app.deleteContacts_btn.addEventListener('click', app.deleteContacts);
    }
    app.sendMessage_btn = document.getElementById('compose');
    app.sendMessage_btn.addEventListener('click', app.sendEmail);
};

var app = {};
app.checkChoice = function (form) {
    //noinspection JSUnresolvedFunction
    var tableId = form.closest('table').id;
    var inputChecked = document.getElementById(tableId).querySelectorAll('input[type=checkbox]:checked');
    var buttons = document.querySelectorAll('button' + '.action');
    if (inputChecked.length === 0) {
        buttons[0].classList.add('hidden');
        buttons[1].classList.add('hidden');
    } else {
        buttons[0].classList.remove('hidden');
        buttons[1].classList.remove('hidden');
    }
};

app.deleteContacts = function () {
    var form = document.getElementById('form1');
    form.setAttribute("action", "/contacts/delete");
    form.setAttribute("method", "POST");
    form.submit();
};

app.sendEmail = function () {
    var form = document.getElementById('form1');
    form.setAttribute("action", "/contacts/compose");
    form.setAttribute("method", "POST");
    form.submit();
};
