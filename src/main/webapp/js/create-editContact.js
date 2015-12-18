window.onload = function () {
    appContainer.getData();
    var save_phone_btn = document.getElementById('save_phone');
    save_phone_btn.addEventListener('click', appContainer.savePhone);
    var remove_phone_btn = document.getElementById('remove-phone');
    remove_phone_btn.addEventListener('click', appContainer.removePhone);
    var send_form_btn = document.getElementById('send_form');
    send_form_btn.addEventListener('click', appContainer.sendForm);
    var save_attach_btn = document.getElementById('save_attach');
    save_attach_btn.addEventListener('click', appContainer.saveAttachment);
    var saveEditedAttach = document.getElementById('btnSaveEdit');
    saveEditedAttach.addEventListener('click', appContainer.saveEditedAttachment);
    var remove_attach_btn = document.getElementById('remove-attach');
    remove_attach_btn.addEventListener('click', appContainer.removeAttachment);
    var edit_phone_btn = document.getElementById('edit_phone');
    edit_phone_btn.addEventListener('click', appContainer.editPhone);
    var edit_attach_btn = document.getElementById('edit_attach');
    edit_attach_btn.addEventListener('click', appContainer.editAttachment);
    document.getElementById('photoFile').addEventListener('change', appContainer.handleFileSelect, false);
};
var appContainer = {};

appContainer.checkChoice = function (form) {
    //noinspection JSUnresolvedFunction
    var tableId = form.closest('table').id;
    var inputChecked = document.getElementById(tableId).querySelectorAll('input[type=checkbox]:checked');
    //noinspection JSUnresolvedFunction
    var buttons = form.closest('.section').querySelectorAll('button.action');
    if (inputChecked.length == 0) {
        buttons[0].classList.add('hidden');
        buttons[1].classList.add('hidden');
    } else if (inputChecked.length == 1) {
        buttons[0].classList.remove('hidden');
        buttons[1].classList.remove('hidden');
    }
    else {
        buttons[0].classList.remove('hidden');
        buttons[1].classList.add('hidden');
    }
};


appContainer.contact = function (id, firstName, lastName, middleName, birthday, gender, nationality,
                                 relationship_status, web_site, email, current_job, location, street, house,
                                 flat, attachments, phones) {
    return {
        id: id,
        firstName: firstName,
        lastName: lastName,
        middleName: middleName,
        dateOfBirth: birthday,
        gender: gender,
        nationality: nationality,
        relationshipStatus: relationship_status,
        webSite: web_site,
        email: email,
        currentJob: current_job,
        street: street,
        house: house,
        flat: flat,
        phones: phones,
        attachments: attachments,
        location: location
    }
};


appContainer.editPhone = function () {
    var countryCode = document.getElementById('country_code');
    var operatorCode = document.getElementById('operator_code');
    var phoneNumber = document.getElementById('phone_number');
    var typePhone = document.getElementById('phone_type');
    var commentPhone = document.getElementById('comment_phone');
    var editInput = document.getElementById('phone_tbody').querySelector('input[type=checkbox]:checked');
    var id = editInput.dataset.index;
    appContainer.phones.forEach(function (item, i) {
        if (parseInt(item.phoneId) === parseInt(id)) {
            countryCode.value = appContainer.phones[i].countryCode;
            operatorCode.value = appContainer.phones[i].operatorCode;
            phoneNumber.value = appContainer.phones[i].phoneNumber;
            typePhone.value = appContainer.phones[i].phoneType;
            commentPhone.value = appContainer.phones[i].comment;
            if (appContainer.phones[i].hasInDB == true) {
                appContainer.phones[i].edited = true;
            }
        }
    });
};

appContainer.theLocation = function (country, city, zipCode) {
    return {
        country: country,
        city: city,
        zipCode: zipCode
    }
};


(function modalPhoneEdit() {
    var modalWindow = document.getElementById('modal_window');
    var page = document.getElementById('phone_actions');
    swap(modalWindow, page);
    modalDialog(getId('modal_window'), getId('edit_phone'), getId('modal_close'),
        getId('modal_holder'));
})();


