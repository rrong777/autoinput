package com.controller.goods;

import com.model.http.HttpResult;
import com.model.page.PageRequest;
import com.service.goods.DeliveryService;
import com.utils.AutomationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author: wql78
 * @date: 2020/10/18 11:57
 * @description: 发货控制器
 */
@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @PostMapping("/list")
    public HttpResult list(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(deliveryService.list(pageRequest));
    }



    @PostMapping("/add")
    public HttpResult add(@RequestBody Map params) {
        return HttpResult.ok(deliveryService.add(params));
    }

    @PostMapping("/listGrantUser")
    public HttpResult listGrantUser(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(deliveryService.listGrantUser(pageRequest));
    }

    @GetMapping("/deleteUser")
    public HttpResult deleteUser(@RequestParam Integer id) {
        return HttpResult.ok(deliveryService.deleteUser(id));
    }
    @PostMapping("/findDeliveryDetails")
    public HttpResult findDeliveryDetails(@RequestBody Map params) {
        return HttpResult.ok(deliveryService.findDeliveryDetails(params));
    }

    @GetMapping("/getDetails")
    public HttpResult getDetails(@RequestParam String applyNum) {
        return HttpResult.ok(deliveryService.getDetailsByApplyNum(applyNum));
    }


    @GetMapping("/crawl")
    public void crawl() {
        AutomationUtils automationUtils = new AutomationUtils(deliveryService);
        automationUtils.crawlDeliveryList(null, null);
    }
    @GetMapping("/hello")
    public String hello() {
        return "helloWorld!";
    }


    /**
     * 增加自动发货明细
     */
//    @PostMapping("/addAutoInputDetails")
//    public HttpResult addAutoInputDetails(@RequestBody List<DetailsParams> params) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Map<String, List<DetailsParams>> autoInputMaps = new HashMap<>();
//                for(int i = 0;i < params.size();i++) {
//                    DetailsParams tmp = params.get(i);
//                    List<DetailsParams> tmpList = autoInputMaps.get(tmp.getInspectionNumber());
//                    if(tmpList==null) {
//                        tmpList = new ArrayList<>();
//                        autoInputMaps.put(tmp.getInspectionNumber(), tmpList);
//                    }
//                    tmpList.add(tmp);
//                }
//                AutomationUtils automationUtils = new AutomationUtils(deliveryService);
//                automationUtils.autoInput(autoInputMaps, params.size());
//            }
//        }).start();
//        return HttpResult.ok("开始自动录入！");
//    }
}
