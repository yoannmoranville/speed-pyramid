window.addEvent('load', function() {
    var objContainer = document.getElementById( "pyramid" ),
        intLevels = 10,
        strBlocksHTML = '';

    // Using innerHTML is faster than DOM appendChild
    var id = 0;
    for( var i=0; i<intLevels; i++ ){
        for( var n=0; n<i+1; n++ ){
            strBlocksHTML += '<div class="buildingBlock" id="block_' + (++id) +'">??</div>';
        }
        strBlocksHTML += '<div></div>'; // Line break after each row
    }

    objContainer.innerHTML = strBlocksHTML;
    preparePyramid();
});

function preparePyramid() {
    addNames();
    addAvailability();
}

function addNames() {
    $("span.player").each(function(index) {
        $("#block_" + index+1).text($(this).text());
        alert((index+1) + " - " + $(this).text());
    });
}

function addAvailability() {

}