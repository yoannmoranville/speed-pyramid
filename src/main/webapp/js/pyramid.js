$(function() {
//    preparePyramid();
});

function preparePyramid() {
    addNames();
    addAvailability();
}

function addNames() {
    $("span.player").each(function(index) {
        $("#block_" + index+1).text($(this).text());
//        alert((index+1) + " - " + $(this).text());
    });
}

function addAvailability() {

}