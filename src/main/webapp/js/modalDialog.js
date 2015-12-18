
(function modalAttachEdit() {
    var modalWindow = document.getElementById('editModalWindow');
    var page = document.getElementById('attachment_actions');
    swap(modalWindow, page);
    modalDialog(getId('editModalWindow'), getId('edit_attach'), getId('btnCloseEdit'),
        getId('editModalHolder'));
})();

(function modalPhones() {
    var modalWindow = document.getElementById('modal_window');
    var page = document.getElementById('phone_actions');
    swap(modalWindow, page);
    modalDialog(getId('modal_window'), getId('new_phone'), getId('modal_close'),
        getId('modal_holder'));
})();

(function modalAttachments() {
    var modalWindow = document.getElementById('modal_window1');
    var action = document.getElementById('attachment_actions');
    swap(modalWindow, action);
    modalDialog(getId('modal_window1'), getId('new-attach'), getId('modal_close1'),
        getId('modal_holder1'));
})();

function swap(modalWindow, page) {
    page.parentNode.insertBefore(modalWindow, page);
}


function getId(id) {
    return document.getElementById(id);
}

function modalDialog(mOverlay, mOpen, mClose, modal) {
    var allNodes = document.querySelectorAll("*");
    var modalOpen = false;
    var lastFocus;
    var i;

    function modalShow() {
        var upload_file = document.getElementById('upload_file');
        var input = upload_file.querySelector('input[type=file]');
        if (input == null) {
            var div = document.createElement('div');
            div.innerHTML = '<input type="file" class="form-control" ' +
                'id="file_name" name="fileName" ' +
                'placeholder="Enter file name" maxlength="20">';
            upload_file.appendChild(div);
        }
        lastFocus = document.activeElement;
        mOverlay.setAttribute('aria-hidden', 'false');
        modalOpen = true;
        modal.setAttribute('tabindex', '0');
        modal.focus();
    }


    // binds to both the button click and the escape key to close the modal window
    // but only if modalOpen is set to true
    function modalClose(event) {
        if (modalOpen && ( !event.keyCode || event.keyCode === 27 )) {
            mOverlay.setAttribute('aria-hidden', 'true');
            modal.setAttribute('tabindex', '-1');
            modalOpen = false;
            lastFocus.focus();
        }
    }
    mOpen.addEventListener('click', modalShow);

    mClose.addEventListener('click', modalClose);

    document.addEventListener('keydown', modalClose);

}