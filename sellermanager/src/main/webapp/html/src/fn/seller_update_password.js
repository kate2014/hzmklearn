!function(s){s._({"~name":"seller.sellerinfo","~superclass":s.base,ctor:function(){this.api="sellerinfo",this._super()},submitPwdForm:function(){this.getSellerUptPwdArgs=function(){return{type:"post",data:{oldPasswd:this.$.old_passwd.val(),passwd:this.$.new_passwd.val(),passwdConfirm:this.$.new_passwd_repeat.val()},done:cobra.ride(this,function(s){this.clearInput(),this._msgBox.info(s.responseInfo.reasons.msg)}),fail:function(s){this.clearInput(),this._msgBox.warn(s.responseBody.responseInfo.reasons.msg)}}},this.validateForm()&&this.request("getSellerUptPwd")},clearInput:function(){this.$.old_passwd.val(""),this.$.new_passwd.val(""),this.$.new_passwd_repeat.val("")},validateForm:function(){return this.$.passwordForm.valid([{name:"old_passwd",type:"",simple:"旧密码"},{name:"new_passwd",type:"password",simple:"密码",min:8,max:20},{name:"new_passwd_repeat",type:"password",simple:"密码",min:8,max:20}])}}),$(function(){new seller.sellerinfo})}(cobra);