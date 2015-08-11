//package test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.mockuai.itemcenter.client.ItemClient;
//import com.mockuai.itemcenter.common.api.Response;
//import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
//import com.mockuai.itemcenter.common.domain.dto.ItemImageDTO;
//import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
//import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;
//
//public class DubboTest {
//
//	public static void main(String args[]){
//
//		System.out.println("begin");
//
//		ApplicationContext ctx =new ClassPathXmlApplicationContext("spring-dubbo-consumer.xml");
//		long start = System.currentTimeMillis();
//		ItemClient client = (ItemClient)ctx.getBean("itemClient");
//		System.out.println("client is null " + (client ==null));
//
//		ItemDTO itemDTO = new ItemDTO();
//		itemDTO.setItemName("婴儿毛巾444");
//		itemDTO.setSupplierId(1L);
//		itemDTO.setItemBriefName("商品简称");
//		itemDTO.setItemBrandId(1);
//		itemDTO.setBriefIntroduction("商品简介");
//		itemDTO.setItemType(1);
//		itemDTO.setIconUrl("http://www.163.com/e.jpeg");
//		itemDTO.setCategoryId(1);
//		itemDTO.setIsSellerPost(1);
//
//		List<ItemSkuDTO> retItemSkuDTOList = new ArrayList<ItemSkuDTO>();
//		ItemSkuDTO itemSkuDTO = new ItemSkuDTO();
//		itemSkuDTO.setMaterialCode("aa");
//		itemSkuDTO.setBarCode("a");
//		itemSkuDTO.setMarketPrice(100L);
//		itemSkuDTO.setPromotionPrice(90L);
//		itemSkuDTO.setWirelessPrice(80L);
//		itemSkuDTO.setStockNum(1000L);
//		itemSkuDTO.setSoldNum(1L);
//
//		SkuPropertyDTO skuPropertyDTO1 = new SkuPropertyDTO();
//		skuPropertyDTO1.setKeyName("尺寸");
//		skuPropertyDTO1.setSort(1);
//		skuPropertyDTO1.setVal("16码");
//		skuPropertyDTO1.setValType(1);
//
//		SkuPropertyDTO skuPropertyDTO2 = new SkuPropertyDTO();
//		skuPropertyDTO2.setKeyName("颜色");
//		skuPropertyDTO2.setSort(2);
//		skuPropertyDTO2.setVal("红色");
//		skuPropertyDTO2.setValType(1);
//
//		List<SkuPropertyDTO> skuPropertyDTOList = new ArrayList<SkuPropertyDTO>();
//		skuPropertyDTOList.add(skuPropertyDTO1);
//		skuPropertyDTOList.add(skuPropertyDTO2);
//		itemSkuDTO.setSkuPropertyDTOList(skuPropertyDTOList);
//
//		ItemSkuDTO itemSkuDTO2 = new ItemSkuDTO();
//		itemSkuDTO2.setMaterialCode("bb");
//		itemSkuDTO2.setBarCode("b");
//		itemSkuDTO2.setMarketPrice(100L);
//		itemSkuDTO2.setPromotionPrice(90L);
//		itemSkuDTO2.setWirelessPrice(80L);
//		itemSkuDTO2.setStockNum(1000L);
//		itemSkuDTO2.setSoldNum(1L);
//
//		SkuPropertyDTO skuPropertyDTO3 = new SkuPropertyDTO();
//		skuPropertyDTO3.setKeyName("尺寸");
//		skuPropertyDTO3.setSort(2);
//		skuPropertyDTO3.setVal("17码");
//		skuPropertyDTO3.setValType(1);
//
//		SkuPropertyDTO skuPropertyDTO4 = new SkuPropertyDTO();
//		skuPropertyDTO4.setKeyName("颜色");
//		skuPropertyDTO4.setSort(1);
//		skuPropertyDTO4.setVal("蓝色");
//		skuPropertyDTO4.setValType(1);
//
//		List<SkuPropertyDTO> skuPropertyDTOList2 = new ArrayList<SkuPropertyDTO>();
//		skuPropertyDTOList2.add(skuPropertyDTO3);
//		skuPropertyDTOList2.add(skuPropertyDTO4);
//		itemSkuDTO2.setSkuPropertyDTOList(skuPropertyDTOList2);
//
//		retItemSkuDTOList.add(itemSkuDTO);
//		retItemSkuDTOList.add(itemSkuDTO2);
//
//		itemDTO.setItemSkuDTOList(retItemSkuDTOList);
//
//		ItemImageDTO itemImageDTO = new ItemImageDTO();
//		itemImageDTO.setImageName("测试狗11");
//		itemImageDTO.setImageType(2);
//		itemImageDTO.setImgUrl("http://www.163.com/a.jpeg");
//
//		ItemImageDTO itemImageDTO2 = new ItemImageDTO();
//		itemImageDTO2.setImageName("测试狗22");
//		itemImageDTO2.setImageType(2);
//		itemImageDTO2.setImgUrl("http://www.163.com/a.jpeg");
//		List<ItemImageDTO> itemImageDTOList = new ArrayList<ItemImageDTO>();
//		itemImageDTOList.add(itemImageDTO);
//		itemImageDTOList.add(itemImageDTO2);
//
//		itemDTO.setItemImageDTOList(itemImageDTOList);
//
//		Response<ItemDTO> resp = client.addItem(itemDTO);
//		//Response<UserDTO> resp = client.login("15000138726", "chen");
//		//Response<UserDTO> resp = client.register("15000138721", "chen");
//		//Response<SupplierExtraInfoDTO> resp = client.getSupplierExtraInfo(1L);
//
//		System.out.println(System.currentTimeMillis() - start);
//		System.out.println(resp);
//	}
//}
