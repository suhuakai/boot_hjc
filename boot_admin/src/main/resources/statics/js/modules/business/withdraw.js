$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/withdraw/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '数量', name: 'number', index: 'number', width: 80 }, 			
			{ label: '用户id', name: 'userId', index: 'user_id', width: 80 },
            {
                label: '审核状态', name: 'status', index: 'status', width: 80, formatter: function (value) {
                    if (value == "audit") {
                        return '<span class="label label-primary" style="background-color:#dcb989 ">审核中</span>';
                    }
                    if (value == "succeed") {
                        return '<span class="label label-info" style="background-color:#bacad9 ">成功</span>';
                    }
                    if (value == "failed") {
                        return '<span class="label label-success" style="background-color:#d8bf7c">失败</span>';
                    }
                    if (value == "creation") {
                        return '<span class="label label-danger" style="background-color:#7c9ff8">创建</span>';
                    }
                }
            },
			{ label: '备注', name: 'remark', index: 'remark', width: 80 }, 			
			{ label: '驳回说明', name: 'reject', index: 'reject', width: 80 }, 			
			{ label: '创建时间', name: 'date', index: 'date', width: 80 }, 			
			{ label: '审核时间', name: 'accomplishDate', index: 'accomplish_date', width: 80 },
            {
                label: '操作类型', name: 'type', index: 'type', width: 80, formatter: function (value) {
                    if (value == "withdraw") {
                        return '<span class="label label-primary" style="background-color:#dcb989 ">提现</span>';
                    }
                    if (value == "recharge") {
                        return '<span class="label label-info" style="background-color:#bacad9 ">充值</span>';
                    }
                }
            },
			{ label: '手续费', name: 'fee', index: 'fee', width: 80 }, 			
			{ label: '订单号', name: 'randomCode', index: 'random_code', width: 80 }, 			
			{ label: '图片路径', name: 'url', index: 'url', width: 80 }, 			
			{ label: '实际充值金额', name: 'realityNumber', index: 'reality_number', width: 80 },
            {
                label: '支付类型', name: 'useType', index: 'useType', width: 80, formatter: function (value) {
                    if (value == "bankCard") {
                        return '<span class="label label-primary" style="background-color:#dcb989 ">银行卡</span>';
                    }
                    if (value == "pay") {
                        return '<span class="label label-info" style="background-color:#bacad9 ">支付宝</span>';
                    }
                }
            },
			{ label: '卡号', name: 'account', index: 'account', width: 80 }, 			
			{ label: '账号名称', name: 'accountName', index: 'account_name', width: 80 }, 			
			{ label: '银行类型', name: 'banksType', index: 'banks_type', width: 80 },
            { label: '子行', name: 'banksSonType', index: 'banks_son_type', width: 80 }
        ],
		viewrecords: true,
    height: 500,         rowNum: 10, 		rowList : [30,50,100],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		withdraw: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.withdraw = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.withdraw.id == null ? "business/withdraw/save" : "business/withdraw/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.withdraw),
                    success: function(r){
                        if(r.code === 0){
                             layer.msg("操作成功", {icon: 1});
                             vm.reload();
                             $('#btnSaveOrUpdate').button('reset');
                             $('#btnSaveOrUpdate').dequeue();
                        }else{
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
			if(ids == null){
				return ;
			}
			var lock = false;
            layer.confirm('确定要删除选中的记录？', {
                btn: ['确定','取消'] //按钮
            }, function(){
               if(!lock) {
                    lock = true;
		            $.ajax({
                        type: "POST",
                        url: baseURL + "business/withdraw/delete",
                        contentType: "application/json",
                        data: JSON.stringify(ids),
                        success: function(r){
                            if(r.code == 0){
                                layer.msg("操作成功", {icon: 1});
                                $("#jqGrid").trigger("reloadGrid");
                            }else{
                                layer.alert(r.msg);
                            }
                        }
				    });
			    }
             }, function(){
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
                        url: baseURL + "business/withdraw/audit",
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
        failing: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }
            var lock = false;
            layer.confirm('确定要失败？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                if (!lock) {
                    lock = true;
                    $.ajax({
                        type: "POST",
                        url: baseURL + "business/withdraw/failing",
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
		getInfo: function(id){
			$.get(baseURL + "business/withdraw/info/"+id, function(r){
                vm.withdraw = r.withdraw;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});