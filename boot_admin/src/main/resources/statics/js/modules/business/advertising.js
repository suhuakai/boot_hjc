$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/advertising/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true},
            {label: '购买价格', name: 'number', index: 'number', width: 80},
            {label: '标题', name: 'title', index: 'title', width: 80},
            {label: '总数量', name: 'grossCount', index: 'gross_count', width: 80},
            {label: '已购买数量', name: 'buyCount', index: 'buy_count', width: 80},
            {label: '视频url', name: 'url', index: 'url', width: 80},
            {
                label: '状态', name: 'status', index: 'status', width: 80, formatter: function (value) {
                    if (value == 'open') {
                        return '<span class="label label-primary" style="background-color:#dcb989 ">开启</span>';
                    }
                    if (value == 'close') {
                        return '<span class="label label-info" style="background-color:#bacad9 ">关闭</span>';
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
        advertising: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.advertising = {};
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
                var url = vm.advertising.id == null ? "business/advertising/save" : "business/advertising/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.advertising),
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
        imgUploading: function () {
            var formData = new FormData();
            var crowd_file = $('#file')[0].files[0];
            formData.append("file", crowd_file);
            $.ajax({
                type: "POST",
                url: baseURL + "business/banks/imgUploading",
                processData: false,
                dataType: "json",
                contentType: false,
                data: formData,
                success: function (r) {
                    $('#location').val(r.data);
                    vm.advertising.url = r.data;
                }
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
                        url: baseURL + "business/advertising/delete",
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
            $.get(baseURL + "business/advertising/info/" + id, function (r) {
                vm.advertising = r.advertising;
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