package com.dao.goods;

/**
 * 发货列表实体
 * @author: wql78
 * @date: 2020/10/18 11:48
 * @description:
 */

import java.util.Date;

/**
 * // 建表语句
 CREATE TABLE `delivery_list` (
 `id` bigint(20) NOT NULL,
 `check_num` bigint(20) DEFAULT NULL COMMENT '报检号',
 `check_date` date DEFAULT NULL COMMENT '报检日期',
 `goods` varchar(50) DEFAULT NULL COMMENT '货物名称',
 `origin` varchar(20) DEFAULT NULL COMMENT '原产国',
 `transport` varchar(50) DEFAULT NULL COMMENT '运输方式',
 `import_business` varchar(50) DEFAULT NULL COMMENT '进口商',
 `weight` double DEFAULT NULL COMMENT '调运重量',
 `update_time` datetime DEFAULT NULL COMMENT '更新时间',
 `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
 `apply_num` varchar(50) DEFAULT NULL COMMENT '申请单号',
 `receiving_unit` varchar(50) DEFAULT NULL COMMENT '收货单位',
 `goods_type` varchar(20) DEFAULT NULL COMMENT '货物品种（发货列表[加工粮这一列]）',
 `already_delivery_weight` double DEFAULT NULL COMMENT '已发货重量',
 `final_delivery_date` date DEFAULT NULL COMMENT '最后发货日期',
 `delivery_status` int(4) DEFAULT NULL COMMENT '发货状态',
 `status` int(4) DEFAULT NULL COMMENT '状态，预留字段',
 PRIMARY KEY (`id`),
 UNIQUE KEY `check_num_idex` (`check_num`) USING BTREE
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
public class Delivery {
    // 主键 id
    private Long id;
    // 报检号
    private Long checkNum;
    // 报检日期
    private Date checkDate;
    // 货物名称
    private String goods;
    // 原产国
    private String origin;
    // 运输方式
    private String transport;
    // 进口商
    private String importBusiness;
    // 调运重量
    private Double weight;
    // 更新时间
    private Date updateTime;
    // 更新人
    private Long updateBy;
    // 申请单号
    private String applyNum;
    // 收货单位
    private String receivingUnit;
    // 货物品种
    private String goodsType;
    // 已发货重量
    private Double alreadyDeliveryWeight;
    // 最后发货日期
    private Date finalDeliveryDate;
    // 发货状态
    private String deliveryStatus;
    // 状态 预留字段
    private int status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(String applyNum) {
        this.applyNum = applyNum;
    }

    public String getReceivingUnit() {
        return receivingUnit;
    }

    public void setReceivingUnit(String receivingUnit) {
        this.receivingUnit = receivingUnit;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public Double getAlreadyDeliveryWeight() {
        return alreadyDeliveryWeight;
    }

    public void setAlreadyDeliveryWeight(Double alreadyDeliveryWeight) {
        this.alreadyDeliveryWeight = alreadyDeliveryWeight;
    }

    public Date getFinalDeliveryDate() {
        return finalDeliveryDate;
    }

    public void setFinalDeliveryDate(Date finalDeliveryDate) {
        this.finalDeliveryDate = finalDeliveryDate;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Long getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(Long checkNum) {
        this.checkNum = checkNum;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getImportBusiness() {
        return importBusiness;
    }

    public void setImportBusiness(String importBusiness) {
        this.importBusiness = importBusiness;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", 报检号=" + checkNum +
                ", 报检日期=" + checkDate +
                ", 货物名称='" + goods + '\'' +
                ", 原产国='" + origin + '\'' +
                ", 运输方式='" + transport + '\'' +
                ", 进口商'" + importBusiness + '\'' +
                ", 调运重量=" + weight +
                ", 更新时间=" + updateTime +
                ", 更新人=" + updateBy +
                ", 申请单号='" + applyNum + '\'' +
                ", 接收单位='" + receivingUnit + '\'' +
                ", 货物品种='" + goodsType + '\'' +
                ", 已发货重量=" + alreadyDeliveryWeight +
                ", 最后发货日期=" + finalDeliveryDate +
                ", 发货状态='" + deliveryStatus + '\'' +
                ", 预留字段=" + status +
                '}';
    }
}
