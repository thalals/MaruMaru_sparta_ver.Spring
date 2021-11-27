$(document).ready(function () {
    show_post_list();
});

function show_post_list() {
    $.ajax({
        type: 'GET',
        url: '/post-list',
        contentType: 'application/json; charset=utf-8',
        data: {},
        success: function (response) {
            console.log("console hi")
            console.log(response);
            const articles = response;
            // const best = response['best']
            let list_num = 0
            // show_best(best);
            for (let i = 0; i < articles.length; i++) {
                const username = "username"
                const title = articles[i]['title']
                const number = articles[i]['idx']
                const contents = articles[i]['content']
                const time = formatDate(articles[i]['createdAt'])
                const view = 3
                const card_img = articles[i]['file']
                list_num+=1

                let temp_html = `
                                 <div onclick="location.href='/detail/${number}'" class="row card-post">
                                    <div class="col-lg-4">
                                        <img class="card-img" src="/static/postimg/${card_img}" class="img-fluid rounded-start" alt="pic">
                                    </div>
                                    <div class="col-lg-8">
                                        <div class="card-content">
                                            <div class="num float-right">#${list_num}</div>
                                            <div class="post-title"><a href="/detail/${number}">${title}</a></div>
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
    });
}

function formatDate(date) {
    let DateYMD = date.split('T')[0];
    return DateYMD;
};


function show_best(best) {
    const username = best['username']
    const title = best['title']
    const contents = best['contents']
    const number = best['number']
    const time = formatDate(best['present_time'])
    const view = best['view']
    const card_img = best['file']

    const temp_html = `
                                <div onclick="location.href='posts/detail/${number}'" class="row card-post-best">
                                    <div class="col-lg-4">
                                        <img class="card-img" src="/static/postimg/${card_img}" class="img-fluid rounded-start" alt="pic">
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
    $('#post-body').append(temp_html)
}