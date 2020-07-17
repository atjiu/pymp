<#include "../../layout/layout.ftlh"/>
<@html page_title="关注自动回复" page_tab="sub_reply">
    <ol class="breadcrumb">
        <li><a href="/">首页</a></li>
        <li><a href="/wechat/common/setting/sub_reply">关注回复</a></li>
        <li class="active">设置</li>
    </ol>
    <div class="form-group">
        <textarea name="content" id="content" cols="30" rows="10"
                  class="form-control">${subReply.content!}</textarea>
    </div>
    <div class="form-group">
        <button class="btn btn-sm btn-primary" onclick="save_sub_reply()">保存</button>
    </div>

    <script>
        function save_sub_reply() {
            $.post("/wechat/common/setting/sub_reply", {content: $("#content").val()})
                .done(function () {
                    layer.msg("保存成功", {time: 1500});
                })
                .fail(function (err) {
                    layer.msg(err.responseText, {time: 1500});
                });
        }
    </script>
</@html>