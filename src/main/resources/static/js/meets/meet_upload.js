$(document).ready(function () {
    if (localStorage.getItem('token')) {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('token'));
        });
    } else {
        alert('로그인을 해주세요')
        location.replace('/meets')
    }
});

function setThumbnail(event) {
    // Todo : 파일 1개로 제한하기
    var reader = new FileReader();
    reader.onload = function (event) {
        var img = document.createElement("img");
        img.setAttribute("src", event.target.result);
        img.setAttribute("width", 350);
        document.querySelector("div#image_container").appendChild(img);
    }
    reader.readAsDataURL(event.target.files[0]);
};


$(function () {
    $("#datepicker").datepicker({
        dateFormat: "yy-mm-dd",
        changeMonth: true,
        changeYear: true,
        nextText: '다음 달',
        prevText: '이전 달',
        dayNames: ['일요알', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
        dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
        monthNamesShort: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
        monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
    });
    $("#anim").on("change", function () {
        $("#datepicker").datepicker("option", "showAnim", $(this).val());
    });
})

function saveMeet() {
    // TODO: 유효성 체크
    if (!$('#name').val() || !$('#message').val()) {
        alert("제목과 내용은 필수입니다.");
        return;
    }


    const formData = new FormData();
    if (typeof $("#img")[0].files[0] != 'undefined') {
        formData.append("img", $("#img")[0].files[0]);
    }
    formData.append("title", $('#name').val());
    formData.append("content", $('#message').val());
    formData.append("address", $('#address').val());
    formData.append("date", $('#datepicker').val());

    $.ajax({
        type: "POST",
        url: "/meet/api/meets",
        processData: false,
        contentType: false,
        data: formData,
        success: function (responese) {
            alert("작성 완료!.")
            location.href = '/meets';
        },
        error: function (err) {
            console.log("err:", err)
        }
    })
}