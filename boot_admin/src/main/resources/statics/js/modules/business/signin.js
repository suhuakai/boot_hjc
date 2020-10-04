$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/signin/list',
        datatype: "json",
        colModel: [			
			{ label: 'signId', name: 'signId', index: 'sign_id', width: 50, key: true },
			{ label: '用户id', name: 'userId', index: 'user_id', width: 80 }, 			
			{ label: '签到日期', name: 'signDate', index: 'sign_date', width: 80 }, 			
			{ label: '签到金额', name: 'signBalance', index: 'sign_balance', width: 80 },
            {
                label: '签到类型', name: 'signType', index: 'sign_type', width: 80, formatter: function (value) {
                    if (value == 1) {
                        return '<span class="label label-primary" style="background-color:#dcb989 ">签到</span>';
                    }
                    if (value == 2) {
                        return '<span class="label label-info" style="background-color:#bacad9 ">关注</span>';
                    }
                    if (value == 3) {
                        return '<span class="label label-success" style="background-color:#d8bf7c">浏览</span>';
                    }
                }
            },
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
		signin: {walletTypeId:-1,signType:-1}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.signin = {};
		},
		update: function (event) {
			var signId = getSelectedRow();
			if(signId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(signId)
		},
		saveOrUpdate: function (event) {
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.signin.signId == null ? "business/signin/save" : "business/signin/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.signin),
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
			var signIds = getSelectedRows();
			if(signIds == null){
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
                        url: baseURL + "business/signin/delete",
                        contentType: "application/json",
                        data: JSON.stringify(signIds),
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
		getInfo: function(signId){
			$.get(baseURL + "business/signin/info/"+signId, function(r){
                vm.signin = r.signin;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData: {
                    "walletTypeId": vm.signin.walletTypeId,
                    "signType": vm.signin.signType
                },
                page:page
            }).trigger("reloadGrid");
		}
	}
});