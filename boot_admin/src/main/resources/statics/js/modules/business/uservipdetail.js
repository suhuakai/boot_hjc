$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/uservipdetail/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '用户id', name: 'userId', index: 'user_id', width: 80 }, 			
			{ label: '消耗数量', name: 'number', index: 'number', width: 80 }, 			
			{ label: '消耗福豆', name: 'consumeBlessingBean', index: 'consume_blessing_bean', width: 80 }, 			
			{ label: '消耗余额', name: 'consumeBalance', index: 'consume_balance', width: 80 }, 			
			{ label: '创建时间', name: 'date', index: 'date', width: 80 },
            {
                label: '用户等级', name: 'vipId', index: 'vip_id', width: 80, formatter: function (value) {
                    if (value == 1) {
                        return '<span class="label label-primary" style="background-color:#dcb989 ">一星矿工</span>';
                    }
                    if (value == 2) {
                        return '<span class="label label-info" style="background-color:#bacad9 ">二星矿工</span>';
                    }
                    if (value == 3) {
                        return '<span class="label label-success" style="background-color:#d8bf7c">三星店家</span>';
                    }
                    if (value == 4) {
                        return '<span class="label label-danger" style="background-color:#7c9ff8">四星千户</span>';
                    }
                    if (value == 5) {
                        return '<span class="label label-danger" style="background-color:#29ccd3">五星城主</span>';
                    }
                }
            },
            {
                label: '操作类型', name: 'type', index: 'type', width: 80, formatter: function (value) {
                    if (value == "upgrade") {
                        return '<span class="label label-primary" style="background-color:#dcb989 ">升级</span>';
                    }
                    if (value == "buy") {
                        return '<span class="label label-info" style="background-color:#bacad9 ">购买</span>';
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
            {
                label: '初始等级', name: 'originalVpiId', index: 'original_vpi_id', width: 80, formatter: function (value) {
                    if (value == 1) {
                        return '<span class="label label-primary" style="background-color:#dcb989 ">一星矿工</span>';
                    }
                    if (value == 2) {
                        return '<span class="label label-info" style="background-color:#bacad9 ">二星矿工</span>';
                    }
                    if (value == 3) {
                        return '<span class="label label-success" style="background-color:#d8bf7c">三星店家</span>';
                    }
                    if (value == 4) {
                        return '<span class="label label-danger" style="background-color:#7c9ff8">四星千户</span>';
                    }
                    if (value == 5) {
                        return '<span class="label label-danger" style="background-color:#29ccd3">五星城主</span>';
                    }
                    if (value == null || value == "") {
                        return '<span class="label label-danger" style="background-color:#29ccd3">一星矿工</span>';
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
		userVipDetail: {vipId: -1,settleStatus:-1,type:-1}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.userVipDetail = {};
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
                var url = vm.userVipDetail.id == null ? "business/uservipdetail/save" : "business/uservipdetail/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.userVipDetail),
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
                        url: baseURL + "business/uservipdetail/delete",
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
			$.get(baseURL + "business/uservipdetail/info/"+id, function(r){
                vm.userVipDetail = r.userVipDetail;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData: {
                    "vipId": vm.userVipDetail.vipId,
                    "type": vm.userVipDetail.type,
                    "settleStatus":vm.userVipDetail.settleStatus
                },
                page:page
            }).trigger("reloadGrid");
		}
	}
});