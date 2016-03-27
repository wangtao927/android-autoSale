package com.ys.ui.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品表
 * Created by wangtao on 2016/3/27.
 */
public class GoodsDo implements Serializable {
    /**
     * 主键ID
     */
    @SerializedName("gd_id")
    private String id;
    /**
     * 商品编号
     */
    @SerializedName("gd_no")
    private String no;

    /**
     * 商品编码
     */
    @SerializedName("gd_code")
    private String code;
    /**
     * 商品名称
     */
    @SerializedName("gd_name")
    private String name;
    /**
     * 商品类型
     */
    @SerializedName("gd_type")
    private String type;
    /**
     * 准字号
     */
    @SerializedName("gd_approve_code")
    private String approveCode;
    /**
     * 商品规格
     */
    @SerializedName("gd_spec")
    private String spec;
    /**
     * 生产厂家
     */
    @SerializedName("gd_manufacturer")
    private String manufacturer;
    /**
     * 条形码
     */
    @SerializedName("gd_barcode")
    private String barcode;
    /**
     * 商品系列
     */
    @SerializedName("gd_class")
    private String classtype;
    /**
     * 售价
     */
    @SerializedName("gd_sale_price")
    private Long salePrice;
    @SerializedName("gd_disc_price")
    private Long discPrice;
    @SerializedName("gd_vip_price")
    private Long vipPrice;
    @SerializedName("gd_score_price")
    private Long scorePrice;

    /**
     * 供应商编号
     */
    @SerializedName("gd_supplier")
    private Integer supplier;
    @SerializedName("gd_img1")
    private String img1;
    @SerializedName("gd_img2")
    private String img2;
    @SerializedName("gd_img3")
    private String img3;
    @SerializedName("gd_img4")
    private String img4;
    @SerializedName("gd_img_s")
    private String img5;
    /**
     * 说明书文件
     */
    @SerializedName("gd_instruction_file")
    private String instructionFile;
    /**
     * 说明书文件
     */
    @SerializedName("gd_desc")
    private String desc;

    /**
     * 商户号
     */
    @SerializedName("mer_no")
    private String merNo;

    @SerializedName("addtime")
    private Date addTime;
    @SerializedName("remark")
    private String remark;
    @SerializedName("updatetime")
    private Date updateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApproveCode() {
        return approveCode;
    }

    public void setApproveCode(String approveCode) {
        this.approveCode = approveCode;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getClasstype() {
        return classtype;
    }

    public void setClasstype(String classtype) {
        this.classtype = classtype;
    }

    public Long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Long salePrice) {
        this.salePrice = salePrice;
    }

    public Long getDiscPrice() {
        return discPrice;
    }

    public void setDiscPrice(Long discPrice) {
        this.discPrice = discPrice;
    }

    public Long getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(Long vipPrice) {
        this.vipPrice = vipPrice;
    }

    public Long getScorePrice() {
        return scorePrice;
    }

    public void setScorePrice(Long scorePrice) {
        this.scorePrice = scorePrice;
    }

    public Integer getSupplier() {
        return supplier;
    }

    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg4() {
        return img4;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }

    public String getImg5() {
        return img5;
    }

    public void setImg5(String img5) {
        this.img5 = img5;
    }

    public String getInstructionFile() {
        return instructionFile;
    }

    public void setInstructionFile(String instructionFile) {
        this.instructionFile = instructionFile;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "GoodsDo{" +
                "id='" + id + '\'' +
                ", no='" + no + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", approveCode='" + approveCode + '\'' +
                ", spec='" + spec + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", barcode='" + barcode + '\'' +
                ", classtype='" + classtype + '\'' +
                ", salePrice=" + salePrice +
                ", discPrice=" + discPrice +
                ", vipPrice=" + vipPrice +
                ", scorePrice=" + scorePrice +
                ", supplier=" + supplier +
                ", img1='" + img1 + '\'' +
                ", img2='" + img2 + '\'' +
                ", img3='" + img3 + '\'' +
                ", img4='" + img4 + '\'' +
                ", img5='" + img5 + '\'' +
                ", instructionFile='" + instructionFile + '\'' +
                ", desc='" + desc + '\'' +
                ", merNo='" + merNo + '\'' +
                ", addTime=" + addTime +
                ", remark='" + remark + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
