<#macro html page_title page_tab>
    <!doctype html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>${page_title}</title>

        <link href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">

        <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <script src="https://cdn.bootcdn.net/ajax/libs/layer/3.1.1/layer.min.js"></script>
        <#if _mapKey??>
            <script type="text/javascript" src="//api.map.baidu.com/api?v=2.0&ak=${_mapKey!}"></script>
            <script>
                function txMap_to_bdMap(lat, lng) {
                    pi = 3.14159265358979324 * 3000.0 / 180.0;
                    x = lng;
                    y = lat;
                    var z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);
                    var theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);
                    lng = z * Math.cos(theta) + 0.0065;
                    lat = z * Math.sin(theta) + 0.006;
                    return {'lng': lng, 'lat': lat};
                }
            </script>
        </#if>
    </head>
    <body>
    <#include "./header.ftlh"/>
    <@header page_tab=page_tab/>
    <div class="container">
        <#nested />
    </div>
    </body>
    </html>
</#macro>