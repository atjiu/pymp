<#include "../../layout/layout.ftlh"/>
<@html page_title="菜单" page_tab="menu">
    <ol class="breadcrumb">
        <li><a href="/">首页</a></li>
        <li><a href="/wechat/menu/config">菜单</a></li>
        <li class="active">配置</li>
    </ol>
    <p class="pull-right">
        <button class="btn btn-primary" onclick="format_json()">格式化json</button>
    </p>
    <div class="form-group">
        <textarea name="content" id="content" rows="20" class="form-control"
                  style="font-size:16px;font-family:Menlo,Monaco,Arial,serif;font-weight:500;">${menuinfo!}</textarea>
    </div>
    <div class="form-group" style="display: flex; justify-content: space-between;">
        <button class="btn btn-danger" onclick="delmenu()">删除菜单</button>
        <button class="btn btn-primary" onclick="pubmenu()">发布菜单</button>
    </div>
    <script type="text/javascript">
        function delmenu() {
            if (confirm("确定要删除菜单吗？")) {
                $.get('/wechat/menu/delete')
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

        function pubmenu() {
            if (confirm("确定要发布菜单吗？")) {
                $.post('/wechat/menu/pub', {menuJson: $("#content").val()})
                    .done(function () {
                        layer.msg("发布成功", {time: 1500});
                        location.reload();
                    })
                    .fail(function (err) {
                        layer.msg(err.responseText, {time: 1500});
                    });
            }
        }

        function format_json() {
            var json = $("#content").val();
            $.post("/format_json", {json: json})
                .done(function (data) {
                    $("#content").val(data);
                })
                .fail(function (err) {
                    layer.msg(err.responseText, {time: 1500});
                })
        }
    </script>
</@html>