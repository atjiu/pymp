<#include "../../layout/layout-nohead.ftlh">
<@html>
    <br>
    <div class="form-group">
        <input type="text" class="form-control" id="kf_account" value="${kf_account!}" placeholder="客服名">
    </div>
    <div class="form-group">
        <input type="text" class="form-control" id="nickname" value="${nickname!}" placeholder="昵称">
    </div>
    <div class="form-group">
        <button class="btn btn-sm btn-primary btn-block" onclick="updateKFAccount()">更新</button>
        <button class="btn btn-sm btn-danger btn-block" onclick="closePage()">取消</button>
    </div>
    <script>
        function updateKFAccount() {
            var kf_account = $("#kf_account").val();
            var nickname = $("#nickname").val();
            if (kf_account && nickname) {
                $.post("/wechat/kfaccount/update", {
                    kf_account: kf_account,
                    nickname: nickname
                }).done(function () {
                    layer.msg("更新成功", {time: 1500}, function () {
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