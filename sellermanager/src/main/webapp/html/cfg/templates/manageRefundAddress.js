cobra.add({manageAddress:'{{~it.data :value:index}}    <tr>        <td>{{=value.consignee}}</td>        <td>{{=value.regionName}}</td><td>{{=value.address}}</td><td class="c-999">{{=value.zipcode}}</td><td class="c-999">{{=value.mobile}}</td>{{? value.is_default==="1" }}<td class="c-999">默认地址</td>{{??}}<td class="c-999"><a data-id="56" href="javascript:;" class="blue action-default" cb-event="click~setDefault:{{=index}}">设为默认</a></td>{{?}}<td><a data-id="22" href="javascript:;" class="blue action-modify" cb-event="click~changeAddr:{{=index}}">修改</a>丨<a data-id="22" href="javascript:;" class="blue action-remove" cb-event="click~confirmDel:{{=index}}">删除</a></td></tr>             {{~}} '});