!function(t){t._({"~name":"seller.order","~superclass":t.base,ctor:function(){this.api="order",this._super()},tipInit:function(){$("[title]").poshytip({className:"tip-twitter",showTimeout:1,alignTo:"target",alignX:"center",offsetY:5,allowTipHover:!1,fade:!1,slide:!1})},postCreate:function(){this.$.create_time_start.cxCalendar(),this.$.create_time_end.cxCalendar(),this.$.loading.show(),this.request("getOrderList")},getOrderListArgs:function(){this.$.loading.show();var t,e=this.$.miniDialog_prompt_input;return e&&(t=e.val()),{type:"post",data:{page:this.pageId,order_sn:this.$.order_id.val(),receiver_phone:this.$.receiver_phone.val(),create_time_start:this.$.create_time_start.val(),create_time_end:this.$.create_time_end.val(),order_status:this.$.order_status.val(),remark:t},done:cobra.ride(this,function(t){$.when(this.compile(this.$.orderList,"orderList>order_list",t)).done(cobra.ride(this,function(){this.tipInit(),$.when(this.compile(this.$.pageset,"orderList>footer_pageset",t)).done(cobra.ride(this,function(){$(document).scrollTop(0,0)}))})),this.$.loading.hide()}),fail:function(t){this.$.loading.hide(),this._msgBox.warn(t.responseBody.responseInfo.reasons.msg)}}},getOrderRemarkArgs:function(){var t,e=this.$.miniDialog_prompt_input,i=this.orderId;return e&&(t=e.val()),{type:"post",data:{order_sn:i,remark:t},done:cobra.ride(this,function(){this.request("getOrderList")}),fail:function(t){this._msgBox.warn(t.responseBody.responseInfo.reasons.msg)}}},searchResult:function(){this.request("getOrderList")},addComment:function(t,e){this.orderId=t,miniDialog.prompt("",e,cobra.ride(this,function(){this.request("getOrderRemark")}),function(){},{title:"请输入备注内容"})},turnPage:function(){var t,e=this.req.getOrderList;this.$.pageId&&this.$.pageId.val()>0&&this.$.pageId.val()<e.pagesize?t=this.$.pageId.val():this._msgBox.warn("请输入一个正确的页数"),this.pageId=t,this.request("getOrderList")},preNextPage:function(t){this.$.bid.val(t),this.pageId=t,this.request("getOrderList")},downOrderList:function(){var t,e,i,r,s,n,a="YYYY-MM-DD";if(this.$.order_status_input.val(this.$.order_status.val()),t=moment().unix(),e=1209600,s=$.trim($("input[name=create_time_start]").val())?$("input[name=create_time_start]").val():!1,n=$.trim($("input[name=create_time_end]").val())?$("input[name=create_time_end]").val():!1,!s&&!n)return this.$.down_form.submit(),!0;if(i=s?moment(s,a).unix():!1,r=n?moment(n,a).unix():!1,i||r){if(i&&0>t-i)return this._msgBox.warn("开始时间不能大于当前时间"),!1;if(r&&0>t-r)return this._msgBox.warn("结束时间不能大于当前时间"),!1;if(i&&r&&0>r-i)return this._msgBox.warn("开始时间不能大于结束时间"),!1}return e>r-i?(this.$.down_form.submit(),!0):(this._msgBox.warn("只能选择时间差距离两周的订单"),!1)}}),$(function(){new seller.order})}(cobra);