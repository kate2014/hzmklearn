!function(e){e._({"~name":"seller.order","~superclass":e.base,ctor:function(){this.api="order",this._super()},postCreate:function(){this.set("tmpl#orderDetail>order_detail","getOrderDetail")},addComment:function(e){miniDialog.prompt("",e,cobra.ride(this,function(){this.request("getOrderRemark")}),function(){},{title:"请输入备注内容"})},getOrderDetailArgs:function(){var e=this.getQuery("order_sn");return{type:"post",data:{order_sn:e},done:cobra.ride(this,function(e){if(e.data.orders.length>0){var r=e.data.orders[0].dilivery_info;$.each(r,cobra.ride(this,function(e,r){r.express_id&&""!=r.express_id&&r.notice_sn&&""!=r.notice_sn&&(this.s_id=r.express_id,this.s_sn=r.notice_sn,this.request("getOrderLogistic"))}))}return e}),fail:function(e){this._msgBox.warn(e.responseBody.responseInfo.reasons.msg)}}},getOrderLogisticArgs:function(){var e=this.s_id,r=this.s_sn;return{type:"get",data:{sn:r,id:e},done:cobra.ride(this,function(e){$.when(this.compile(this.$[r],"orderDetail>order_Logistic",e)).done(cobra.ride(this,function(){}))}),fail:function(e){this._msgBox.warn(e.responseBody.responseInfo.reasons.msg)}}},getOrderRemarkArgs:function(){var e,r=this.getQuery("order_sn"),t=this.$.miniDialog_prompt_input;return t&&(e=t.val()),{type:"post",data:{order_sn:r,remark:e},done:cobra.ride(this,function(){location.reload()}),fail:function(e){this._msgBox.warn(e.responseBody.responseInfo.reasons.msg)}}}}),$(function(){new seller.order})}(cobra);