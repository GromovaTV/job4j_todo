function logout() {
    console.log(logout);
    $.ajax({
        cache: false,
        url: '/todo/logout.do',
        type: 'GET'
    }).done(function () {
        console.log("Done");
    }).fail(function () {
        console.log("Error");
    });
}