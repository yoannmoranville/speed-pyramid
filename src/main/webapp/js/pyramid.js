function bindPyramidFunctions(isInChallengeDate) {
    $(document).on('click','.mybox', function(){
        var modal_id = '#modal_'+$(this).data('playerid');
        console.log('model_id:'+modal_id);

        $(modal_id).modal({});
    });

    $("#giveup_button").attr("title", "If you have no time to play, if you are injured or if you want to challenge someone else instead, you can cancel this game");
    $("#giveup_button").tooltip();

    if(isInFrame()) {
        $('#signinBtn').attr('target', '_blank');
    }

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

    $(document).on('mouseenter', '.mybox', function(){
        var againstid = $(this).data('againstid');
        var playerid = $(this).data('playerid');
        if(againstid != "" && playerid != "") {
            $("#mybox_" + againstid).addClass('playagainst');
            $("#mybox_" + playerid).addClass('playagainst');
        }
    });

    $(document).on('mouseleave', '.mybox', function(){
        var againstid = $(this).data('againstid');
        var playerid = $(this).data('playerid');
        $("#mybox_" +againstid).removeClass('playagainst');
        $("#mybox_" +playerid).removeClass('playagainst');
    });

    $(document).on('click','.btn-challenge',function(){
        var challenge_player = $(this).data('challenge_player');
        var challengee_name = $(this).data('challengee_player_name');
        console.log('Challenging '+challenge_player + ", name: " + challengee_name);

        if(confirm("Are you sure you want to challenge '" + challengee_name + "'?")) {
            $(this).attr("disabled", "disabled");
            $(".btn-challenge-close").attr("disabled", "disabled");
            $(this).text("Challenging......")
            $.post("usersEncounterQuestion.html", { challenge_player: challenge_player}, function(data){
                if(data.success == 'true'){
                    location.reload();
                } else {
                    $(this).removeAttr("disabled");
                    $(".btn-challenge-close").removeAttr("disabled");
                    $(this).text("Challenge Player.")
                    console.log(data);
                    alert("Sorry a problem occurred, please contact admin...");
                }
            });
        }
    });

    /* Enter Results from Pyramid View */

    $('#dateMatchPlayed').datepicker();

    $("#giveup_button").click(function() {
        if(confirm("Are you really sure you want to give up on this game? There is no going back, you will lose the game...")) {
            $.post("giveupChallenge.html" , {matchid: $(this).data("matchid")}, function() {
                location.reload();
            }).fail(function(data) {
                console.log(data);
                alert("There was a problem... Please try again later or contact an admin.");
            })
        }
    });

    $("#enter_result_button").click(function() {
        var modal_id = '#modal_results';
        $(modal_id).modal({});
    });

    $("#savematch").click(function() {
        console.log("saving match id:"+$("#matchform").data('matchid'));
        $(this).attr("disabled", "disabled");
        $("#savematch-close").attr("disabled", "disabled");
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
                $("#savematch").removeAttr("disabled");
                $("#savematch-close").removeAttr("disabled");
                $("#savematch").text("Save Result");
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

function isInFrame () {
    try {
        return window.self !== window.top;
    } catch (e) {
        return true;
    }
}