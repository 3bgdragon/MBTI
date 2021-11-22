var pageName = "userList";
obj.pageInit = function () {
    $("#userId").change(function () {
        $("#valid_id").val("N");
    })
}

function check(element) {
    var checkbox = document.getElementsByName("chk");
    checkbox.forEach((cb) => {
        cb.checked = false;
    });

    element.checked = true;
}

function validate_email() {
    var userEmail = $("#email").val();
    if (!obj.validateEmail(userEmail)) {
        swal("회원관리", "이메일를 확인해주세요.\n( 허용 특수 문자 : ! @ # $ % ^ & * _ . < > ? , ` ~ )", "warning").then(() => {
            $("#userEmail").focus();
        });
        $.ajax({
            url: "admin/member/getMemberEmail",
            type: "POST",
            data: JSON.stringify(userEmail),
            dataType: "json",
            contentType: "application/json",
        }).done(function (res) {
            console.log("res",res);
            if(res) {
                $("#valid_email").val("Y");
                swal("회원관리", "사용가능한 이메일 입니다.", "success");
            } else {
                swal("회원관리", "이미 존재하는 이메일 입니다.", "warning");
            }
        })
        /*return false;*/
    } else {
        console.log("userEmail",userEmail);
        $.ajax({
            url: "admin/member/getMemberEmail",
            type: "POST",
            data: JSON.stringify(userEmail),
            dataType: "json",
            contentType: "application/json",
        }).done(function (res) {
            console.log("res",res);
            if(res) {
                $("#valid_email").val("Y");
                swal("회원관리", "사용가능한 이메일 입니다.", "success");
            } else {
                swal("회원관리", "이미 존재하는 이메일 입니다.", "warning");
            }
        })
    }
}

function validate() {
    var result = true;
    $("[data-type*='required']").each(function () {
        if (!$(this).val()) {
            if ($(this).attr("type") == "password" && $("#modify").val() == "Y") return;
            swal("회원관리", $(this).attr("title") + "를(을) 입력해주세요.", "warning").then((res) => {
                $(this).focus();
            });
            return result = false;
        } else if ($(this).attr("type") == "password") {
            var password = $("input[type='password']");
            if (password[0].value != password[1].value) {
                swal("회원관리", "패스워드가 일치하지 않습니다.", "warning").then(() => {
                    password[1].focus();
                });
                return result = false;
            } else if (!obj.validatePassword(password.val())) {
                swal("회원관리", "비밀번호를 확인해주세요.", "warning").then((res) => {
                    $("#password").focus();
                });
                return result = false;
            } else if (!obj.validateEmail($("#email").val())) {
                swal("회원관리", "이메일을 확인해주세요.", "warning").then((res) => {
                    $("#email").focus();
                });
                return result = false;
            } else if ($("#mbti").val() == "none") {
                swal("회원관리", "MBTI을 확인해주세요.", "warning").then((res) => {
                    $("#mbti").focus();
                });
                return result = false;
            }
        }}
    );

    return result;
}

function save() {
    var valid_email = $("#valid_email").val();
    if(validate()) {
        if(valid_email == "N") {
            swal("회원관리", "이메일 중복확인을 해주세요.", "warning").then(() => {
                $("#valid_email").focus();
            });
            return false;
        } else if (valid_email == "Y") {
            swal("회원관리", "회원가입이 완료되었습니다.", "success").then(() => {
                $("#form").submit();
            });
        }
    }
}

function modify() {
        if(validate()) {
            swal("회원관리", "회원수정이 완료되었습니다.", "success").then(() => {
                $("#form").submit();
            });
        }
}

function userListPaging(page) {
    filter = $( '#filter' ).val();
    if($( '#filter' ).val() == "") {
        filter = " ";
    }
    $.ajax({
        type: "POST",
        contentType: "application/json",
        data: filter,
        url: "/admin/member/list/paging/?page=" + page,
        success: function(result) {
            $("#userListFrag").replaceWith(result);
        },
        error: function (request, status, error){
        }
    });
}


function deleteUser() {
    var checkedList = $('td input:checkbox:checked');
    var idStr = "";

    if(checkedList.length == 0) {
        swal("회원관리", "삭제할 행을 선택해주세요.", "warning");
        return false;
    } else {
        swal("회원관리", "삭제하시겠습니까?", "warning", {
            buttons: {
                confirm: {
                    text: "네",
                    value: true,
                    visible: true
                },
                cancel: {
                    text: "아니오",
                    value: false,
                    visible : true
                }
            }
        }).then((res) => {
            if(res) {
                checkedList.each(function () {
                    idStr += $(this).attr("id").replaceAll("chk_", "") +',';
                });
                idStr = idStr.substr(0, idStr.length-1);

                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: "/admin/user/deleteCheck/?id=" + idStr,
                    success: function(result) {
                        if(result == "N") {
                            swal("회원관리", "미반납인 도서가 있어 탈퇴가 불가능합니다.", "warning");
                            return false;
                        } else {
                            swal("회원관리", "회원탈퇴가 완료되었습니다.", "warning").then(() => {
                                $("#idStr").val(idStr);
                                $("#form").submit().then(() => {
                                    window.location.reload();
                                })
                            });
                        }
                    },
                    error: function (request, status, error){
                    }
                });
            }
        });
    }
}

function search() {
    var filter = $("#filter").val();
    if(filter.length <= 0) {
        swal("회원관리", "검색어를 입력해주세요.", "warning").then(() => {
            $("#filter").focus();
        });
    } else {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            data: filter,
            url: "/admin/member/list/paging/?page=" + 1,
            success: function(result) {
                $("#userListFrag").replaceWith(result);
            },
            error: function (request, status, error){
            }
        });
    }

}






