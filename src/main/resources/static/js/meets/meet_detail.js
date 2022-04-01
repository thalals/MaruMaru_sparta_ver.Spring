$(document).ready(() => {
    const curUrl = window.location.href.split('/');
    const idx = curUrl[curUrl.length - 1];
    showMeetDetail(idx);
    // 지도 기능
    const href = '/map?id=' + idx
    $('#go-map').attr("src", href);

    if (localStorage.getItem('token')) {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('token'));
        });
    } else {
        alert('로그인을 해주세요')
        location.replace('/user/login')
    }
});


function showMeetDetail(idx) {
    $.ajax({
        type: 'GET',
        url: `/api/meet/${idx}`,
        success: (response) => {
            const temp = `
                    <div class="top_box m-auto">
                        <div class="author">
                            <div class="input-group mb-3">
                                <span class="input-group-text">작성자</span>
                                <div id="author_box" type="text" class="form-control" placeholder="" aria-label="Username"
                                     aria-describedby="basic-addon1">
                                     ${response.username}
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="title">
                        <input type="hidden" id="idx" value="${response.idx}">
                        <div class="input-group mb-3">
                            <span class="input-group-text">제목</span>
                            <div id="title_box" type="text" class="form-control" aria-label="Username"
                                 aria-describedby="basic-addon1">
                                 ${response.title}
                            </div>
                        </div>
                    </div>
        
                    <div class="contents">
                        <div class="input-group mb-3">
                            <div class="form-control content" aria-label="With textarea" id="contents_box">
                                <img id="content-img" src="${response.imgUrl}" alt="picture" height="250px" width="250px"><br>
                                <span id="contents_box_span">${response.content}</span>
                            </div>
                        </div>
                    </div>
                `;
            $('#meet-post').append(temp);

            if(response.address != '') {
                const addHTML = `
                    <div class="address mt-3">
                        <div class="input-group mb-3">
                            <span class="input-group-text">주소</span>
                            <div id="address-box" type="text" class="form-control address" aria-label="address"
                                 aria-describedby="basic-addon1">
                                ${response.address}
                            </div>
                            <button class="btn btn-primary ml-3" data-bs-toggle="modal" data-bs-target="#map-modal">지도
                            </button>
                        </div>
                    </div>
                `
                $('#address-div').append(addHTML);
            }

            // 댓글
            showComments(response.comments);
        },
        error: (err) => {
            console.log(err);
        }
    })
}

function deleteMeet() {
    const id = $('#idx').val();
    const result = confirm("해당 글을 삭제 하시겠습니까?");
    if (result) {
        $.ajax({
            type: "DELETE",
            url: `/api/meet/` + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            success: (response) => {
                alert("삭제 성공!");
                window.location.href = '/meets';
            },
            error: (request) => {
                alert(request.responseJSON.message);
            }
        })
    }
}


function updateMeet() {
    const id = $('#idx').val();

    $.ajax({
        type: "GET",
        url: `/api/meet/check`,
        data: {id: id},
        success: function (response) {
            if (response) {
                location.href = `/meet-change/` + id;
            } else {
                alert("작성자만 수정할 수 있습니다.")
                }
            },

        error: function (request, status, error) {
            alert(error);
        }
    });
}


function showComments(comments) {
    let temp_comments = "";
    const arr_comment = comments.reverse();
    arr_comment.forEach((e) => {
        temp_comments += `
                            <div class="card mb-2">
                                <div class="card-header bg-light">
                                    <i class="fa fa-comment fa"></i> 작성자: ${e.user['username']}
                                    <input id="comment-idx" hidden value="${e['idx']}">
                                        <div class="h-auto dropdown">
                                          <a class="btn btn-secondary dropdown-toggle" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
                                            Edit
                                          </a>
                                           
                                          <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                                            <li><a class="dropdown-item" onclick="showUpdateComment(${e['idx']})">수정</a></li>
                                            <li><a class="dropdown-item" onclick="deleteComment(${e['idx']})">삭제</a></li>
                                          </ul>
                                        </div>
                                </div>
                                <div class="card-body">
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item">
                                            <div class="comment_wrote">${e.comment}</div>
                                               <!--수정 댓글-->
                                               
                                             <div id="comment_input_${e['idx']}" class="d-none">
                                                <input id="comment_upvalue_${e['idx']}" type="text"  class="form-control">
                                                <button onclick="updateComment(${e['idx']})" type="button" class="h-auto btn btn-dark mt-3">수정</button>
                                             </div>
                                             
                                        </li>
                                    </ul>
                                </div>
                            </div>`;
    });
    $('#comment_list').append(temp_comments);
}


function saveComment() {
    const content = $('#comment_content');
    const id = $('#idx').val();
    if (!content.val().trim()) {
        alert("내용은 필수입니다.");
        return;
    }
    const inputData = {
        idx: id,
        comment: content.val()
    };
    $.ajax({
        type: "POST",
        url: `/api/meet/comment`,
        data: JSON.stringify(inputData),
        contentType: 'application/json; charset=utf-8',
        success: (response) => {
            alert("댓글 저장!");
            const comment = `
                            <div class="card mb-2">
                                <div class="card-header bg-light">
                                    <i class="fa fa-comment fa"></i> 작성자: ${response.user['username']}
                                    <input id="comment-idx" hidden value="${response['idx']}">
                                        <div class="h-auto dropdown">
                                          <a class="btn btn-secondary dropdown-toggle" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
                                            Edit
                                          </a>
                                           
                                          <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                                            <li><a class="dropdown-item" onclick="showUpdateComment(${response['idx']})">수정</a></li>
                                            <li><a class="dropdown-item" onclick="deleteComment(${response['idx']})">삭제</a></li>
                                          </ul>
                                        </div>
                                </div>
                                <div class="card-body">
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item">
                                            <div class="comment_wrote">${response.comment}</div>
                                               <!--수정 댓글-->
                                               
                                             <div id="comment_input_${response['idx']}" class="d-none">
                                                <input id="comment_upvalue_${response['idx']}" type="text"  class="form-control">
                                                <button onclick="updateComment(${response['idx']})" type="button" class="h-auto btn btn-dark mt-3">수정</button>
                                             </div>
                                             
                                        </li>
                                    </ul>
                                </div>
                            </div>
                `;
            content.val("");
            $('#comment_list').append(comment);
            // Todo : 내림차순 추가
        },
        error: (request) => {
            alert(request.responseJSON.message);
        }

    })
}


// api 수정
function showUpdateComment(id) {
    $("#comment_input_" + id).toggleClass('d-none')

}

// api 제작
function deleteComment(id) {
    const result = confirm("댓글을 삭제 하시겠습니까?");
    if (result) {
        $.ajax({
            type: "DELETE",
            url: `/api/meet/comment/` + id,
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(),
            success: function (response) {
                window.location.reload();
            },
            error: function (request, status, error) {
                alert(request.responseJSON.message);
            }
        })
    }
}


function updateComment(id) {
    const result = confirm("수정하시겠습니까?");
    if (result) {
        data = {
            idx: id,
            comment: $("#comment_upvalue_" + id).val()
        }
        $.ajax({
            type: "PUT",
            url: `/api/meet/comment`,
            data: JSON.stringify(data),
            contentType: 'application/json; charset=utf-8',
            success: function (response) {
                window.location.reload();
            },
            error: function (request, status, error) {
                alert(request.responseJSON.message);
            }
        })
    }
}