appContainer.editAttachment = function () {
    var editInput = document.getElementById('attach_tbody').querySelector('input[type=checkbox]:checked');
    var fileName = document.getElementById('fileName');
    var attachmentComment = document.getElementById('attachComment');
    //noinspection JSUnresolvedFunction
    var id = editInput.dataset.index;
    for (var i = 0; i < appContainer.attachments.length; ++i) {
        if (appContainer.attachments[i].attachmentId === parseInt(id)) {
            fileName.value = appContainer.attachments[i].fileName;
            attachmentComment.value = appContainer.attachments[i].comment;
            if (appContainer.attachments[i].hasInDB == true) {
                appContainer.attachments[i].edited = true;
            }
            break;
        }
    }
};

appContainer.saveEditedAttachment = function () {
    var fileName = document.getElementById('fileName');
    var attachmentComment = document.getElementById('attachComment');
    var modalWindow = document.getElementById('editModalWindow');
    var modalHolder = document.getElementById('editModalHolder');
    var editInput = document.getElementById('attach_tbody').querySelector('input[type=checkbox]:checked');
    var data_index = editInput.dataset.index;
    appContainer.editAttachItem(data_index, fileName, attachmentComment);
    for (var i = 0; i < appContainer.attachments.length; ++i) {
        if (parseInt(appContainer.attachments[i].attachmentId) === parseInt(data_index)) {
            appContainer.attachments[i].fileName = fileName.value;
            appContainer.attachments[i].comment = attachmentComment.value;
            break;
        }
    }
    appContainer.closeAttachmentModalDialog(fileName, attachmentComment, modalWindow, modalHolder);
};

appContainer.handleFileSelect = function (evt) {
    var file = evt.target;
    var reader = new FileReader();

    reader.onload = function () {
        var img = document.getElementById('contact_photo');
        img.src = reader.result;
        img.title = reader.name;
    };
    reader.readAsDataURL(file.files[0]);
};

appContainer.phones = [];
appContainer.attachments = [];

appContainer.thePhone = function (id, country_code, operator_code, phone_number, phone_type, comment,
                                  isDeleted, isEdited, hasInDB) {
    return {
        phoneId: parseInt(id),
        countryCode: parseInt(country_code),
        operatorCode: parseInt(operator_code),
        phoneNumber: parseInt(phone_number),
        phoneType: phone_type,
        comment: comment,
        deleted: isDeleted,
        edited: isEdited,
        hasInDB: hasInDB
    };
};

appContainer.theAttachment = function (id, file_name, comment, isDeleted, isEdited, hasInDB) {
    return {
        attachmentId: parseInt(id),
        fileName: file_name,
        comment: comment,
        deleted: isDeleted,
        edited: isEdited,
        hasInDB: hasInDB
    };
};


appContainer.createHiddenInput = function (contact) {
    var form = document.getElementById('contact_form');
    var div = document.createElement('div');
    div.innerHTML = '<input type="text" value="" name="contact" size="200"/>';
    var a = div.firstChild;
    a.setAttribute("value", contact);
    div.classList.add('hidden');
    form.appendChild(div);
};

appContainer.sendForm = function () {
    var contactId = document.getElementById('contact_id').dataset.index;
    var firstName = document.getElementById('firstName').value;
    var secondName = document.getElementById('lastName').value;
    var middleName = document.getElementById('middleName').value;
    var dateOfBirth = document.getElementById('dateOfBirth').value;
    var gender = document.getElementById('gender').value;
    var nationality = document.getElementById('nationality').value;
    var relationshipStatus = document.getElementById('relationship_status').value;
    var webSite = document.getElementById('web_site').value;
    var email = document.getElementById('email').value;
    var job = document.getElementById('job').value;
    var country = document.getElementById('country').value;
    var city = document.getElementById('city').value;
    var street = document.getElementById('street').value;
    var house = document.getElementById('house').value;
    var flat = document.getElementById('flat').value;
    var zipCode = document.getElementById('ZIP').value;

    var form = document.getElementById('contact_form');
    if (appContainer.isValidateForm()) {
        var readyContact = appContainer.contact(contactId, firstName, secondName, middleName, dateOfBirth,
            gender, nationality, relationshipStatus, webSite, email, job,
            appContainer.theLocation(country, city, zipCode), street, house, flat,
            appContainer.attachments, appContainer.phones);
        var jsonContact = appContainer.toJson(readyContact);

        appContainer.createHiddenInput(jsonContact);
        if (contactId !== null && contactId !== "") {
            form.action = '/contacts/' + parseInt(contactId) + '/edit';
        } else {
            form.action = '/contacts/create';
        }
        form.submit();
    }
};

