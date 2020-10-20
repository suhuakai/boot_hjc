$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/user/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '助记词', name: 'mnemonic', index: 'mnemonic', width: 80 }, 			
			//{ label: '私钥', name: 'privateKey', index: 'private_key', width: 80 },
			//{ label: '密码', name: 'password', index: 'password', width: 80 },
			{ label: '昵称', name: 'name', index: 'name', width: 80 },
            {
                label: '登入状态', name: 'status', index: 'status', width: 80, formatter: function (value) {
                    if (value == 'yes') {
                        return '<span class="label label-primary" style="background-color:#dcb989 ">正常</span>';
                    }
                    if (value == 'no') {
                        return '<span class="label label-info" style="background-color:#bacad9 ">禁用</span>';
                    }
                }
            },
			{ label: '日期', name: 'date', index: 'date', width: 80 },
			{ label: '设备号', name: 'equipmentNumber', index: 'equipment_number', width: 80 }, 			
			//{ label: '交易密码', name: 'dealPassword', index: 'deal_password', width: 80 },
			{ label: '上级用户编号', name: 'upUserId', index: 'up_user_id', width: 80 }, 			
			{ label: '阶梯', name: 'grade', index: 'grade', width: 80 }, 			
			{ label: '阶梯路径', name: 'gradeUrl', index: 'grade_url', width: 80 }, 			
			{ label: '直推人数', name: 'recommendCount', index: 'recommend_count', width: 80 }, 			
			{ label: '激活码', name: 'identityCard', index: 'Identity_card', width: 80 }, 			
			//{ label: '头像地址', name: 'headUrl', index: 'head_url', width: 80 },
            {
                label: '会员等级', name: 'userVipId', index: 'user_vip_id', width: 80, formatter: function (value) {
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
                label: '是否激活', name: 'isActivate', index: 'is_activate', width: 80, formatter: function (value) {
                    if (value == 'yes') {
                        return '<span class="label label-primary" style="background-color:#dcb989 ">是</span>';
                    }
                    if (value == 'no') {
                        return '<span class="label label-info" style="background-color:#bacad9 ">否</span>';
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
		user: {userVipId:-1}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.user = {};
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
                var url = vm.user.id == null ? "business/user/save" : "business/user/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.user),
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
        activate: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }
            var lock = false;
            layer.confirm('确定要激活？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                if (!lock) {
                    lock = true;
                    $.ajax({
                        type: "post",
                        url: baseURL + "business/user/activate",
                        contentType: "application/json",
                        data: JSON.stringify(ids),
                        success: function (r) {
                            if (r.code === 0) {
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
			$.get(baseURL + "business/user/info/"+id, function(r){
                vm.user = r.user;
            });
		},
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    "id": vm.user.id,
                    "name": vm.user.name,
                    "userVipId": vm.user.userVipId
                },
                page: page
            }).trigger("reloadGrid");
        }
	}
});