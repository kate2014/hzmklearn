!function(e){e._({"~name":"seller.order_ship","~superclass":e.base,ctor:function(){this.api="order",this._super()},__rnd:0,tipInit:function(){$("[title]").poshytip({className:"tip-twitter",showTimeout:1,alignTo:"target",alignX:"center",offsetY:5,allowTipHover:!1,fade:!1,slide:!1})},postCreate:function(){this.$.order_search_time_start.cxCalendar(),this.$.order_search_time_end.cxCalendar(),this.requireOrderShip(),console.log(this)},order_shipArgs:function(){var e=this;this.compile(this.$.order_list,"orderList>ship_loading",{});var t=e.getQuery("page")||1,i={order_sn:$.trim(e.$.order_search_sn_input.val()),receiver_phone:$.trim(e.$.order_search_phone_input.val()),create_time_start:$.trim(e.$.order_search_time_start.val()),create_time_end:$.trim(e.$.order_search_time_end.val()),page:t};return{type:"post",data:i,done:cobra.ride(this,function(t){return console.log(t),t.data&&t.data.sid&&(e.__sid=t.data.sid),this.$.loading.hide(),$.when(this.compile(this.$.order_list,"orderList>order_ship_list",t)).done(cobra.ride(this,function(){this.tipInit(),console.log(t)})).fail(function(){}),$.when(this.compile(this.$.pageination,"orderList>goods_list_pageination",t)).done(cobra.ride(this,function(){})).fail(function(){}),t}),fail:function(t){console.log(arguments),arguments.length>1&&e._msgBox.warn(t.responseBody.responseInfo.reasons.msg)}}},requireOrderShip:function(){this.$.loading.show(),this.request("order_ship")},orderSearchSubmit:function(){this.requireOrderShip()},showDetail:function(){var e=arguments[arguments.length-1],t=e.attr("data-toggle");console.log(t);var i=e.parent().find("table[toggle="+t+"]");console.log(i),i.toggle()},sellerRemarkArgs:function(){console.log(arguments);var e=this;return{type:"post",data:{order_sn:e._seller_remark_sn,remark:e._seller_remark},done:cobra.ride(this,function(t){return this.$.loading.hide(),e.requireOrderShip(),t}),fail:function(t){arguments.length>1&&e._msgBox.warn(t.responseBody.responseInfo.reasons.msg)}}},sellerRemark:function(){this.$.loading.show(),this.request("sellerRemark")},addComment:function(e,t){var i=this,o="";t&&""!=t&&(o=t),miniDialog.prompt(null,o,function(){i._seller_remark_sn=e,i._seller_remark=arguments[arguments.length-1],i.sellerRemark()},function(){console.log("no")},{width:225,height:165,title:"请输入卖家备注"})},goOrderDetail:function(e){console.log(arguments),window.open("order_detail.html?order_sn="+e)},orderExpand:function(){var e=arguments[arguments.length-1],t=e.data("type");t?$("table[toggle]").show()&&e.data("type",0)&&e.children("a").html("收起全部订单"):$("table[toggle]").hide()&&e.data("type",1)&&e.children("a").html("展开全部订单")},goPage:function(){window.location.href=location.pathname+"?page="+arguments[0]},showImportDialog:function(){var e=this.__rnd++,t=$("<div></div>");$.when(this.compile(t,"orderList>order_ship_multi_import",{name:e})).done(cobra.ride(this,function(){var i={title:"导入发货单(*.xls):",content:t.html(),closeX:!0,width:550,height:330};miniDialog(i),this.createUploadXLS(e)})).fail(function(){})},createUploadXLSOpt:function(){var e=this,t={formData:{timestamp:(new Date).getTime(),token:"A",sid:e.__sid?e.__sid:""},fileSizeLimit:"3MB",buttonClass:"uploadify-button uploadify-button2",width:146,height:35,fileTypeDesc:"请选择Excel表格文件（.xls）",fileTypeExts:"*.xls",multi:!1,swf:e.host[e.host.use]+"/seller/src/plugin/uploadify/uploadify.swf",uploader:e.host[e.host.use]+"/seller_order-multi_delivery.html",buttonText:"点击上传<br/>",button_placeholder:"",onUploadProgress:function(){this.button.html("正在上传...")},onUploadError:function(){this.queueData.queueBytesUploaded=0},onUploadStart:function(){e.$.loading.show(),this.queueData.queueBytesUploaded=0},onUploadSuccess:function(){e.$.loading.hide(),this.queueData.queueBytesUploaded=0;try{var t=$.parseJSON(arguments[1]);console.log(arguments),console.log(t),this.button.html("再次上传");var i={};i.total=t.responseBody.data.count,i.suc=t.responseBody.data.suc,i.fail=t.responseBody.data.fail,i.errs=t.responseBody.data.errors,i.sucs=t.responseBody.data.sucs;var o="发货结果 - 总共${total}条记录  成功${suc}条  失败${fail}条",r=$("<div></div>");$.when(e.compile(r,"orderList>order_ship_multi_result",i)).done(cobra.ride(e,function(){var t={width:0==i.fail?350:600,height:0==i.fail?150:400,closeX:!0,title:e.parse(o,i),content:0==i.fail?"<div style='text-align: center;font-size: 16px;line-height: 50px;color:#E62D8B;font-weight: bold;'>批量发货成功</div>":r.html(),buttons:[{value:"确定",type:"",click:function(){e.$.loading.show(),window.location.reload()}}],clickCloseX:function(){e.$.loading.show(),window.location.reload()}};miniDialog(t)})).fail(function(){})}catch(n){e._msgBox.error("上传失败，请稍后重试")}}};return t},createUploadXLS:function(e){this.$["uploadXls_"+e].uploadify(this.createUploadXLSOpt())},downOrderList:function(){var e,t,i,o,r,n,s="YYYY-MM-DD";if(e=moment().unix(),t=1209600,r=$.trim($("input[name=create_time_start]").val())?$("input[name=create_time_start]").val():!1,n=$.trim($("input[name=create_time_end]").val())?$("input[name=create_time_end]").val():!1,!r&&!n)return this.$.down_form.submit(),!0;if(i=r?moment(r,s).unix():!1,o=n?moment(n,s).unix():!1,i||o){if(i&&0>e-i)return this._msgBox.warn("开始时间不能大于当前时间"),!1;if(o&&0>e-o)return this._msgBox.warn("结束时间不能大于当前时间"),!1;if(i&&o&&0>o-i)return this._msgBox.warn("开始时间不能大于结束时间"),!1}return t>o-i?(this.$.down_form.submit(),!0):(this._msgBox.warn("只能选择时间差距离两周的订单"),!1)}}),$(function(){new seller.order_ship})}(cobra);