appContainer.toJson = function (object) {
    return JSON.stringify(object);
};

appContainer.getData = function () {
    var phonesData = document.getElementById('phone_tbody').getElementsByTagName('tr');
    var attachmentsData = document.getElementById('attach_tbody').getElementsByTagName('tr');
    appContainer.setPhones(phonesData);
    appContainer.setAttachments(attachmentsData);
};


appContainer.parsedPhone = function (elem) {
    var phoneTdBlocks = elem.getElementsByTagName('td');
    var id = phoneTdBlocks[0].children[0].children[0].dataset.index;
    var number = phoneTdBlocks[1].textContent;
    var operator_code, country_code, phone_number;
    var match = /(\d+)\((\d+)\)(\d+)/;
    var phone = match.exec(number);
    country_code = phone[1];
    operator_code = phone[2];
    phone_number = phone[3];
    var phone_type = phoneTdBlocks[2].textContent;
    var phone_comment = phoneTdBlocks[3].textContent;
    return {
        id: id,
        operator_code: operator_code,
        country_code: country_code,
        phone_number: phone_number,
        phone_type: phone_type,
        phone_comment: phone_comment
    };
};

appContainer.setPhones = function (phonesData) {
    for (var i = 0; i < phonesData.length; ++i) {
        var phone = appContainer.parsedPhone(phonesData[i]);
        var id = phone.id;
        var operator_code = phone.operator_code;
        var country_code = phone.country_code;
        var phone_number = phone.phone_number;
        var phone_type = phone.phone_type;
        var phone_comment = phone.phone_comment;
        var newPhone = appContainer.thePhone(id, country_code, operator_code, phone_number, phone_type,
            phone_comment, false, false, true);
        appContainer.phones.push(newPhone);
    }
};

appContainer.parsedAttachment = function (attachmentsData, i) {
    var attachTdBlocks = attachmentsData[i].getElementsByTagName('td');
    var id = attachTdBlocks[0].children[0].children[0].dataset.index;
    var file_name = attachTdBlocks[1].textContent;
    var date = attachTdBlocks[2].textContent;
    var attach_comment = attachTdBlocks[3].textContent;
    return {id: id, file_name: file_name, date: date, attach_comment: attach_comment};
};

appContainer.setAttachments = function (attachmentsData) {
    for (var i = 0; i < attachmentsData.length; ++i) {
        var attachment = appContainer.parsedAttachment(attachmentsData, i);
        var id = attachment.id;
        var file_name = attachment.file_name;
        var date = attachment.date;


        var attach_comment = attachment.attach_comment;
        var newAttachment = appContainer.theAttachment(id, file_name, attach_comment, false, false, true);
        appContainer.attachments.push(newAttachment);
    }
};

appContainer.removePhone = function () {
    var phone_body = document.getElementById('phone_tbody');
    appContainer.phones = appContainer.remove(phone_body, appContainer.phones, 'phoneId');
};

appContainer.removeAttachment = function () {
    var attach_body = document.getElementById('attach_tbody');
    appContainer.attachments = appContainer.remove(attach_body, appContainer.attachments, 'attachmentId')
};

