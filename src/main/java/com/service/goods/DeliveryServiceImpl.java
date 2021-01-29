package com.service.goods;

import com.dao.goods.Delivery;
import com.dao.goods.DeliveryDetails;
import com.dao.goods.DeliveryMapper;
import com.dao.goods.DetailsParams;
import com.model.http.HttpResult;
import com.model.page.MybatisPageHelper;
import com.model.page.PageRequest;
import com.model.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: wql78
 * @date: 2020/10/18 11:58
 * @description:
 */
@Service
public class DeliveryServiceImpl implements DeliveryService {
    @Autowired
    private DeliveryMapper deliveryMapper;
    @Override
    public HttpResult listGrantUser(PageRequest pageRequest) {
        return HttpResult.ok(MybatisPageHelper.findPage(pageRequest, deliveryMapper, "listGrantUser", pageRequest.getParams()));
    }


    @Override
    public PageResult list(PageRequest pageRequest) {
        Integer status = (Integer) pageRequest.getParam("status");
        return MybatisPageHelper.findPage(pageRequest, deliveryMapper, "list", pageRequest.getParams());
    }



    @Override
    public Integer deleteUser(Integer id) {
        return deliveryMapper.deleteUser(id);
    }

    @Override
    public Integer addAutoInputDetails(List<Map<String,String>> params) {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return deliveryMapper.addAutoInputDetails(params, time);
    }

    @Override
    public Integer alterStatus(String applyNum, String sendDate, Double sendWeight, String plateNo) {
        return deliveryMapper.alterStatus(applyNum, sendDate, sendWeight, plateNo);
    }

    @Override
    public Integer batchInsert(List<Delivery> deliveryList) {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return deliveryMapper.batchInsert(deliveryList, time);
    }

    @Override
    public Integer batchInsertDetails(List<DeliveryDetails> deliveryDetails) {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return deliveryMapper.batchInsertDetails(deliveryDetails, time);
    }

    @Override
    public DeliveryDetails getDetailsByApplyNum(String applyNum) {
        return deliveryMapper.getDetailsByApplyNum(applyNum);
    }

    @Override
    public List findDeliveryDetails(Map params) {
        String applyNum = (String)params.get("applyNum");

        return deliveryMapper.findDeliveryDetails(applyNum);
    }

    @Override
    public Integer add(Map params) {
        return deliveryMapper.add(params);
    }


}
