function preparePyramid(yourself) {
    $(".mybox").click(function() {
//    $(document).on('click','.mybox', function(){
        var modal_id = '#modal_' + $(this).data('playerid');
        $(modal_id).modal({});
    });

    $(".btn-challenge").click(function(){
//    $(document).on('click','.btn-challenge',function(){
        $(this).attr("disabled", "disabled");
        var challenge_player = $(this).data('challenge_player');
        console.log('player '+yourself+' is challenging '+challenge_player);

        $.post("usersEncounterQuestion.html", {asker: yourself, asked: challenge_player}, function(data){
            if(data.success == 'true'){
                console.log("sucessfully challenged. emails sent.");
                location.reload();
            } else {
                $(this).removeAttr("disabled");
                console.log(data);
                alert("Sorry a problem occured, please contact admin...");
            }
        });
        $(this).text("Player challenged.");
    });
}