appContainer.remove = function (body, array, id) {
    var tr = body.getElementsByTagName('tr');
    var checkedInputs = body.querySelectorAll('input[type=checkbox]:checked');
    var ids = [];
    for (var i = 0; i < checkedInputs.length; ++i) {
        ids.push(checkedInputs[i].dataset.index);
        checkedInputs[i].checked = false;
        appContainer.checkChoice(checkedInputs[i]);
    }
    var newArray = [];
    for (i = 0; i < ids.length; ++i) {
        var trDelete = body.querySelector('tr[data-index="' + ids[i] + '"]');
        body.removeChild(trDelete);
    }
    for (i = 0; i < array.length; ++i) {
        var flag = false;
        for (var j = 0; j < ids.length; ++j) {
            if (array[i][id] === parseInt(ids[j])) {
                flag = true;
                if (array[i].hasInDB) {
                    array[i].deleted = true;
                    newArray.push(array[i]);
                }
                break;
            }
        }
        if (!flag) {
            newArray.push(array[i]);
        }
    }
    return newArray;
};

appContainer.isValidateForm = function () {
    var firstName = document.getElementById('firstName');
    var lastName = document.getElementById('lastName');
    var middleName = document.getElementById('middleName');
    var dateOfBirth = document.getElementById('dateOfBirth');
    var gender = document.getElementById('gender');
    var citizenship = document.getElementById('nationality');
    var relationshipStatus = document.getElementById('relationship_status');
    var webSite = document.getElementById('web_site');
    var email = document.getElementById('email');
    var currentJob = document.getElementById('job');
    var country = document.getElementById('country');
    var city = document.getElementById('city');
    var zipCode = document.getElementById('ZIP');
    var street = document.getElementById('street');
    var house = document.getElementById('house');
    var flat = document.getElementById('flat');

    var array = [
        {"field": firstName, "message": '*Enter your First Name'},
        {"field": lastName, "message": '*Enter your Last Name'},
        {"field": middleName, "message": '*Enter your Middle Name'},
        {"field": dateOfBirth, "message": '*Invalid date'},
        {"field": gender, "message": '*Enter your gender'},
        {"field": citizenship, "message": '*Enter your citizenship'},
        {"field": relationshipStatus, "message": '*Enter your relationship status'},
        {"field": webSite, "message": '*Enter your web site'},
        {"field": email, "message": 'Invalid email. Example: aaaa@mail.ru'},
        {"field": currentJob, "message": '*Enter your currentJob'},
        {"field": country, "message": '*Enter your country'},
        {"field": city, "message": '*Enter your city'},
        {"field": zipCode, "message": '*Enter your zip-code'},
        {"field": street, "message": '*Enter your street'},
        {"field": house, "message": '*Enter your house'},
        {"field": flat, "message": '*Enter your flat'}
    ];

    var valid = true;
    for (var i = array.length - 1; i >= 0; --i) {
        if (array[i].field === email) {
            if (!appContainer.isValidEmail(email)) {
                appContainer.showError(array[i].field, array[i].message);
                valid = false;
                array[i].field.focus();
            }
        }
        if (array[i].field === dateOfBirth) {
            if (!appContainer.isValidDate(array[i].field)) {
                appContainer.showError(array[i].field, array[i].message);
                valid = false;
                array[i].field.focus();
            }
        } else {
            if (!appContainer.isValidate(array[i].field)) {
                appContainer.showError(array[i].field, array[i].message);
                valid = false;
                array[i].field.focus();
            }
        }
    }

    return valid;
};

appContainer.isValidEmail = function (email) {
    var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
    var address = email.value;
    return reg.test(address) != false;
};

appContainer.isValidDate = function (date) {
    var nowDate = new Date();
    var matches = /^(\d{4})[-](\d{2})[-](\d{2})$/.exec(date.value);
    if (matches == null) return false;
    var y = matches[1];
    var m = matches[2] - 1;
    var d = matches[3];

    var composedDate = new Date(y, m, d);
    var differenceYear = nowDate.getFullYear() - composedDate.getFullYear();
    if (composedDate > nowDate || differenceYear > 100) {
        return false;
    }
    return composedDate.getDate() == d &&
        composedDate.getMonth() == m &&
        composedDate.getFullYear() == y;
};

appContainer.isValidate = function (obj) {
    if (!obj) {
        return;
    }
    return obj.value;
};

appContainer.hideModalDialog = function (modalWindow, modalHolder) {
    modalWindow.setAttribute('aria-hidden', 'true');
    modalHolder.setAttribute('tabindex', '-1');
};

