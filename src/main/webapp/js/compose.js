window.onload = function () {
    app.templates = document.getElementById("template");
    app.templates.addEventListener("change", app.chooseTemplate);
    app.send_btn = document.getElementById('btn-send');
    app.send_btn.addEventListener("click", app.sendMessage);
};
var app = {};
app.emails = [];
app.message = function (emails, theme, messageBody) {
    return {
        emails: emails,
        theme: theme,
        messageBody: messageBody
    }
};

(app.chooseTemplate = function () {
    var message = document.getElementById("message");
    var index = document.getElementById("template").selectedIndex;
    var values = document.getElementsByName("templates");
    if (values !== null) {
        message.value = values[index].value;
    }
})();

app.createInput = function (form, message) {
    var div = document.createElement('div');
    div.innerHTML = '<input type="text" value="" name="message" size="200"/>';
    var a = div.firstChild;
    a.setAttribute("value", message);
    div.classList.add('hidden');
    form.appendChild(div);
};

app.convertToJson = function (message) {
    return JSON.stringify(message);
};

app.sendMessage = function () {
    var form = document.querySelector('form');
    var emails = document.getElementById('emails').value;
    app.emails = emails.split(';');
    if (app.emails[app.emails.length - 1] === " ") {
        app.emails.splice(app.emails.length - 1, 1);
    }
    var theme = document.getElementById('theme').value;
    var messageBody = document.getElementById('message').value;
    var message = app.message(app.emails, theme, messageBody);
    var jsonMessage = app.convertToJson(message);
    app.createInput(form, jsonMessage);
    form.submit();
};
