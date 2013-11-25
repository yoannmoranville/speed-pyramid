function preparePlayerData() {
    $('#dateMatchPlayed').datepicker();

    $(".enter_result_button").click(function() {
        var modal_id = '#modal_'+$(this).data('matchid');
        $(modal_id).modal({});
    });


    $("#savematch").click(function() {
        console.log("saving match id:"+$("#matchform").data('matchid'));
        $.post("saveResults.html",
            {   matchid: $("#matchform").data('matchid'),
                challengerid: $("#matchform").data('challengerid'),
                challengeeid: $("#matchform").data('challengeeid'),
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
}