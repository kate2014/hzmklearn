cobra.add({type:"object",required:!0,properties:{statusCode:{type:"integer",required:!0},responseBody:{type:"object",required:!0,properties:{responseInfo:{type:"object",required:!0,properties:{reasons:{type:"object",required:!0,properties:{code:{type:"string",required:!0},type:{type:"integer",required:!0},msg:{type:"string",required:!0}}}}},data:{type:"array",required:!0,items:{type:"object",required:!1,properties:{price:{type:"string",required:!1},num:{type:"string",required:!1},outer_id:{type:"string",required:!1},origin_price:{type:"string",required:!1},prop:{type:"array",required:!1,items:{id:"#1",type:"object",required:!1,properties:{prop_id:{type:"string",required:!1},prop_name:{type:"string",required:!1},value_id:{type:"string",required:!1},value_name:{type:"string",required:!1}}}}}}}}}}});