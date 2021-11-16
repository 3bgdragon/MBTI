var obj = {}
$(document).ready(function () {
    if (obj.pageInit) {
        obj.pageInit();
    }

    /* 테이블 체크박스 */
    $("[data-type*='checkAll']").click(function (e){
        var table = $(e.target).closest('table');
        $('td input:checkbox',table).prop('checked', this.checked);
    });

    $("input[data-type*='phone']").each(function () {
        $(this).attr("maxLength", 13);
        $(this).keypress(function (event) {
            var val = ($(this).val() || "").replace(/\D/g, "");
            var regExpPattern3 = /^([0-9]{3})\-?([0-9]{1,4})?\-?([0-9]{1,4})?\-?([0-9]{1,4})?\-?([0-9]{1,4})?/,
                returnValue = val.replace(regExpPattern3, function (a, b) {
                    var nval = [arguments[1]];
                    if (arguments[2]) nval.push(arguments[2]);
                    if (arguments[3]) nval.push(arguments[3]);
                    if (arguments[4]) nval.push(arguments[4]);
                    if (arguments[5]) nval.push(arguments[5]);
                    return nval.join("-");
                });
            $(this).val(returnValue);
        });
    });


    /* 수량 validation*/
    $("input[data-type*='qntty']").each(function () {
        $(this).keypress(function (event) {
            if ($(this).val() < 1) {
                $(this).val('');
            }
        })
    })
});

/* 전화번호 validation*/
obj.validatePhone = function () {
    var result = true;
    $("input[data-type*='phone']").each(function () {
        $(this).attr("maxLength", 13);
        $(this).keypress(function (event) {
            var val = ($(this).val() || "").replace(/\D/g, "");
            var regExpPattern3 = /^([0-9]{3})\-?([0-9]{1,4})?\-?([0-9]{1,4})?\-?([0-9]{1,4})?\-?([0-9]{1,4})?/,
                returnValue = val.replace(regExpPattern3, function (a, b) {
                    var nval = [arguments[1]];
                    if (arguments[2]) nval.push(arguments[2]);
                    if (arguments[3]) nval.push(arguments[3]);
                    if (arguments[4]) nval.push(arguments[4]);
                    if (arguments[5]) nval.push(arguments[5]);
                    return nval.join("-");
                });
            $(this).val(returnValue);
        });

        if ($(this).val().length < 13) {
            result = false;
        }
    });

    return result;
};

/*조건 validation*/
obj.validate = function () {
    var result = true;
    $("[data-type='required']").each(function () {
        if (!$(this).val()) {
            swal($(this).attr("title") + "를(을) 입력해주세요.").then((res) => {
                $(this).focus();
            });
            return result = false;
        }
    });

    return result;
};

obj.validateName = function(userNm) {
    var filter = /^[가-힣]{2,15}$/;

    if (!userNm || userNm == "") {
        return false;
    } else return filter.test(userNm);
};

/* 비밀번호 validation*/
obj.validatePassword = function (password) {
    var filter = /^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;

    if (password.length < 6) {
        return false;
    } else return filter.test(password);
};

obj.validateUserId = function (userId) {
    var filter = /^[a-zA-Z0-9!@#$%^&*_.<>?,`~]{5,20}$/;

    if(userId == "" || !userId) {
        return false;
    } else if (userId.length < 5) {
        return false;
    } else return filter.test(userId);
};

obj.validateEmail = function (email) {
    var filter = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

    if (email == "" || !email) {
        return true;
    } else  return email && filter.test(email);
};


