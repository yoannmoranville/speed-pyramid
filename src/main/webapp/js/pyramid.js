function preparePyramidLoggedout() {
    $(".mybox").each(function(index) {
        $(this).on('click', function() {
            var mybox = $(this);
            var playerId = $(this).data('playerid');
            var modal_id = '#modal_' + playerId;
            $(modal_id).modal({});
            $("#lastResultPlayer_" + playerId).removeClass("hidden");
            $("#openChallengePlayer_" + playerId).removeClass("hidden");
            $("#lastResultPlayerLoader_" + playerId).removeClass("hidden");
            $("#openChallengePlayerLoader_" + playerId).removeClass("hidden");
            $("#lastResultPlayerData_" + playerId).html("");
            $("#openChallengePlayerData_" + playerId).html("");

            $(modal_id).unbind('shown.bs.modal');
            $(modal_id).on('shown.bs.modal', function() {
                $.post("getUserMatchData.html", {playerid: playerId}, function(data) {
                    if(data) {
                        $("#lastResultPlayerLoader_" + playerId).addClass("hidden");
                        $("#openChallengePlayerLoader_" + playerId).addClass("hidden");
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
                        $(mybox).off('click');
                        $(modal_id).unbind('shown.bs.modal');
                        simplyShowPlayerData(mybox);
                    } else {
                        $("#lastResultPlayer_" + playerId).addClass("hidden");
                        $("#openChallengePlayer_" + playerId).addClass("hidden");
                    }
                });
            });
        });
    });
}

function simplyShowPlayerData(mybox) {
    $(mybox).on('click', function() {
        var modal_id = '#modal_' + $(this).data('playerid');
        $(modal_id).modal({});
    });
}

function preparePyramid(yourself, isInChallenge, days) {
    preparePyramidLoggedout();

    if(isInChallenge && days != -1) {
        $("#isInChallenge").text(" | You are in a challenge and have " + days + " days to play");
    }

    $(".btn-challenge").on('click', function(){
        $(this).attr("disabled", "disabled");
        var challenge_player = $(this).data('challenge_player');
        console.log('player '+yourself+' is challenging '+challenge_player);
        if(confirm("Are you sure?")) {
            $.post("usersEncounterQuestion.html", {asker: yourself, asked: challenge_player}, function(data){
                if(data.success == 'true'){
                    console.log("sucessfully challenged. emails sent.");
                    $(this).text("Player challenged.");
                    setTimeout(function() {
                        location.reload();
                    }, 2000);
                } else {
                    $(this).removeAttr("disabled");
                    console.log(data);
                    alert("Sorry a problem occured, please contact admin...");
                }
            });
        }
    });
}