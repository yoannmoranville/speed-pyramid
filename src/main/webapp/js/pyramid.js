
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

function bindPyramidFunctions(isInChallengeDate) {
    $(document).on('click','.mybox', function(){
        var modal_id = '#modal_'+$(this).data('playerid');
        console.log('model_id:'+modal_id);

        $(modal_id).modal({});

    });

    $('#confirmLostMatch').click(function(){
        $.post("confirmMatchResults.html", function(data){
            if(data.success == 'true'){
                console.log("sucessfully confirmed lost match.");
                $('#confirmLostMatch').attr("disabled", "disabled");
                $('#confirmLostMatch').text("confirming result...");
                setTimeout(new function() {
                    location.reload();
                }, 500);
            } else {
                console.log(data);
                alert("Sorry we could not confirm this result... Please contact an admin.");
            }
        });
    });



    $(document).on('click','.btn-challenge',function(){
        var challenge_player = $(this).data('challenge_player');
        var challengee_name = $(this).data('challengee_player_name');
        console.log('Challenging '+challenge_player + ", name: " + challengee_name);

        if(confirm("Are you sure you want to challenge '" + challengee_name + "'?")) {
            $(this).attr("disabled", "disabled");
            $(this).text("Challenging......")
            $.post("usersEncounterQuestion.html", { challenge_player: challenge_player}, function(data){
                if(data.success == 'true'){
                    console.log("sucessfully challenged. emails sent.");
                    location.reload();
                } else {
                    $(this).removeAttr("disabled");
                    $(this).text("Challenge Player.")
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
        $(this).attr("disabled", "disabled");
        $(this).text("Saving......");
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
            function(){
                location.reload();
            }).fail(function(data) {
                $(this).removeAttr("disabled");
                console.log(data);
                $("#resultsValidationBox").show();
                $("#resultsValidationBox").html(data.responseJSON.errors);
            })

        ;
    });

    if(undefined != isInChallengeDate && isInChallengeDate != -1) {
        $("#isInChallenge").text("You are in a challenge and have " + isInChallengeDate + " days to play");
    }
}