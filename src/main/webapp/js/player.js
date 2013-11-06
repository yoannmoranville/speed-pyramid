function preparePlayer(matchIdsWithoutResults) {
    $("#btnCancel").click(function() {
        $.fn.colorbox.close();
    });
    var array = $.parseJSON(matchIdsWithoutResults);
    $.each(array, function (index, value) {
        bindColorboxLinks(value);
    });
}

function bindColorboxLinks(aId) {
    var linkId = "#link_" + aId;
    $(linkId).colorbox(
        {
            width:"80%",
            height:"400px",
            inline:true,
            overlayClose:false,
            escKey:false,
            onOpen:function() {
                $(document).unbind('keydown.cbox_close');
            },
            onLoad:function() {
                $("#cboxClose").remove();
                $.post("resultColorbox.html", {id: aId}, function(databack){
                    if(databack.askerName && databack.askedName) {
                        $("#colorbox #name_player1").html(databack.askerName);
                        $("#colorbox #name_player2").html(databack.askedName);

                        $("#btnSave").click(function(){
                            if(confirm("Are you sure?")) {
                                $.post("saveResults.html", {matchId: aId, asker: databack.askerId, asked: databack.askedId, results_set1_player1: $("#colorbox #set11").val(), results_set1_player2: $("#colorbox #set12").val(), results_set2_player1: $("#colorbox #set21").val(), results_set2_player2: $("#colorbox #set22").val(), results_set3_player1: $("#colorbox #set31").val(), results_set3_player2: $("#colorbox #set32").val(), datePlayed: $("#colorbox #dateMatchPlayed").val()}, function(databack2){
                                    if(databack2.success == 'true'){
                                        alert("Results saved");
                                        $.fn.colorbox.close();
                                        location.reload(true);
                                    } else {
                                        alert("Problem, please contact admin...");
                                    }
                                });
                            }
                        });
                    } else {
                        $("#colorbox").html("Error");
                    }
                });
            },
            onCleanup:function() {
            },
            href:"#colorbox"
        }
    );
}