cobra.add({martshow_list:'        {{?it.data.length>0}}            {{~ it.data :item:index}}            <tr>                <td>{{=item.id}}</td>                <td>{{=item.name}}</td>                <td>{{?item.time_begin=="0000-00-00 00:00:00" }}等待排期{{?}}</td>                <td>{{?item.time_end=="0000-00-00 00:00:00" }}等待排期{{?}}</td>                <td>{{=item.create_time}}</td>                <td class="status1">{{?item.status=="0"}}等待审核{{?? item.status=="1"}}审核通过{{?? item.status=="2"}}审核不通过{{??}}已结束{{?}}</td>                <td class="ui-table-action"><a href="/seller/martshow/items.html?mart_id={{=item.id}}">查看专场</a></td>            </tr>            {{~}}        {{??}}             <tr colspan="8" class="no-result"><td>暂无符合条件的订单记录</td></tr>        {{?}}',select_brand_list:'        {{?it.data.length>0}}            {{~ it.data :item:index}}            <option value="{{=item.id}}">{{=item.name}}</option>            {{~}}        {{??}}            <option>请先添加品牌</option>        {{?}}    ',qq_list:'        <p class="mt-8 multi_qq clearfix">            <input name="kf_qq[]" class="ui-input" type="text" value="">            <a href="javascript:;" class="del-icon del-record" cb-event="click~del_qq"><i class="iconfont"></i></a>        </p>    ',goods_list_pageination:'{{? it.page-1>0}}<a href="javascript:;" cb-event="click~goPage:{{= it.page-1}}">&lt; 上一页</a>{{?}}    {{? it.page-2>0}}<a href="javascript:;" cb-event="click~goPage:{{= it.page-2}}">{{= it.page-2}}</a>{{?}}    {{? it.page-1>0}}<a href="javascript:;" cb-event="click~goPage:{{= it.page-1}}">{{= it.page-1}}</a>{{?}}    {{? it.page && it.page>0}}<span class="current" cb-node="page_current">{{= it.page}}</span>{{?}}    {{? it.page+1<=it.total_pages}}<a href="javascript:;" cb-event="click~goPage:{{= it.page+1}}">{{= it.page+1}}</a>{{?}}    {{? it.page+2<=it.total_pages}}<a href="javascript:;" cb-event="click~goPage:{{= it.page+2}}">{{= it.page+2}}</a>{{?}}    {{? it.page+1<=it.total_pages}}<a href="javascript:;" cb-event="click~goPage:{{= it.page+1}}">下一页  &gt;</a>{{?}}    <span>{{? parseInt(it.total) ==0 }}当前0项{{?? parseInt(it.total) <= it.pagesize}}当前{{=it.total}}项{{?? it.total_pages > it.page }}当前{{= it.pagesize}}项{{?? parseInt(it.total) % it.pagesize == 0}}当前{{=it.pagesize}}项{{??}}当前{{= parseInt(it.total) % it.pagesize}}项{{?}} - {{? it.total}}共{{=it.total}}项{{?}}</span>',SubAddress:""});