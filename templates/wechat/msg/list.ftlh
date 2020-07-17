<#include "../../layout/layout.ftlh"/>
<@html page_title="消息列表" page_tab="msg_setting">
    <ol class="breadcrumb">
        <li><a href="/">首页</a></li>
        <li><a href="/wechat/msg_setting/list">消息</a></li>
        <li class="active">列表</li>
    </ol>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>类型</th>
            <th>时间</th>
            <th>发起者</th>
            <th>OP</th>
        </tr>
        </thead>
        <tbody>
        <#list page.content as msg>
            <tr>
                <td>${msg.id?c}</td>
                <td>${msg.msgType!}</td>
                <td>${msg.createTime!}</td>
                <td>${msg.fromUserName!}</td>
                <td>
                    <button class="btn btn-xs btn-primary" onclick="replyThis()">回复</button>
                </td>
            </tr>
            <#if msg.msgType != "event">
                <tr>
                    <td colspan="5">
                        <#if msg.msgType == "text">
                            ${msg.content!}
                        <#elseif msg.msgType == "link">
                            <a href="${msg.content!}" target="_blank">${msg.content!}</a>
                        <#elseif msg.msgType == "image">
                        <img src="/loadImage?id=${msg.id?c}" style="width: 300px;" alt="">
                        <#elseif msg.msgType == "video" || msg.msgType == "shortvideo">
                            <video width="500" height="300" controls>
                                <source src="/static/media/${msg.mediaId!}.mp4" type="video/mp4">
                                Your browser does not support the video tag.
                            </video>
                        <#elseif msg.msgType == "voice">
                            <audio controls>
                                <source src="/static/media/${msg.mediaId!}.mp3" type="audio/mp3">
                                Your browser does not support the audio element.
                            </audio>
                        <#elseif msg.msgType == "location">
                        <#if _mapKey??>
                            <div id="location-${msg_index}" style="width: 500px; height: 300px;"></div>
                            <script>
                                var bdPoint = txMap_to_bdMap(${msg.location_X!}, ${msg.location_Y!});
                                // 百度地图API功能
                                var map = new BMap.Map("location-${msg_index}");
                                var point = new BMap.Point(bdPoint.lng, bdPoint.lat);
                                map.centerAndZoom(point, 15);
                                map.enableScrollWheelZoom(true);

                                var marker = new BMap.Marker(point);
                                map.addOverlay(marker);
                            </script>
                        </#if>
                        </#if>
                    </td>
                </tr>
            </#if>
        </#list>
        </tbody>
    </table>
    <nav>
        <ul class="pager">
            <#if page.first>
                <li class="disabled"><a href="javascript:;">上一页</a></li>
            <#else>
                <li><a href="/wechat/msg_setting/list?pageNo=${page.number-1}">上一页</a></li>
            </#if>
            <#if page.last>
                <li class="disabled"><a href="javascript:;">下一页</a></li>
            <#else>
                <li><a href="/wechat/msg_setting/list?pageNo=${page.number+1}">下一页</a></li>
            </#if>
        </ul>
    </nav>
    <script type="text/javascript">
        function replyThis() {

        }
    </script>
</@html>