function validate() {
    var email = $('#email').val();
    if (email == "") {
        alert($('#email').attr('title'));
        return false;
    }
    var password = $('#password').val();
    if (password == "") {
        alert($('#password').attr('title'));
        return false;
    }
    return true;
}