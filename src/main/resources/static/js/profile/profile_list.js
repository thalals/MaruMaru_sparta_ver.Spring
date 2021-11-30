function create_baby_profile() {
    var popup = window.open('/profileup', '네이버팝업', 'width=700px,height=700px,scrollbars=yes');
    popup.onbeforeunload=function (){
        window.location.reload();
    }
}

$(document).ready(function () {
    show_all_profile();
});

function show_all_profile() {
    $.ajax({
        type: "GET",
        url: "/profiles",
        contentType: 'application/json',
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let profiles = response[i];
                let dogName = profiles[i]['dogName'];
                let dogAge = profiles[i]['dogAge'];
                let dogGender = profiles[i]['dogGender'];
                let dogComment = profiles[i]['dogComment'];
                let fileName = profiles[i]['fileName'];
                addHTML(dogName, dogAge, dogGender, dogComment, fileName);
            }
        }
    })
}

function addHTML(dogName, dogAge, dogGender, dogComment) {
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
                            <img class="dog_img" src="/img/maru.png">
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
                          </div>
                        </div>
                      </div>`;
    $("#profile_card").append(tempHtml);
}

// function ProfileLike(profile_number) {
//
//     let id = profile_number
//
//     $.ajax({
//         type: "POST",
//         url: "/meets/profile",
//         data: JSON.stringify(id),
//         success: function (response) {
//             alert(response['msg'])
//             window.location.reload();
//         }
//     })
// }