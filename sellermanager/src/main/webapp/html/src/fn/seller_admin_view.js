!function(e){e._({"~name":"seller.sellerinfo","~superclass":e.base,ctor:function(){this.api="sellerinfo",this._super()},postCreate:function(){this.set("tmpl#adminviewtpl>admin_view","getAdminview")},getAdminviewArgs:function(){return{type:"post",data:{seller_id:"1"},done:cobra.ride(this,function(e){return $.when(this.compile(this.$.sub,"adminviewtpl>sub_view",e)).done(cobra.ride(this,function(){})),e}),fail:function(){this._msgBox.warn(data.responseBody.responseInfo.reasons.msg)}}}}),$(function(){new seller.sellerinfo})}(cobra);