package com.ys.dao;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Index;
import de.greenrobot.daogenerator.Schema;

public class DaoGenerate {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.ys.gen.data.bean");
        schema.setDefaultJavaPackageDao("com.ys.gen.data.dao");

//        addAd(schema);
//        addGoods(schema);
        //addMCGoods(schema);
//        addMCParams(schema);
//        addMCStatus(schema);
//        addMcAdmin(schema);
//        addPromotion(schema);
        addSaleList(schema);


        new DaoGenerator().generateAll(schema, "E:/code/github/android-autoSale/ui/src/main/java-gen");
    }

    private static void addAd(Schema schema) {
        Entity adv = schema.addEntity("AdvBean");

        adv.setTableName("adv");
        adv.addStringProperty("ai_id").primaryKey();
        adv.addStringProperty("ai_file_id");// 文件名称
        adv.addStringProperty("ai_file_name");
        adv.addStringProperty("ai_type");
        adv.addStringProperty("ai_gd_no");



    }

    private static void addMcAdmin(Schema schema) {
        Entity mcParamBean = schema.addEntity("McAdminBean");
        mcParamBean.setTableName("mcadmin");
        mcParamBean.addStringProperty("u_no").primaryKey();//机身号
        mcParamBean.addStringProperty("u_pwd");

    }

    /**
     * @param schema
     */
    private static void addGoods(Schema schema) {
        // 一个实体（类）就关联到数据库中的一张表，此处表名为「Note」（既类名）
        Entity goodsBean = schema.addEntity("GoodsBean");
        goodsBean.setTableName("goods");
        // 你也可以重新给表命名
        // note.setTableName("NODE");

        // greenDAO 会自动根据实体类的属性值来创建表字段，并赋予默认值
        // 接下来你便可以设置表中的字段：
        goodsBean.addStringProperty("gd_no").primaryKey();
        goodsBean.addStringProperty("gd_code");
        goodsBean.addStringProperty("gd_short_name");
        goodsBean.addStringProperty("gd_name");
        goodsBean.addStringProperty("gd_type");
        goodsBean.addStringProperty("gd_ty_class");
        goodsBean.addStringProperty("gd_pm_class");
        goodsBean.addStringProperty("gd_nm_class");
        goodsBean.addStringProperty("gd_approve_code");
        goodsBean.addStringProperty("gd_spec");
        goodsBean.addStringProperty("gd_brand");
        goodsBean.addStringProperty("gd_manufacturer"); // 生产厂家
        goodsBean.addStringProperty("gd_saleunit");
        goodsBean.addStringProperty("gd_barcode");

        goodsBean.addLongProperty("gd_sale_price");
        goodsBean.addLongProperty("gd_disc_price");
        goodsBean.addLongProperty("gd_vip_price");
        goodsBean.addLongProperty("gd_score_price");
        goodsBean.addLongProperty("gd_supplier");
        goodsBean.addStringProperty("gd_img1");
        goodsBean.addStringProperty("gd_img2");
        goodsBean.addStringProperty("gd_img3");
        goodsBean.addStringProperty("gd_img4");
        goodsBean.addStringProperty("gd_img_s");
        goodsBean.addStringProperty("gd_instruction_file");
        goodsBean.addStringProperty("gd_desc");
        goodsBean.addStringProperty("gd_url");
        goodsBean.addStringProperty("gd_desc1"); // 配方
        goodsBean.addStringProperty("gd_desc2");// 功能主治
        goodsBean.addStringProperty("gd_desc3");// 用法用量
        goodsBean.addStringProperty("gd_desc4");
        goodsBean.addStringProperty("gd_desc5");
        goodsBean.addStringProperty("mer_no");
        goodsBean.addStringProperty("remark");
        goodsBean.addStringProperty("addtime");
        goodsBean.addStringProperty("updatetime");




    }

    private static void addMCGoods(Schema schema) {
        Entity mcGoodsBean = schema.addEntity("McGoodsBean");
        mcGoodsBean.setTableName("mcgoods");

//        mcGoodsBean.addLongProperty("mg_id").primaryKey();

        mcGoodsBean.addStringProperty("mc_no");//终端号
        mcGoodsBean.addStringProperty("mg_channo").primaryKey();//货道编号
        mcGoodsBean.addStringProperty("gd_no");//商品编码
        mcGoodsBean.addStringProperty("gd_type");//商品类型
        mcGoodsBean.addStringProperty("gd_approve_code");
        mcGoodsBean.addStringProperty("gd_batch_no");
        mcGoodsBean.addStringProperty("gd_des_code");
        mcGoodsBean.addStringProperty("gd_mf_date");
        mcGoodsBean.addStringProperty("gd_exp_date");
        mcGoodsBean.addLongProperty("mg_gvol");//商品容量
        mcGoodsBean.addLongProperty("mg_gnum");//商品存量
        mcGoodsBean.addLongProperty("mg_pre_price"); // prePrice 原价
        mcGoodsBean.addLongProperty("mg_score_price"); // 积分价
        mcGoodsBean.addLongProperty("mg_vip_price");  // 会员价
        mcGoodsBean.addLongProperty("mg_disc_price"); // 折扣价

        mcGoodsBean.addLongProperty("mg_price"); // 销售单价
        mcGoodsBean.addLongProperty("mg_chann_status"); // 默认不卡货   1 正常  2 卡货
        mcGoodsBean.addDateProperty("addtime");
        mcGoodsBean.addDateProperty("updatetime");


    }

    private static void addMCParams(Schema schema) {
        Entity mcParamBean = schema.addEntity("McParamsBean");
        mcParamBean.setTableName("mcparams");

//        mcParamBean.addLongProperty("mp_id").autoincrement();
        mcParamBean.addStringProperty("pcode").primaryKey();
        mcParamBean.addStringProperty("pvalue");
    }

    private static void addMCStatus(Schema schema) {
        Entity mcParamBean = schema.addEntity("McStatusBean");
        mcParamBean.setTableName("mcstatus");
        mcParamBean.addStringProperty("mc_no").primaryKey();//终端号
        mcParamBean.addStringProperty("mc_serial_no");//机身号
        mcParamBean.addStringProperty("mr_coin_status");//DEFAULT '0' COMMENT '硬币器状态：''0''-正常，''1''-异常。',
        mcParamBean.addStringProperty("mr_coin_short");//DEFAULT NULL COMMENT '硬币预警',
        mcParamBean.addStringProperty("mr_bill_status");//DEFAULT '0' COMMENT '纸币器状态：''0''-正常，''1''-异常。',
        mcParamBean.addStringProperty("mr_bill_short");
        mcParamBean.addStringProperty("mr_uppos_status");
        mcParamBean.addStringProperty("mr_scpos_status");
        mcParamBean.addStringProperty("mr_alipay_status");
        mcParamBean.addStringProperty("mr_wxpay_status");
        mcParamBean.addStringProperty("mr_net_status");
        mcParamBean.addStringProperty("mr_temp");
        mcParamBean.addStringProperty("mr_door_isfault");
        mcParamBean.addStringProperty("mr_door_status");
        mcParamBean.addLongProperty("mr_chann_fault_num");
        mcParamBean.addStringProperty("mr_chann_fault_nos");
        mcParamBean.addLongProperty("mr_nogd_chnum");
        mcParamBean.addStringProperty("mr_nogd_chann");
        mcParamBean.addLongProperty("mr_gear_fault_num");
        mcParamBean.addStringProperty("mr_gear_fault_nos");
        mcParamBean.addLongProperty("mr_data_fault");
        mcParamBean.addDateProperty("mr_door_date");
        mcParamBean.addStringProperty("mr_mc_position");
    }


    private static void addPromotion(Schema schema) {
        Entity mcParamBean = schema.addEntity("PromotionBean");
        mcParamBean.setTableName("promotion");
        mcParamBean.addLongProperty("pt_id").primaryKey();
        mcParamBean.addStringProperty("pt_name");
        mcParamBean.addStringProperty("pt_msg");
        mcParamBean.addStringProperty("pt_desc");
        mcParamBean.addStringProperty("pt_status");
        mcParamBean.addStringProperty("pt_starttime");
        mcParamBean.addStringProperty("pt_endtime");
        mcParamBean.addStringProperty("pt_range");
        mcParamBean.addStringProperty("pt_rangeparam");
        mcParamBean.addStringProperty("pt_goods");
        mcParamBean.addStringProperty("pt_goodsparam");
        mcParamBean.addStringProperty("pt_condition");
        mcParamBean.addStringProperty("pt_conditionparam");
        mcParamBean.addStringProperty("pt_type");
        mcParamBean.addStringProperty("pt_typeparam");
        mcParamBean.addStringProperty("remark");
        mcParamBean.addStringProperty("addtime");
        mcParamBean.addStringProperty("updatetime");
    }
    private static void addSaleList(Schema schema) {
        Entity mcParamBean = schema.addEntity("SaleListBean");
        mcParamBean.setTableName("salelist");
        mcParamBean.addStringProperty("sl_no").primaryKey(); //终端号
        mcParamBean.addStringProperty("sl_batch_no");// 批次号
        mcParamBean.addStringProperty("sl_time");//交易时间
        mcParamBean.addStringProperty("mc_no");// 终端号
        mcParamBean.addStringProperty("sl_gd_no");// 商品编码
        mcParamBean.addStringProperty("sl_gd_name");//商品名称
        mcParamBean.addLongProperty("sl_pre_price");//原价
        mcParamBean.addLongProperty("sl_disc_price");// 折扣价
        mcParamBean.addLongProperty("sl_vip_price");//会员价

        mcParamBean.addLongProperty("sl_amt");//交易金额
        mcParamBean.addLongProperty("sl_score");//小费积分
        mcParamBean.addLongProperty("sl_cash_in");//投纸币金额
        mcParamBean.addLongProperty("sl_cash_out");//找零纸币金额
        mcParamBean.addStringProperty("sl_coin_in");
        mcParamBean.addStringProperty("sl_coin_out");
        mcParamBean.addStringProperty("sl_chann");//货道编号
        mcParamBean.addLongProperty("sl_num"); //销售数量
        mcParamBean.addStringProperty("sl_type");//付款方式：1-现金、2-银行卡、3-微信支付，4-支付宝，5-积分兑换，6-提货码
        mcParamBean.addStringProperty("sl_isvip");// 是否会员，0-否，1-是
        //mcParamBean.addStringProperty("sl_status");//交易状态：1-已下单，2-已支付，3-已出货，4-已退款，5-支付失败，6-出货失败
        mcParamBean.addStringProperty("sl_err_msg");
        mcParamBean.addStringProperty("sl_acc_no");
        mcParamBean.addLongProperty("sl_bf_amt");
        mcParamBean.addLongProperty("sl_af_amt");
        mcParamBean.addLongProperty("sl_send_status");//上报状态 0 未上报 1 已上报
        mcParamBean.addStringProperty("sl_pay_status");//支付状态：1-待支付，2-支付中，3-支付成功，4-支付失败，5-已退款，6-订单撤销
        mcParamBean.addStringProperty("sl_out_status");//出货状态：1-待出货，2-出货成功，3-出货失败

    }

    }
