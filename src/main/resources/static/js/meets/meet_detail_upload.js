$(document).ready(function () {
    const curUrl = window.location.href.split('/');
    const idx = curUrl[curUrl.length - 1];
    showUpload(idx);
});

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
});

function showUpload(idx) {
    $.ajax({
        type: 'GET',
        url: '/meet/api/meet/' + idx,
        success: function (response) {
            const temp_html = `<div class="half">
                                    <div class="name">
                                        <label for="name">행사 이름</label>
                                        <input type="text" id="name" placeholder="제목을 입력해주세요" value="${response.title}">
                                    </div>
                                    <div class="address">
                                        <label for="address">행사 장소</label>
                                        <input onclick="execDaumPostcode()" type="text" id="address" placeholder="주소를 입력해주세요" value="${response.address}">
                                        <input type="text" id="detailAddress" placeholder="상세주소를 입력해주세요" value="${response.address}">
                                        
                                    </div>
                                    <div class="date">
                                        <label for="date">행사 날짜</label>
                                        <input type="text" id="datepicker" placeholder="날짜를 선택해주세요" value="${response.date}">
                                    </div>
                                </div>
                                <div class="half">
                                    <div class="message">
                                        <label for="message">
                                            <span>내용</span>
                                            <span class="point">*</span>
                                            <span class="length">(<span>0</span> / 3000)</span>
                                        </label>
                                        <textarea id="message" placeholder="내용을 입력주해세요">${response.content}</textarea>
                                    </div>
                                    <div class="action">
                                        <button onclick="saveUpload(${response.idx})" type="button">저장</button>
                                    </div>
                                </div>`
            $('#content').append(temp_html)
        },
        error: (err) => {
            console.log(err);
        }
    });
}


function saveUpload(idx) {
    const data = {
        title: $('#name').val(),
        content: $('#message').val(),
        address: $('#address').val(),
        date: $('#datepicker').val(),
    }
    $.ajax({
        type: "PUT",
        url: `/meet/api/meet/` + idx,
        data: JSON.stringify(data),
        contentType : 'application/json; charset=utf-8',
        success: function (response) {
            alert("수정 완료!")
            window.location.href = `/meet/` + idx;
        },
        error: function (request, status, error) {
            alert(error);
        }
    });
}

function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById("address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailAddress").focus();
        }
    }).open();
}
