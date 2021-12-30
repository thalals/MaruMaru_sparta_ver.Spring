$(document).ready(function () {
    bsCustomFileInput.init();


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
        url: `/userprofile/${username}`,
        contentType: 'application/json; charset=utf-8',
        data: {},
        success: function (response) {
            $('.thumbnail').attr("src", response["userProfileImg"])
            $('.thumbnail').attr("src", response["userProfileImg"])
            $('#username').attr("placeholder", response['username'])
            $('#name').attr("placeholder", response['nickname'])
            $('#description').attr("placeholder", response['userContent'])
        }
    })

    $.ajax({
        type: "GET",
        url: `/user/dogprofile`,
        contentType: 'application/json; charset=utf-8',
        data: {},
        success: function (response) {
            for(let i=0; i<response.length;i++){
                let temp_html=`<div class="card color-card">
                        <div class="card_top">
                            <!--<ul>
                              <a href="href="javascript:void(0);" onclick="ProfileLike(${response[i]['idx']})">
                              <li><i class="fas fa-heart" title="좋아요"></i></li>
                              </a>
                            </ul>-->
                            <div>
                                <h3 class="name desc">${response[i]['dogName']}</h3>
                            </div>
                        </div>
                        
                        <img class="dog_img" src="${response[i]['dogImg']}">
                        
                            <div class="card_middle">
                                <div class="desc">
                                    <p class="age" style="font-size: 20px">나이: ${response[i]['dogAge']}살<span class="gender ml-4">성별: ${response[i]['dogGender']}</span></p>
                                    <div class="desc comment">
                                    <p>자기소개: ${response[i]['dogComment']}</p>
                                    </div>
                                </div>
                            </div>
                        
                            <button class="detail-button btn btn-primary" onclick="location.href ='/profile/detail/${response[i]['idx']}'">자세히 보기</button>
                            <input type="hidden" value=${response[i]['idx']} id="${response[i]['idx']}card">
                     </div>`
                $('#baby_list').append(temp_html)
            }
        }
    })
})



function create_baby_profile() {
    var popup = window.open('/profileup', '네이버팝업', 'width=700px,height=700px,scrollbars=yes');
    popup.onbeforeunload=function (){
        window.location.reload();
    }
}

// function ProfileLike(number) {
//
//     const profile_id = number
//
//     $.ajax({
//         type: "POST",
//         url: `/dogprofile/like`,
//         data: {id_give: profile_id},
//         success: function (response) {
//             alert(response['msg'])
//             window.location.reload();
//         }
//     })
// }