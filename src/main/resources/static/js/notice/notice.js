var ACTIONS = {
    PAGE: function (page) {
        var filter = $( '#filter' ).val();

        if($( '#filter' ).val() == "") {
            filter = " ";
        }

        $.ajax({
            type: "POST",
            contentType: "application/json",
            data: filter,
            url: "/admin/notice/list/paging/?page=" + page,
            success: function(result) {
                $("#notice-list-fragment").replaceWith(result);
            }
        });
    },
    UPLOAD_SUMMERNOTE_IMAGE_FILE: function (file, editor) {
        var data = new FormData();
        data.append("file", file);
        $.ajax({
            data: data,
            type: "POST",
            url: "/uploadSummernoteImageFile",
            contentType: false,
            processData: false,
            success: function (data) {
                $(editor).summernote('insertImage', data.url);
            }
        })
    },
    NOTICE_SAVE: function () {
        if (!$("#title").val()) {
            swal("공지사항", "제목을 입력해주세요", "warning");
            return false;
        }

        $("#notice_form").submit();
    },
    IMPORTANT_CHECK: function (item) {
        if (item.checked == true) {
            item.value = "Y";
        } else {
            item.value = "N";
        }
    },
    DELETE: function () {
        swal("공지사항", "삭제하시겠습니까?", "warning", {
            buttons: {
                confirm: {
                    text: "네",
                    value: true,
                    visible: true
                },
                cancel: {
                    text : "아니오",
                    value: false,
                    visible : true
                }
            }
        }).then((res) => {
            var noticeId = $("#noticeId").val();

            if (res) {
                $.ajax({
                    type: "POST",
                    url: "/admin/notice/delete",
                    data: JSON.stringify(noticeId),
                    contentType: "application/json",
                    success: function (res) {
                        let url = "/admin/notice/list";
                        location.replace(url);
                    }
                })
            }
        })
    },
    ONMOUSEOVER: function (noticeId) {
        $(".ntc_"+noticeId).css('text-decoration', 'underline');
    },
    ONMOUSEOUT: function (noticeId) {
        $(".ntc_"+noticeId).css('text-decoration', 'none');
    },
    COMMENT: function () {
        if (!$("#content").val()) {
            swal("공지사항", "댓글을 입력해주세요.", "warning");
            return false;
        } else {
            var noticeId = $("#noticeId").val();
            var comment = {content: $("#content").val()};

            $.ajax({
                type: "POST",
                url: "/admin/notice/comment/?id="+noticeId,
                contentType: "application/json",
                data: JSON.stringify(comment),
                success: function (res) {
                    window.location.reload();
                }
            })
        }
    },
    COMMENT_MODIFY: function (item) {
        var commentId = $(item).attr('id').replaceAll("comment-modify_", '');

        $("<textarea name='content' id='hiddenContent' rows='1' placeholder='댓글을 입력해주세요.'></textarea>").replaceAll($("#content_"+commentId));
        $(`<a href='javascript:void(0)' id='hiddenModify_${commentId}' class='modify_btn' onclick='ACTIONS.COMMENT_MODIFY_BTN(this)'>변경</a>`).replaceAll($("#comment-modify_"+commentId));
        $(`<a href='javascript:void(0)' id='hiddenCancel_${commentId}' class='delete_btn' onclick='window.location.reload()'>취소</a>`).replaceAll($("#comment-delete_"+commentId));
    },
    COMMENT_MODIFY_BTN: function (item) {
        var commentId = $(item).attr('id').replaceAll("hiddenModify_", '');
        var content = $("#hiddenContent").val();

        if (!content) {
            swal("공지사항", "댓글을 입력해주세요.").then(() => {
                $("#hiddenContent").focus();
            });

            return false;
        }

        var comment = {
            id: commentId,
            content: content
        };

        $.ajax({
            type: "POST",
            url: "/admin/notice/comment/modify",
            contentType: "application/json",
            data: JSON.stringify(comment),
            success: function (res) {
                window.location.reload();
            }
        })
    },
    COMMENT_MOUSEOVER: function (obj) {
        var commentId = obj.id;
       $("#comment_"+commentId).show();
    },
    COMMENT_COMMENT: function (item) {
        var commentId = $(item).attr('id').replaceAll("comment-btn_", '');

        var html = `
            <div style="display: block;" id="hiddenCommentForm">
                <input type="text" id="comment-comment_${commentId}" name="content" style="width: 50%" placeholder="댓글을 입력해주세요.">
                <button type="button" onclick="ACTIONS.COMMENT_SAVE(${commentId})">입력</button>
            </div>
        `;

        $("#btn_"+commentId).after(html);
    },
    COMMENT_SAVE: function (commentId) {
        var content = $("#comment-comment_"+commentId).val();
        if (!content) {
            swal("공지사항", "댓글을 입력해주세요.", "warning").then(() => {
                $("#comment-comment_"+commentId).focus();
            });

            return false;
        }

        var reply = { content: content };

        $.ajax({
            type: "POST",
            url: "/admin/notice/comment/reply/?id="+commentId,
            contentType: "application/json",
            data: JSON.stringify(reply),
            success: function (res) {
                window.location.reload();
            },
            error: function (err) {
                console.log(err)
            }
        });
    },
    COMMENT_MOUSEOUT: function (obj) {
        var commentId = obj.id;
        $("#comment_"+commentId).hide();
    },
    REPLY_MODIFY: function (item) {
        var replyId = $(item).attr('id').replaceAll("relpy-modify_", '');

        $("<textarea name='content' id='reply-hiddenContent' rows='1' placeholder='댓글을 입력해주세요.'></textarea>").replaceAll($("#reply-content_"+replyId));
        $(`<a href='javascript:void(0)' id='reply-hiddenModify_${replyId}' class='modify_btn' onclick='ACTIONS.REPLY_MODIFY_BTN(this)'>변경</a>`).replaceAll($("#relpy-modify_"+replyId));
        $(`<a href='javascript:void(0)' id='reply-hiddenCancel_${replyId}' class='delete_btn' onclick='window.location.reload()'>취소</a>`).replaceAll($("#reply-delete_"+replyId));
    },
    REPLY_MODIFY_BTN: function (item) {
        var replyId = $(item).attr('id').replaceAll("reply-hiddenModify_", '');
        var content = $("#reply-hiddenContent").val();

        if (!content) {
            swal("공지사항", "댓글을 입력해주세요.").then(() => {
                $("#reply-hiddenContent").focus();
            });

            return false;
        }

        var reply = {
            id : replyId,
            content: content
        };

        $.ajax({
            type: "POST",
            url: "/admin/notice/comment/reply/modify",
            contentType: "application/json",
            data: JSON.stringify(reply),
            success: function (res) {
                window.location.reload();
            }
        })
    }
};

