<#include "../../layout/layout.ftlh"/>
<@html page_title="客服列表" page_tab="kfaccount">
    <ol class="breadcrumb">
        <li><a href="/">首页</a></li>
        <li><a href="/wechat/user/list">客服</a></li>
        <li class="active">列表</li>
    </ol>
    <p class="pull-right">
        <button class="btn btn-sm btn-primary" onclick="kfadd()">添加客服</button>
    </p>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>头像</th>
            <th>客服ID/th>
            <th>客服名</th>
            <th>客服昵称</th>
            <th>绑定微信号</th>
            <th>邀请过期时间</th>
            <th>邀请状态</th>
            <th>OP</th>
        </tr>
        </thead>
        <tbody>
        <#list list as kf>
            <tr>
                <td><img src="${kf.kf_headimgurl!}" style="width: 32px; height:32px;" alt=""></td>
                <td>${kf.kf_id!}</td>
                <td>${kf.kf_account!}</td>
                <td>${kf.kf_nick!}</td>
                <td>${kf.kf_wx!}</td>
                <td>${kf.invite_expire_time!}</td>
                <td>${kf.invite_status!}</td>
                <td>
                    <button class="btn btn-xs btn-primary" onclick="inviteworker(['${kf.kf_account!}'])">邀请
                    </button>
                    <button class="btn btn-xs btn-warning" onclick="kfupdate(['${kf.kf_account!}', '${kf.kf_nick!}'])">
                        更新
                    </button>
                    <button class="btn btn-xs btn-danger" onclick="kfdelete(['${kf.kf_account!}'])">删除
                    </button>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
    <script type="text/javascript">
        function kfdelete(kf_account) {
            if (confirm("确定要删除这个客服吗？")) {
                $.get("/wechat/kfaccount/delete?kf_account=" + kf_account)
                    .done(function () {
                        layer.msg("删除成功", {time: 1500});
                    })
                    .fail(function (err) {
                        layer.msg("删除失败，失败信息：" + err.responseText, {time: 1500});
                    });
            }
        }

        function inviteworker(kf_account) {
            var kf_wx = layer.prompt("输入微信号");
            if (kf_wx) {
                $.post('/wechat/kfacccount/inviteworker', {
                    kf_account: kf_account,
                    invite_wx: kf_wx
                }).done(function () {
                    layer.msg("邀请已发出，等待对方同意.", {time: 1500});
                }).fail(function (err) {
                    layer.msg("发送邀请失败，失败信息：" + err.responseText, {time: 1500});
                })
            }
        }

        function kfadd() {
            layer.open({
                title: "添加客服",
                type: 2,
                area: ['500px', '300px'],
                fixed: false, //不固定
                maxmin: true,
                content: '/wechat/kfaccount/add'
            });
        }

        function kfupdate() {
            layer.open({
                title: "更新客服",
                type: 2,
                area: ['500px', '300px'],
                fixed: false, //不固定
                maxmin: true,
                content: '/wechat/kfaccount/add'
            });
        }
    </script>
</@html>