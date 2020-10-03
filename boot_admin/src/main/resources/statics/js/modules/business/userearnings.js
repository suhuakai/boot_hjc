$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/userearnings/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '数量', name: 'number', index: 'number', width: 80 }, 			
			{ label: '用户ID', name: 'userId', index: 'user_id', width: 80 },
			{ label: '创建时间', name: 'date', index: 'date', width: 80 },
            {
                label: '钱包类型', name: 'walletTypeId', index: 'wallet_type_id', width: 80, formatter: function (value) {
                    if (value == 1) {
                        return '<span class="label label-primary" style="background-color:#dcb989 ">余额</span>';
                    }
                    if (value == 2) {
                        return '<span class="label label-info" style="background-color:#bacad9 ">平台币</span>';
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
            {
                label: '类型', name: 'type', index: 'type', width: 80, formatter: function (value) {
                    if (value == "register") {
                        return '<span class="label label-primary" style="background-color:#dcb989 ">注册</span>';
                    }
                    if (value == "recommend") {
                        return '<span class="label label-info" style="background-color:#bacad9 ">注册推荐</span>';
                    }
                    if (value == "sign") {
                        return '<span class="label label-info" style="background-color:#bacad9 ">签到</span>';
                    }
                    if (value == "attention") {
                        return '<span class="label label-info" style="background-color:#bacad9 ">关注</span>';
                    }
                    if (value == "browse") {
                        return '<span class="label label-info" style="background-color:#bacad9 ">浏览</span>';
                    }
                    if (value == "upRecommend") {
                        return '<span class="label label-info" style="background-color:#bacad9 ">升级推荐</span>';
                    }
                }
            },
            {
                label: '状态', name: 'settleStatus', index: 'settle_status', width: 80, formatter: function (value) {
                    if (value == "yes") {
                        return '<span class="label label-primary" style="background-color:#dcb989 ">已结算</span>';
                    }
                    if (value == "no") {
                        return '<span class="label label-info" style="background-color:#bacad9 ">未结算</span>';
                    }
                }
            },
			{ label: '来源用户id', name: 'upUserId', index: 'up_user_id', width: 80 }			
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
		userEarnings: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.userEarnings = {};
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
                var url = vm.userEarnings.id == null ? "business/userearnings/save" : "business/userearnings/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.userEarnings),
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
                        url: baseURL + "business/userearnings/delete",
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
		getInfo: function(id){
			$.get(baseURL + "business/userearnings/info/"+id, function(r){
                vm.userEarnings = r.userEarnings;
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