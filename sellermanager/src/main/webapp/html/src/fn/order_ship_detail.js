!function(e){e._({"~name":"seller.order_ship_detail","~superclass":e.base,ctor:function(){this.api="order",this._super()},postCreate:function(){this.getOrderDetail()},getOrderDetailArgs:function(){var e=this;return{type:"post",data:{order_sn:this.getQuery("order_sn")},done:cobra.ride(this,function(e){return this.$.loading.hide(),$.when(this.compile(this.$.order_detail_info,"orderList>order_ship_detail",e.data.orders)).done(cobra.ride(this,function(){var s=e.data.orders[0].dilivery_info;$.each(s,function(e,s){s&&s.notice_sn&&$.each(s.item,function(e,s){$("input[data-item-id="+s.item_id+"]").remove()})})})).fail(function(){}),$.when(this.compile(this.$.order_detail_confirm,"orderList>confirm_info",e.data.orders)).done(cobra.ride(this,function(){})).fail(function(){}),e}),fail:function(s){arguments.length>1&&e._msgBox.warn(s.responseBody.responseInfo.reasons.msg)}}},getOrderDetail:function(){var e=this,s=function(){return e._msgBox.warn("无效的订单号"),!1};(this.getQuery("order_sn")||s())&&(this.$.loading.show(),$.when(e.request("getOrderDetail")).done(function(){e.request("getExpressList")}))},getExpressListArgs:function(){var e=this;return{type:"get",data:{},done:cobra.ride(this,function(e){return $.when(this.compile(this.$.expressList,"orderList>express_list",e.data.express_list)).done(cobra.ride(this,function(){this.$.expressList.children("tr").each(function(e,s){e>6&&$(s).addClass("hidden other")})})).fail(function(){}),e}),fail:function(s){arguments.length>1&&e._msgBox.warn(s.responseBody.responseInfo.reasons.msg)}}},showAllExpress:function(){this.$.expressList.children(".other").toggle(),this.$.openExpress.toggle(),this.$.closeExpress.toggle()},modifyAddress:function(){var e=this;this.$.modifyAddressBox.children().hide(),this.$.modifyAddressBox.children(".hiddenForm").show(),window.regionConf&&e.fill_address_select(e.$.province,window.regionConf)},sendModifyAddress:function(e){this.__modify_address_sn=e,this.request("changeOrderAddress")},changeOrderAddressArgs:function(){var e=this,s={};s.order_sn=e.__modify_address_sn,s.national=e.$.nation.val()||0,s.province=e.$.province.val()||0,s.city=e.$.city.val()||0,s.area=e.$.area.val()||0,s.street=e.$.town.val()||0;var r=[];r.push(e.$.nation[0]?$(e.$.nation[0].selectedOptions).html():""),r.push(e.$.province[0]?$(e.$.province[0].selectedOptions).html():""),r.push(e.$.city[0]?$(e.$.city[0].selectedOptions).html():""),r.push(e.$.area[0]?$(e.$.area[0].selectedOptions).html():""),r.push(e.$.town[0]?$(e.$.town[0].selectedOptions).html():"");var t=r.join(" - ");return t=t.replace(/ - 请选择/g,""),t=t.replace(/ - /g,"-"),s.address=t+" "+e.$.address.val(),s.fullname=e.$.fullname.val()||0,s.mobile=e.$.mobile.val()||0,s.tel=e.$.tel.val()||0,{type:"post",data:s,done:cobra.ride(this,function(s){return e._msgBox.info(s.responseInfo.reasons.msg),e.request("getOrderDetail"),s}),fail:function(s){arguments.length>1&&e._msgBox.warn(s.responseBody.responseInfo.reasons.msg)}}},cancelModifyAddress:function(){this.$.modifyAddressBox.children().show(),this.$.modifyAddressBox.children(".hiddenForm").hide()},sendShip:function(){var e=this,s=arguments[arguments.length-1],r=e.$.order_detail_sn.data("sn"),t=s.data("ship"),n=e.$["express_input_"+t].val();if(""==$.trim(n))return e._msgBox.warn("请填写运单号"),!1;var i=$(".order_goods_item"),o=[];i.each(function(e,s){var r=$(s).find(".selectItem");r.length>0&&r[0].checked&&o.push($(r[0]).data("item-id"))});var a={order_sn:r,delivery_sn:n,express_id:t,order_deals:o};e.__sendShipData=a,e.request("doDelivery")},doDeliveryArgs:function(){var e=this,s=e.__sendShipData.order_sn;return{type:"post",data:e.__sendShipData,done:cobra.ride(this,function(r){return e._msgBox.info(r.responseInfo.reasons.msg),setTimeout(function(){window.location.href="/seller/seller_deal_related/order_detail.html?order_sn="+s},2e3),r}),fail:function(s){arguments.length>1&&e._msgBox.warn(s.responseBody.responseInfo.reasons.msg)}}},getSubAddressArgs:function(){var e=this;return{type:"post",data:{id:e.__selected_id},done:cobra.ride(this,function(s){return window.regionConf2=s,e.fill_address_area(),s}),fail:function(e){this._msgBox.warn(e.responseBody.responseInfo.reasons.msg)}}},fill_address_select:function(e,s){var r=s,t=e;t.html("<option value>请选择</option>"),$.each(r,function(e,s){t.append("<option value="+s.id+">"+s.name+"</option>")}),this.$.select_address_load.hide()},fill_address:function(e){this.$.select_address_load.show();var s=this,r=window.regionConf,t=arguments[1].val();if(s.__selected_id=t,""==s.__selected_id){this.$.select_address_load.hide();var n=$(arguments[arguments.length-1]).attr("level");return $.each($(".addressEdit"),function(e,s){parseInt($(s).attr("level"))>n&&$(s).hide(),console.log(e,s)}),!1}switch(e){case"city":var r=s.filter_region(window.regionConf,t);s.fill_address_select(s.$.city,r),s.$.city.show(),s.$.area.hide(),s.$.town.hide();break;case"area":s.__region_live="area",s.request("getSubAddress");break;case"town":s.__region_live="town",s.fill_address_area()}},fill_address_area:function(){var e=this,s=window.regionConf2;switch(e.__region_live){case"area":e.fill_address_select(e.$.area,s.data),e.$.city.show(),s.data.length&&e.$.area.show(),e.$.town.hide();break;case"town":var r=e.filter_region(s.data,e.__selected_id);e.fill_address_select(e.$.town,r),e.$.city.show(),e.$.area.show(),s.data.length&&e.$.town.show()}},filter_region:function(e,s){for(var r=e.length-1;r>=0;r--)if(e[r].id==s)return e[r].list}}),$(function(){new seller.order_ship_detail})}(cobra);