function preparePyramid(yourself, availables) { //1. your id, 2. list of available player ids
    addPlayerPosition(yourself);
    bindAllColorbox();
    addAvailability(availables, yourself);
}

function addPlayerPosition(yourself) {
    $("#" + yourself).addClass("yourself");
}
function bindAllColorbox() {
    $(".spanLink").each(function() {
        $(this).removeClass("spanLink");
        var ident = $(this).attr("id");
        bindColorboxLinks('#' + ident, ident.replace('link_', ''), null);
    });

}

function addAvailability(availables, yourself) {
    var array = $.parseJSON(availables);
    $("#btnCancel").click(function() {
        $.fn.colorbox.close();
    });
    $.each(array, function (index, value) {
        $("#" + value).addClass("available");
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
                $("#btnEncounter").addClass("hidden");
                $("#error_user_already_challenged").addClass("hidden");
                $("#error_not_reachable").addClass("hidden");
                $("#error_you_already_challenged").addClass("hidden");

                $("#cboxClose").remove();
                $.post("usersDataColorbox.html", {id: aId}, function(databack){
                    if(databack.username) {
                        $("#colorbox #data #name").html(databack.username + ' (' + databack.email + ')');
                        if(databack.avatarPath) {
                            $("#colorbox #avatar").attr("src", databack.avatarPath);
                        }
                        $("#colorbox #data #position").html("Position: " + databack.pyramidPosition);
                        $("#colorbox #data #gender").html("Gender: " + databack.gender);
                        if(yourself != null) {
                            $("#btnEncounter").removeClass("hidden");
                            $("#btnEncounter").click(function(){
                                if(confirm("Are you sure?")) {
                                    $.post("usersEncounterQuestion.html", {asker: yourself, asked: aId}, function(databack2){
                                        if(databack2.success == 'true'){
                                            alert("Emails sent");
                                            $.fn.colorbox.close();
                                            location.reload(true);
                                        } else {
                                            alert("Problem, please contact admin...");
                                        }
                                    });
                                }
                            });
                        }
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