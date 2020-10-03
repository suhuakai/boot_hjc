$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/commodity/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '', name: 'commodityTypeId', index: 'commodity_type_id', width: 80 }, 			
			{ label: '商品类型', name: 'commodityTypeName', index: 'commodity_type_name', width: 80 }, 			
			{ label: '商品名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '原价', name: 'priceOriginal', index: 'price_original', width: 80 }, 			
			{ label: '团购价', name: 'priceGroup', index: 'price_group', width: 80 }, 			
			{ label: '销售数量', name: 'salesVolume', index: 'sales_volume', width: 80 }, 			
			{ label: '商品图片', name: 'imgUrl', index: 'img_url', width: 80 }, 			
			{ label: '团购人数', name: 'grougCount', index: 'groug_count', width: 80 }, 			
			{ label: '可中奖人数', name: 'awardCount', index: 'award_count', width: 80 }, 			
			{ label: '创建时间', name: 'date', index: 'date', width: 80 }, 			
			{ label: '上架用户id', name: 'userId', index: 'user_id', width: 80 }, 			
			{ label: '系统，用户', name: 'type', index: 'type', width: 80 }, 			
			{ label: '快递id', name: 'expressageId', index: 'expressage_id', width: 80 }, 			
			{ label: '正常/禁用 审核 失败', name: 'status', index: 'status', width: 80 }, 			
			{ label: 'vp值', name: 'vpNumber', index: 'vp_number', width: 80 }, 			
			{ label: 'vp 流水利率', name: 'vpRunningRate', index: 'vp_running_rate', width: 80 }, 			
			{ label: '未中奖红包利率', name: 'vpRefundRate', index: 'vp_refund_rate', width: 80 }, 			
			{ label: '团购积分利率', name: 'vpIntegralRate', index: 'vp_integral_rate', width: 80 }, 			
			{ label: '产品图片详情', name: 'imgUrlParticulars', index: 'img_url_particulars', width: 80 }, 			
			{ label: '', name: 'dateUpdate', index: 'date_update', width: 80 }, 			
			{ label: '排序', name: 'commodityIndex', index: 'commodity_index', width: 80 }, 			
			{ label: '团购时间', name: 'endHour', index: 'end_hour', width: 80 }			
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
		commodity: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.commodity = {};
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
                var url = vm.commodity.id == null ? "business/commodity/save" : "business/commodity/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.commodity),
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
                        url: baseURL + "business/commodity/delete",
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
			$.get(baseURL + "business/commodity/info/"+id, function(r){
                vm.commodity = r.commodity;
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