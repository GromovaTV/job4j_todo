function selectItem(id) {
    console.log(id);
    $.ajax({
        cache: false,
        url: '/todo/all',
        type: 'POST',
        data: { id: id }
    }).done(function () {
        console.log("Done");
    }).fail(function () {
        console.log("Error");
    });
}