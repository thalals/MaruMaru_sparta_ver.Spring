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
        url: `/userProfile/${username}`,
        contentType: 'application/json; charset=utf-8',
        data: {},
        success: function (response) {
            console.log(response['dogProfile'])
            $('.thumbnail').attr("src", response["userProfileImg"])
            $('#username').attr("placeholder",response['username'])
            $('#name').attr("placeholder",response['nickname'])
            $('#description').attr("placeholder",response['userContent'])

           // 댕댕쓰
            let baby_info = response['profile_info']
            console.log(baby_info)
            for(let i=0; i<baby_info.length;i++){
                let temp_html=`<div class="card color-card">
                                    <ul>
                                        <a href="javascript:void(0);" onclick="ProfileLike(${baby_info[i].number})">
                                             <i class="fas fa-heart" title="좋아요"></i>
                                        </a>
                                    </ul>

                                    <div class="card_top">
                                        <div>
                                            <div class="profile_img">
                                                <img class="dog_img" src="/static/profileimg/${baby_info[i].file}">
                                            </div>
                                        </div>
                                        <div>
                                            <p class="name desc">${baby_info[i].name}</p>
                                        </div>
                                        <div class="desc">
                                            <p class="age">나이: ${baby_info[i].age}</p>
                                            <p class="gender">성별: ${baby_info[i].gender}</p>
                                        </div>
                                        <hr>
                                        <div class="desc comment" style="font-size: 15px">
                                            <p>${baby_info[i].comment}</p>
                                            <button class="btn color-a top mt-5"
                                                    onclick="location.href ='/profile/${baby_info[i].number}'">자세히 보기
                                            </button>
                                        </div>

                                    </div>
                                    <input type="hidden" value=${baby_info[i].number} id="${baby_info[i].number}card">
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

function ProfileLike(number) {

    const profile_id = number

    $.ajax({
        type: "POST",
        url: "/dogprofile/like",
        data: {id_give: profile_id},
        success: function (response) {
            alert(response['msg'])
            window.location.reload();
        }
    })
}