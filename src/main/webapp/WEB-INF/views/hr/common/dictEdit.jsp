<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-gift"></i>字典组管理
        </div>
    </div>
    <div class="portlet-body">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-3">
                    <div class="list-group js_dict_list">
                        <c:forEach items="${dictList}" var="dict">
                            <a href="javascript:void(0)" class="list-group-item" data-id="${dict.id}">${dict.dictName}</a>
                        </c:forEach>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="list-group js_dict_data_list">

                    </div>
                    <button type="button" class="btn btn-default js_save_dictData hidden">保存</button>
                </div>
            </div>
        </div>


    </div>
</div>
<script>
    $(document).ready(function () {
            $("div.js_dict_list a").on('click',function () {
               $(this).addClass('active').siblings().removeClass('active');
                getDictData($(this).data('id'));
            });

        /**
         * 获取字典数据
         * @param id
         */
        var getDictData = function (id) {
                $.post('<c:url value="/hr/hrDict/getDictData"/>',{dictId:id},function (result) {
                    var list = $('div.js_dict_data_list');
                    list.empty();
                    $(result).each(function (k, v) {
                        var a = $('<a href="javascript:void(0)" class="list-group-item" data-id="'+v.id+'">'+v.dictDataKey+'<i class="fa fa-close pull-right"></i></a>');
                        list.append(a);
                    });
                    list.append('<a href="javascript:void(0)" class="list-group-item  js_add text-center"><i class="fa fa-plus"></i></a>');
                    $('button.js_save_dictData').removeClass('hidden');
                },'json');
            }

            $('div.js_dict_data_list').on('click','a.js_add',function () {
                $(this).before('<a href="javascript:void(0)" class="list-group-item" ><div class="input-group"><input type="text" class="form-control"><span class="input-group-btn"><button class="btn btn-default" type="button">x</button></span></div></a>');
            }).on('click','button',function () {
                $(this).parents('a').remove();
            }).on('click','i.fa-close',function () {
                var a =$(this).parent();
                var _id = a.data('id');
                var dictId = $('div.js_dict_list').find('a.active').data('id');
                var index = layer.confirm("确定删除？",function () {
                    $.post('<c:url value="/hr/hrDict/deleteDictData"/>',{id:_id,dictId:dictId},function (result) {
                        if (result.success){
                            a.remove();
                            layer.close(index);
                        }else {
                            layer.msg(a.rmsg);
                        }
                    },'json');
                });
            });

        /**
         *保存
         */
        var resp = false;
        $('button.js_save_dictData').on('click',function () {
            var oldDatas = new Array();
            $('div.js_dict_data_list a[data-id]').each(function (k, v) {
                oldDatas.push($(v).text());
            });
            var newDatas = new Array();
            $('div.js_dict_data_list input[type=text]').each(function (k, v) {
                var val = $(v).val();
                if (val){
                    newDatas.push(val);
                }
            });
            if (newDatas.length==0){
                return;
            }
            newDatas = newDatas.sort();
            var flag = true;
            for(var i=0;i<newDatas.length;i++) {
                if (newDatas[i] == newDatas[i + 1]) {
                    layer.msg("数据重复：" + newDatas[i]);
                    flag = false;
                    break;
                }
            }
            if (!flag){
                return;
            }
            var _arr = new Array();
            var sum = $('div.js_dict_data_list').find('a[data-id]').length;
            $('div.js_dict_data_list input[type=text]').each(function (k, v) {
                var data = v.value;
                if (data){
                    var obj = {};
                    obj.dictDataKey = data;
                    obj.dictDataValue = sum+k+1;
                    obj.rank = sum+k+1;
                    if (oldDatas.indexOf(data)!=-1){
                        layer.msg("数据重复："+data);
                        flag = false;
                        return false;
                    }
                    _arr.push(obj);
                }
            });
            if (flag&&_arr.length>0){
                var dictId = $('div.js_dict_list').find('a.active').data('id');
                if (!resp){
                    resp = true;
                    $.post('<c:url value="/hr/hrDict/addDictData"/>',{dictDataArray:JSON.stringify(_arr),dictId:dictId},function (result) {
                        resp = false;
                        getDictData(dictId);
                    },'json');
                }
            }
        });

    });

</script>