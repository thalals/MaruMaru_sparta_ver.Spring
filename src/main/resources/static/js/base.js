let ebUrl ="http://maruapp-env-2.eba-i5ijnpti.ap-northeast-2.elasticbeanstalk.com"

//쿠키에 mytoken이 존재하면 user 정보 띄우기
$(document).ready(function () {
    let my_token = localStorage.getItem('token');

    if (my_token == null) {
        $('#login-button').show();
        $('#user-profile').hide();
    } else {
        $('#login-button').hide();
        $('#user-profile').show();

        if (localStorage.getItem('token')) {
            $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
                jqXHR.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('token'));

            });
        } else {
            alert('로그인을 해주세요')
            location.replace(`/login`)
        }

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

    $.ajax({
        type: "GET",
        url: `/profile`,
        contentType: false,
        processData: false,
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let profiles = response[i];
                let idx = profiles['idx']
                let dogImgUrl = profiles['dogImgUrl']
                let dogName = profiles['dogName'];
                let dogAge = profiles['dogAge'];
                let dogGender = profiles['dogGender'];
                let dogComment = profiles['dogComment'];
                addHTML(idx, dogImgUrl, dogName, dogAge, dogGender, dogComment);
            }
        }
    })
})

function get_card() {
    $('#user-card').toggleClass('invisible');
    $('#user-profile').toggleClass('border border-2');
}


function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    location.href = ebUrl;
}

function addHTML(idx, dogImgUrl, dogName, dogAge, dogGender, dogComment) {
    //1. HTML 태그를 만든다.
    let tempHtml = `<div class="card color-card">
                        <ul>
                          <a href="#">
                          <li>
                          <i class="fas fa-heart" title="좋아요"></i>
                          </i>
                          </li>
                          </a>
                        </ul>
                        <div class="card_top">
                          <div>
                            <div class="profile_img">
                            <img class="dog_img" src="${dogImgUrl}">
                            </div>
                          </div>
                          <div>
                            <p class="name desc">${dogName}</p>
                          </div>
                          <div class="desc">
                            <p class="age">나이: ${dogAge}</p>
                            <p class="gender">성별: ${dogGender}</p>
                          </div>
                          <hr>
                          <div class="desc comment" style="font-size: 20px">
                            <p style="font-size: 15px">${dogComment}</p>
                            <button class="button color-a top mt-5" onclick = "location.href ='/profile/detail/${idx}'">자세히 보기</button>
                          </div>
                        </div>
                      </div>`;
    console.log(dogName, dogImgUrl)
    $("#profile_card_list").append(tempHtml);
}