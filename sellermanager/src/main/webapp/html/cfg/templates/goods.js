cobra.add({category:'<div class="pa-category-list view-CategoryList" style="display: block;">                    <ul op-each-item="categoryListItem" op-each="category">                        {{~it.data :obj:index}}                        <li cb-node="cs_{{=obj.id}}" class="view-CategoryListItem" cb-event="click~_showCategory:{{=obj.id}},{{=obj.cate_level-0+1}},{{=obj.cate_name}}" style="display: list-item;"><span>{{=obj.cate_name}}</span>                            <span op-value="nextIcon">{{? obj.hasChildren == 1}}<i class="iconfont">󰅭</i>{{?}}</span>                        </li>                       {{~}}                    </ul></div>',brandList:'<option value="">-请选择-</option>                  {{~it.data :brand:index}}                  <option value="{{=brand.id}}">{{=brand.brand_name}}</option>                  {{~}}',addtionalPI:'{{~it.data :product:index}}<div class="ui-form-item" cb-id="{{=product.id}}" cb-name="{{=product.name}}">                        <label for="" class="ui-label"><span {{? product.must == 1}}class="ui-form-required">*{{?? product.must == 0}}>{{??}}{{?}}</span>{{=product.name}}:</label>                        {{? product.user_defined == 1}}                        <input class="ui-input {{? product.must == 1}}require{{?}}" type="text" cb-node="{{=product.name}}" cb-type="0" />{{?}}                        {{? product.user_defined == 0 && product.multi == 0}}                        <select class="small ui-input ui-input-select" cb-node="{{=product.name}}" cb-type="1" {{? product.must == 1}}class="require"{{?}}>                            <option value="">-请选择-</option>                            {{~product.prop_values.value :prop:idx}}                            <option value="{{=product.prop_values.vid[idx]}}">{{=prop}}</option>                            {{~}}                        </select>{{?}}                        {{? product.user_defined == 0 && product.multi == 1}}                        <ul cb-node="{{=product.name}}" cb-type="2" class="product-add-checkbox {{? product.must == 1}}require{{?}}">                            {{~product.prop_values.value :mp:j}}                            <li style="display: inline-block;">                                <label>                                    <input type="checkbox" value="{{=product.prop_values.vid[j]}}" cb-name="{{=mp}}"><span>{{=mp}}</span>                                </label>                            </li>{{~}}                        </ul>                        {{?}}                    </div>{{~}}',salesProps:'{{~it.data :sale:index}}<div class="ui-form-item view-InputItem view-MultipleSelectInput"  cb-id="{{=sale.id}}" cb-name="{{=sale.name}}">                        <label class="ui-label"><span {{? sale.must == 1}}class="ui-form-required">*{{?? sale.must == 0}}>{{??}}{{?}}</span>{{=sale.name}}:{{? index==0}}<p>图片属性</p><p>(最多只能选20个)</p>{{??}}<p>(最多只能选15个)</p>{{?}}</label>                        <ul class="product-add-checkbox" cb-node="{{=sale.name}}" cb-type="2" cb-name="{{=index}}">                            {{~sale.prop_values.value :property:t}}                            <li style="display: inline-block;"><label>{{? index==0 && it.colors[property] }}<div class="color-tag" style="background:{{=it.colors[property]}}"></div>{{?}}<input type="checkbox" value="{{=sale.prop_values.vid[t]}}" cb-name="{{=property}}" cb-index="{{=index}}" cb-event="click~doSku:{{=sale.name}},{{=property}},{{=sale.prop_values.vid[t]}},{{=index}}"><span>{{=property}}</span></label>                            <a href="javascript:;" style="display: none;" cb-node="wth_{{=sale.prop_values.vid[t]}}" cb-event="click~customize:{{=sale.name}},{{=property}},{{=sale.prop_values.vid[t]}},{{=index}}"><i class="iconfont">󰅏</i></a></li>{{~}}                        </ul>                     </div>{{~}}',skuImageUpload:'{{? it}}<div class="product-add-imgbox view-ImgUploadBox" style="display: inline-block;" cb-node="{{=it.id}}" cb-name="{{=it.color}}" cb-id="{{=it.rId}}">                                 <div cb-name="{{=it.color}}" cb-id="{{=it.rId}}" cb-index="{{=it.i}}">                                    <div class="upload-img uploadify" style="width:120px; height:120px;" data-type="sku">                                        <div class="uploadify-button" id="{{=it.id}}" cb-node="{{=it.id}}_uploadify" cb-name="{{=it.color}}" style="width:120px; height:120px;line-height: 120px"></div>                                    </div>                                 </div>                             </div>{{?}}',commonImageUpload:'{{~it :num:index}}<div class="product-add-imgbox view-ImgUploadBox" style="display: inline-block; vertical-align: middle">                                 <div cb-index="{{=num}}" cb-name="通用" cb-id="0" cb-node="commonImageBox-{{=num}}" >                                    <div class="upload-img uploadify" style="width:120px; height:120px;" data-type="common">                                        <div class="uploadify-button" style="width:120px; height:120px;line-height: 120px">                                            <span class="uploadify-button-text"><i class="iconfont">󰃷</i>添加图片</span>                                        </div>                                    </div>                                    <div class="cancel-common-upload" style="display: none; text-align: center"><a href="javascript:;">取消添加</a></div>                                 </div>                             </div>{{~}}',skuHead:'<table class="ui-table product-add-sku" style="display: table;" cb-node="skuEditorHelper">                    <thead>                        <tr>                        {{~it :item:index}}<th style="display: table-cell;" cb-name="{{=item.name}}">{{=item.name}}</th>{{~}}                        <th style="display: table-cell;" cb-name="原价">原价</th>                        <th style="display: table-cell;" cb-name="现价">现价</th>                        <th style="display: table-cell;" cb-name="库存">库存</th>                        <th style="display: table-cell;" cb-name="物料码">条形码</th>                        </tr>                    </thead>                    <tbody cb-node="skuBodyHelper"></tbody>                </table>',skuItem:"{{ for(var key in it) { }}<tr cb-node=\"{{=key}}\">                    {{~it[key] :n:idx}}<td cb-id=\"{{=n.id}}\" cb-name=\"{{=n.name}}\">{{=n.vn}}</td>{{~}}                    <td>                        <input data-inputmask=\"'alias': 'numeric', 'groupSeparator': ',', 'autoGroup': true, 'digits': 2, 'digitsOptional': false, 'prefix': '￥', 'placeholder': '0', 'rightAlign' : false, 'autoUnmask' : true\" name=\"origin_price\" type=\"text\">                    </td>                    <td>                        <input data-inputmask=\"'alias': 'numeric', 'groupSeparator': ',', 'autoGroup': true, 'digits': 2, 'digitsOptional': false, 'prefix': '￥', 'placeholder': '0', 'rightAlign' : false, 'autoUnmask' : true\" name=\"price\" type=\"text\">                    </td>                    <td>                        <input name=\"num\" type=\"text\">                   </td>                   <td>                        <input name=\"barcode\" type=\"text\">                   </td>                 </tr>{{ } }}",batchBox:'<div class="batch-fill-box view-SkuHelperBatchFillBox">                    <h2>选择需要填充的销售属性</h2>                    <fieldset class="batch-fill-sellprop">                    {{~it :item:index}}                    <div class="pure-control-group view-SkuHelperBatchFillGroup" style="display: block;" cb-node="{{=item.name}}_skubpv">                        <label style="width : 75px;"><span>{{=item.name}}</span><br><a href="javascript:;" class="view-SkuHelperBatchFillCheckAll" cb-event="click~checkAllItems:{{=item.name}},{{=index}}">全选</a></label>                        <ul class="batch-fill-checklist" style="width: 400px;">                            {{~item.val :property:idx}}<li style="display: inline-block;">                                <label><input type="checkbox" value="{{=property.id}}" cb-name="{{=property.name}}" cb-id="{{=property.id}}" cb-event="click~tcbCheck:{{=index}},{{=item.name}},{{=property.id}},{{=property.name}}"><span>{{=property.vn}}</span></label>                            </li>{{~}}                        </ul>                    </div>{{~}}                    </fieldset>                    <h2 class="second">填充商品内容</h2>                    <p class="note">留空的内容不会被填充</p>                    <fieldset class="batch-fill-content">                        <div class="pure-control-group">                            <label for="originPrice">原价</label>                            <input data-inputmask="\'alias\': \'numeric\', \'groupSeparator\': \',\', \'autoGroup\': true, \'digits\': 2, \'digitsOptional\': false, \'prefix\': \'￥\', \'placeholder\': \'0\', \'rightAlign\' : false, \'autoUnmask\' : true" id="origin_price" type="text" name="origin_price" placeholder="" cb-node="b_origin_price">                        </div>                         <div class="pure-control-group">                            <label for="price">特卖价格</label>                            <input data-inputmask="\'alias\': \'numeric\', \'groupSeparator\': \',\', \'autoGroup\': true, \'digits\': 2, \'digitsOptional\': false, \'prefix\': \'￥\', \'placeholder\': \'0\', \'rightAlign\' : false, \'autoUnmask\' : true" id="price" type="text" name="price" placeholder="" cb-node="b_price">                        </div>                        <div class="pure-control-group">                            <label for="num">库存</label>                            <input id="num" type="text" name="num" placeholder="" cb-node="b_num">                         </div>                         <div class="pure-control-group">                            <label for="outerId">条形码</label>                            <input id="outerId" type="text" name="barcode" placeholder="" cb-node="b_barcode">                         </div>                    </fieldset>                 </div>',customizeTpl:'{{? it}}<div class="sellprop-custom">                        <div class="sellprop-custom-topbox">                            <h3>自定义销售属性</h3>                            <p>请选择相近的属性进行自定义，例如：自定义“鲜花红”时，选择“红色”进行自定义，这样用户搜索“红色”时“鲜花红”也可以被搜索到。<span style="color:#E62D8B;">（限制30个字符，中间不能包含空格或者:）</span></p>                        </div>                        <div class="sellprop-custom-list view-SellpropCustomList" style="height: 60px;">                            <div class="sellprop-custom-item" style="display: inline-block;">                                <p>{{=it.p}}</p>                                <ul>                                    <li style="display: inline-block;">                                        <input type="text" value="{{=it.n}}" class="focus" cb-node="fku_{{=it.i}}" style="width : 150px;"/>                                        <a href="javascript:;" title="重设默认值" cb-name="{{=it.n}}" cb-event="click~reset:{{=it.rn}}"><i class="iconfont">󰅜</i></a>                                    </li>                                </ul>                            </div>                        </div>                      </div>{{?}}',goods_list_item:'{{~ it.data.data :item:index}}        <tr data-id="{{=item.id}}">            <td class="product-list-img"><img {{? item.icon_url}}data-src="{{=item.icon_url}}"{{?}} src="http://s1.ve.cn/statics/dot.gif" alt="">            </td>            <td>{{=item.id}}</td>            <td class="product-list-title">{{=item.item_name}}</td>            <td>{{=item.brand_name || "-"}}</td>            <td class="product-list-category">{{=item.category_name}}</td>            <td class="no-break">￥{{=item.market_price && item.market_price/100 || "-"}}</td>            <td class="no-break">￥{{=item.promotion_price && item.promotion_price/100 || "-"}}</td>            <td>{{= item.create_time && item.create_time.replace(" ", "<br>")}}</td>            <td><span class="{{? item.item_status==1}}approve{{?? item.item_status==0}}pending{{??}}not-approve{{?}}">{{=item.status_name || ((item.item_status==5) ? "已下架" : (item.item_status==4) ? "已上架" : "-")}}</span>{{? item.item_status==3 && item.audit_msg}}<br/><span title="{{=item.audit_msg}}" style="cursor: pointer;">查看原因</span>{{?}}</td>            <td>                <a href="http://m.yangdongxi.com/detail.html?item_uid={{=item.item_uid}}" target="_blank">查看</a>  丨                <a href="javascript:;" class="copy-btn" data-clipboard-text="http://m.yangdongxi.com/detail.html?item_uid={{=item.item_uid}}">复制链接</a>  丨                 <a target="_blank" href="{{="../seller_goods_management/goods_modify.html?id=" + item.id + "&modify=" + (item.is_sale === undefined ? 0 : item.is_sale)}}">修改</a>  |                 {{? item.item_status==4}}<a class="j-up-down" href="javascript:;" data-type="down" data-id="{{=item.id}}">下架</a> | {{?? item.item_status==5}}<a class="j-up-down" href="javascript:;" data-type="up" data-id="{{=item.id}}">上架</a> | {{?}}                <a href="javascript:;" cb-event="click~deleteGoods:{{=item.id}}">删除</a>             </td>        </tr>    {{~}}',goods_list_item_review:'{{~ it.data.data :item:index}}        <tr data-id="{{=item.id}}">            <td class="product-list-img"><img {{? item.icon==""}}src="http://s1.ve.cn/statics/dot.gif"{{?? item.icon!=""}}src="{{=item.icon}}"{{??}}{{?}} alt="">            </td>            <td>{{=item.id}}</td>            <td class="product-list-title">{{=item.name}}</td>            <td>{{=item.brand_name}}</td>            <td class="no-break">￥{{=item.origin_price}}</td>            <td class="no-break">￥{{=item.current_price}}</td>            <td>{{? item.create_time}}console.log(item.create_time); item.create_time.replace(" ","<br/>"){{?}}</td>            <td><span class="{{? item.status==1}}approve{{?? item.status==0}}pending{{??}}not-approve{{?}}">{{=item.status_name}}</span>{{? item.status==2}}<br/><span title="{{=item.audit_msg}}" style="cursor: pointer;">查看原因</span>{{?}}</td>            <td>                <a href="#" target="_blank">查看</a> 丨                <a href="javascript:;" cb-event="click~modifyGoods:{{=item.id}},{{=item.is_sale === undefined ? 0 : item.is_sale}},{{=item.draftId}}">修改</a> 丨                <a href="javascript:;" cb-event="click~deleteGoods:{{=item.id}}">删除</a>            </td>        </tr>    {{~}}',goods_list_pageination:'{{? it.page-1>0}}<a href="javascript:;" cb-event="click~goPage:{{= it.page-1}}">&lt; 上一页</a>{{?}}    {{? it.page-2>0}}<a href="javascript:;" cb-event="click~goPage:{{= it.page-2}}">{{= it.page-2}}</a>{{?}}    {{? it.page-1>0}}<a href="javascript:;" cb-event="click~goPage:{{= it.page-1}}">{{= it.page-1}}</a>{{?}}    {{? it.page && it.page>0}}<span class="current" cb-node="page_current">{{= it.page}}</span>{{?}}    {{? it.page+1<=it.total_pages}}<a href="javascript:;" cb-event="click~goPage:{{= it.page+1}}">{{= it.page+1}}</a>{{?}}    {{? it.page+2<=it.total_pages}}<a href="javascript:;" cb-event="click~goPage:{{= it.page+2}}">{{= it.page+2}}</a>{{?}}    {{? it.page+1<=it.total_pages}}<a href="javascript:;" cb-event="click~goPage:{{= it.page+1}}">下一页  &gt;</a>{{?}}    <span>{{? parseInt(it.total) ==0 }}当前0项{{?? parseInt(it.total) <= it.pagesize}}当前{{=it.total}}项{{?? it.total_pages > it.page }}当前{{= it.pagesize}}项{{?? parseInt(it.total) % it.pagesize == 0}}当前{{=it.pagesize}}项{{??}}当前{{= parseInt(it.total) % it.pagesize}}项{{?}} - {{? it.total}}共{{=it.total}}项{{?}}</span>',goods_search_result_item:'{{~ it.data:item:index}}    <div class="list hover" cb-event="click~select_search_result:{{= item.name}}">{{= item.name}}</div>    {{~}}',brand_key:'<option value="">请选择品牌</option>{{~it.data:val:inx}}<option value="{{=val.id}}">{{=val.brand_name}}</option>{{~}}',goods_import_result:'   {{?it && it.errs && it.errs.length>0}}                                {{~it.errs:value:idx}}                                    <tr>                                        <td><!--商品名称-->{{?value.name}}{{=value.name}}{{??}}--/--{{?}}</td>                                        <td><!--商品品牌-->{{?value.brand}}{{=value.brand}}{{??}}--/--{{?}}</td>                                        <td><!--状态-->--/--</td>                                        <td><!--结果-->导入失败</td>                                        <td><!--备注-->{{?value.cause}}{{=value.cause}}{{??}}--/--{{?}}</td>                                    </tr>                                {{~}}                            {{??}}                                <tr><td colspan="5" style="height: 80px;">请导入商品</td></tr>                            {{?}}',goods_draft_list_item:'{{~ it.data :item:index}}        <tr data-id="{{=item.id}}">            <td class="product-list-img">{{?item.icon&&item.icon!=""}}<img {{? item.icon}}data-src="{{=item.icon}}"{{?}} src="http://s1.ve.cn/statics/dot.gif" alt="">{{?}}            </td>            <td class="product-list-title">{{?item.name&&item.name!=""}}{{=item.name}}{{??}}--{{?}}</td>            <td>{{?item.brand_name&&item.brand_name!=""}}{{=item.brand_name}}{{??}}--{{?}}</td>            <td class="product-list-category">{{?item.cate_name&&item.cate_name!=""}}{{=item.cate_name}}{{??}}--{{?}}</td>            <td>{{?item.origin_price&&item.origin_price!=""}}￥{{=item.origin_price}}{{??}}--{{?}}</td>            <td>{{?item.current_price&&item.current_price!=""}}￥{{=item.current_price}}{{??}}--{{?}}</td>            <td>{{?item.create_time&&item.create_time!=""}}{{=item.create_time.replace(" ","<br/>")}}{{??}}--{{?}}</td>            <td>                <a href="javascript:;" cb-event="click~modifyGoods:{{=item.draftId}}">修改</a>  丨                <a href="javascript:;" cb-event="click~deleteGoods:{{=item.draftId}}">删除</a>            </td>        </tr>    {{~}}'});