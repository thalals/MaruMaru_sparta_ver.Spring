//쿠키에 mytoken이 존재하면 user 정보 띄우기
$(document).ready(function () {
    let my_token = getCookie('mytoken');

    if (my_token == null) {
        $('#login-button').show();
        $('#user-profile').hide();
    } else {
        $('#login-button').hide();
        $('#user-profile').show();

        $.ajax({
            type: "GET",
            url: "/user_info",
            data: {},
            success: function (response) {
                let user = response['user_info']
                let profile_name = user['profile_name']
                let username = user['username']
                let profile_info = user['profile_info']
                let profile_img = user['profile_pic']

                $('#user-profile').attr("src", '/static/' + profile_img)

                //유저 프로필 카드
                $('.card-profile-img').attr("src", '/static/' + profile_img)
                $('.card-title').text(profile_name)
                $('.card-subtitle').text(username)
                $('.card-text').text(profile_info)
            },
            error: function (request, status, error) {
                alert(error);
            }
        });
    }
})

function get_card() {
    $('#user-card').toggleClass('invisible');
    $('#user-profile').toggleClass('border border-2');
}

function getCookie(key) {
    let result = null;
    let cookie = document.cookie.split(';');
    cookie.some(function (item) {
        // 공백을 제거
        item = item.replace(' ', '');

        let dic = item.split('=');
        if (key === dic[0]) {
            result = dic[1];
            return true;    // break;
        }
    });
    return result;
}

function onClickCreateEvent() {
    if (document.cookie.match("mytoken") != null) {
        window.location.href = "/events"
    } else {
        alert("로그인 후 생성해주세요 !!")
        window.location.href = '/login';
    }
}

function onClickCreateProfile() {
    if (document.cookie.match("mytoken") != null) {
        window.location.href = "/profile/create"
    } else {
        alert("로그인 후 생성해주세요 !!")
        window.location.href = '/login';
    }
}

function logout() {
    $.removeCookie('mytoken', {path: '/'});
    window.location.href = '/';
}