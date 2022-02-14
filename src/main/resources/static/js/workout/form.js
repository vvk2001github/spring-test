$(document).ready(function(){

    function exIdChange() {
        let extype = $("#exid").find('option:selected').data('extype');
        if(extype == 0) {
            $('#divWeight1').hide();
            $('#divWeight2').hide();
            $('#divCount2').hide();
        }
        if(extype == 1) {
            $('#divWeight1').hide();
            $('#divWeight2').hide();
            $('#divCount2').show();
        }
        if(extype == 2) {
            $('#divWeight1').show();
            $('#divWeight2').hide();
            $('#divCount2').hide();
        }
        if(extype == 3) {
            $('#divWeight1').show();
            $('#divWeight2').show();
            $('#divCount2').show();
        }
    }

    $('#exid').change(exIdChange);


    //On Load
    // $('#divWeight1').hide();
    // $('#divWeight2').hide();
    // $('#divCount2').hide();
    exIdChange();
});