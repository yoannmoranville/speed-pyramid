function bindStatistics() {
    console.log("bind");
    $(document).on('click', '.radio_player', function() {
        console.log("click");
        if($(this).is(":checked")) {
            $("#line-statistics").html("");
            console.log($(this).attr("id") + " is checked");
            $.post("getStatistics.html", { playerId: $(this).attr("id")},
                function(data) {
                    Morris.Line({
                        element: 'line-statistics',
                        data: data.stats,
                        xkey: 'date',
                        ykeys: ['position'],
                        labels: [data.playername],
                        dateFormat: function(x) {
                            return moment(x).format("Do MMMM YYYY");
                        },
                        xLabelFormat: function(x) {
                            return moment(x).format("DD-MM-YYYY");
                        },
                        yLabelFormat: function(y) {
                            return ("Position " + y).replace("-", "");
                        },
                        ymax: 0,
                        hideOver: false,
                        ymin: -24,
                        smooth: false
                    });

                }
            ).fail(function(data) {
                    console.log("Error! " + data);
                }
            );
        }
    });
}