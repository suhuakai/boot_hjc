$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/dictionaries/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '分流福豆利率', name: 'consumeBlessingBean', index: 'consume_blessing_bean', width: 80 }, 			
			{ label: '分流抵扣卷利率', name: 'consumeVoucher', index: 'consume_voucher', width: 80 }, 			
			{ label: '提现手续费利率，进入抵扣卷', name: 'withdrawFee', index: 'withdraw_fee', width: 80 }, 			
			{ label: '兑换利率 福豆', name: 'conversionRate', index: 'conversion_rate', width: 80 }, 			
			{ label: '充值最小金额', name: 'rechargeMin', index: 'recharge_min', width: 80 }, 			
			{ label: '提现最小金额', name: 'withdrawMin', index: 'withdraw_min', width: 80 }, 			
			{ label: '提现最大金额', name: 'withdrawMax', index: 'withdraw_max', width: 80 }, 			
			{ label: '兑现最小数量', name: 'conversionMin', index: 'conversion_min', width: 80 }, 			
			{ label: '兑换开关', name: 'conversionSwitch', index: 'conversion_switch', width: 80 },
			{ label: '划转开关', name: 'transferSwitch', index: 'transfer_switch', width: 80 },
			{ label: '提现开关', name: 'withdrawSwitch', index: 'withdraw_switch', width: 80 }
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
		dictionaries: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.dictionaries = {};
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
                var url = vm.dictionaries.id == null ? "business/dictionaries/save" : "business/dictionaries/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.dictionaries),
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
                        url: baseURL + "business/dictionaries/delete",
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
			$.get(baseURL + "business/dictionaries/info/"+id, function(r){
                vm.dictionaries = r.dictionaries;
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