appContainer.closePhoneModalDialog = function (operatorCode, phoneNumber, phoneComment, modalWindow, modalHolder) {
    appContainer.hideModalDialog(modalWindow, modalHolder);
    operatorCode.value = '';
    phoneNumber.value = '';
    phoneComment.value = '';
};

appContainer.savePhone = function () {
    var countryCode = document.getElementById('country_code');
    var operatorCode = document.getElementById('operator_code');
    var phoneNumber = document.getElementById('phone_number');
    var phoneType = document.getElementById('phone_type');
    var phoneComment = document.getElementById('comment_phone');
    var modalWindow = document.getElementById('modal_window');
    var modalHolder = document.getElementById('modal_holder');
    var newId = 1;
    var error;
    var phone_tbody = document.getElementById('phone_tbody');
    var editInput = document.getElementById('phone_tbody').querySelector('input[type=checkbox]:checked');

    function isNotEqualsPhones(obj) {
        return (obj.country_code !== countryCode.value || obj.operator_code !== operatorCode.value ||
            obj.phone_number !== phoneNumber.value) || (obj.deleted !== false);
    }

    if (appContainer.isValidPhone(countryCode, operatorCode, phoneNumber, phoneType, phoneComment)) {
        if (editInput !== null) {
            var data_index = editInput.dataset.index;
            var tempArray = appContainer.phones.filter(function (element) {
                return element.phoneId !== data_index;
            });
            var uniquePhone = tempArray.every(isNotEqualsPhones);
            if (uniquePhone) {
                data_index = parseInt(data_index);
                appContainer.editPhoneItem(data_index, countryCode, operatorCode, phoneNumber, phoneType, phoneComment);
                appContainer.phones.forEach(function (item, index) {
                    if (parseInt(item.phoneId) === data_index) {
                        appContainer.phones[index].countryCode = countryCode.value;
                        appContainer.phones[index].operatorCode = operatorCode.value;
                        appContainer.phones[index].phoneNumber = phoneNumber.value;
                        appContainer.phones[index].phoneType = phoneType.value;
                        appContainer.phones[index].comment = phoneComment.value;
                    }
                });
                appContainer.closePhoneModalDialog(operatorCode, phoneNumber, phoneComment, modalWindow, modalHolder);
            } else {
                error = document.getElementById('comment_phone');
                appContainer.showErrorForEqualsPhones(error, 'This phone has been');
            }
        } else {
            uniquePhone = appContainer.phones.every(isNotEqualsPhones);
            if (uniquePhone) {
                var tr = phone_tbody.getElementsByTagName('tr');
                if (tr.length > 0) {
                    newId = appContainer.createPhoneNewItem(tr, newId, countryCode, operatorCode,
                        phoneNumber, phoneType, phoneComment, phone_tbody);
                } else {
                    appContainer.firstItemPhone(countryCode, operatorCode, phoneNumber, phoneType, phoneComment, phone_tbody);
                }
                appContainer.phones.push(appContainer.thePhone(newId, countryCode.value,
                    operatorCode.value, phoneNumber.value,
                    phoneType.value, phoneComment.value, false, false, false));
                appContainer.closePhoneModalDialog(operatorCode, phoneNumber, phoneComment, modalWindow, modalHolder);
            } else {
                error = document.getElementById('comment_phone');
                appContainer.showErrorForEqualsPhones(error, 'This phone has been');
            }
        }
    }
};

appContainer.editPhoneItem = function (index, country_code, operator_code, phone_number, phone_type, phone_comment) {
    var phoneTable = document.getElementById('phone_tbody');
    var trItem = phoneTable.querySelector('tr[data-index=' + '"' + index + '"' + ']');
    trItem.children[0].children[0].children[0].checked = false;
    appContainer.checkChoice(trItem.children[0].children[0].children[0]);
    trItem.children[1].innerText = '+' + country_code.value + '(' + operator_code.value + ')' + phone_number.value;
    trItem.children[2].innerText = phone_type.value;
    trItem.children[3].innerText = phone_comment.value;
};

