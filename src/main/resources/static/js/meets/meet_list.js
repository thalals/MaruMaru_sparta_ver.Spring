$(document).ready(function (){
    getMeet();
});

function getMeet() {
    $.ajax({
        type: "GET",
        url: "/meet/api/meets",
        processData: false,
        contentType: false,
        data: {},
        success: function(responese){
            let temp = ""
            let count = 0;
            for(let i = 0; i < responese.length; i++) {
                count += 1;
                temp += `                       
                                <div onclick="location.href='/meet/${responese[i].idx}'" class="row card-event">
                                    <div class="col-lg-4">
                                        <img class="card-img" src="${responese[i].imgUrl}" class="img-fluid rounded-start" alt="pic">
                                    </div>
                                    <div class="col-lg-8">
                                        <div class="card-content">
                                            <div class="num float-right">#${count}</div>
                                            <div class="evnet-title">${responese[i].title}</div>
                                            <div class="author">작성자</div>
                                            <p class="event-content">${responese[i].content}</p>
                                            <div class="event-sub" id="time">${responese[i].date}</div>
                                            <div class="view">조회수 ${responese[i].address}</div>
                                        </div>
                                    </div>
                                </div>
                `;
                $('#event-body').html(temp);
            }
        },
        error: function(err){
            console.log("err:", err);
        }
    })
}