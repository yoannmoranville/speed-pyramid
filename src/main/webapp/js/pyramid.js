function preparePyramid(yourself, availables) { //1. your id, 2. list of available player ids
    addPlayerPosition(yourself);
    addAvailability(availables, yourself);
}

function addPlayerPosition(yourself) {
    $("#" + yourself).addClass("yourself");
}

function addAvailability(availables, yourself) {
    var array = $.parseJSON(availables);
    $("#btnCancel").click(function() {
        $.fn.colorbox.close();
    });
    $.each(array, function (index, value) {
        $("#" + value).addClass("available");
        $("#link_" + value).removeClass("spanLink");
        bindColorboxLinks("#link_" + value, value, yourself);
    });
}

function bindColorboxLinks(linkId, aId, yourself) {
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
                $("#btnEncounter").unbind();
                $("#cboxClose").remove();
                $.post("usersDataColorbox.html", {id: aId}, function(databack){
                    if(databack.username) {
                        $("#colorbox #data").html(databack.username + ' (' + databack.email +')');
                        $("#btnEncounter").click(function(){
                            if(confirm("Are you sure?")) {
                                $.post("usersEncounterQuestion.html", {asker: yourself, asked: aId}, function(databack2){
                                    if(databack2.success == 'true'){
                                        alert("Emails sent");
                                        $.fn.colorbox.close();
                                    } else {
                                        alert("Problem, please contact admin...");
                                    }
                                });
                            }
                        });
                    } else {
                        $("#colorbox #data").html("Error");
                    }
                });
            },
            onCleanup:function() {
            },
            href:"#colorbox"
        }
    );
}