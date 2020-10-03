$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/walletrecharge/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '发送地址', name: 'formAddress', index: 'form_address', width: 80 }, 			
			{ label: '接受地址', name: 'toAddress', index: 'to_address', width: 80 }, 			
			{ label: '数量', name: 'number', index: 'number', width: 80 }, 			
			{ label: 'hash值', name: 'txid', index: 'txid', width: 80 }, 			
			{ label: '区块高度', name: 'block', index: 'block', width: 80 }, 			
			{ label: '区块下标', name: 'blockIndex', index: 'block_index', width: 80 }, 			
			{ label: '手续费', name: 'fee', index: 'fee', width: 80 }, 			
			{ label: 'token', name: 'token', index: 'token', width: 80 }, 			
			{ label: '创建时间', name: 'createDate', index: 'create_date', width: 80 }, 			
			{ label: '出块时间', name: 'comeDate', index: 'come_date', width: 80 }, 			
			{ label: '备注', name: 'remark', index: 'remark', width: 80 }, 			
			{ label: '', name: 'coinId', index: 'coin_id', width: 80 }, 			
			{ label: '', name: 'walletId', index: 'wallet_id', width: 80 }, 			
			{ label: '0 确认中 1 成功 2 失败 3 删除 4 成功', name: 'status', index: 'status', width: 80 }, 			
			{ label: '代币名称', name: 'coinName', index: 'coin_name', width: 80 }, 			
			{ label: '1 链上充值 2 内部充值', name: 'type', index: 'type', width: 80 }, 			
			{ label: '', name: 'userId', index: 'user_id', width: 80 }, 			
			{ label: ' 成功 未归集 手续费确认中  手续费支付失败 
usdt发送确认中 失败', name: 'collectionType', index: 'collection_type', width: 80 }, 			
			{ label: '', name: 'scanType', index: 'scan_type', width: 80 }			
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
		walletRecharge: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.walletRecharge = {};
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
                var url = vm.walletRecharge.id == null ? "business/walletrecharge/save" : "business/walletrecharge/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.walletRecharge),
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
                        url: baseURL + "business/walletrecharge/delete",
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
			$.get(baseURL + "business/walletrecharge/info/"+id, function(r){
                vm.walletRecharge = r.walletRecharge;
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