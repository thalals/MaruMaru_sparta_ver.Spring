$(document).ready(function () {

    if (localStorage.getItem('token')) {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('token'));

        });
    } else {
        alert('로그인을 해주세요')
        location.replace('/login')
    }

    let username = localStorage.getItem("username")
    $.ajax({
        type: "GET",
        url: `/userProfile/${username}`,
        contentType: 'application/json; charset=utf-8',
        data: {},
        success: function (response) {
            let profile_name = response['nickname']
            let profile_info = response['userContent']
            let profile_img = response['userProfileImg']

            $('#profile-img').attr("src", +profile_img)
            $('#name').attr("placeholder", profile_name)
            $('#description').attr("placeholder", profile_info)
        }
    })
})

function go_out() {
    let username = localStorage.getItem("username")
    let data = {
        username: username
    }
    if (confirm("정말 회원 탈퇴하실건가욥?") == false) {
        window.location.reload();
    } else {
        $.ajax({
            type: "DELETE",
            url: `/withdrawal/${username}`,
            contentType: 'application/json; charset=utf-8',
            data: {},
            success: function (response) {
                localStorage.removeItem("token")
                localStorage.removeItem("username")
                window.location.href = '/';
            }
        })
    }
}


function user_update() {
    let username = localStorage.getItem("username")
    let nickname = $('#name').val();
    let userContent = $('#description').val()
    let fileInput = document.getElementsByClassName("file");
    if (fileInput.length > 0) {
        file = $('#file')[0].files[0]
    }
    let data = {
        username: username,
        nickname: nickname,
        userContent: userContent,
    };

    console.log(data, file)
    let formData = new FormData()
    formData.append("userImage", file);
    formData.append("key", new Blob([JSON.stringify(data)] , {type: "application/json"}));

    //사진이 들어가지 않았을 때
    if ($('#file')[0].files[0] == null) {
        alert("사진을 넣어주세요!");
        $("#file").focus();
        return false;
    } else if ($('#name').val().length === 0) {
        alert("별명을 입력해주세요!");
        $("#name").focus();
        return false;
    }

    $.ajax({
        type: "PUT",
        url: "/userProfile",
        contentType: false,
        processData: false,
        data: formData,
        success: function (response) {

            // FormData의 key 확인
            for (let key of formData.keys()) {
                console.log(key);
            }

            // FormData의 value 확인
            for (let value of formData.values()) {
                console.log(value);
            }
            alert("회원정보 수정 완료!");
            location.replace('/user/profile')
        }
    })
}
