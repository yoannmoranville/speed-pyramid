function preparePyramidLoggedout() {
    $(".mybox").click(function() {
        var modal_id = '#modal_' + $(this).data('playerid');
        $(modal_id).modal({});
        $(".lastResultPlayer").removeClass("hidden");
        $(".openChallengePlayer").removeClass("hidden");
        $(".lastResultPlayerData").html("rotating image to load");
        $(".openChallengePlayerData").html("rotating image to load");
        $.post("getUserMatchData.html", {playerid: $(this).data('playerid')}, function(data){
            $(".lastResultPlayerData").html("");
            $(".openChallengePlayerData").html("");
            if(data) {
                $.each(data.lastResults, function(i, item) {
                    $(".lastResultPlayerData").append(item + "<br/>");
                });
                if(data.lastResults.length == 0) {
                    $(".lastResultPlayer").addClass("hidden");
                }
                if(data.openChallenge) {
                    $(".openChallengePlayerData").append(data.openChallenge);
                } else {
                    $(".openChallengePlayer").addClass("hidden");
                }
            } else {
                $(".lastResultPlayer").addClass("hidden");
                $(".openChallengePlayer").addClass("hidden");
            }
        });
    });
}

function preparePyramid(yourself, isInChallenge, days) {
    preparePyramidLoggedout();

    if(isInChallenge && days != -1) {
        $("#isInChallenge").text(" | You are in a challenge and have " + days + " days to play");
    }

    $(".btn-challenge").click(function(){
        $(this).attr("disabled", "disabled");
        var challenge_player = $(this).data('challenge_player');
        console.log('player '+yourself+' is challenging '+challenge_player);
        if(confirm("Are you sure?")) {
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
        }
    });
}