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
        adv.addStringProperty("ai_file_id");// �ļ�����
        adv.addStringProperty("ai_file_name");
        adv.addStringProperty("ai_type");
        adv.addStringProperty("ai_gd_no");



    }

    private static void addMcAdmin(Schema schema) {
        Entity mcParamBean = schema.addEntity("McAdminBean");
        mcParamBean.setTableName("mcadmin");
        mcParamBean.addStringProperty("u_no").primaryKey();//�����
        mcParamBean.addStringProperty("u_pwd");

    }

    /**
     * @param schema
     */
    private static void addGoods(Schema schema) {
        // һ��ʵ�壨�ࣩ�͹��������ݿ��е�һ�ű��˴�����Ϊ��Note������������
        Entity goodsBean = schema.addEntity("GoodsBean");
        goodsBean.setTableName("goods");
        // ��Ҳ�������¸�������
        // note.setTableName("NODE");

        // greenDAO ���Զ�����ʵ���������ֵ���������ֶΣ�������Ĭ��ֵ
        // ���������������ñ��е��ֶΣ�
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
        goodsBean.addStringProperty("gd_manufacturer"); // ��������
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
        goodsBean.addStringProperty("gd_desc1"); // �䷽
        goodsBean.addStringProperty("gd_desc2");// ��������
        goodsBean.addStringProperty("gd_desc3");// �÷�����
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

        mcGoodsBean.addStringProperty("mc_no");//�ն˺�
        mcGoodsBean.addStringProperty("mg_channo").primaryKey();//�������
        mcGoodsBean.addStringProperty("gd_no");//��Ʒ����
        mcGoodsBean.addStringProperty("gd_type");//��Ʒ����
        mcGoodsBean.addStringProperty("gd_approve_code");
        mcGoodsBean.addStringProperty("gd_batch_no");
        mcGoodsBean.addStringProperty("gd_des_code");
        mcGoodsBean.addStringProperty("gd_mf_date");
        mcGoodsBean.addStringProperty("gd_exp_date");
        mcGoodsBean.addLongProperty("mg_gvol");//��Ʒ����
        mcGoodsBean.addLongProperty("mg_gnum");//��Ʒ����
        mcGoodsBean.addLongProperty("mg_pre_price"); // prePrice ԭ��
        mcGoodsBean.addLongProperty("mg_score_price"); // ���ּ�
        mcGoodsBean.addLongProperty("mg_vip_price");  // ��Ա��
        mcGoodsBean.addLongProperty("mg_disc_price"); // �ۿۼ�

        mcGoodsBean.addLongProperty("mg_price"); // ���۵���
        mcGoodsBean.addLongProperty("mg_chann_status"); // Ĭ�ϲ�����   1 ����  2 ����
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
        mcParamBean.addStringProperty("mc_no").primaryKey();//�ն˺�
        mcParamBean.addStringProperty("mc_serial_no");//�����
        mcParamBean.addStringProperty("mr_coin_status");//DEFAULT '0' COMMENT 'Ӳ����״̬��''0''-������''1''-�쳣��',
        mcParamBean.addStringProperty("mr_coin_short");//DEFAULT NULL COMMENT 'Ӳ��Ԥ��',
        mcParamBean.addStringProperty("mr_bill_status");//DEFAULT '0' COMMENT 'ֽ����״̬��''0''-������''1''-�쳣��',
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
        mcParamBean.addStringProperty("sl_no").primaryKey(); //�ն˺�
        mcParamBean.addStringProperty("sl_batch_no");// ���κ�
        mcParamBean.addStringProperty("sl_time");//����ʱ��
        mcParamBean.addStringProperty("mc_no");// �ն˺�
        mcParamBean.addStringProperty("sl_gd_no");// ��Ʒ����
        mcParamBean.addStringProperty("sl_gd_name");//��Ʒ����
        mcParamBean.addLongProperty("sl_pre_price");//ԭ��
        mcParamBean.addLongProperty("sl_disc_price");// �ۿۼ�
        mcParamBean.addLongProperty("sl_vip_price");//��Ա��

        mcParamBean.addLongProperty("sl_amt");//���׽��
        mcParamBean.addLongProperty("sl_score");//С�ѻ���
        mcParamBean.addLongProperty("sl_cash_in");//Ͷֽ�ҽ��
        mcParamBean.addLongProperty("sl_cash_out");//����ֽ�ҽ��
        mcParamBean.addStringProperty("sl_coin_in");
        mcParamBean.addStringProperty("sl_coin_out");
        mcParamBean.addStringProperty("sl_chann");//�������
        mcParamBean.addLongProperty("sl_num"); //��������
        mcParamBean.addStringProperty("sl_type");//���ʽ��1-�ֽ�2-���п���3-΢��֧����4-֧������5-���ֶһ���6-�����
        mcParamBean.addStringProperty("sl_isvip");// �Ƿ��Ա��0-��1-��
        //mcParamBean.addStringProperty("sl_status");//����״̬��1-���µ���2-��֧����3-�ѳ�����4-���˿5-֧��ʧ�ܣ�6-����ʧ��
        mcParamBean.addStringProperty("sl_err_msg");
        mcParamBean.addStringProperty("sl_acc_no");
        mcParamBean.addLongProperty("sl_bf_amt");
        mcParamBean.addLongProperty("sl_af_amt");
        mcParamBean.addLongProperty("sl_send_status");//�ϱ�״̬ 0 δ�ϱ� 1 ���ϱ�
        mcParamBean.addStringProperty("sl_pay_status");//֧��״̬��1-��֧����2-֧���У�3-֧���ɹ���4-֧��ʧ�ܣ�5-���˿6-��������
        mcParamBean.addStringProperty("sl_out_status");//����״̬��1-��������2-�����ɹ���3-����ʧ��

    }

    }
