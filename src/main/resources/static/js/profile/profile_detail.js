let url_list = window.location.href.split('/')
const id = url_list[url_list.length-1]

$(document).ready(function () {
    $.ajax({
            type: "GET",
            url: `/dog_info`,
            data: {id: id},
            success: function (response) {
                profile_db = response['profile_db']
                console.log(profile_db)
                $('#profile-name').text(profile_db.name+ '프로필')
                $('#dogdetail_url').attr('href','/dogdetail/'+id)
                $('#profile_id').val(id)
                $('.dog_img').attr('src','/static/profileimg/'+profile_db.file)
                $('#dog-name').text(profile_db.name)
                $('#dog-age').text('나이: '+profile_db.age)
                $('#dog-gender').text('성별: '+profile_db.gender)
                $('#dog-comment').text(profile_db.comment)


            },
            error: function (request, status, error) {
                alert(error);
            }
        });
});
function delete_post() {
    const result = confirm("정말로 삭제 하시겠습니까?");
    if (result) {
        $.ajax({
            type: "DELETE",
            url: `/profile`,
            data: {id_give: id},
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