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
            for(let i = 0; i < responese.length; i++) {
                temp += `
                    <div>
                        <div>${responese[i].createdAt}</div>
                        <div>${responese[i].modifiedAt}</div>
                        <div>${responese[i].title}</div>
                        <img class="img" src="${responese[i].imgUrl}" />
                        <div>${responese[i].address}</div>
                        <div>${responese[i].content}</div>
                        <div>${responese[i].date}</div>
        
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