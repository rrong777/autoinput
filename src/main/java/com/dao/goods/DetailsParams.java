package com.dao.goods;

/**
 * 自动录入请求参数实体
 * @author: wql78
 * @date: 2020/11/22 13:45
 * @description:
 */
public class DetailsParams {
    // 申请单号
    private String InspectionNumber;

    // 发货时间
    private String DeliveryTime;

    // 运输工具号码
    private String VehicleNumber;

    // 配载数量
    private Integer Pcs;

    // 发货数量
    private Double Weight;

    public DetailsParams() {
    }

    @Override
    public String toString() {
        return "DetailsParams{" +
                "InspectionNumber='" + InspectionNumber + '\'' +
                ", DeliveryTime='" + DeliveryTime + '\'' +
                ", VehicleNumber='" + VehicleNumber + '\'' +
                ", Pcs=" + Pcs +
                ", Weight=" + Weight +
                '}';
    }

    public DetailsParams(String inspectionNumber, String deliveryTime, String vehicleNumber, Integer pcs, Double weight) {
        InspectionNumber = inspectionNumber;
        DeliveryTime = deliveryTime;
        VehicleNumber = vehicleNumber;
        Pcs = pcs;
        Weight = weight;
    }

    public String getInspectionNumber() {
        return InspectionNumber;
    }

    public void setInspectionNumber(String inspectionNumber) {
        InspectionNumber = inspectionNumber;
    }

    public String getDeliveryTime() {
        return DeliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        DeliveryTime = deliveryTime;
    }

    public String getVehicleNumber() {
        return VehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        VehicleNumber = vehicleNumber;
    }

    public Integer getPcs() {
        return Pcs;
    }

    public void setPcs(Integer pcs) {
        Pcs = pcs;
    }

    public Double getWeight() {
        return Weight;
    }

    public void setWeight(Double weight) {
        Weight = weight;
    }
}
