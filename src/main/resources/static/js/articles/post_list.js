$(document).ready(function () {
    show_post_list(0);
    show_post_best();
    let page = 1;
});

//post 보여주기
function display(articles){
    let list_num = 0

    for (let i = 0; i < articles.length; i++) {
        const username = articles[i]['user']['nickname']
        const title = articles[i]['title']
        const number = articles[i]['idx']
        const contents = articles[i]['content']
        const time = formatDate(articles[i]['createdAt'])
        const view = articles[i]['view']
        const card_img = articles[i]['img']

        list_num+=1

        let temp_html = `
                                 <div onclick="location.href='/posts/detail/${number}'" class="row card-post">
                                    <div class="col-lg-4">
                                        <img class="card-img" src="${card_img}" class="img-fluid rounded-start" alt="pic">
                                    </div>
                                    <div class="col-lg-8">
                                        <div class="card-content">
                                            <div class="num float-right">#${list_num}</div>
                                            <div class="post-title">${title}</div>
                                            <div class="author">${username}</div>
                                            <p class="post-content">${contents}</p>
                                            <div class="post-sub" id="time">${time}</div>
                                            <div class="view">조회수 ${view}</div>
                                        </div>
                                    </div>
                                </div>
                                 `
        $('#post-body').append(temp_html)
    }
}

function createPagination(length){
    $('#post-pagination-number').empty();

    for(let i=0;i<length;i++){
        let temp_html=`<li class="page-item"><a class="page-link" onclick="show_post_list(${i})">${i+1}</a></li>`
        $('#post-pagination-number').append(temp_html);
    }
}
//page post-list 불러오기
function show_post_list(page) {
    $('#post-body').empty();

    $.ajax({
        type: 'GET',
        url: '/post-list',
        contentType: 'application/json; charset=utf-8',
        data: {"page":page},
        success: function (response) {
            const totalPages = Object.keys(response);
            const articles = Object.values(response);


            display(articles[0]);
            createPagination(totalPages);
        }
    });
}

//best 글
function show_post_best() {
    $('#post-best').empty();

    $.ajax({
        type: 'GET',
        url: '/post-best',
        contentType: 'application/json; charset=utf-8',
        data: {},
        success: function (response) {
            const best = response;

            show_best(best);
        }
    });
}


function formatDate(date) {
    let DateYMD = date.split('T')[0];
    return DateYMD;
};


function show_best(best) {
    const username = best['user']['nickname']
    const title = best['title']
    const contents = best['content']
    const number = best['idx']
    const time = formatDate(best['createdAt'])
    const view = best['view']
    const card_img = best['img']

    const temp_html = `
                                <div onclick="location.href='posts/detail/${number}'" class="row card-post-best">
                                    <div class="col-lg-4">
                                        <img class="card-img" src="${card_img}" class="img-fluid rounded-start" alt="pic">
                                    </div>
                                    <div class="col-lg-8">
                                        <div class="card-content">
                                            <div class="num float-right" style="color: rgb(255, 70, 110);">Best</div>
                                            <div class="post-title">${title}</div>
                                            <div class="author">${username}</div>
                                            <p class="post-content">${contents}</p>
                                            <div class="post-sub" id="time">${time}</div>
                                            <div class="view">조회수 ${view}</div>
                                        </div>
                                    </div>
                                </div>                 
                     
                     `
    $('#post-best').append(temp_html)
}

function postSearch(){
    $("#post-pagination").empty();
    let category = $('#SearchSelect').val();
    let keyword = $('#post-search-keyword').val();
    $.ajax({
        type: 'GET',
        url: '/posts/search',
        data: {
            "category" : category,
            "keyword" : keyword,
        },
        success: function (response) {
            $('#post-body').empty();
            console.log(response)
            display(response)
        },
        error: function (request, status, error) {
            console.log(error);
            alert(request.responseJSON.message);
        }
    })
}