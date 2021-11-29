$(document).ready(function () {
    bsCustomFileInput.init();

    if (localStorage.getItem('token')) {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('token'));

        });
    } else {
        alert('로그인을 해주세요')
        location.replace('/show-post')
    }
});

function post_upload() {
    let title = $("#title_box").val()
    let content = $("#contents_box").val()
    let filename = null
    let file = null

    // 파일명 출력
    let fileInput = document.getElementsByClassName("file");

    if (fileInput.length > 0) {
        filename = fileInput[0].files[0].name
        file = $('#file')[0].files[0]
    }

    let data = {
        "title": title,
        "content": content,
        "file": filename
    }
    if ($("#title_box").val().length == 0) {
        alert("제목을 입력하세요!");
        $("#title_box").focus();
        return false;
    }
    if ($("#contents_box").val().length == 0) {
        alert("내용을 입력하세요!");
        $("#contents_box").focus();
        return false;
    } else {
        $.ajax({
            type: "POST",
            url: "/posts",
            data: JSON.stringify(data),
            cache: false,
            contentType: 'application/json; charset=utf-8',
            processData: false,
            success: function (response) {
                alert("게시글 작성 성공!")
                location.replace('/show-post')
            }
        })
    }

}