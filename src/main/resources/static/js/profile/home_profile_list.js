$(document).ready(function () {
    show_all_profile();
});

function show_all_profile() {
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
    $("#profile_card_list").append(tempHtml);
}