obj.pageInit = function () {
    $('#summernote').summernote({
        height: 800,
        minHeight: null,
        maxHeight: null,
        focus: false,
        lang: "ko-KR",
        placeholder: '최대 2048자까지 쓸 수 있습니다',

        callback: {
            onImageUpload: function (files) {
                for (var i = files.length-1; i >= 0; i--) {
                    ACTIONS.UPLOAD_SUMMERNOTE_IMAGE_FILE(files[i], this);
                }
            },
            onPaste: function (e) {
                var clipboardData = e.originalEvent.clipboardData;
                if (clipboardData && clipboardData.items && clipboardData.items.length) {
                    var item = clipboardData.items[0];
                    if (item.kind === 'file' && item.type.indexOf('image/') !== -1) {
                        e.preventDefault();
                    }
                }
            }
        },
        toolbar: [
            // [groupName, [list of button]]
            ['fontname', ['fontname']],
            ['fontsize', ['fontsize']],
            ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
            ['color', ['forecolor','color']],
            ['table', ['table']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['height', ['height']],
            ['insert',['picture','link','video']],
            ['view', ['fullscreen', 'help']]
        ],
        fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋움체','바탕체'],
        fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72']
    });

    $("div.note-editable").on('drop',function(e){
        for(i=0; i< e.originalEvent.dataTransfer.files.length; i++){
            uploadSummernoteImageFile(e.originalEvent.dataTransfer.files[i],$("#summernote")[0]);
        }
        e.preventDefault();
    });

    $('#content').on('keyup', function () {
        if ($(this).val().length > 10) {
            $(this).val($(this).val().substring(0, 10));
        }
    })
};

$(document).ready(function () {
    if ($("#important").val() == "Y") {
        $("#important").prop('checked', true);
    } else {
        $("#important").prop('checked', false);
    }
})
