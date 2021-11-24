$(document).ready(function () {
    bsCustomFileInput.init();
})

function address_input() {
    new daum.Postcode({
        oncomplete: function (data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.

            // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 참고 항목 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if (data.buildingName !== '' && data.apartment === 'Y') {
                extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if (extraRoadAddr !== '') {
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            // document.getElementById('sample4_postcode').value = data.zonecode;
            // document.getElementById("sample4_roadAddress").value = roadAddr;
            // document.getElementById("sample4_jibunAddress").value = data.jibunAddress;
            document.getElementById("address-box").value = roadAddr;
        }
    }).open();
};

function post_upload() {
    let title = $("#title_box").val()
    let address = $("#address-box").val()
    let content = $("#contents_box").val()


    // 파일명 출력
    let fileInput = document.getElementsByClassName("file");

    //사진이 들어가지 않았을 때
    if (fileInput[0].files.length == 0) {
        alert("사진을 넣어주세요!");
        $("#file").focus();
        return false;
    }
    for (let i = 0; i < fileInput.length; i++) {
        if (fileInput[i].files.length > 0) {
            for (let j = 0; j < fileInput[i].files.length; j++) {
                let filename = fileInput[i].files[j].name //

                let file = $('#file')[0].files[0]
                let form_data = new FormData()

                form_data.append("file_give", file)
                form_data.append("title_give", title)
                form_data.append("address_give", address)
                form_data.append("content_give", content)
                form_data.append("filename_give", filename)

                if ($("#title_box").val().length == 0) {
                    alert("제목을 입력하세요!");
                    $("#title_boxl").focus();
                    return false;
                }
                if ($("#contents_box").val().length == 0) {
                    alert("내용을 입력하세요!");
                    $("#contents_box").focus();
                    return false;
                }
                if ($("#address-box").val().length == 0) {
                    alert("주소를 입력하세요!");
                    $("#address-box").focus();
                    return false;
                } else {
                    $.ajax({
                        type: "POST",
                        url: "/posts",
                        data: form_data,
                        cache: false,
                        contentType: false,
                        processData: false,
                        success: function (response) {
                            alert(response["msg"])
                            location.replace('/list')
                        }
                    })
                }
            }
        }
    }
}