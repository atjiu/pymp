<#include "../../layout/layout.ftlh"/>
<@html page_title="标签列表" page_tab="tag_list">
    <ol class="breadcrumb">
        <li><a href="/">首页</a></li>
        <li><a href="/wechat/tag/list">标签</a></li>
        <li class="active">列表</li>
    </ol>
    <p class="pull-right">
        <button class="btn btn-xs btn-primary" onclick="createTag()">创建标签</button>
    </p>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>id</th>
            <th>名字</th>
            <th>粉丝数</th>
            <th>OP</th>
        </tr>
        </thead>
        <tbody>
        <#list tags as tag>
            <tr>
                <td>${tag.id?c}</td>
                <td>${tag.name!}</td>
                <td>${tag.count!0}</td>
                <td>
                    <button class="btn btn-xs btn-warning" onclick="updateTag(${tag.id?c}, '${tag.name!}')">更新</button>
                    <button class="btn btn-xs btn-danger" onclick="deleteTag(${tag.id?c})">删除
                    </button>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
    <script type="text/javascript">
        function updateTag(id) {
            var new_name = window.prompt("请输入新的标签名");
            if (new_name) {
                $.post("/wechat/tag/update", {
                    id: id,
                    name: new_name,
                }).done(function () {
                    layer.msg('修改成功', {time: 1500}, function () {
                        location.reload();
                    });
                }).fail(function (err) {
                    layer.msg(err.responseText, {time: 1500});
                });
            }
        }

        function createTag() {
            var new_name = window.prompt("请输入标签名");
            if (new_name) {
                $.post("/wechat/tag/create", {
                    name: new_name,
                }).done(function () {
                    layer.msg('创建成功', {time: 1500}, function () {
                        location.reload();
                    });
                }).fail(function (err) {
                    layer.msg(err.responseText, {time: 1500});
                });
            }
        }

        function deleteTag(id) {
            if (window.confirm('确定要删除这个标签吗？')) {
                $.post("/wechat/tag/delete", {
                    id: id,
                }).done(function () {
                    layer.msg('删除成功', {time: 1500}, function () {
                        location.reload();
                    });
                }).fail(function (err) {
                    layer.msg(err.responseText, {time: 1500});
                });
            }
        }
    </script>
</@html>