function preparePyramid(yourself, availables) { //1. your id, 2. list of available player ids
    addPlayerPosition(yourself);
    addAvailability(availables);
}

function addPlayerPosition(yourself) {
    $("#" + yourself).addClass("yourself");
    $("a.spanLink").click(function() { return false; });
}

function addAvailability(availables) {
    var array = $.parseJSON(availables);
    $.each(array, function (index, value) {
        $("#" + value).addClass("available");
        $("#link_" + value).removeClass("spanLink");
        $("#link_" + value).click(function() {
            bindColorboxLinks("#link_" + value);
            return true;
        });
    });
}

function bindColorboxLinks(aId) {
    $(".colorbox").colorbox(
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
                $('#cboxClose').remove();
                $.post("usersDataColorbox.html", {id: aId}, function(databack){
                    if(databack.success) {
                        //to print data of user with possibility to select for encounter
                        $(".colorbox .data").html("Yoyo");
                    } else {
                        //to print error
                        $(".colorbox .data").html("Error");
                    }
                });
            },
            onCleanup:function() {
            },
            href:"#editColorbox"
        }
    );
}