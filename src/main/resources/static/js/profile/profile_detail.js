let url_list = window.location.href.split('/')
const id = url_list[url_list.length - 1].replace(/[^0-9.]/g, '')

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

function Modify_baby_profile() {
    var popup = window.open(`/profile/modify/${id}`, '네이버팝업', 'width=700px,height=700px,scrollbars=yes');
    popup.onbeforeunload=function (){
        window.location.reload();
    }
}

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
            $('#dog-name').text(dogName);
            $('#dog-age').text('나이:'+ dogAge);
            $('#dog-gender').text('성별:'+ dogGender);
            $('#dog-comment').text(dogComment);
        },
        error: function (request, status, error) {
            alert(error);
        }
    });
}
function delete_profile() {
    let id = $("#profile_id").val();
    let result =confirm("정말로 삭제 하시겠습니까?");
    if (result) {
        $.ajax({
            type: "DELETE",
            url: `/profile/detail`,
            data: {id: id},
            success: function (response) {
                window.location.href = `/profiles`
            },
            error: function (request, status, error) {
                alert(error);
            }
        });
    } else {
        return false;
    }
}