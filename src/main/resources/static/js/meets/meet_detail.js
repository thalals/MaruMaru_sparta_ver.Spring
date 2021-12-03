$(document).ready(() => {
    const curUrl = window.location.href.split('/');
    const idx = curUrl[curUrl.length - 1];
    showMeetDetail(idx);
});

function showMeetDetail(idx) {
    let comments = ""
    $.ajax({
        type: 'GET',
        url: `/meet/api/meet/${idx}`,
        success: (response) => {
            const temp = `
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

            for(let i = 0; i < response['comments'].length; i++) {
                comments += `
                    <div class="card mb-2">
                    <div class="card-header bg-light">
                        <i class="fa fa-comment fa"></i> 작성자: <span id="username"></span>
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
    const id = $('#idx').val();
    console.log(id);
    $.ajax({
        type: "POST",
        url: "/meet/api/meet/comment",
        data: {idx: id, comment: $('#comment_content').val()},
        dataType: 'json',
        contentType : 'application/json; charset=utf-8',
        success: (response) => {
            alert("댓글 저장!");
            window.location.href='/';
        },
        error: (error) => {
            console.log(error)
        }

    })
}
