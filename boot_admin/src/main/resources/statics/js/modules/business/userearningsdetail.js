$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/userearningsdetail/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 30, key: true},
            {label: '阶梯', name: 'grade', index: 'grade', width: 40},
            {label: '分流利率', name: 'shuntRate', index: 'shunt_rate', width: 40},
            {label: '加权利率', name: 'weightingRate', index: 'weighting_rate', width: 40},
            {label: '用户id', name: 'userId', index: 'user_id', width: 50},
            {label: '来源订单id', name: 'orderId', index: 'order_id', width: 50},
            {label: '来源用户id', name: 'upUserId', index: 'up_user_id', width: 50},
            {label: '变化值计算值', name: 'number', index: 'number', width: 60},
            {label: '变化值显示值', name: 'numberZifu', index: 'number_zifu', width: 60},
            {label: '钱包当前余额', name: 'balance', index: 'balance', width: 60},
            {
                label: '钱包类型', name: 'walletTypeId', index: 'wallet_type_id', width: 60, formatter: function (value) {
                    if (value == 1) {
                        return '<span class="label label-primary" style="background-color:#dcb989 ">现金钱包</span>';
                    }
                    if (value == 2) {
                        return '<span class="label label-info" style="background-color:#bacad9 ">福豆钱包</span>';
                    }
                    if (value == 3) {
                        return '<span class="label label-success" style="background-color:#d8bf7c">抵扣卷钱包</span>';
                    }
                }
            },
            {label: '说明', name: 'content', index: 'content', width: 80},
            {
                label: '类型', name: 'type', index: 'type', width: 60, formatter: function (value) {
                    if (value == 'operation') {
                        return '<span class="label label-primary" style="background-color:#dcb989 ">操作</span>';
                    }
                    if (value == 'dynamic') {
                        return '<span class="label label-info" style="background-color:#bacad9 ">动态收益</span>';
                    }
                    if (value == 'static') {
                        return '<span class="label label-success" style="background-color:#d8bf7c">静态收益</span>';
                    }
                }
            },

            {
                label: '收益类型',
                name: 'earningsType',
                index: 'earnings_type',
                width: 60,
                formatter: function (value) {
                    if (value == 'recommend') {
                        return '<span class="label label-primary" style="background-color:#dcb989 ">推荐收益</span>';
                    }
                    if (value == 'sign') {
                        return '<span class="label label-info" style="background-color:#bacad9 ">签到收益</span>';
                    }
                    if (value == 'not') {
                        return '<span class="label label-success" style="background-color:#d8bf7c">非收益</span>';
                    }
                }
            },
            {label: '创建时间', name: 'date', index: 'date', width: 80}
        ],
        viewrecords: true,
        height: 500, rowNum: 10, rowList: [30, 50, 100],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        userEarningsDetail: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.userEarningsDetail = {};
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function () {
                var url = vm.userEarningsDetail.id == null ? "business/userearningsdetail/save" : "business/userearningsdetail/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.userEarningsDetail),
                    success: function (r) {
                        if (r.code === 0) {
                            layer.msg("操作成功", {icon: 1});
                            vm.reload();
                            $('#btnSaveOrUpdate').button('reset');
                            $('#btnSaveOrUpdate').dequeue();
                        } else {
                            layer.alert(r.msg);
                            $('#btnSaveOrUpdate').button('reset');
                            $('#btnSaveOrUpdate').dequeue();
                        }
                    }
                });
            });
        },
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }
            var lock = false;
            layer.confirm('确定要删除选中的记录？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                if (!lock) {
                    lock = true;
                    $.ajax({
                        type: "POST",
                        url: baseURL + "business/userearningsdetail/delete",
                        contentType: "application/json",
                        data: JSON.stringify(ids),
                        success: function (r) {
                            if (r.code == 0) {
                                layer.msg("操作成功", {icon: 1});
                                $("#jqGrid").trigger("reloadGrid");
                            } else {
                                layer.alert(r.msg);
                            }
                        }
                    });
                }
            }, function () {
            });
        },
        getInfo: function (id) {
            $.get(baseURL + "business/userearningsdetail/info/" + id, function (r) {
                vm.userEarningsDetail = r.userEarningsDetail;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        }
    }
});