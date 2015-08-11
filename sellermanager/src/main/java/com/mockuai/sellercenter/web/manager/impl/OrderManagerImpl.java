package com.mockuai.sellercenter.web.manager.impl;

import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.dto.PageDTO;
import com.mockuai.sellercenter.web.manager.OrderManager;
import com.mockuai.sellercenter.web.util.ExcelUtil;
import com.mockuai.tradecenter.client.OrderClient;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderDeliveryInfoDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.enums.EnumOrderStatus;
import com.mockuai.tradecenter.common.enums.EnumPaymentMethod;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class OrderManagerImpl
  implements OrderManager
{
  private OrderClient orderClient;

  public void setOrderClient(OrderClient orderClient)
  {
    this.orderClient = orderClient;
  }

  public Integer pendingDelivery(Long userId) throws ServiceException {
    return Integer.valueOf(1011);
  }

  public Response<List<OrderDTO>> queryOrder(OrderQTO query, String appKey)
    throws ServiceException
  {
    Response response = this.orderClient.queryOrder(query, appKey);
    if (response.getCode() != 10000) {
      int errorCode = Integer.valueOf(response.getCode()).intValue();
      throw new ServiceException(errorCode, response.getMessage());
    }
    return response;
  }

  public Boolean updateOrderPrice(long orderId, long userId, long freight, long floatingPrice, String appKey) throws ServiceException
  {
    Response response = this.orderClient.updateOrderPrice(orderId, userId, freight, floatingPrice, appKey);
    if (response.getCode() == 10000) {
      return (Boolean)response.getModule();
    }
    int errorCode = Integer.valueOf(response.getCode()).intValue();
    throw new ServiceException(errorCode, response.getMessage());
  }

  public Boolean cancelOrder(long orderId, long userId, String cancelReason, String appKey) throws ServiceException
  {
    Response response = this.orderClient.cancelOrder(orderId, userId, cancelReason, appKey);
    if (response.getCode() == 10000) {
      return (Boolean)response.getModule();
    }
    int errorCode = Integer.valueOf(response.getCode()).intValue();
    throw new ServiceException(errorCode, response.getMessage());
  }

  public Boolean deliveryGoods(long orderId, long userId, List<OrderDeliveryInfoDTO> list, String appKey)
    throws ServiceException
  {
    Response response = this.orderClient.deliveryGoods(orderId, userId, list, appKey);
    if (response.getCode() == 10000) {
      return (Boolean)response.getModule();
    }
    int errorCode = Integer.valueOf(response.getCode()).intValue();
    throw new ServiceException(errorCode, response.getMessage());
  }

//  public void downReportXls(HttpServletResponse response, OrderQTO query, String appkey)
//    throws ServiceException
//  {
//    response.setContentType("application/vnd.ms-excel");
//    String fileName = null;
//    try {
//      fileName = "订单报表";
//      response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso8859-1") + ".xls");
//      LinkedHashMap hederMap = new LinkedHashMap();
//
//      List autoSizeColumns = new ArrayList();
//      hederMap.put("0", "订单号");
//      hederMap.put("1", "商品名称");
//      hederMap.put("2", "单价");
//      hederMap.put("3", "买家");
//      hederMap.put("4", "下单时间");
//      hederMap.put("5", "订单状态");
//      hederMap.put("6", "支付方式");
//      hederMap.put("7", "实付金额");
//
//      for (int i = 2; i < 9; i++) {
//        autoSizeColumns.add(Integer.valueOf(i));
//      }
//
//      int rowTitleIndex = 0;
//      int rowContextHeight = 14;
//
//      HSSFWorkbook workbook = new HSSFWorkbook();
//
//      HSSFSheet sheet = ExcelUtil.createSheet(workbook, fileName, 10);
//
//      HSSFFont fontContext = ExcelUtil.createFont(workbook, "宋体", (short)400, (short)11);
//
//      HSSFCellStyle styleContext = ExcelUtil.createCellStyle(workbook, (short)400, fontContext);
//
//      HSSFCellStyle styleMoney = ExcelUtil.createMoneyCellStyle(workbook, (short)400, fontContext);
//
//      HSSFRow titleRow = ExcelUtil.createRow(sheet, rowTitleIndex, rowContextHeight);
//
//      for (Iterator headerIterator = hederMap.entrySet().iterator(); headerIterator.hasNext(); ) {
//        Map.Entry propertyEntry = (Map.Entry)headerIterator.next();
//        ExcelUtil.createCell(titleRow, Integer.parseInt((String)propertyEntry.getKey()), styleContext, propertyEntry.getValue());
//      }
//      int currentRow = 1;
//
//      query.setCount(Integer.valueOf(500));
//
//      query.setOffset(Integer.valueOf(0));
//
//      Response res = queryOrder(query, appkey);
//
//      PageDTO pageInfo = new PageDTO();
//      pageInfo.setData(res.getModule());
//      pageInfo.setTotalCount(Long.valueOf(res.getTotalCount()));
//
//      Long pageCount = pageInfo.getTotalCount();
//
//      List list = new ArrayList();
//
//      for (int tt = 1; tt <= pageCount.longValue(); tt++) {
//        query.setCount(Integer.valueOf(2000));
//        query.setOffset(Integer.valueOf((tt - 1) * 2000));
//        res = queryOrder(query, appkey);
//        list = (List)res.getModule();
//        if ((list == null) || (list.size() == 0)) {
//          break;
//        }
//        for (OrderDTO o : list)
//        {
//          List orderItems = o.getOrderItems();
//
//          int startRow = currentRow;
//
//          for (OrderItemDTO oItem : orderItems)
//          {
//            HSSFRow workingRow = ExcelUtil.createRow(sheet, currentRow, rowContextHeight);
//
//            ExcelUtil.createCell(workingRow, 0, styleContext, o.getOrderSn() + "");
//            ExcelUtil.createCell(workingRow, 1, styleContext, oItem.getItemName() + "");
//            ExcelUtil.createCell(workingRow, 2, styleMoney, oItem.getUnitPrice().toString());
//            ExcelUtil.createCell(workingRow, 3, styleMoney, oItem.getUserName());
//            ExcelUtil.createCell(workingRow, 4, styleMoney, o.getOrderTimeStr());
//            ExcelUtil.createCell(workingRow, 5, styleMoney, EnumOrderStatus.getByCode(o.getOrderStatus() + "").getDescription());
//            ExcelUtil.createCell(workingRow, 6, styleMoney, EnumPaymentMethod.getByCode(o.getPaymentId() + "").getDescription());
//            ExcelUtil.createCell(workingRow, 7, styleMoney, o.getTotalAmountStr());
//            currentRow++;
//          }
//
//          ExcelUtil.mergeCell(sheet, startRow, currentRow - 1, 0, 0);
//
//          ExcelUtil.mergeCell(sheet, startRow, currentRow - 1, 7, 7);
//        }
//
//      }
//
//      for (Integer i : autoSizeColumns) {
//        sheet.autoSizeColumn(i.intValue());
//        int width = sheet.getColumnWidth(i.intValue());
//
//        sheet.setColumnWidth(i.intValue(), width + 1000);
//      }
//      ExcelUtil.writeWorkbook(workbook, response);
//    } catch (Exception e) {
//      throw new ServiceException(99999, e.getMessage());
//    }
//  }

  public Boolean updateOrderSellerMemo(long orderId, long userId, String memo, String appkey)
    throws ServiceException
  {
    Response response = this.orderClient.updateOrderSellerMemo(orderId, userId, memo, appkey);
    if (response.getCode() == 10000) {
      return (Boolean)response.getModule();
    }
    int errorCode = Integer.valueOf(response.getCode()).intValue();
    throw new ServiceException(errorCode, response.getMessage());
  }

  public Boolean updateAsteriskMark(long orderId, long userId, String asteriskMark, String appkey)
    throws ServiceException
  {
    Response response = this.orderClient.updateOrderAsteriskMark(orderId, userId, asteriskMark, appkey);
    if (response.getCode() == 10000) {
      return (Boolean)response.getModule();
    }
    int errorCode = Integer.valueOf(response.getCode()).intValue();
    throw new ServiceException(errorCode, response.getMessage());
  }

  public Response<OrderDTO> getOrder(Long orderId, Long userId, String appkey)
    throws ServiceException
  {
    Response response = this.orderClient.getOrder(orderId, userId, appkey);
    if (response.getCode() == 10000) {
      return response;
    }
    int errorCode = Integer.valueOf(response.getCode()).intValue();
    throw new ServiceException(errorCode, response.getMessage());
  }
}