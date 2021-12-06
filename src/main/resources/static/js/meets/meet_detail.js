$(document).ready(() => {
    const curUrl = window.location.href.split('/');
    const idx = curUrl[curUrl.length - 1];
    showMeetDetail(idx);
});


function showModal(idx) {
    $('#update-modal').modal('show');
    console.log(idx)
    $.ajax({
        type: 'GET',
        url: '/meet/api/meet' + idx,
        dataType: 'json',
        contentType : 'application/json; charset=utf-8',
        success: (response) => {
            const temp_html = `
                <div>
                    <div>제목: ${response.title}</div>
                    <div>내용: ${response.content}</div>
                    <div>주소: ${response.address}</div>
                    <div>날짜: ${response.date}</div>
                    <div>이미지: ${response.imgUrl}</div> 
                </div>
            `;
            $('#modal-content').append(temp_html);
        },
        error: (err) => {
            console.log(err);
        }


    })

}

function showMeetDetail(idx) {
    let comments = ""
    $.ajax({
        type: 'GET',
        url: `/meet/api/meet/${idx}`,
        success: (response) => {
            // 본문
            const temp = `
                    <div class="top_box m-auto" style="width: 80%">
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
                                <img id="content-img" src="${response.imgUrl}" height="250px" width="250px"><br>
                                <span id="contents_box_span">${response.content}</span>
                            </div>
                        </div>
                    </div>
                `;
            $('#meet-post').append(temp);

            // 수정 ui


            // 댓글
            for(let i = 0; i < response['comments'].length; i++) {
                comments += `
                    <div class="card mb-2">
                    <input type="hidden" id="comment-idx" value="${response['comments'][i].idx}" />
                    <div class="card-header bg-light">
                        <i class="fa fa-comment fa"></i> 작성자: <span id="username">${response['username']}</span>
                    </div>
                    <div class="card-body">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">
                                <div class="comment_wrote">내용: ${response['comments'][i].comment}</div>
                            </li>
                        </ul>
                    </div>
                </div>
                `;
            }
            $('#comment_list').append(comments);
        },
        error: (err) => {
            console.log(err);
        }
    })
}

function deleteMeet() {
    const id = $('#idx').val();
    console.log(id);
    $.ajax({
        type: "DELETE",
        url: "/meet/api/meet/" + id,
        dataType: 'json',
        contentType : 'application/json; charset=utf-8',
        success: (response) => {
            alert("글이 삭제 되었습니다.");
            window.location.href='/';
        },
        error: (error) => {
            console.log(error)
        }
    })
}

function saveComment() {
    const content = $('#comment_content');
    const comment = {
        "idx": $('#idx').val(),
        "comment": content.val()
    };

    $.ajax({
        type: "POST",
        url: "/meet/api/meet/comment",
        data: JSON.stringify(comment),
        contentType : 'application/json; charset=utf-8',
        success: (response) => {
            alert("댓글 저장!");
            const comment = `
                 <div class="card mb-2">
                 <input type="hidden" id="comment-idx" value="${response['idx']}" />
                    <div class="card-header bg-light">
                        <i class="fa fa-comment fa"></i> 작성자 <span id="username"></span>
                    </div>
                    <div class="card-body">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">
                                <div class="comment_wrote">${response['comment']}</div>
                            </li>
                        </ul>
                    </div>
                </div>
                `;
        $('#comment_list').append(comment);
        content.val("");
        },
        error: (error) => {
            console.log(error);
        }

    })
}
