!function(t,e,n){function s(t){var n=t.$,s=(h.width()-n.outerWidth(!0))/2,o=(h.height()-n.outerHeight(!0))/(1+1.61803398875);n&&n.css(d?{left:e.documentElement.scrollLeft+s,top:e.documentElement.scrollTop+o}:{left:s,top:o})}function o(t){t.css({width:r.width(),height:r.height()})}function i(n){var o,i,l,a=this;t.isPlainObject(n)?t.each(c,function(t,e){!c.hasOwnProperty(t)||n.hasOwnProperty(t)&&typeof n[t]==typeof c[t]||(n[t]=e)}):n=c,t.each(n,function(t,e){a[t]=e}),a.isShow=!1,a.$=t(e.createElement("DIV")).addClass(a.warpperCssClass).css({width:a.width,height:a.height}),a.$title=t(e.createElement("DIV")).addClass(a.titleCssClass).html(a.title).appendTo(a.$),a.$contentOuter=t(e.createElement("DIV")).addClass(a.contentOuterCssClass).appendTo(a.$),a.$content=t(e.createElement("DIV")).addClass(a.contentCssClass).html(a.content).appendTo(a.$contentOuter),o=[],l=a.buttons.length,t.each(a.buttons,function(n,s){i=t(e.createElement("a")).addClass(a.buttonCssClass).attr("href",s.href||"javascript:;").html(s.value).on("click",function(t){s.click&&"function"==typeof s.click?s.click.call(this,t,a)!==!1&&a.hide():a.hide()}),l==n+1&&i.addClass(a.lastButtonCssClass),s.type&&i.addClass(a.otherTypeButtonCssClassPrefix+s.type),o.push(i)}),a.$buttonsArea=t(e.createElement("DIV")).addClass(a.buttonsAreaCssClass),a.$buttonsArea.append.apply(a.$buttonsArea,o).appendTo(a.$),a.closeX&&(a.$closeX=t(e.createElement("DIV")).addClass(a.closeXCssClass).appendTo(a.$),a.$closeX.html('<i class="iconfont">&#xf00b3;</i>'),a.$closeX.on("click",function(t){a.clickCloseX&&"function"==typeof a.clickCloseX?a.clickCloseX.call(this,t,a)!==!1&&a.hide():a.hide()})),s(a),h.on("resize",function(){s(a)}),d&&h.on("scroll",function(){s(a)}),a.$.appendTo(t("body")),a.init&&"function"==typeof a.init&&a.init.call(a),a.autoShow&&a.show()}function l(t){return new i(t)}var a,c={title:"消息",content:"",width:200,height:150,mask:!0,closeX:!1,autoShow:!0,clickCloseX:function(){},init:function(){},warpperCssClass:"miniDialog_wrapper",titleCssClass:"miniDialog_title",contentOuterCssClass:"miniDialog_content_outer",contentCssClass:"miniDialog_content",buttonsAreaCssClass:"miniDialog_buttons_area",buttonCssClass:"miniDialog_button",otherTypeButtonCssClassPrefix:"miniDialog_button_",lastButtonCssClass:"miniDialog_last_button",closeXCssClass:"miniDialog_close_x",buttons:[{value:"确定"}]},u={maskCssClass:"miniDialog_mask"},r=t(e),h=t(n),p=0,d=/msie 6/i.test(navigator.userAgent);i.prototype.show=function(){!this.isShow&&this.$&&(this.mask&&(a?a.show():(a=t(e.createElement("DIV")).addClass(u.maskCssClass),d&&(o(a),h.on("resize",function(){o(a)})),a.appendTo(t("body"))),p++),this.$.show(),this.isShow=!0)},i.prototype.hide=function(){this.isShow&&(this.isShow=!1,this.mask&&(p--,a&&0==p&&a.hide()),this.$&&this.$.hide())},i.prototype.remove=function(){this.hide(),this.$.remove()},i.prototype.setContent=function(t){this.$content.html(t)},i.prototype.getWrapper=function(){return this.$content},l.config={defaultOptions:c,otherOptions:u},l.alert=function(e,n,s,o){return new i(t.extend({content:e,buttons:[{value:"确定",click:function(t,e){n.call(this,t,e),o||e.remove()}}]},s))},l.confirm=function(e,n,s,o,l){return new i(t.extend({content:e,buttons:[{href:o.okHref,value:o.okValue||"确定",click:function(t,e){n.call(this,t,e),l||e.remove()}},{value:o.cancelValue||"取消",type:"secondary",click:function(t,e){s.call(this,t,e),l||e.remove()}}]},o))},l.prompt=function(e,n,s,o,l,a){var c="miniDialog_prompt_input",u="miniDialog_prompt_input";return new i(t.extend({content:['<div style="margin-bottom:5px">',e,'</div><div><input class="',c,'" cb-node="',u,'" style="width:175px;padding:6px 4px" value="',n,'"/></div>'].join(""),buttons:[{value:"确定",click:function(t,e){s.call(this,t,e,e.$content.find("input."+c).val()),a||e.remove()}},{value:"取消",type:"secondary",click:function(t,e){o.call(this,t,e,e.$content.find("input."+c).val()),a||e.remove()}}]},l))},n.miniDialog=l}($,document,window);