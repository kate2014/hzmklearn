!function(t){t._({"~name":"seller.refundDetail","~superclass":cobra.base,api:"refund",ctor:function(){this._super()},postCreate:function(){this.set("refundDetailView~tmpl#refund>refundDetail","refund_detail")},refund_detailArgs:function(){var t=this.getQuery("id");return{type:"get",data:{id:t}}},checkLogisticArgs:function(){var t=miniDialog.alert(this.$.loading.html(),function(){},{title:"查看物流",width:550,height:420}),e=this.e_sn,i=this.e_id;return{type:"get",data:{sn:e,id:i},done:function(e){t.setContent(e.data||"暂时没有数据，请稍后重试")},fail:function(){t.setContent("暂时没有数据，请稍后重试")}}},checkLogistic:function(t,e){this.e_id=t,this.e_sn=e,this.e_id&&this.e_sn&&this.request("checkLogistic")},checkLogisticArgs:function(){var t=miniDialog.alert(this.$.loading.html(),function(){},{title:"查看物流",width:550,height:420}),e=this.e_sn,i=this.e_id;return{type:"get",data:{sn:e,id:i},done:function(e){t.setContent(e.data||"暂时没有数据，请稍后重试")},fail:function(){t.setContent("暂时没有数据，请稍后重试")}}}}),$(function(){new seller.refundDetail})}(cobra);