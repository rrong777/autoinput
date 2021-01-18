package com.dao.goods;

import com.model.page.PageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: wql78
 * @date: 2020/10/18 11:53
 * @description:
 */
public interface DeliveryMapper {
    Map<String,String> getUserPasw();
    // 分页查询
    List<Delivery> list(@Param("params") Map params);
    Integer batchInsert(@Param("list")List<Delivery> deliveryList, @Param("time") String time);
    Integer batchInsertDetails(@Param("list")List<DeliveryDetails> deliveryDetails, @Param("time")String time);
    DeliveryDetails getDetailsByApplyNum(String applyNum);
    List<AutoInputDetails> findDeliveryDetails(String applyNum);
    Integer add(Map params);
    List<Map> listGrantUser(@Param("params")Map params);
    Integer deleteUser(Integer id);
    Integer addAutoInputDetails(@Param("params") List<Map<String,String>> params,@Param("time") String time);
    List<DetailsParams> getAutoInputDetails(@Param("createTime")String createTime);
}