appContainer.closeAttachmentModalDialog = function (file_name, comment, modalWindow, modalHolder) {
    appContainer.hideModalDialog(modalWindow, modalHolder);
    comment.value = '';
};

appContainer.saveAttachment = function () {
    var date;
    var file_name = document.getElementById('file_name');
    var comment = document.getElementById('comment_attach');
    var body = document.getElementById('attach_tbody');
    var modalWindow = document.getElementById('modal_window1');
    var modalHolder = document.getElementById('modal_holder1');
    if (appContainer.isValidateAttachment(file_name, comment)) {
        var newId = 1;
        var trArray = body.getElementsByTagName('tr');
        if (trArray.length > 0) {
            var param = appContainer.createNewItemAttach(trArray, newId, file_name, comment, body);
            date = param.date;
            newId = param.newId;
        } else {
            date = appContainer.firstItemAttach(file_name, comment, body);
        }
        appContainer.attachments.push(appContainer.theAttachment(newId, file_name.files[0].name, comment.value,
            false, false, false));

        appContainer.closeAttachmentModalDialog(file_name, comment, modalWindow, modalHolder);
    }
};

appContainer.editAttachItem = function (index, file_name, comment) {
    var attachTable = document.getElementById('attach_tbody');
    var trItem = attachTable.querySelector('tr[data-index=' + '"' + index + '"' + ']');
    trItem.children[0].children[0].children[0].checked = false;
    appContainer.checkChoice(trItem.children[0].children[0].children[0]);
    trItem.children[1].innerText = file_name.value;
    trItem.children[3].innerText = comment.value;
};

appContainer.isValidCountryCode = function (operator_codes) {
    var country_code = document.getElementById('country_code').value;
    var oper_code = document.getElementById('operator_code').value;
    for (var i = 0; i < operator_codes.length; ++i) {
        if (country_code == operator_codes[i].country_code) {
            var code = appContainer.find(operator_codes[i].operator_code, oper_code);
            return code !== -1;
        }
    }
    return false;
};

appContainer.isValidatePhoneNumber = function (field) {
    if (field.value.length !== 7) {
        return false;
    }
    for (var i = 1; i <= 7; ++i) {
        if (field.value[i] < '0' || field.value[i] > '9') {
            return false;
        }
    }
    return true;
};

appContainer.isValidateAttachment = function (file_name, comment) {
    var flag = true;
    var allowedFiles = [".", ".docx", ".pdf"];
    var attachField = [
        {
            "field": file_name,
            "message": "Please upload files having extensions:" + allowedFiles.join(', ') + " only."
        },
        {"field": comment, "message": "Field can not be empty"}
    ];

    for (var i = attachField.length - 1; i >= 0; --i) {
        if (!appContainer.isValidate(attachField[i].field)) {
            appContainer.showError(attachField[i].field, "Field can not be empty");
            flag = false;
        } else if (attachField[i].field.id === file_name.id) {
            if (!appContainer.isValidateFile(attachField[i].field, allowedFiles)) {
                appContainer.showError(attachField[i].field, attachField[i].message);
                flag = false;
            }
        }

    }
    return flag;
};

appContainer.isValidateFile = function (fileUpload, allowedFiles) {
    var regex = new RegExp("([a-zA-Z0-9\s_\\.\-:])+(" + allowedFiles.join('|') + ")$");
    return regex.test(fileUpload.value.toLowerCase());

};

