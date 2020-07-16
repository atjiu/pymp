<#--<#include "../../layout/layout-nohead.ftlh"/>-->
<#--<@html>-->
<#--    <br>-->
<#--    <div class="form-group">-->
<#--        <input type="text" disabled id="id" name="id" value="${id!c}" class="form-control">-->
<#--        <input type="text" id="name" name="name" value="${name!}" class="form-control">-->
<#--    </div>-->
<#--    <div class="form-group">-->
<#--        <button class="btn btn-sm btn-primary" onclick="submitTagUpdate()">更新</button>-->
<#--    </div>-->
<#--    <script>-->
<#--        function submitTagUpdate() {-->
<#--            $.post("/wechat/tag/update", {-->
<#--                id: ${id!c},-->
<#--                name: $("#name").val(),-->
<#--            }, function () {-->
<#--                layer.msg('修改成功', {time: 1000}, function () {-->
<#--                    var index = parent.layer.getFrameIndex(window.name);-->
<#--                    parent.location.reload();-->
<#--                    parent.layer.close(index);-->
<#--                });-->
<#--            });-->
<#--        }-->
<#--    </script>-->
<#--</@html>-->