let url_list = window.location.href.split('/')
const id = url_list[url_list.length-1]

$(document).ready(function () {
    if (localStorage.getItem('token')) {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('token'));

        });
    } else {
        alert('로그인을 해주세요')
        location.replace('/user/login')
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
            console.log(response)
            let number = response["idx"];
            let dogImgUrl = response["dogImgUrl"]
            let dogName = response["dogName"]
            let dogAge = response["dogAge"]
            let dogGender = response["dogGender"]
            let dogComment = response["dogComment"]

            $("#profile_id").val(number);
            $('#dog_img').attr("src", dogImgUrl);
            $('#dog_name').attr("placeholder", dogName);
            $('#dog_age').attr("placeholder", dogAge);
            $('#dog_gender').attr("placeholder", dogGender);
            $('#dog_comment').attr("placeholder", dogComment);
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

    let formData = new FormData();
    formData.append("dogimg", file);
    formData.append("key", new Blob([JSON.stringify(data)], {type: "application/json"}));

    $.ajax({
        type: "PUT",
        url: `/profile/detail`,
        processData: false,
        contentType: false,
        data: formData,
        success: function (response) {
            window.close();
            window.location.reload();
        },
        error: function (request, status, error) {
            alert(error);
        }
    });
}