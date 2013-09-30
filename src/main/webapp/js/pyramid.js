function preparePyramid(yourself, availables) { //1. your id, 2. list of available player ids
    addPlayerPosition(yourself);
    addAvailability(availables);
}

function addPlayerPosition(yourself) {
    $("#" + yourself).addClass("yourself");
    $("a.spanLink").click(function() { return false; });
}

function addAvailability(availables) {
    var array = $.parseJSON(availables);
    $.each(array, function (index, value) {
        $("#" + value).addClass("available");
        $("#link_" + value).removeClass("spanLink");
        $("#link_" + value).click(function() { return true; });
    });
}