<#include "../../layout/layout.ftlh"/>
<@html page_title="自动回复" page_tab="auto_reply">
    <ol class="breadcrumb">
        <li><a href="/">首页</a></li>
        <li><a href="/wechat/msg_setting/auto_reply">自动回复</a></li>
        <li class="active">设置</li>
    </ol>
    <p class="pull-right">
        <button class="btn btn-sm btn-primary" onclick="addAutoReply()">添加</button>
    </p>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <td>ID</td>
            <td>关键字</td>
            <td>匹配规则</td>
            <td>回复内容</td>
            <td>OP</td>
        </tr>
        </thead>
        <tbody>
        <#list page.content as auto_reply>
            <tr>
                <td>${auto_reply.id?c}</td>
                <td>${auto_reply.keyword!}</td>
                <td>${auto_reply.type!}</td>
                <td>${auto_reply.replyContent!}</td>
                <td>
                    <button class="btn btn-xs btn-danger" onclick="deleteAutoReply(${auto_reply.id?c})">删除</button>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
    <nav>
        <ul class="pager">
            <#if page.first>
                <li class="disabled"><a href="javascript:;">上一页</a></li>
            <#else>
                <li><a href="/wechat/msg_setting/auto_reply?pageNo=${page.number-1}">上一页</a></li>
            </#if>
            <#if page.last>
                <li class="disabled"><a href="javascript:;">下一页</a></li>
            <#else>
                <li><a href="/wechat/msg_setting/auto_reply?pageNo=${page.number+1}">下一页</a></li>
            </#if>
        </ul>
    </nav>
    <script>
        function addAutoReply() {
            layer.open({
                title: "添加自动回复",
                type: 2,
                area: ['500px', '300px'],
                fixed: false, //不固定
                maxmin: true,
                content: '/wechat/msg_setting/auto_reply_add'
            })
        }

        function deleteAutoReply(id) {
            if (confirm("确定要删除这条自动回复吗？")) {
                $.post("/wechat/msg_setting/auto_reply", {id: id})
                    .done(function () {
                        layer.msg("删除成功", {time: 1500});
                        location.reload();
                    })
                    .fail(function (err) {
                        layer.msg(err.responseText, {time: 1500});
                        location.reload();
                    });
            }
        }
    </script>
</@html>