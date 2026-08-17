$(function() {
    var plot = document.getElementById('plot');
    var textresult = document.getElementById('textresult');

    var host = 'http://localhost:4567';
    const history_server = host + '/history';
    const historytext_server = host + '/historytext';
    const hyponyms_server = host + '/hyponyms';
    const hypohist_server = host + '/hypohist';
    const hypohisttext_server = host + '/hypohisttext';
    const commonancestors_server = host + '/ancestors';

    const shortestpath_server = host + '/shortestpath';
    const zipf_server = host + '/zipf';
    const wordlength_server = host + '/wordlength';
    const trending_server = host + '/trending';

    function get_params() {
        return {
            words: document.getElementById('words').value,
            startYear: document.getElementById('start').value,
            endYear: document.getElementById('end').value,
            k: document.getElementById('k').value
        }
    }

    $('#history').click(historyButton);
    $('#historytext').click(historyTextButton);
    $('#hyponyms').click(hyponymsButton);
    $('#hypohist').click(hypohistButton);
    $('#hypohisttext').click(hypohistTextButton);
    $('#commonancestors').click(commonAncestorsButton);

    $('#shortestpath').click(shortestPathButton);
    $('#zipf').click(zipfButton);
    $('#wordlength').click(wordLengthButton);
    $('#trending').click(trendingButton);

    function displayPlot(url) {
        $("#textresult").hide();
        $("#plot").show();
        $.get({
            async: false,
            url: url,
            data: get_params(),
            success: function(data) {
                plot.src = 'data:image/png;base64,' + data;
            },
            error: function(data) {
                console.log("error", data);
            },
            dataType: 'json'
        });
    }

    function displayText(url) {
        $("#plot").hide();
        $("#textresult").show();
        $.get({
            async: false,
            url: url,
            data: get_params(),
            success: function(data) {
                textresult.value = data;
            },
            error: function(data) {
                console.log("error", data);
            },
            dataType: 'json'
        });
    }

    function historyButton() { displayPlot(history_server); }
    function historyTextButton() { displayText(historytext_server); }
    function hyponymsButton() { displayText(hyponyms_server); }
    function hypohistButton() { displayPlot(hypohist_server); }
    function hypohistTextButton() { displayText(hypohisttext_server); }
    function commonAncestorsButton() { displayText(commonancestors_server); }

    function shortestPathButton() { displayText(shortestpath_server); }
    function zipfButton() { displayPlot(zipf_server); }
    function wordLengthButton() { displayPlot(wordlength_server); }
    function trendingButton() { displayText(trending_server); }
});