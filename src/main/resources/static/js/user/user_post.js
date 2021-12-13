$(document).ready(function () {

    if (localStorage.getItem('token')) {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('token'));

        });
    } else {
        alert('로그인을 해주세요')
        location.replace('/login')
    }

    $.ajax({
        type: "GET",
        url: `/user/posts`,
        contentType: 'application/json; charset=utf-8',
        data: {},
        success: function (response) {
            for(let i=0; i<response.length;i++){
                let temp_html=`
                        <div class="title"><a href="/posts/detail/${response[i]['idx']}">${response[i]['title']}</a></div>
                        <div class="author">${response[i]['user']['username']}</div>
                        <div class="date">${response[i]['createdAt']}</div>`
                $('#post_list').append(temp_html)
            }
        }
    })
})

