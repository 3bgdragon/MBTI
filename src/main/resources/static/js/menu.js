var pageName = "";
$(function () {
    var click_id = localStorage.getItem("click");

    var target = "";
    if(click_id == undefined) {
        target = $("#admin");
    } else {
        target = $("#"+click_id);
    }

    target.parents(".sub-group").css("display", "block");
    target.parent("li").addClass("active");

    if(target.parents(".sub-group").length != 0) {
        target.parents(".sub-group").siblings("span").children("a").addClass("active_menu_parent");
    } else {
        target.parents('span').children("a").addClass("active_menu_parent");
    }

    $(".app-sidebar li").click(function () {
        $(".app-sidebar li").not(this).children('ul').slideUp();
        $(".app-sidebar li").not(this).children('span').children('a').removeClass("active_menu_parent");
        $(this).children('ul').slideToggle();
        $(this).children('span').children("a").toggleClass("active_menu_parent");
    });

    $(".sub-group a").click(function(e) {
        e.stopPropagation();
    });

    $(".app-sidebar a").click(function () {
        localStorage.setItem("click", $(this).attr("id"));
    })

    if(pageName == "dashboard") {
        $('#admin').addClass("active_menu_parent");
    } else if (pageName != "dashboard") {
        $('#admin').removeClass("active_menu_parent");
    }

    if(pageName == "noticeList")
    {
        $('#notice').addClass("active_menu_parent");
        $('#notice').parents('span').siblings(".sub-group").children().eq(0).children().addClass("active_menu_parent");
        $('#notice').parents('span').siblings(".sub-group").children().eq(1).removeClass("active");
        $('#notice').parents('span').siblings(".sub-group").children().eq(1).children().removeClass("active_menu_parent");
        $('#notice').parents('span').siblings(".sub-group").css("display", "block");
    }

    if(pageName == "noticeDetail")
    {
        $('#notice').addClass("active_menu_parent");
    }

    if(pageName == "userList") {
        $('#user_list').addClass("active_menu_parent");
        $('#user_regist').closest('li').removeClass("active");
    }

    if(pageName == "userModify") {
        $('#user_list').addClass("active_menu_parent");
        $('#user_regist').closest('li').removeClass("active");
    }

    if(pageName == "userRegist") {
        $('#user_regist').closest('li').addClass("active");
        $('#user_regist').addClass("active_menu_parent");
    }

});
