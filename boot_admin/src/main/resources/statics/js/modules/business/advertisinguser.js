$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/advertisinguser/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true},
            {label: '消耗金卷', name: 'number', index: 'number', width: 80},
            {label: '预约id', name: 'advertistingId', index: 'advertisting_id', width: 80},
            {label: '预约的标题', name: 'advertistingName', index: 'advertisting_name', width: 80},
            {label: '金卷价格', name: 'price', index: 'price', width: 80},
            {label: '用户id', name: 'userId', index: 'user_id', width: 80},
            {
                label: '购买钱包类型', name: 'walletTypeId', index: 'wallet_type_id', width: 80, formatter: function (value) {
                    if (value == 1) {
                        return '<span class="label label-primary" style="background-color:#dcb989 ">余额</span>';
                    }
                    if (value == 2) {
                        return '<span class="label label-info" style="background-color:#bacad9 ">金券</span>';
                    }
                    if (value == 3) {
                        return '<span class="label label-success" style="background-color:#d8bf7c">银矿池</span>';
                    }
                    if (value == 4) {
                        return '<span class="label label-danger" style="background-color:#7c9ff8">金矿池</span>';
                    }
                    if (value == 5) {
                        return '<span class="label label-danger" style="background-color:#29ccd3">贡献池</span>';
                    }
                    if (value == 6) {
                        return '<span class="label label-danger" style="background-color:#29ccd3">期股权</span>';
                    }
                }
            },
            {label: '预约时间', name: 'dateSubscribe', index: 'date_subscribe', width: 80},
            {label: '观看时间', name: 'dateYiWatch', index: 'date_yi_watch', width: 80},
            {label: '加入时间', name: 'dateUnderway', index: 'date_underway', width: 80},
            {label: '本金退出时间', name: 'datePrincipalrefunded', index: 'date_principalRefunded', width: 80},
            {label: '完成时间', name: 'dateAccomplish', index: 'date_accomplish', width: 80},
            {
                label: '状态', name: 'status', index: 'status', width: 80, formatter: function (value) {
                    if (value == "subscribe") {
                        return '<span class="label label-primary" style="background-color:#dcb989">预约中</span>';
                    }
                    if (value == "yiWatch") {
                        return '<span class="label label-info" style="background-color:#bacad9">已观看</span>';
                    }
                    if (value == "underway") {
                        return '<span class="label label-success" style="background-color:#d8bf7c">进行中</span>';
                    }
                    if (value == "principalRefunded") {
                        return '<span class="label label-success" style="background-color:#7c9ff8">本金已退</span>';
                    }
                    if (value == "accomplish") {
                        return '<span class="label label-success" style="background-color:#29ccd3">完成</span>';
                    }
                }
            }
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
        advertisingUser: {"status":-1}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.advertisingUser = {"status":-1};
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
        accelerate: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }
            var lock = false;
            layer.confirm('确定要通过？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                if (!lock) {
                    lock = true;
                    $.ajax({
                        type: "POST",
                        url: baseURL + "business/advertisinguser/accelerate",
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
        audit: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }
            var lock = false;
            layer.confirm('确定要通过？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                if (!lock) {
                    lock = true;
                    $.ajax({
                        type: "POST",
                        url: baseURL + "business/advertisinguser/audit",
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
        saveOrUpdate: function (event) {
            $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function () {
                var url = vm.advertisingUser.id == null ? "business/advertisinguser/save" : "business/advertisinguser/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.advertisingUser),
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
                        url: baseURL + "business/advertisinguser/delete",
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
            $.get(baseURL + "business/advertisinguser/info/" + id, function (r) {
                vm.advertisingUser = r.advertisingUser;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    "userId": vm.advertisingUser.userId,
                    "status": vm.advertisingUser.status
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});