function preparePlayerList() {
    $(".disablePlayer, .enablePlayer").submit(function(e){
        if (!confirm("Are you sure?")) {
            e.preventDefault();
        }
    });
}