<#include "../../layout/layout-nohead.ftlh">
<@html>
    <br>
    <div class="form-group">
        <select class="form-control" id="type">
            <option value="">选择匹配规则</option>
            <option value="all">all</option>
            <option value="include">include</option>
        </select>
    </div>
    <div class="form-group">
        <input type="text" class="form-control" id="keyword" placeholder="关键字">
    </div>
    <div class="form-group">
        <input type="text" class="form-control" id="replyContent" placeholder="回复内容">
    </div>
    <div class="form-group">
        <button class="btn btn-sm btn-primary btn-block" onclick="saveAutoReply()">保存</button>
        <button class="btn btn-sm btn-danger btn-block" onclick="closePage()">取消</button>
    </div>
    <script>
        function saveAutoReply() {
            var type = $("#type").val();
            var keyword = $("#keyword").val();
            var replyContent = $("#replyContent").val();
            if (type && keyword) {
                $.post("/wechat/msg_setting/auto_reply_save", {
                    type: type,
                    keyword: keyword,
                    replyContent: replyContent
                }).done(function () {
                    layer.msg("创建成功", {time: 1500}, function () {
                        closePage();
                    });
                }).fail(function (err) {
                    layer.msg(err.responseText, {time: 1500});
                })
            }
        }

        function closePage() {
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); //再执行关闭
        }
    </script>
</@html>