!function(o){o._({"~name":"goods.import","~superclass":o.base,__rnd:0,ctor:function(){this.api="goods",this._super()},__si:0,__q:0,postCreate:function(){this.$.loading.show(),this.request("getAccountprofile")},getAccountprofileArgs:function(){return{type:"post",data:{seller_id:"1"},done:cobra.ride(this,function(o){return this.$.loading.hide(),console.log(o),o.data&&o.data.sid&&(this.__sid=o.data.sid),this.createUploadXLS(),o}),fail:function(o){this.$.loading.hide(),this._msgBox.warn(o.responseBody.responseInfo.reasons.msg)}}},modifyGoods:function(o,t){window.location.href="../seller_goods_management/goods_modify.html?id="+o+"&modify="+$.trim(t)},deleteGoods:function(o){var t=this;miniDialog.confirm('<span style="color:red;font-weight:700;">确认要删除该商品?</span>',function(){t.goods_delArgs=null,t.goods_delArgs=function(){return{type:"get",data:{id:o},done:cobra.ride(t,function(o){this._msgBox.done(o.responseInfo.reasons.msg),setTimeout(function(){window.location.reload()},800)}),fail:function(o){arguments.length>1&&this._msgBox.info(o.responseBody.responseInfo.reasons.msg)}}},t.request("goods_del")},function(){},{title:"唯一优品提醒您",width:300})},createUploadXLSOpt:function(){var o=this,t={formData:{timestamp:(new Date).getTime(),sid:o.__sid?o.__sid:""},fileSizeLimit:"3MB",buttonClass:"uploadify-button uploadify-button2",width:80,height:28,fileTypeDesc:"请选择Excel表格文件（.xls）",fileTypeExts:"*.xls",multi:!1,swf:o.host[o.host.use]+"/seller/src/plugin/uploadify/uploadify.swf",uploader:o.host[o.host.use]+"/seller_goods-upload_goods.html",buttonText:"导入商品",button_placeholder:"",onUploadProgress:function(){this.button.html("正在导入...")},onUploadError:function(){this.queueData.queueBytesUploaded=0,o.$.loading.hide(),clearInterval(o.__si),this.button.html("再次导入"),o._msgBox.error("上传失败，请稍后重试")},onUploadStart:function(){o.$.loading.show(),o.__si=setInterval(function(){o.__q++;for(var t="",e=o.__q%8;e>0;e--)t+=". ";o.$.loading.html('<p><i class="iconfont">󰃄</i> 正在导入商品，可能需要较长时间，请勿关闭页面'+t+"</p>")},500),this.queueData.queueBytesUploaded=0},onUploadSuccess:function(){o.$.loading.hide(),clearInterval(o.__si),this.queueData.queueBytesUploaded=0;try{var t=$.parseJSON(arguments[1]);console.log(arguments),console.log(t),this.button.html("再次导入");var e={};e.total=t.responseBody.data.count,e.suc=t.responseBody.data.suc,e.fail=t.responseBody.data.fail,e.errs=t.responseBody.data.errors,e.sucs=t.responseBody.data.sucs,$.when(o.compile(o.$.import_table,"goods>goods_import_result",e)).done(cobra.ride(o,function(){})),$(".import_result_desc.fl").html(o.parse("共${total}条记录，${suc}条记录成功，${fail}条记录失败",e))}catch(s){console.log(s),o.$.loading.hide(),clearInterval(o.__si),this.button.html("再次导入"),o._msgBox.error("上传失败，请稍后重试")}}};return t},createUploadXLS:function(){this.$.uploadXls.uploadify(this.createUploadXLSOpt())}}),$(function(){new goods.import})}(cobra);