var ACTIONS = {
    UPLOAD_SUMMERNOTE_IMAGE_FILE: function (file, editor) {
        data = new FormData();
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
        }
    });

    $("div.note-editable").on('drop',function(e){
        for(i=0; i< e.originalEvent.dataTransfer.files.length; i++){
            uploadSummernoteImageFile(e.originalEvent.dataTransfer.files[i],$("#summernote")[0]);
        }
        e.preventDefault();
    });

    $('.summernote').summernote({
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
};
