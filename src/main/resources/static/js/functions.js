function checkDropDownList(position) {
    const strPosition = position.toString();
    if (strPosition === 'Специалист отдела' || strPosition === 'Менеджер отдела') {
        $("#department").attr('disabled', false);
    } else {
        $("#department").attr('disabled', true);
    }
}

function showRegister() {
    $("#login-block").hide();
    $("#registration-block").show();
}
