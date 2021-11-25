$(document).ready(function () {
    show_post_list();
});

function show_post_list() {
    $.ajax({
        type: 'GET',
        url: '/post_list',
        data: {},
        success: function (response) {
            const articles = response['all_articles']
            const best = response['best']
            let list_num = 0
            show_best(best);
            for (let i = 0; i < articles.length; i++) {
                const username = articles[i]['username']
                const title = articles[i]['title']
                const number = articles[i]['number']
                const contents = articles[i]['contents']
                const time = formatDate(articles[i]['present_time'])
                const view = articles[i]['view']
                const card_img = articles[i]['file']
                if (articles.length > list_num)
                    list_num = list_num + 1
                else
                    list_num = list_num

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
    let newDateYear = date.substring(0, 5)
    let newDateMM = date.substring(5, 8)
    let newDateDD = date.substring(8, 11)
    return [newDateYear, newDateMM, newDateDD].join('-');
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
                                <div onclick="location.href='/detail/${number}'" class="row card-post-best">
                                    <div class="col-lg-4">
                                        <img class="card-img" src="/static/postimg/${card_img}" class="img-fluid rounded-start" alt="pic">
                                    </div>
                                    <div class="col-lg-8">
                                        <div class="card-content">
                                            <div class="num float-right" style="color: rgb(255, 70, 110);">Best</div>
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