function preparePyramid(yourself, availables) { //1. your id, 2. list of available player ids
    addPlayerPosition(yourself);
    addAvailability(availables);
}

function addPlayerPosition(yourself) {
    $("#" + yourself).addClass("yourself");
}

function addAvailability(availables) {
    var array = $.parseJSON(availables);
    $("#btnCancel").click(function() {
        $.fn.colorbox.close();
    });
    $.each(array, function (index, value) {
        $("#" + value).addClass("available");
        $("#link_" + value).removeClass("spanLink");
        bindColorboxLinks("#link_" + value, value);
    });
}

function bindColorboxLinks(linkId, aId) {
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
                $.post("usersDataColorbox.html", {id: aId}, function(databack){
                    if(databack.username) {
                        $("#colorbox #data").html(databack.username + ' (' + databack.email +')');
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