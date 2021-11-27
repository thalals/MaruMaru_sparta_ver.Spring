let url_list = window.location.href.split('/')
const id = url_list[url_list.length - 1]

$(document).ready(function () {
    //iframe url 삽입
    let href = '/map?id=' + id
    $('#go-map').attr("src", href)
    show_post(id)
});

function showModal() {
    $('#update-modal').modal('show')
}


function show_post(id) {
    $.ajax({
        type: "GET",
        url: `/detail`,
        data: {id: id},
        success: function (response) {
            const title = response["post"].title;
            const contents = response["post"].contents;
            const address = response["post"].address;
            const img = response["post"].file;
            const number = response["post"].number;

            $("#idx").val(number);
            $("#title_box").text(title);
            $("#content-img").attr('src','/static/postimg/' + img)
            console.log(img)
            $("#contents_box").text(contents);
            $("#address-box").text(address);

            $("#update-title").val(title);
            $("#update-content").text(contents);


        },
        error: function (request, status, error) {
            alert(error);
        }
    })
}


function delete_post() {
    const idx = $("#idx").val();
    const result = confirm("정말로 삭제 하시겠습니까?");
    if (result) {
        $.ajax({
            type: "DELETE",
            url: `/detail`,
            data: {id_give: idx},
            success: function (response) {
                window.location.href = `/list`
            },
            error: function (request, status, error) {
                alert(error);
            }
        });
    } else {
        return false;
    }
}

function comment_upload() {
    const comment_input = $("#comment_content").val();
    if (comment_input.length == 0) {
        alert("댓글을 입력해주세요!");
        return;
    }
    const g_idx = $("#idx").val();
    $.ajax({
        type: "POST",
        url: `/comment`,
        data: {
            id_give: g_idx,
            comment_give: comment_input
        },
        success: function (response) {
            alert(response["msg"])
            let comment_text = ""
            const arr_comment = response["save_comment"]["comment"].reverse();
            arr_comment.forEach((e) => {
                comment_text += `
                            &nbsp;
                            <div class="card mb-2">
                                <div class="card-header bg-light">
                                    <i class="fa fa-comment fa"></i> 작성자: ${e.user}
                                </div>
                                <div class="card-body">
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item">
                                            <div class="comment_wrote">내용: ${e.comment}</div>
                                            <button onclick="comment_update()" type="button" class="btn btn-dark mt-3">수정</button>
                                            <button onclick="comment_delete()" type="button" class="btn btn-dark mt-3">삭제</button>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        `
            })
            $("#comment_content").val("")
            $(`#comment_list_${g_idx}`).html(comment_text)

        },
        error: function (request, status, error) {
            alert(error);
        }
    });
}


function comment_delete() {
    alert("삭제 기능 입니다!")
    const idx = $("#idx").val();
    // comment idx 찾아줘야함
    $.ajax({
        type: "DELETE",
        url: `/comment/${idx}`,
        data: {
            id_give: idx,
        },
        success: function (response) {
            console.log(response['result'])
        },
        error: function (request, status, error) {
            console.log(error);
        }
    })
}

function comment_update() {
    alert("수정 버튼 입니다!")
    const idx = $("#idx").val();
    // comment idx 찾아줘야함
    $.ajax({
        type: "PUT",
        url: `/comment/${idx}`,
        data: {
            id_give: idx,
        },
        success: function (response) {
            console.log(response['result'])
        },
        error: function (request, status, error) {
            console.log(error);
        }
    })
}