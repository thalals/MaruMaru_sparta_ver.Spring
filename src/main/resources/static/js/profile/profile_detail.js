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
            $('#dog-age').text(dogAge);
            $('#dog-gender').text(dogGender);
            $('#dog-comment').text(dogComment);
        },
        error: function (request, status, error) {
            alert(error);
        }
    });
}
// function delete_post() {
//     const result = confirm("정말로 삭제 하시겠습니까?");
//     if (result) {
//         $.ajax({
//             type: "DELETE",
//             url: `/profile`,
//             data: {id_give: id},
//             success: function (response) {
//                 window.location.href = `/profiles`
//             },
//             error: function (request, status, error) {
//                 alert(error);
//             }
//         });
//     } else {
//         return false;
//     }
// }