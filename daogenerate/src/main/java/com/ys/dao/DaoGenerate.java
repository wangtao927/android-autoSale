package com.ys.dao;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Index;
import de.greenrobot.daogenerator.Schema;

public class DaoGenerate {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.ys.data.bean");
        schema.setDefaultJavaPackageDao("com.ys.data.dao");

        addGoods(schema);
        addMCGoods(schema);
        addMCParams(schema);
        addMCStatus(schema);
        addPromotion(schema);
        addSaleList(schema);

         new DaoGenerator().generateAll(schema, "E:/code/github/android-autoSale/ui/src/main/java-gen");
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
        goodsBean.addIntProperty("gd_id").primaryKey();
        goodsBean.addStringProperty("gd_no").index();
        goodsBean.addStringProperty("gd_code");
        goodsBean.addStringProperty("gd_name");
        goodsBean.addStringProperty("gd_type");
        goodsBean.addStringProperty("gd_approve_code");
        goodsBean.addStringProperty("gd_spec");
        goodsBean.addStringProperty("gd_manufacturer");
        goodsBean.addStringProperty("gd_barcode");
        goodsBean.addStringProperty("gd_class");
        goodsBean.addLongProperty("gd_sale_price");
        goodsBean.addLongProperty("gd_disc_price");
        goodsBean.addLongProperty("gd_vip_price");
        goodsBean.addLongProperty("gd_score_price");
        goodsBean.addIntProperty("gd_supplier");
        goodsBean.addStringProperty("gd_img1");
        goodsBean.addStringProperty("gd_img2");
        goodsBean.addStringProperty("gd_img3");
        goodsBean.addStringProperty("gd_img4");
        goodsBean.addStringProperty("gd_img_s");
        goodsBean.addStringProperty("gd_instruction_file");
        goodsBean.addStringProperty("gd_desc");
        goodsBean.addStringProperty("mer_no");
        goodsBean.addStringProperty("remark");
        goodsBean.addStringProperty("addtime");
        goodsBean.addStringProperty("updatetime");




    }

    private static void addMCGoods(Schema schema) {
        Entity mcGoodsBean = schema.addEntity("McGoodsBean");
        mcGoodsBean.setTableName("mcgoods");

        mcGoodsBean.addIntProperty("mg_id").primaryKey();

        mcGoodsBean.addStringProperty("mc_no");
        mcGoodsBean.addStringProperty("mg_channo").index().unique();
        mcGoodsBean.addStringProperty("gd_id");
        mcGoodsBean.addStringProperty("gd_no");
        mcGoodsBean.addStringProperty("gd_type");
        mcGoodsBean.addStringProperty("gd_approve_code");
        mcGoodsBean.addStringProperty("gd_batch_no");
        mcGoodsBean.addStringProperty("gd_des_code");
        mcGoodsBean.addDateProperty("gd_mf_date");
        mcGoodsBean.addDateProperty("gd_exp_date");
        mcGoodsBean.addIntProperty("mg_gvol");
        mcGoodsBean.addIntProperty("mg_gnum");
        mcGoodsBean.addIntProperty("prePrice");
        mcGoodsBean.addIntProperty("scorePrice");
        mcGoodsBean.addIntProperty("mg_vip_price");
        mcGoodsBean.addIntProperty("mg_price");
        mcGoodsBean.addIntProperty("chanStatus");
        mcGoodsBean.addDateProperty("addtime");
        mcGoodsBean.addDateProperty("updatetime");


    }

    private static void addMCParams(Schema schema) {
        Entity mcParamBean = schema.addEntity("McParamsBean");
        mcParamBean.setTableName("mcparams");

        mcParamBean.addLongProperty("mp_id").autoincrement();
        mcParamBean.addStringProperty("p_code").primaryKey();
        mcParamBean.addStringProperty("mcp_pvalue");
    }

    private static void addMCStatus(Schema schema) {
        Entity mcParamBean = schema.addEntity("McStatusBean");
        mcParamBean.setTableName("mcstatus");
        mcParamBean.addIntProperty("mr_id").primaryKey();
        mcParamBean.addStringProperty("mc_no");
        mcParamBean.addStringProperty("mr_coin_status");
        mcParamBean.addStringProperty("mr_coin_short");
        mcParamBean.addStringProperty("mr_bill_status");
        mcParamBean.addStringProperty("mr_bill_short");
        mcParamBean.addStringProperty("mr_uppos_status");
        mcParamBean.addStringProperty("mr_scpos_status");
        mcParamBean.addStringProperty("mr_net_status");
        mcParamBean.addStringProperty("mr_temp");
        mcParamBean.addStringProperty("mr_door_isfault");
        mcParamBean.addStringProperty("mr_door_status");
        mcParamBean.addIntProperty("mr_chann_fault_num");
        mcParamBean.addStringProperty("mr_chann_fault_nos");
        mcParamBean.addIntProperty("mr_nogd_chnum");
        mcParamBean.addStringProperty("mr_nogd_chann");
        mcParamBean.addIntProperty("mr_gear_fault_num");
        mcParamBean.addStringProperty("mr_gear_fault_nos");
        mcParamBean.addIntProperty("mr_data_fault");
        mcParamBean.addDateProperty("mr_door_date");
        mcParamBean.addDateProperty("addtime");
        mcParamBean.addDateProperty("updatetime");
    }


    private static void addPromotion(Schema schema) {
        Entity mcParamBean = schema.addEntity("PromotionBean");
        mcParamBean.setTableName("promotion");
        mcParamBean.addIntProperty("pt_id").primaryKey();
        mcParamBean.addStringProperty("pt_name");
        mcParamBean.addStringProperty("pt_msg");
        mcParamBean.addStringProperty("pt_desc");
        mcParamBean.addStringProperty("pt_status");
        mcParamBean.addDateProperty("pt_starttime");
        mcParamBean.addDateProperty("pt_endtime");
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
        mcParamBean.addLongProperty("sl_id").primaryKey().autoincrement();
        mcParamBean.addStringProperty("sl_no");
        mcParamBean.addStringProperty("sl_batch_no");
        mcParamBean.addDateProperty("sl_time");
        mcParamBean.addStringProperty("mc_no");
        mcParamBean.addStringProperty("sl_gd_no");
        mcParamBean.addLongProperty("sl_pre_price");
        mcParamBean.addLongProperty("sl_amt");
        mcParamBean.addLongProperty("sl_score");
        mcParamBean.addLongProperty("sl_cash_in");
        mcParamBean.addLongProperty("sl_cash_out");
        mcParamBean.addStringProperty("sl_coin_in");
        mcParamBean.addStringProperty("sl_coin_out");
        mcParamBean.addStringProperty("sl_chann");
        mcParamBean.addLongProperty("sl_num");
        mcParamBean.addStringProperty("sl_type");
        mcParamBean.addStringProperty("sl_isvip");
        mcParamBean.addStringProperty("sl_status");
        mcParamBean.addStringProperty("sl_err_msg");
        mcParamBean.addStringProperty("sl_acc_no");
        mcParamBean.addLongProperty("sl_bf_amt");
        mcParamBean.addLongProperty("sl_af_amt");
    }

    }
