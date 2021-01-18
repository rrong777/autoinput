package com.dao.goods;

import java.util.Date;
import java.util.List;

/**
 * @author: wql78
 * @date: 2020/10/18 22:05
 * @description: 发货详情Bean
 * // 建表语句
 * CREATE TABLE `delivery_details` (
 *   `check_num` bigint(20) DEFAULT NULL COMMENT '报检号',
 *   `check_date` datetime DEFAULT NULL COMMENT '报检日期',
 *   `ciq` bigint(50) DEFAULT NULL COMMENT 'CIQ编码',
 *   `licence` varchar(50) DEFAULT NULL COMMENT '许可证号',
 *   `goods_ch_name` varchar(50) DEFAULT NULL COMMENT '货物中文名称',
 *   `goods_varieties` varchar(50) DEFAULT NULL COMMENT '货物品种',
 *   `weight` double DEFAULT NULL COMMENT '重量（吨）',
 *   `transport_weight` double DEFAULT NULL COMMENT '调运重量（吨）',
 *   `origin` varchar(20) DEFAULT NULL COMMENT '原产国',
 *   `delivery_weight` double DEFAULT NULL COMMENT '发货总重量',
 *   `transport_type` varchar(20) DEFAULT NULL COMMENT '运输方式',
 *   `transport_tools` varchar(20) DEFAULT NULL COMMENT '运输工具',
 *   `transport_tools_num` int(11) DEFAULT NULL COMMENT '运输工具号码',
 *   `delivery_unit_name` varchar(50) DEFAULT NULL COMMENT '发货单位名称',
 *   `delivery_unit_pos` varchar(50) DEFAULT NULL COMMENT '发货单位地址',
 *   `receive_unit_name` varchar(50) DEFAULT NULL COMMENT '接收单位名称',
 *   `receive_unit_pos` varchar(50) DEFAULT NULL COMMENT '接收单位地址',
 *   `transport_plan` varchar(1000) DEFAULT NULL COMMENT '运输计划',
 *   `delivery_status` varchar(50) DEFAULT NULL COMMENT '发货状态',
 *   `apply_num` varchar(50) DEFAULT NULL COMMENT '申请单号',
 *   `id` bigint(20) NOT NULL COMMENT '主键id',
 *   PRIMARY KEY (`id`),
 *   UNIQUE KEY `apply_num_idx` (`apply_num`) USING BTREE COMMENT '申请单号唯一键'
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
public class DeliveryDetails {
    private List<AutoInputDetails> autoInputDetailsList;

    public List<AutoInputDetails> getAutoInputDetailsList() {
        return autoInputDetailsList;
    }

    public void setAutoInputDetailsList(List<AutoInputDetails> autoInputDetailsList) {
        this.autoInputDetailsList = autoInputDetailsList;
    }

    // 主键id
    private Long id;
    // 申请单号
    private String applyNum;
    // 发货状态
    private String deliveryStatus;
    // 运输计划
    private String transportPlan;
    // 接收单位地址
    private String receiveUnitPos;
    // 接收单位名称
    private String receiveUnitName;
    // 发货单位地址
    private String deliveryUnitPos;
    // 发货单位名称
    private String deliveryUnitName;
    // 运输工具号码
    private String transportToolsNum;
    // 运输工具
    private String transportTools;
    // 运输方式
    private String transportType;
    // 发货总重量
    private Double deliveryWeight;
    // 原产国
    private String origin;
    // 调运重量
    private Double transportWeight;
    // 重量
    private Double weight;
    // 货物品种
    private String goodsVarieties;
    // 货物中文名称
    private String goodsChName;
    // 许可证号
    private String licence;
    // CIQ编码
    private Long ciq;
    // 报检日期
    private Date checkDate;
    // 报检号
    private Long checkNum;

    @Override
    public String toString() {
        return "DeliveryDetails{" +
                "id=" + id +
                ", 申请号码='" + applyNum + '\'' +
                ", 发货状态='" + deliveryStatus + '\'' +
                ", 运输计划='" + transportPlan + '\'' +
                ", 接收单位地址='" + receiveUnitPos + '\'' +
                ", 接收单位名称='" + receiveUnitName + '\'' +
                ", 发货单位地址='" + deliveryUnitPos + '\'' +
                ", 发货单位名称='" + deliveryUnitName + '\'' +
                ", 运输工具号码=" + transportToolsNum +
                ", 运输工具='" + transportTools + '\'' +
                ", 运输方式='" + transportType + '\'' +
                ", 发货总重量=" + deliveryWeight +
                ", 原产国='" + origin + '\'' +
                ", 运输总重量=" + transportWeight +
                ", 重量=" + weight +
                ", 货物品种='" + goodsVarieties + '\'' +
                ", 货物中文名称='" + goodsChName + '\'' +
                ", 许可证号='" + licence + '\'' +
                ", CIQ编码=" + ciq +
                ", 报检日期=" + checkDate +
                ", 报检号=" + checkNum +
                '}';
    }

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

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getTransportPlan() {
        return transportPlan;
    }

    public void setTransportPlan(String transportPlan) {
        this.transportPlan = transportPlan;
    }

    public String getReceiveUnitPos() {
        return receiveUnitPos;
    }

    public void setReceiveUnitPos(String receiveUnitPos) {
        this.receiveUnitPos = receiveUnitPos;
    }

    public String getReceiveUnitName() {
        return receiveUnitName;
    }

    public void setReceiveUnitName(String receiveUnitName) {
        this.receiveUnitName = receiveUnitName;
    }

    public String getDeliveryUnitPos() {
        return deliveryUnitPos;
    }

    public void setDeliveryUnitPos(String deliveryUnitPos) {
        this.deliveryUnitPos = deliveryUnitPos;
    }

    public String getDeliveryUnitName() {
        return deliveryUnitName;
    }

    public void setDeliveryUnitName(String deliveryUnitName) {
        this.deliveryUnitName = deliveryUnitName;
    }

    public String getTransportToolsNum() {
        return transportToolsNum;
    }

    public void setTransportToolsNum(String transportToolsNum) {
        this.transportToolsNum = transportToolsNum;
    }

    public String getTransportTools() {
        return transportTools;
    }

    public void setTransportTools(String transportTools) {
        this.transportTools = transportTools;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public Double getDeliveryWeight() {
        return deliveryWeight;
    }

    public void setDeliveryWeight(Double deliveryWeight) {
        this.deliveryWeight = deliveryWeight;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Double getTransportWeight() {
        return transportWeight;
    }

    public void setTransportWeight(Double transportWeight) {
        this.transportWeight = transportWeight;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getGoodsVarieties() {
        return goodsVarieties;
    }

    public void setGoodsVarieties(String goodsVarieties) {
        this.goodsVarieties = goodsVarieties;
    }

    public String getGoodsChName() {
        return goodsChName;
    }

    public void setGoodsChName(String goodsChName) {
        this.goodsChName = goodsChName;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public Long getCiq() {
        return ciq;
    }

    public void setCiq(Long ciq) {
        this.ciq = ciq;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Long getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(Long checkNum) {
        this.checkNum = checkNum;
    }
}
