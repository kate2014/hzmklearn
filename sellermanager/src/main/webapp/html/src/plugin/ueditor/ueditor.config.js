!function(){function e(e,i){return r(e||self.document.URL||self.location.href,i||t())}function t(){var e=document.getElementsByTagName("script");return e[e.length-1].src}function r(e,t){var r=t;return/^(\/|\\\\)/.test(t)?r=/^.+?\w(\/|\\\\)/.exec(e)[0]+t.replace(/^(\/|\\\\)/,""):/^[a-z]+:/i.test(t)||(e=e.split("#")[0].split("?")[0].replace(/[^\\\/]+$/,""),r=e+""+t),i(r)}function i(e){var t=/^[a-z]+:\/\//.exec(e)[0],r=null,i=[];for(e=e.replace(t,"").split("?")[0].split("#")[0],e=e.replace(/\\/g,"/").split(/\//),e[e.length-1]="";e.length;)".."===(r=e.shift())?i.pop():"."!==r&&i.push(r);return t+i.join("/")}var n=window.UEDITOR_HOME_URL||e();window.UEDITOR_CONFIG={UEDITOR_HOME_URL:n,serverUrl:"http://b.taojae.com/service.php",toolbars:[["undo","redo","|","bold","italic","underline","strikethrough","forecolor","|","removeformat","|","insertunorderedlist","paragraph","|","justifyleft","justifycenter","justifyjustify","|","simpleupload"]],labelMap:{anchor:"",undo:"",simpleupload:"上传图片",insertimage:"多图上传"},lang:"zh-cn",langPath:n+"lang/",charset:"utf-8",isShow:!0,initialFrameWidth:750,initialFrameHeight:400,pasteplain:!0,insertunorderedlist:{circle:"",disc:"",square:""},elementPathEnabled:!1,wordCount:!1,maximumWords:5e4,autoHeightEnabled:!1},window.UE={getUEBasePath:e}}();