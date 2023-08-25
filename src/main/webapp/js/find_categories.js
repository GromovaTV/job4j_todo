$(document).ready(function () {
    $.ajax({
        cache: false,
        url: '/todo/add.do',
        type: 'GET',
        dataType: 'json'
    }).done(function (data) {
        for (const ctgr of data) {
            var option = $(
                '<option value="' + ctgr.id + '">' + ctgr.name + '</option>');
            $('#cIds').append(option);
        }
    }).fail(function () {
    });
});

