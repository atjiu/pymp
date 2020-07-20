<#include "../../layout/layout.ftlh"/>
<@html page_title="用户列表" page_tab="user">
    <ol class="breadcrumb">
        <li><a href="/">首页</a></li>
        <li><a href="/wechat/user/list">用户</a></li>
        <li class="active">列表</li>
    </ol>
    <p>
        <button class="btn btn-xs btn-primary" disabled id="batch-btn" onclick="batchUpdate()">批量同步</button>
    </p>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th><input type="checkbox" id="all_checked"/></th>
            <th>头像</th>
            <th>openid</th>
            <th>关注时间</th>
            <th>最后更新时间</th>
            <th>状态</th>
            <th>昵称</th>
            <th>姓别</th>
            <th>地区</th>
            <th>标签</th>
            <th>备注</th>
            <th>OP</th>
        </tr>
        </thead>
        <tbody>
        <#list page.content as user>
            <tr>
                <td>
                    <input type="checkbox" name="openids" value="${user.fromUserName!}">
                </td>
                <td id="user-info-headimgurl-td-${user_index}" data="${user.userInfo!}"></td>
                <td>${user.fromUserName!}</td>
                <td>${user.createTime!}</td>
                <td>${user.lastUpdateTime!}</td>
                <td <#if user.status == 'subscribe'>class="success" <#else>class="danger"</#if>>
                    <#if user.status == 'subscribe'>
                        订阅
                    <#else>
                        未订阅
                    </#if>
                </td>
                <td id="user-info-nickname-td-${user_index}"></td>
                <td id="user-info-sex-td-${user_index}"></td>
                <td id="user-info-address-td-${user_index}"></td>
                <td id="user-info-tag-td-${user_index}">
                    <select id="tag" class="form-control" onchange="batchTags('${user.fromUserName!}', this)">
                        <option value="">选择标签</option>
                        <#list _tags as tag>
                            <option value="${tag.id?c}">${tag.name!}</option>
                        </#list>
                    </select>
                </td>
                <td id="user-info-remark-td-${user_index}" contenteditable="true" data=""
                    onblur="remarkUser('${user.fromUserName!}', this)"></td>
                <td>
                    <button class="btn btn-xs btn-warning" onclick="updateUser(['${user.fromUserName!}'])">同步
                    </button>
                </td>
            </tr>
        <#if user.userInfo??>
            <script>
                $(function () {
                    let userinfo = JSON.parse($("#user-info-headimgurl-td-${user_index}").attr("data"));
                    if (userinfo.sex === 1) {
                        userinfo.sex = "男";
                    } else if (userinfo.sex === 2) {
                        userinfo.sex = "女";
                    } else if (userinfo.sex === 0) {
                        userinfo.sex = "未知";
                    }
                    $("#user-info-headimgurl-td-${user_index}").html('<img src="' + userinfo.headimgurl + '" style="width: 32px; height: 32px; max-width: 100%;" alt="">');
                    $("#user-info-nickname-td-${user_index}").html(userinfo.nickname);
                    $("#user-info-sex-td-${user_index}").html(userinfo.sex);
                    $("#user-info-address-td-${user_index}").html(userinfo.country + ' - ' + userinfo.province + ' - ' + userinfo.city);
                    var tagid_list = userinfo.tagid_list;
                    if (tagid_list.length > 0) {
                        $("#user-info-tag-td-${user_index}").find("select").val(tagid_list[0]);
                    }
                    $("#user-info-remark-td-${user_index}").html(userinfo.remark);
                    $("#user-info-remark-td-${user_index}").attr("data", userinfo.remark);
                });
            </script>
        </#if>
        </#list>
        </tbody>
    </table>
    <nav>
        <ul class="pager">
            <#if page.first>
                <li class="disabled"><a href="javascript:;">上一页</a></li>
            <#else>
                <li><a href="/wechat/user/list?pageNo=${page.number-1}">上一页</a></li>
            </#if>
            <#if page.last>
                <li class="disabled"><a href="javascript:;">下一页</a></li>
            <#else>
                <li><a href="/wechat/user/list?pageNo=${page.number+1}">下一页</a></li>
            </#if>
        </ul>
    </nav>
    <script type="text/javascript">
        $(function () {
            $("#all_checked").click(function () {
                if ($(this).is(":checked")) {
                    $("input[name='openids']").each(function (i, v) {
                        $(v).prop("checked", true);
                    });
                    $("#batch-btn").prop("disabled", false);
                } else {
                    $("input[name='openids']").each(function (i, v) {
                        $(v).prop("checked", false);
                    });
                    $("#batch-btn").prop("disabled", true);
                }
            });
            $("input[name='openids']").click(function () {
                var a = $("input[name='openids']:checked");
                var openids = [];
                $.each(a, function (i, v) {
                    openids.push(v.value);
                });
                if (openids.length > 0) {
                    $("#batch-btn").prop("disabled", false);
                } else {
                    $("#batch-btn").prop("disabled", true);
                }
                if (openids.length === $("input[name='openids']").length) {
                    $("#all_checked").prop("checked", true);
                }
                if (openids.length > 0 && openids.length !== $("input[name='openids']").length) {
                    $("#all_checked").prop("checked", false);
                }
            });
        });

        function batchUpdate() {
            let checked_box = $("input[name='openids']:checked");
            var openids = [];
            $.each(checked_box, function (i, v) {
                openids.push(v.value);
            });
            if (openids.length > 0) {
                updateUser(openids);
            }
        }

        function batchTags(openid, _this) {
            var tagId = $(_this).val();
            var url = '';
            if (tagId) {
                url = "/wechat/user/tags";
            } else {
                url = "/wechat/user/untags";
            }
            var index = layer.load();
            $.post(url, {
                openids: [openid],
                tagId: tagId
            }).done(function (result) {
                layer.close(index);
                layer.msg('成功', {time: 1500});
            }).fail(function (err) {
                layer.close(index);
                layer.msg(err.responseText, {time: 1500});
            });
        }

        function updateUser(openids) {
            $.post("/wechat/user/update", {
                openids: openids
            }).done(function (result) {
                layer.msg('更新成功', {time: 1500}, function () {
                    location.reload();
                });
            }).fail(function (err) {
                layer.msg(err.responseText, {time: 1500});
            });
        }

        function remarkUser(openid, _this) {
            var origin_remark = $(_this).attr("data") || "";
            var remark = $(_this).text();
            if (remark && remark !== origin_remark) {
                var index = layer.load();
                $.post("/wechat/user/updateremark", {
                    openid: openid,
                    remark: remark
                }).done(function (result) {
                    layer.close(index);
                    layer.msg('成功', {time: 1500});
                }).fail(function (err) {
                    layer.close(index);
                    layer.msg(err.responseText, {time: 1500});
                });
            }
        }

    </script>
</@html>