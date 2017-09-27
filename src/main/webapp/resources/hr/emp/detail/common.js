var tableOper = function () {
    var validate = function (entity) {
        var obj = $(entity);
        if (obj.val()) {
            obj.parents('td').removeClass("has-error");
            obj.nextAll('span.help-block').remove();
            return true;
        } else {
            if (!obj.parent().hasClass("has-error")) {
                obj.after('<span class="help-block help-block-error">这是必填字段</span>').parent().addClass("has-error");
            }
            return false;
        }
    }
    var root = function (tb, parent, empId,delUrl,saveFn,buildTrCallBack) {
        var tb = $(tb),
            parent = parent,
            empId = empId,
            delUrl=delUrl,
            saveFn=saveFn,
            buildTrCallBack=buildTrCallBack;

        var operRow = function (action) {
            if (action == 'add') {
                var tr = addRow();
                tr.find('select').select2();
                parent.changeFrameHeight();
                parent.operSaveBtn(0);
            } else if (action == 'del') {
                removeRow();
                parent.changeFrameHeight();
                parent.operSaveBtn(0);
            } else if (action == 'save') {
                saveTbInfo(tb,validate);
            }
        };

        var addRow = function (index) {
            var tr = $('<tr>');
            tb.find('tr:eq(0) td').each(function (k, v) {
                var type = $(v).data('type');
                var name = $(v).data('name');
                var validate = $(v).data('validate');
                var _class = $(v).data('class');
                var td = null;
                if (type == 'text') {
                    td = $('<td><input type="text" value="" class="form-control ' + (validate == undefined ? '' : validate) + '" name="' + (name == undefined ? '' : name) + '"></td>');
                } else if (type == 'datetime') {
                    td = $('<td><input type="text" value="" class="form-control ' + (validate == undefined ? '' : validate) + ' js_datetime_picker" name="' + (name == undefined ? '' : name) + '"></td>');
                    initDateTimePicker(td.find('input'));
                } else if (type == "checkbox") {
                    td = $('<td><input type="checkbox" class="checkbox ' + (_class == undefined ? '' : _class) + '" name="' + (name == undefined ? '' : name) + '" value=""></td>');
                    if (_class=="js_checkbox"){
                        td.find('input').prop('checked', true);
                    }
                } else if (type == "select") {
                    td = $('<td>');
                    td.append($(v).find('select').clone());
                    var _select = td.find('select');
                    _select.attr('name',name);
                    _select.removeClass('hidden');
                }
                tr.append(td);
            });
            if (index) {
                tb.find('tr').eq(index).hide().after(tr);
            } else {
                tb.append(tr);
            }
            return tr;
        };

        var saveTbInfo;
        if ("function" == typeof(saveFn)){
            saveTbInfo = saveFn;
        }

        var removeRow = function () {
            parent.confirmOper(function () {
                var _arr = new Array();
                tb.find('input.js_checkbox').each(function (k, v) {
                    if ($(v).prop('checked')) {
                        $(v).parents('tr').remove();
                        if (v.value) {
                            _arr.push(v.value);
                        }
                    }
                });
                if (_arr.length > 0) {
                    $.post(delUrl, {
                        detailArray: _arr.join(','),
                        empId: empId
                    }, function (result) {
                        if (result.success) {
                            layer.msg('操作成功!', function () {
                                self.location.reload();
                            })
                        }
                    }, 'json');
                }
            })
        };

        $('div.js_oper button').on('click', function () {
            var action = $(this).data('action');
            operRow(action);
        });

        /**
         * 添加行
         * @param obj
         */
        var buildTr = function (obj,buildTrCallBack) {
            var _this = $(obj);
            _this.prop('checked', false);
            var tr = addRow(_this.parents('tr').index());
            tr.find('input:eq(0)').prop('checked', true).val(_this.val());
            _this.parents('tr').find('td:gt(0)').each(function (k, v) {
                var td = tb.find('tr:eq(0)').find('td:eq('+(k+1)+')');
                var _type = $(td).data('type');
                if(_type=='datetime'||_type=='text'){
                    tr.find('td:eq('+(k + 1)+')').find('input').val($(v).text());
                }else if(_type=='select'){
                    tr.find('td:eq('+(k + 1)+')').find('select').val($(v).find('select').val()).select2();
                }
            });
            if ("function" == typeof(buildTrCallBack)){
                buildTrCallBack(_this,tr);
            }
        }

        /**
         * 删除行
         * @param obj
         */
        var reductionTr = function (obj) {
            var tr = $(obj).parents('tr');
            tr.prev().show();
            tr.remove();
        }

        var showBtn = function () {
            var _len = $('input.js_checkbox:checked').length;
            if(_len>0){
                parent.operSaveBtn(0);
            }else {
                parent.operSaveBtn(1);
            }
        }

        tb.on('click', 'input.js_checkbox', function () {
            if ($(this).val() && $(this).prop('checked')) {
                buildTr(this,buildTrCallBack);
                showBtn();
            } else {
                if (!$(this).val()){
                    var _this = this;
                    layer.confirm("是否删除改行？",{icon: 3, title:'提示'},function (_index) {
                        reductionTr(_this);
                        showBtn();
                        layer.close(_index);
                    },function () {
                        $(_this).prop('checked',true);
                    });
                }else {
                    showBtn();
                    reductionTr(this);
                }
            }
        });


        var initDateTimePicker = function (obj) {
            $(obj).datepicker({
                todayBtn: "linked",
                autoclose: true,
                language: 'zh-CN',
                format: "yyyy-mm-dd",
                todayHighlight: true,
                endDate: new Date()
            })
        }



    };
    return {
        init: function (tb,parent,empId,delUrl,saveFn,buildTrCallBack) {
            root(tb,parent,empId,delUrl,saveFn,buildTrCallBack);
        },
        validate:validate
    }
}();