appContainer.isValidPhone = function (countryCode, operatorCode, phoneNumber, typePhone, commentPhone) {
    var flag = true;
    var phoneFields = [
        {"field": countryCode, "message": "Invalid country code. Example valid country code: 375"},
        {"field": operatorCode, "message": "Invalid operator code"},
        {"field": phoneNumber, "message": "Invalid phone number. Example valid phone number: 1234567"},
        {"field": typePhone, "message": "Invalid type phone"},
        {"field": commentPhone, "message": "Field can not be empty"}
    ];

    var operatorCodes = [
        {
            "country_code": 375,
            "operator_code": [29, 33, 44, 25]
        },
        {
            "country_code": 7,
            "operator_code": [901, 902, 903, 904, 905, 906, 911, 912, 913]
        },
        {
            "country_code": 380,
            "operator_code": [39, 50, 63, 66, 67, 68, 91, 92, 93, 94, 95, 96, 97, 98]
        },
        {
            "country_code": 371,
            "operator_code": [30, 38, 39, 40]
        },
        {
            "country_code": 1,
            "operator_code": [800, 844, 855, 866, 877, 888]
        }
    ];

    for (var i = phoneFields.length - 1; i >= 0; --i) {
        if (!appContainer.isValidate(phoneFields[i].field)) {
            appContainer.showError(phoneFields[i].field, "Field can not be empty");
            flag = false;
        } else if (phoneFields[i].field.name === "operatorCode") {
            if (!appContainer.isValidCountryCode(operatorCodes)) {
                appContainer.showError(phoneFields[i].field, phoneFields[i].message);
                flag = false;
            }
        } else if (phoneFields[i].field.name === "phoneNumber") {
            if (!appContainer.isValidatePhoneNumber(phoneFields[i].field)) {
                appContainer.showError(phoneFields[i].field, phoneFields[i].message);
                flag = false;
            }
        }
    }
    return flag;
};

appContainer.showError = function (obj, message) {
    if (!obj.errorNode) {
        obj.onchange = appContainer.hideError;
        appContainer.createErrorItem(obj, message);
    }
};

appContainer.showErrorForEqualsPhones = function (obj, message) {
    if (!obj.errorNode) {
        document.getElementById('country_code').onchange = appContainer.hideErrorForEqualsPhones;
        document.getElementById('operator_code').onchange = appContainer.hideErrorForEqualsPhones;
        document.getElementById('phone_number').onchange = appContainer.hideErrorForEqualsPhones;
        appContainer.createErrorItem(obj, message);
    }
};

appContainer.hideErrorForEqualsPhones = function () {
    //noinspection JSUnresolvedFunction
    var childLast = document.getElementById('comment_phone').closest('.form-group').children[2];
    //noinspection JSUnresolvedFunction
    document.getElementById('comment_phone').closest('.form-group').removeChild(childLast);
    document.getElementById('comment_phone').errorNode = null;
    document.getElementById('country_code').onchange = null;
    document.getElementById('operator_code').onchange = null;
    document.getElementById('phone_number').onchange = null;
};

appContainer.hideError = function () {
    //noinspection JSUnresolvedFunction
    this.closest('.form-group').removeChild(this.errorNode);
    this.errorNode = null;
    this.onchange = null;
};

appContainer.createHiddenFileInput = function (file_name, newId) {
    file_name.name = 'attachment[' + newId + ']';
    file_name.id = 'attachment[' + newId + ']';
    //noinspection JSUnresolvedFunction
    var fileNameInputTag = file_name.closest('div');
    fileNameInputTag.className = 'hidden';
    return fileNameInputTag;
};

appContainer.createNewItemAttach = function (trArray, newId, file_name, comment, tbody) {
    var lastTrAttachmentItem = trArray.item(trArray.length - 1);
    newId = parseInt(lastTrAttachmentItem.dataset.index);
    newId++;
    var newTrAttachmentItem = lastTrAttachmentItem.cloneNode(true);
    newTrAttachmentItem.dataset.index = newId;
    var children = newTrAttachmentItem.children;
    children[0].children[0].children[0].dataset.index = newId;
    children[0].children[0].children[0].value = newId;
    children[0].children[0].children[0].addEventListener('change', function () {
        appContainer.checkChoice(children[0].children[0].children[0]);
    });
    children[1].innerText = file_name.files[0].name;
    var date = new Date();
    children[2].innerHTML = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
    children[3].innerText = comment.value;
    tbody.appendChild(newTrAttachmentItem);
    var fileNameInputTag = appContainer.createHiddenFileInput(file_name, newId);
    tbody.appendChild(fileNameInputTag);
    return {newId: newId, date: date}
};

