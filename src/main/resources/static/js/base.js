//쿠키에 mytoken이 존재하면 user 정보 띄우기
$(document).ready(function () {
    let my_token = localStorage.getItem('token');

    if (my_token == null) {
        $('#login-button').show();
        $('#user-profile').hide();
    } else {
        $('#login-button').hide();
        $('#user-profile').show();


        let username = localStorage.getItem("username")
        $.ajax({
            type: "GET",
            url: `/userprofile/${username}`,
            contentType: 'application/json; charset=utf-8',
            data: {},
            success: function (response){
                let profile_name = response['nickname']
                let username = response['username']
                let profile_info = response['userContent']
                let profile_img = response['userProfileImg']

                $('#user-profile').attr("src",  profile_img)

                //유저 프로필 카드
                $('.card-profile-img').attr("src", profile_img)
                if (profile_name == null) {
                    profile_name = username
                    $('.card-title').text(profile_name)
                } else {
                    $('.card-title').text(profile_name)
                }
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


function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    location.href = "/";
}