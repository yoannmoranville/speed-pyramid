
function bindLoggedoutPyramidFunctions() {
    $(document).on('click','.mybox', function(){
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

function bindPyramidFunctions(isInChallenge, days) {
    console.log(isInChallenge + " - " + days)
    bindLoggedoutPyramidFunctions();

    $(document).on('click','.btn-challenge',function(){
        $(this).attr("disabled", "disabled");

        if(confirm("Are you sure?")) {
            console.log($("#matchform").data('challengerid') + ' vs ' + $("#matchform").data('challengeeid'))
            $.post("usersEncounterQuestion.html", {asker: $("#matchform").data('challengerid'), asked: $("#matchform").data('challengeeid')}, function(data){
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

    /* Enter Results from Pyramid View */

    $('#dateMatchPlayed').datepicker();

    $("#enter_result_button").click(function() {
        var modal_id = '#modal_results';
        $(modal_id).modal({});
    });

    $("#savematch").click(function() {
        console.log("saving match id:"+$("#matchform").data('matchid'));
        $.post("saveResults.html",
            {   matchid: $("#matchform").data('matchid'),
                challengerid: $("#matchform").data('challengerid'),
                challengeeid: $("#matchform").data('challengeeid'),
                loggedplayerid: $("#matchform").data('loggedplayerid'),
                results_set1_player1: $("#set11").val(),
                results_set1_player2: $("#set12").val(),
                results_set2_player1: $("#set21").val(),
                results_set2_player2: $("#set22").val(),
                results_set3_player1: $("#set31").val(),
                results_set3_player2: $("#set32").val(),
                datePlayed: $("#dateMatchPlayed").val()
            },
            function(data){
                console.log(data);
                if(data.errors) {
                    alert("Error "+data.errors)
                } else if(data.success == 'true'){
                    alert("Results received, you will receive an email shortly.");
                    location.reload();
                } else {
                    alert("Problem, please contact admin...");
                }
            });
    });

    if(isInChallenge && days != null && days != -1) {
        $("#isInChallenge").text(" | You are in a challenge and have " + days + " days to play");
    }
}