appContainer.createPhoneNewItem = function (tr, newId, countryCode, operatorCode, phoneNumber, typePhone,
                                            commentPhone, tbody) {
    var lastTrPhoneItem = tr.item(tr.length - 1);
    newId = parseInt(lastTrPhoneItem.dataset.index);
    newId++;
    var newTrPhoneItem = lastTrPhoneItem.cloneNode(true);
    newTrPhoneItem.dataset.index = newId;
    //newTrPhoneItem.classList.remove('hidden');
    var children = newTrPhoneItem.children;
    children[0].children[0].children[0].dataset.index = newId;
    children[0].children[0].children[0].value = newId;
    children[0].children[0].children[0].addEventListener('change', function () {
        appContainer.checkChoice(children[0].children[0].children[0]);
    });
    children[1].innerText = '+' + countryCode.value + '(' + operatorCode.value + ')' + phoneNumber.value;
    children[2].innerText = typePhone.value;
    children[3].innerText = commentPhone.value;
    tbody.appendChild(newTrPhoneItem);
    return newId;
};

appContainer.createErrorItem = function (obj, message) {
    var span = document.createElement('span');
    var classAttribute = document.createAttribute('class');
    classAttribute.value = 'error';
    span.setAttributeNode(classAttribute);
    span.appendChild(document.createTextNode(message));
    if (obj.name === "street" || obj.name === "house" || obj.name === "flat") {
        obj.parentNode.appendChild(span);
    } else {
        //noinspection JSUnresolvedFunction
        obj.closest('.form-group').appendChild(span);
    }
    obj.errorNode = span;
};

appContainer.firstItemAttach = function (file_name, comment, body) {
    var tr = document.createElement('tr');
    var data_index = document.createAttribute('data-index');
    //noinspection JSValidateTypes
    data_index.value = 1;
    tr.setAttributeNode(data_index);
    var td1 = document.createElement('td');
    td1.className = 'bs-checkbox';
    var input = document.createElement('input');
    input.dataset.index = 1;
    input.name = 'btSelectItem';
    input.type = 'checkbox';
    input.value = 1;
    var label = document.createElement('label');
    label.appendChild(input);
    td1.appendChild(label);
    tr.appendChild(td1);
    var td2 = document.createElement('td');
    td2.innerText = file_name.files[0].name;
    tr.appendChild(td2);
    var td3 = document.createElement('td');
    var date = new Date();
    td3.innerText = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
    tr.appendChild(td3);

    var td4 = document.createElement('td');
    td4.innerText = comment.value;
    tr.appendChild(td4);
    body.appendChild(tr);
    input.addEventListener('change', function () {
        appContainer.checkChoice(input);
    });
    var fileNameInputTag = appContainer.createHiddenFileInput(file_name, 1);
    body.appendChild(fileNameInputTag);
    return date;
};

appContainer.firstItemPhone = function (countryCode, operatorCode, phoneNumber, typePhone, commentPhone, phone_tbody) {
    var tr = document.createElement('tr');
    var data_index = document.createAttribute('data-index');
    //noinspection JSValidateTypes
    data_index.value = 1;
    tr.setAttributeNode(data_index);
    var td1 = document.createElement('td');
    td1.className = 'bs-checkbox';
    var label = document.createElement('label');
    var input = document.createElement('input');
    input.dataset.index = 1;
    input.name = 'btSelectItem';
    input.type = 'checkbox';
    input.value = 1;
    label.appendChild(input);
    td1.appendChild(label);
    tr.appendChild(td1);
    var td2 = document.createElement('td');
    td2.innerText = '+' + countryCode.value + '(' + operatorCode.value + ')' + phoneNumber.value;
    tr.appendChild(td2);
    var td3 = document.createElement('td');
    td3.innerText = typePhone.value;
    tr.appendChild(td3);
    var td4 = document.createElement('td');
    td4.innerText = commentPhone.value;
    tr.appendChild(td4);
    phone_tbody.appendChild(tr);
    input.addEventListener('change', function () {
        appContainer.checkChoice(input);
    });
};

appContainer.find = function (array, value) {
    value = Number(value);
    if ([].indexOf) {
        return array.indexOf(value);
    } else {
        for (var i = 0; i < array.length; i++) {
            if (array[i] === value) return i;
        }
    }
    return -1;
};
