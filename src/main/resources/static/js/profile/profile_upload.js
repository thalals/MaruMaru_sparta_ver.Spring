$(document).ready(function () {
    bsCustomFileInput.init();

    if (localStorage.getItem('token')) {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('token'));

        });
    } else {
        alert('로그인을 해주세요')
        window.close();
        location.replace('/profiles');
    }
});

$(document).ready(function () {
    bsCustomFileInput.init();
})

function profile_upload() {

    // let dogName = $("#dog_name").val();
    // let dogAge = $("#dog_age").val();
    // let dogGender = $("#dog_gender").val();
    // let dogComment = $("#dog_comment").val();

    // 사진이 들어가지 않았을 때
    let fileInput = document.getElementsByClassName("file");
    if (fileInput[0].files.length == 0) {
        alert("사진을 넣어주세요!");
        $("#profile_image").focus();
        return false;
    }

    // if (fileInput.length > 0) {
    //     let date = new Date();
    //     let today = date.toLocaleString()
    //
    //     fileName = fileInput[0].files[0].name + today
    // 파일명 출력



    let form_data = new FormData()
    form_data.append("dogImg", $('#profile_image')[0].files[0])
    form_data.append("dogName", $("#dog_name").val());
    form_data.append("dogAge", $("#dog_age").val());
    form_data.append("dogGender", $("#dog_gender").val());
    form_data.append("dogComment", $("#dog_comment").val());

    $.ajax({
        type: "POST",
        url: "/profile",
        data: form_data,
        contentType: false,
        processData: false,
        success: function (response) {
            console.log(form_data)
            alert('프로필 등록 완료!')
            window.close();
            window.location.reload();
        }
    })
}