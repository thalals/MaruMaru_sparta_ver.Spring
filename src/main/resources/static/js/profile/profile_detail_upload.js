let url_list = window.location.href.split('/')
const id = url_list[url_list.length-1]

$(document).ready(function () {
    if (localStorage.getItem('token')) {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('token'));

        });
    } else {
        alert('로그인을 해주세요')
        location.replace(`/user/login`)
    }
    detailProfile(id)
});

$(document).ready(function () {
    bsCustomFileInput.init();
})

function detailProfile(id) {
    $.ajax({
        type: "GET",
        url: `/profile/detail`,
        data: {id : id},
        success: function (response) {
            let number = response["idx"];
            let dogImgUrl = response["dogImgUrl"]
            let dogName = response["dogName"]
            let dogAge = response["dogAge"]
            let dogGender = response["dogGender"]
            let dogComment = response["dogComment"]

            $("#profile_id").val(number);
            $('#dog_img').attr("src", dogImgUrl);
            $('#dog_name').val(dogName);
            $('#dog_age').val(dogAge);
            $('#dog_gender').val(dogGender);
            $('#dog_comment').val(dogComment);
        },
        error: function (request, status, error) {
            alert(error);
        }
    });
}


function update_profile() {
    let file = null;
    let dogName = $('#dog_name').val();
    let dogAge = $('#dog_age').val();
    let dogGender = $('#dog_gender').val();
    let dogComment = $('#dog_comment').val();
    let fileInput = document.getElementsByClassName("profile_image");

    if (fileInput.length > 0) {
        file = $('#profile_image')[0].files[0]
    }

    let data = {
        dogName : dogName,
        dogAge : dogAge,
        dogGender : dogGender,
        dogComment : dogComment,
        idx : id
    }

    if (fileInput[0].files.length == 0) {
        alert("사진을 넣어주세요!");
        $("#profile_image").focus();
        return false;
    }

    let formData = new FormData();
    formData.append("dogimg", file);
    formData.append("key", new Blob([JSON.stringify(data)], {type: "application/json"}));

    if ($("#dog_name").val().length == 0) {
        alert("강아지 이름을 입력하세요!");
        $("#dog_name").focus();
        return false;
    }
    if ($("#dog_age").val().length == 0) {
        alert("강아지 나이를 입력하세요!");
        $("#dog_age").focus();
        return false;
    }
    if ($("#dog_gender").val() == 0) {
        alert("강아지 성별을 선택하세요!");
        $("#dog_gender").focus();
        return false;
    }
    if ($("#dog_comment").val().length == 0) {
        alert("강아지 소개를 입력하세요!");
        $("#dog_comment").focus();
        return false;
    } else {
        $.ajax({
            type: "PUT",
            url: `/profile/detail`,
            processData: false,
            contentType: false,
            data: formData,
            success: function (response) {
                alert('수정이 완료되었습니다.')
                window.close();
                window.location.reload();
            },
            error: function (request, status, error) {
                alert(error);
            }
        });
    }
}