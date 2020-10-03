$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/sequence/list',
        datatype: "json",
        colModel: [			
			{ label: 'seqName', name: 'seqName', index: 'seq_name', width: 50, key: true },
			{ label: '', name: 'currentVal', index: 'current_val', width: 80 }, 			
			{ label: '', name: 'incrementVal', index: 'increment_val', width: 80 }			
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
		sequence: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sequence = {};
		},
		update: function (event) {
			var seqName = getSelectedRow();
			if(seqName == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(seqName)
		},
		saveOrUpdate: function (event) {
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.sequence.seqName == null ? "business/sequence/save" : "business/sequence/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.sequence),
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
			var seqNames = getSelectedRows();
			if(seqNames == null){
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
                        url: baseURL + "business/sequence/delete",
                        contentType: "application/json",
                        data: JSON.stringify(seqNames),
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
		getInfo: function(seqName){
			$.get(baseURL + "business/sequence/info/"+seqName, function(r){
                vm.sequence = r.sequence;
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