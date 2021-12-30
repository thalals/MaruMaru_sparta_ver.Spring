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
                        <div class="card_top">
<!--                            <ul>-->
<!--                              <a href="#">-->
<!--                              <li><i class="fas fa-heart" title="좋아요"></i></li>-->
<!--                              </a>-->
<!--                            </ul>-->
                            <div>
                                <h3 class="name desc">${dogName}</h3>
                            </div>
                        </div>
                        
                        <img class="dog_img" src="${dogImgUrl}">
                        
                            <div class="card_middle">
                                <div class="desc">
                                    <p class="age" style="font-size: 20px">나이: ${dogAge}살<span class="gender ml-4">성별: ${dogGender}</span></p>
                                    <div class="desc comment">
                                    <p>자기소개: ${dogComment}</p>
                                    </div>
                                </div>
                            </div>
                        
                            <button class="detail-button btn btn-primary" onclick = "location.href ='/profile/detail/${idx}'">자세히 보기</button>
                     </div>`;
    $("#profile_card_list").append(tempHtml);
}