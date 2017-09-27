<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">报表统计公司绑定设置</h3>
    </div>
    <div class="panel-body">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-4">
                    <div class="list-group js_group_list">
                        <c:forEach items="${baseData}" var="data">
                            <a href="javascript:void(0)" class="list-group-item" data-id="${data.id}" >${data.name}</a>
                        </c:forEach>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="list-group js_child_data_list">

                    </div>
                </div>
                <div class="col-md-4">
                    <div class="list-group js_child_data_list2">

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>

    $(document).ready(function () {

        $("div.js_group_list a").on('click', function () {
            $(this).addClass('active').siblings().removeClass('active');
            getDictData('div.js_child_data_list',$(this).data('id'));
        });

        $("div.js_child_data_list").on('click','a',function () {
            $('div.js_child_data_list2').empty();
            if($(this).data('isparent')){
                getDictData('div.js_child_data_list2',$(this).data('id'));
            }
        });

        var getDictData = function (container,id) {
            $.post('<c:url value="/hr/reports/getBaseBindDataByPid"/>',{parentId:id},function (result) {
                var list = $(container);
                list.empty();
                $(result).each(function (k, v) {
                    var a = $('<a href="javascript:void(0)" class="list-group-item" data-id="'+v.id+'" data-isparent="'+v.isParent+'">'+v.name+'</a>');
                    list.append(a);
                });
            },'json');
        }

    });

</script>