$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/wallet/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '余额', name: 'balance', index: 'balance', width: 80 }, 			
			{ label: '地址', name: 'address', index: 'address', width: 80 }, 			
			{ label: '用户id', name: 'userId', index: 'user_id', width: 80 }, 			
			{ label: '代币id', name: 'coinId', index: 'coin_id', width: 80 }, 			
			{ label: '代币名称', name: 'coinName', index: 'coin_name', width: 80 },
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
            }
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
		wallet: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.wallet = {};
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
                var url = vm.wallet.id == null ? "business/wallet/save" : "business/wallet/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.wallet),
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
                        url: baseURL + "business/wallet/delete",
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
			$.get(baseURL + "business/wallet/info/"+id, function(r){
                vm.wallet = r.wallet;
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