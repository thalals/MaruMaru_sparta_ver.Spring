function sign_in() {
    let username = $("#input-username").val()
    let password = $("#input-password").val()

    let info = {
        username: username,
        password: password
    }

    if (username == "") {
        $("#help-id-login").text("아이디를 입력해주세요.")
        $("#input-username").focus()
        return;
    } else {
        $("#help-id-login").text("")
    }

    if (password == "") {
        $("#help-password-login").text("비밀번호를 입력해주세요.")
        $("#input-password").focus()
        return;
    } else {
        $("#help-password-login").text("")
    }

    $.ajax({
        type: "POST",
        url: `/login`,
        contentType: "application/json",
        data: JSON.stringify(info),
        success: function (response) {
            localStorage.setItem("token", response['token']);
            localStorage.setItem("username", response['username']);
            location.href = '/';
        },
        error: function (response) {
            alert("아이디 혹은 비밀번호를 확인해주세요.")
        }
    });
}

Kakao.init('08786d3e3c498ee2963fd6adc03a2188');

function loginWithKakao() {
    Kakao.Auth.login({
        success: function (authObj) {
            $.ajax({
                type: 'POST',
                url: `/login/kakao`,
                contentType: "application/json",
                data: JSON.stringify({'token': authObj['access_token']}),
                success: function (response) {
                    localStorage.setItem("token", response['token']);
                    localStorage.setItem("username", response['username']);
                    location.href = '/';
                }
            })
        },
        fail: function (err) {
            alert(JSON.stringify(err))
        },
    })
}

function sign_up() {
    let username = $("#input-username").val()
    let password = $("#input-password").val()
    let password2 = $("#input-password2").val()
    let info = {
        username: username,
        password: password
    }


    if ($("#help-id").hasClass("is-danger")) {
        alert("아이디를 다시 확인해주세요.")
        return;
    } else if (!$("#help-id").hasClass("is-success")) {
        alert("아이디 중복확인을 해주세요.")
        return;
    }

    if (password == "") {
        $("#help-password").text("비밀번호를 입력해주세요.").removeClass("is-safe").addClass("is-danger")
        $("#input-password").focus()
        return;
    } else if (!is_password(password)) {
        $("#help-password").text("비밀번호의 형식을 확인해주세요. 영문과 숫자 필수 포함, 특수문자(!@#$%^&*) 사용가능 8-20자").removeClass("is-safe").addClass("is-danger")
        $("#input-password").focus()
        return
    } else {
        $("#help-password").text("사용할 수 있는 비밀번호입니다.").removeClass("is-danger").addClass("is-success")
    }
    if (password2 == "") {
        $("#help-password2").text("비밀번호를 입력해주세요.").removeClass("is-safe").addClass("is-danger")
        $("#input-password2").focus()
        return;
    } else if (password2 != password) {
        $("#help-password2").text("비밀번호가 일치하지 않습니다.").removeClass("is-safe").addClass("is-danger")
        $("#input-password2").focus()
        return;
    } else {
        $("#help-password2").text("비밀번호가 일치합니다.").removeClass("is-danger").addClass("is-success")
    }

    $.ajax({
        type: 'POST',
        url: `/signup`,
        contentType: "application/json",
        data: JSON.stringify(info),
        success: function (response) {
            localStorage.setItem("token", response['token']);
            localStorage.setItem("username", response['username']);
            alert("회원가입이 완료되었습니다!!");
            $('#my-modal').toggleClass("is-active");
            reAction();
        }
    })

}

function toggle_sign_up() {
    $("#sign-up-box").toggleClass("is-hidden")
    $("#div-sign-in-or-up").toggleClass("is-hidden")
    $("#btn-check-dup").toggleClass("is-hidden")
    $("#help-id").toggleClass("is-hidden")
    $("#help-password").toggleClass("is-hidden")
    $("#help-password2").toggleClass("is-hidden")
}

function is_nickname(asValue) {
    var regExp = /^(?=.*[a-zA-Z])[-a-zA-Z0-9_.]{2,10}$/;
    return regExp.test(asValue);
}

function is_password(asValue) {
    var regExp = /^(?=.*\d)(?=.*[a-zA-Z])[0-9a-zA-Z!@#$%^&*]{8,20}$/;
    return regExp.test(asValue);
}

function check_dup() {
    let username = $("#input-username").val()
    let username_info = {
        username: username
    }
    if (username === "") {
        $("#help-id").text("아이디를 입력해주세요.").removeClass("is-safe").addClass("is-danger")
        $("#input-username").focus()
        return;
    }
    if (!is_nickname(username)) {
        $("#help-id").text("아이디의 형식을 확인해주세요. 영문과 숫자, 일부 특수문자(._-) 사용 가능. 8-14자 길이").removeClass("is-safe").addClass("is-danger")
        $("#input-username").focus()
        return;
    }
    $("#help-id").addClass("is-loading")
    $.ajax({
        type: "POST",
        url: "/signup/check-dup",
        contentType: "application/json",
        data: JSON.stringify(username_info),
        success: function (response) {
            if (response === "true") {
                $("#help-id").text("이미 존재하는 아이디입니다.").removeClass("is-safe").addClass("is-danger")
                $("#input-username").focus()
            } else {
                $("#help-id").text("사용할 수 있는 아이디입니다.").removeClass("is-danger").addClass("is-success")
            }
            $("#help-id").removeClass("is-loading")
        }
    });
}

function reAction() {
    $("#startButton").trigger("click");
    setTimeout(function () {
        $("#stopButton").trigger("click");
    }, 5000);
}


function modal_button(key) {
    if (key == 'yes') {
        // alert('프로필로 이동합니다!')
        $('#signup-submit').click()
        window.location.replace("/user/profile")

    } else if (key == 'no') {
        // alert('홈으로 이동합니다')
        window.location.replace("/")

    } else {
        $('#my-modal').toggleClass('is-active');
    }
}

