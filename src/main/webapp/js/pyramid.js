function preparePyramidLoggedout() {
    $(".mybox").click(function() {
        var playerId = $(this).data('playerid');
        var modal_id = '#modal_' + playerId;
        $(modal_id).modal({});
        $("#lastResultPlayer_"+$(this).data('playerid')).removeClass("hidden");
        $("#openChallengePlayer_"+$(this).data('playerid')).removeClass("hidden");
        $("#lastResultPlayerData_"+$(this).data('playerid')).html("<img alt='loader' src='images/loader.gif' />");
        $("#openChallengePlayerData_"+$(this).data('playerid')).html("<img alt='loader' src='images/loader.gif' />");

        $(modal_id).on('shown.bs.modal', function() {
            $.post("getUserMatchData.html", {playerid: playerId}, function(data) {
                $("#lastResultPlayerData_" + playerId).html("");
                $("#openChallengePlayerData_" + playerId).html("");
                if(data) {
                    $.each(data.lastResults, function(i, item) {
                        $("#lastResultPlayerData_" + playerId).append(item + "<br/>");
                    });
                    if(data.lastResults.length == 0) {
                        $("#lastResultPlayer_" + playerId).addClass("hidden");
                    }
                    if(data.openChallenge) {
                        $("#openChallengePlayerData_" + playerId).append(data.openChallenge);
                    } else {
                        $("#openChallengePlayer_" + playerId).addClass("hidden");
                    }
                } else {
                    $("#lastResultPlayer_" + playerId).addClass("hidden");
                    $("#openChallengePlayer_" + playerId).addClass("hidden");
                }
            });
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