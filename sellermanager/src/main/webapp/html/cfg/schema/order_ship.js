cobra.add({type:"object",required:!0,properties:{responseBody:{type:"object",required:!0,properties:{data:{type:"object",required:!1,properties:{orders:{type:"object",required:!1,items:{type:"object",required:!1,properties:{address:{type:"string",required:!1},consignee:{type:"string",required:!1},delivery_fee:{type:"number",required:!1},delivery_status:{type:"string",required:!1},id:{type:"string",required:!1},item:{type:"array",required:!1,items:{type:"object",required:!1,properties:{attr_str:{type:"string",required:!1},is_returned:{type:"string",required:!1},item_id:{type:"string",required:!1},name:{type:"string",required:!1},number:{type:"string",required:!1},origin_price:{type:"number",required:!1},returned_status:{type:"string",required:!1},unit_price:{type:"number",required:!1}}}},mobile:{type:"string",required:!1},order_sn:{type:"string",required:!1},order_status:{type:"string",required:!1},seller_memo:{type:"string",required:!1},time_create:{type:"string",required:!1},time_pay:{type:"string",required:!1},total_price:{type:"number",required:!1}}}}}},pagesize:{type:"number",required:!1},responseInfo:{type:"object",required:!0,properties:{reasons:{type:"object",required:!0,properties:{code:{type:"string",required:!0},msg:{type:"string",required:!0},type:{type:"number",required:!0}}}}},total:{type:"number",required:!1}}},statusCode:{type:"number",required:!0}}});