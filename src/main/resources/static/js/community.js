/*
* 提交回复
* */
function post() {
    var productId = $("#product_id").val();
    var content = $("#comment_content").val();

    comment2target(productId, 1, content);
}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容~~~");
        return;
    }
    $.ajax({
        type: 'POST',
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (data) {
            if (data.code == 200) {
                window.location.reload();
            } else if (data.code == 2003) {
                var isAccepted = confirm(data.message);
                if (isAccepted) {
                    window.open("http://localhost:8080/toLogin")
                    window.localStorage.setItem("closable", true);
                } else {
                    alert(data.message);
                }
            }
        },
        dataType: "json"
    });
}

function community(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
}

function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#community-" + id);
    var collapse = e.getAttribute("data-collapse");

    // 获取二级评论展开状态
    if (collapse) {
        // 折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#community-" + id);
        if (subCommentContainer.children().length != 1) {
            // 展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        }else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index,comment) {
                    var mediaBodyElement = $("<div/>",{
                        "class": "media-body",
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.username
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaLeftElement = $("<div/>",{
                        "class": "media-left",
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": "/images/web.png"
                    }));

                    var mediaElement = $("<div/>",{
                        "class": "media",
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>",{
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                // 展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}

function showSelectTag() {
    $("#select-tag").show();
}

function selectTag(e){
    var value = e.getAttribute("data-tag")
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }

}



function postData() {
    var formData = new FormData();
    formData.append("file", $("#file")[0].files[0]);
    $.ajax({
        url: "/file/upload",
        type: "post",
        data: formData,
        processData: false, // 告诉jQuery不要去处理发送的数据
        contentType: false, // 告诉jQuery不要去设置Content-Type请求头
        dataType: 'text',
        success: function(data) {
            $("#filePath").attr("value",data.message);
        },
    });
}
