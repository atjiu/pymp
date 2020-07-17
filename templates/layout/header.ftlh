<#macro header page_tab>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">PYMP</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li <#if page_tab=="index">class="active"</#if>>
                        <a href="/">首页</a>
                    </li>
                    <li <#if page_tab=="sub_reply">class="active"</#if>>
                        <a href="/wechat/common/setting/sub_reply">关注回复</a>
                    </li>
                    <li <#if page_tab=="auto_reply">class="active"</#if>>
                        <a href="/wechat/msg_setting/auto_reply">自动回复</a>
                    </li>
                    <li <#if page_tab=="tag">class="active"</#if>>
                        <a href="/wechat/tag/list">标签管理</a>
                    </li>
                    <li <#if page_tab=="user">class="active"</#if>>
                        <a href="/wechat/user/list">用户管理</a>
                    </li>
                    <li <#if page_tab=="kfaccount">class="active"</#if>>
                        <a href="/wechat/kfaccount/list">客服管理</a>
                    </li>
                    <li <#if page_tab=="msg_setting">class="active"</#if>>
                        <a href="/wechat/msg_setting/list">消息管理</a>
                    </li>
                    <li <#if page_tab=="menu">class="active"</#if>>
                        <a href="#">菜单设置</a>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/login">登录</a></li>
                </ul>
            </div>
        </div>
    </nav>
</#macro>