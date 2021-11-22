// $(document).ready(function () {
//     bsCustomFileInput.init();
// })
//
// function profile_upload() {
//
//     let dogName = $("#dog_name").val()
//     let dogAge = $("#dog_age").val()
//     let dogGender = $("#dog_gender").val()
//     let dogComment = $("#dog_comment").val()
//
//
//     파일명 출력
//     let fileInput = document.getElementsByClassName("file");
//
//     사진이 들어가지 않았을 때
//     if (fileInput[0].files.length == 0) {
//         alert("사진을 넣어주세요!");
//         $("#profile_image").focus();
//         return false;
//     }
//     for (let i = 0; i < fileInput.length; i++) {
//         if (fileInput[i].files.length > 0) {
//             for (let j = 0; j < fileInput[i].files.length; j++) {
//                 let filename = fileInput[i].files[j].name //
//
//                 let file = $('#profile_image')[0].files[0]
//                 let form_data = new FormData()
//
//                 form_data.append("file_give", file)
//                 form_data.append("name_give", dogName)
//                 form_data.append("age_give", dogAge)
//                 form_data.append("gender_give", dogGender)
//                 form_data.append("comment_give", dogComment)
//                 form_data.append("filename_give", filename)
//
//
//                 if ($("#dog_name").val().length == 0) {
//                     alert("강아지 이름을 입력하세요!");
//                     $("#dog_name").focus();
//                     return false;
//                 }
//                 if ($("#dog_age").val().length == 0) {
//                     alert("강아지 나이를 입력하세요!");
//                     $("#dog_age").focus();
//                     return false;
//                 }
//                 if ($('#dog_gender').val() == 0) {
//                     alert("강아지 성별을 선택하세요!");
//                     $("#dog_gender").focus();
//                     return false;
//                 }
//                 if ($("#dog_comment").val().length == 0) {
//                     alert("강아지 소개를 입력하세요!");
//                     $("#dog_comment").focus();
//                     return false;
//                 } else {
//                     $.ajax({
//                         type: "POST",
//                         url: "/meets/profile",
//                         data:JSON.stringify(form_data),
//                         cache: false,
//                         contentType: "application/json",
//                         processData: false,
//                         success: function (response) {
//                             print(form_data)
//                             alert('프로필 등록 완료!')
//                             window.close()
//                         }
//                     })
//                 }
//             }
//         }
//     }
// }