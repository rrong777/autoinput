package com.service.goods;

import com.dao.goods.Delivery;
import com.dao.goods.DeliveryDetails;
import com.dao.goods.DetailsParams;
import com.model.http.HttpResult;
import com.model.page.PageRequest;
import com.model.page.PageResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author: wql78
 * @date: 2020/10/18 11:59
 * @description:
 */
public interface DeliveryService {
    PageResult list(PageRequest pageRequest);
    Integer batchInsert(List<Delivery> deliveryList);
    Integer batchInsertDetails(List<DeliveryDetails> deliveryDetails);
    DeliveryDetails getDetailsByApplyNum(String applyNum);
    List findDeliveryDetails(Map params);
    Integer add(Map params);
    HttpResult listGrantUser(PageRequest pageRequest);
    Integer deleteUser(Integer id);
    Integer addAutoInputDetails(List<Map<String,String>> params);
    Integer alterStatus(String applyNum, String sendDate, Double sendWeight, String plateNo);
}
