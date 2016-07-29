package com.ys.ui.common.constants;

import com.ys.ui.R;
import com.ys.ui.view.GridDataSet;
import com.ys.ui.view.YsDetailView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangtao on 2016/7/17.
 */
public class YsConstants {

    // 从症状分类 开始计数    症状分类  1 - 100
    //                      身体部位  101- 200
    //                      用药人群   201 - 300



    public static Map<Integer, List<YsDetailView>> level3 = new HashMap<>();




    static {
        // 从症状分类 开始计数    症状分类  1 - 100
        //                      身体部位  101- 200
        //                      用药人群   201 - 300
        //                      名厂名药   301 - 400
        // 滋补养生
        List<YsDetailView> list = new ArrayList<>();
//        list.add(new YsDetailView(3, "保健食品", 6111, 11, "保健"));
//        list.add(new YsDetailView(3, "养生花茶", 6112, 11, "养生花茶"));

     //   list.add(new YsDetailView(3, "营养滋补", 6113, 11, "营养滋补"));
        list.add(new YsDetailView(4, "补阳类", 61131, 6003, "补阳"));
        list.add(new YsDetailView(4, "补阴类", 61132, 6003, "补阴"));
        list.add(new YsDetailView(4, "补血类", 61133, 6003, "补血"));
        list.add(new YsDetailView(4, "补气类", 61134, 6003, "补气"));

      //  list.add(new YsDetailView(3, "药食同源", 6114, 11, "药食同源"));
        list.add(new YsDetailView(4, "药膳汤料", 61141, 6004, "药膳汤料"));

        level3.put(11, list);
        // 运动风湿
        list = new ArrayList<>();
        list.add(new YsDetailView(4, "肩痛", 61211, 12, "肩痛"));
        list.add(new YsDetailView(4, "关节痛", 61212, 12, "关节痛"));
        list.add(new YsDetailView(4, "肌肉疼痛", 61213, 12, "肌肉疼痛"));
        list.add(new YsDetailView(4, "神经病", 61214, 12, "神经病"));
        list.add(new YsDetailView(4, "舒经活络", 61215, 12, ""));
        list.add(new YsDetailView(4, "消肿止痛", 61216, 12, ""));
        list.add(new YsDetailView(4, "风湿骨痛", 61217, 12, ""));
        list.add(new YsDetailView(4, "骨质增生", 61218, 12, ""));
        list.add(new YsDetailView(4, "止痛内服", 61219, 12, ""));
        list.add(new YsDetailView(4, "跌打损伤", 61220, 12, ""));
        level3.put(12, list);

        //营养保健
        list = new ArrayList<>();

     //   list.add(new YsDetailView(3, "延缓衰老", 61301, 13, ""));
     //   list.add(new YsDetailView(3, "肠胃保健", 61302, 13, ""));
        list.add(new YsDetailView(4, "便秘调理", 6130201, 61302, ""));
        list.add(new YsDetailView(4, "调理肠胃", 6130202, 61302, ""));

     //   list.add(new YsDetailView(3, "益智补脑", 61303, 13, ""));
        list.add(new YsDetailView(4, "改善记忆力", 6130301, 61303, ""));
        list.add(new YsDetailView(4, "缓解衰老", 6130302, 61303, ""));
        list.add(new YsDetailView(4, "改善睡眠", 6130303, 61303, ""));


      //  list.add(new YsDetailView(3, "补充维生素", 61304, 13, ""));
     //   list.add(new YsDetailView(3, "预防三高", 61305, 13, ""));
        list.add(new YsDetailView(4, "预防高血压", 6130501, 61305, ""));
        list.add(new YsDetailView(4, "预防高脂肪", 6130502, 61305, ""));
        list.add(new YsDetailView(4, "预防高血糖", 6130503, 61305, ""));
        list.add(new YsDetailView(4, "大蒜精油", 6130504, 61305, ""));


      //  list.add(new YsDetailView(3, "增强免疫", 61306, 13, ""));
        list.add(new YsDetailView(4, "牛初乳", 6130601, 61306, ""));
        list.add(new YsDetailView(4, "蛋白质", 6130602, 61306, ""));
        list.add(new YsDetailView(4, "益生菌", 6130603, 61306, ""));

      //  list.add(new YsDetailView(3, "补锌", 61307, 13, ""));
      //  list.add(new YsDetailView(3, "补钙", 61308, 13, ""));

        level3.put(13, list);

        // 医疗器械
        list = new ArrayList<>();

      //  list.add(new YsDetailView(3, "外用贴", 61401, 14, ""));
        list.add(new YsDetailView(4, "外用贴", 6140101, 61401, ""));
        list.add(new YsDetailView(4, "晕车贴", 6140102, 61401, ""));
        list.add(new YsDetailView(4, "退热贴", 6140103, 61401, ""));
        list.add(new YsDetailView(4, "风湿骨痛贴", 6140104, 61401, ""));
        list.add(new YsDetailView(4, "眼贴/鼻贴", 6140105, 61401, ""));
        list.add(new YsDetailView(4, "女性外用贴", 6140106, 61401, ""));
        list.add(new YsDetailView(4, "驱蚊手环", 6140107, 61401, ""));


      //  list.add(new YsDetailView(3, "医疗用品", 61402, 14, ""));
        list.add(new YsDetailView(4, "药箱", 6140201, 61402, ""));
        list.add(new YsDetailView(4, "医用剪刀", 6140202, 61402, ""));
        list.add(new YsDetailView(4, "医用手套", 6140203, 61402, ""));
        list.add(new YsDetailView(4, "急求包", 6140204, 61402, ""));


      //  list.add(new YsDetailView(3, "检测仪器", 61403, 14, ""));
        list.add(new YsDetailView(4, "体温计", 6140301, 61403, ""));
        list.add(new YsDetailView(4, "计步器", 6140302, 61403, ""));

       // list.add(new YsDetailView(3, "医用辅料", 61404, 14, ""));
        list.add(new YsDetailView(4, "药棉", 6140401, 61404, ""));
        list.add(new YsDetailView(4, "纱布", 6140402, 61404, ""));
        list.add(new YsDetailView(4, "胶布", 6140403, 61404, ""));
        list.add(new YsDetailView(4, "绷带", 6140404, 61404, ""));
        list.add(new YsDetailView(4, "棉签", 6140405, 61404, ""));
        list.add(new YsDetailView(4, "其他", 6140406, 61404, ""));

        level3.put(14, list);

        // 五官用药
        list = new ArrayList<>();
    //    list.add(new YsDetailView(3, "牙齿护理", 62101, 21, "牙齿"));
        list.add(new YsDetailView(4, "牙龈肿痛", 6210101, 62101, ""));
        list.add(new YsDetailView(4, "牙龈出血", 6210101, 62101, ""));
        list.add(new YsDetailView(4, "龋齿", 6210101, 62101, ""));

//        list.add(new YsDetailView(3, "口腔咽喉", 62102, 21, ""));
//        list.add(new YsDetailView(3, "眼科用药", 62103, 21, ""));
//        list.add(new YsDetailView(3, "耳科用药", 62104, 21, ""));
//        list.add(new YsDetailView(3, "鼻科用药", 62105, 21, ""));

        level3.put(21, list);

        // 情趣生活  计生用药
        list = new ArrayList<>();
       // list.add(new YsDetailView(3, "检测用品", 62201, 22, ""));
        list.add(new YsDetailView(4, "早孕检测试纸", 6220101, 62201, ""));
        list.add(new YsDetailView(4, "验孕棒", 6220102, 62201, ""));
        list.add(new YsDetailView(4, "其他检测用品", 6220103, 62201, ""));

       // list.add(new YsDetailView(3, "避孕用品", 62202, 22, ""));
        list.add(new YsDetailView(4, "避孕套", 6220201, 62202, ""));

      //  list.add(new YsDetailView(3, "情趣用品", 62203, 22, ""));
        list.add(new YsDetailView(4, "润滑助情", 6220301, 62203, ""));
        list.add(new YsDetailView(4, "男用专区", 6220302, 62203, ""));
        list.add(new YsDetailView(4, "女用专区", 6220303, 62203, ""));
        list.add(new YsDetailView(4, "延时用品", 6220304, 62203, ""));
        list.add(new YsDetailView(4, "其他情趣用品", 6220305, 62203, ""));
        level3.put(22, list);

        // 肠胃用药
        list = new ArrayList<>();
        list.add(new YsDetailView(4, "腹痛", 62301, 23, ""));
        list.add(new YsDetailView(4, "腹胀", 62302, 23, ""));
        list.add(new YsDetailView(4, "恶心", 62303, 23, ""));
        list.add(new YsDetailView(4, "呕吐", 62304, 23, ""));
        list.add(new YsDetailView(4, "打嗝", 62305, 23, ""));
        list.add(new YsDetailView(4, "胃酸", 62306, 23, ""));
        list.add(new YsDetailView(4, "胃痛", 62307, 23, ""));
        list.add(new YsDetailView(4, "胃胀", 62308, 23, ""));
        list.add(new YsDetailView(4, "胃痉挛", 62309, 23, ""));
        list.add(new YsDetailView(4, "厌食", 62310, 23, ""));
        list.add(new YsDetailView(4, "消化不良", 62311, 23, ""));
        list.add(new YsDetailView(4, "腹泻", 62312, 23, ""));
        list.add(new YsDetailView(4, "便秘", 62313, 23, ""));
        list.add(new YsDetailView(4, "肠鸣", 62314, 23, ""));


        level3.put(23, list);

        // 感冒用药
        list = new ArrayList<>();
        list.add(new YsDetailView(4, "发热头痛", 62401, 24, ""));
        list.add(new YsDetailView(4, "鼻塞", 62402, 24, ""));
        list.add(new YsDetailView(4, "咳嗽", 62403, 24, ""));
        list.add(new YsDetailView(4, "喷嚏", 62404, 24, ""));
        list.add(new YsDetailView(4, "咳嗽多痰", 62405, 24, ""));
        list.add(new YsDetailView(4, "畏寒", 62406, 24, ""));
        list.add(new YsDetailView(4, "流清鼻涕", 62407, 24, ""));
        list.add(new YsDetailView(4, "咳嗽口干", 62408, 24, ""));
        list.add(new YsDetailView(4, "喉咙肿痛", 62409, 24, ""));
        list.add(new YsDetailView(4, "浑身酸痛", 62410, 24, ""));
        list.add(new YsDetailView(4, "白痰", 62411, 24, ""));
        list.add(new YsDetailView(4, "黄痰", 62412, 24, ""));
        list.add(new YsDetailView(4, "流黄鼻涕", 62413, 24, ""));
        list.add(new YsDetailView(4, "恶心厌食", 62414, 24, ""));
        list.add(new YsDetailView(4, "发烧", 62415, 24, ""));

        level3.put(24, list);

        // 呼吸系统
        list = new ArrayList<>();

        list.add(new YsDetailView(4, "化痰", 63101, 31, ""));
        list.add(new YsDetailView(4, "止咳", 63102, 31, ""));
        list.add(new YsDetailView(4, "平喘", 63103, 31, ""));
        list.add(new YsDetailView(4, "咽痛", 63104, 31, ""));
        list.add(new YsDetailView(4, "支气管炎", 63105, 31, ""));
        list.add(new YsDetailView(4, "感冒咳嗽", 63106, 31, ""));
        list.add(new YsDetailView(4, "咳嗽口干", 63107, 31, ""));
        list.add(new YsDetailView(4, "咳嗽喘促", 63108, 31, ""));
        level3.put(31, list);
        // 美妆护理
        list = new ArrayList<>();
       // list.add(new YsDetailView(3, "美体", 63201, 32, ""));
        list.add(new YsDetailView(4, "减肥瘦身", 6320101, 63201, ""));
        list.add(new YsDetailView(4, "狐臭半月清", 6320102, 63201, ""));
        list.add(new YsDetailView(4, "脱毛膏", 6320103, 63201, ""));
        list.add(new YsDetailView(4, "丰胸", 6320104, 63201, ""));

      //  list.add(new YsDetailView(3, "局部护理", 63202, 32, ""));
        list.add(new YsDetailView(4, "牙齿护理", 6320201, 63202, ""));
        list.add(new YsDetailView(4, "口腔护理", 6320202, 63202, ""));
        list.add(new YsDetailView(4, "足部护理", 6320203, 63202, ""));
        list.add(new YsDetailView(4, "头发护理", 6320204, 63202, ""));
        list.add(new YsDetailView(4, "滴眼液", 6320205, 63202, ""));
        list.add(new YsDetailView(4, "秘密护理", 6320206, 63202, ""));

      //  list.add(new YsDetailView(3, "全身护理", 63203, 32, ""));
        list.add(new YsDetailView(4, "洗浴护理", 6320301, 63203, ""));
        list.add(new YsDetailView(4, "一次性用品", 6320302, 63203, ""));
        list.add(new YsDetailView(4, "奇台护理", 6320303, 63203, ""));

       // list.add(new YsDetailView(3, "基础护理", 63204, 32, ""));
        list.add(new YsDetailView(4, "面膜", 6320401, 63204, ""));
        list.add(new YsDetailView(4, "防晒霜", 6320402, 63204, ""));
        list.add(new YsDetailView(4, "滋润护肤", 6320403, 63204, ""));
        list.add(new YsDetailView(4, "精华液", 6320404, 63204, ""));
        list.add(new YsDetailView(4, "唇膏", 6320405, 63204, ""));
        list.add(new YsDetailView(4, "眼霜", 6320406, 63204, ""));

      //  list.add(new YsDetailView(3, "美容修复", 63205, 32, ""));
        list.add(new YsDetailView(4, "祛痘", 6320501, 63205, ""));
        list.add(new YsDetailView(4, "淡斑", 6320502, 63205, ""));
        list.add(new YsDetailView(4, "祛疤", 6320503, 63205, ""));

        level3.put(32, list);
        // 清热解毒
        list = new ArrayList<>();
        list.add(new YsDetailView(4, "牙龈肿痛", 6330101, 63301, ""));
        list.add(new YsDetailView(4, "上火便秘", 6330102, 63301, ""));
        list.add(new YsDetailView(4, "口腔不适", 6330103, 63301, ""));
        list.add(new YsDetailView(4, "咽喉肿痛", 6330104, 63301, ""));
        list.add(new YsDetailView(4, "熬夜上火", 6330105, 63301, ""));
        list.add(new YsDetailView(4, "烦躁口渴", 6330106, 63301, ""));
        list.add(new YsDetailView(4, "发热面赤", 6330107, 63301, ""));
        list.add(new YsDetailView(4, "声音嘶哑", 6330108, 63301, ""));

        level3.put(33, list);
        // 皮肤
        list = new ArrayList<>();
        list.add(new YsDetailView(4, "脚气", 6340101, 34, ""));
        list.add(new YsDetailView(4, "皮肤烫伤", 6340102, 34, ""));
        list.add(new YsDetailView(4, "皮肤湿疹", 6340103, 34, ""));
        list.add(new YsDetailView(4, "真菌感染", 6340104, 34, ""));
        list.add(new YsDetailView(4, "各类廯症", 6340105, 34, ""));
        list.add(new YsDetailView(4, "蚊虫叮咬", 6340106, 34, ""));
        list.add(new YsDetailView(4, "皮肤过敏", 6340107, 34, ""));
        list.add(new YsDetailView(4, "手足破裂", 6340108, 34, ""));
        list.add(new YsDetailView(4, "脂溢性皮炎", 6340109, 34, ""));
        list.add(new YsDetailView(4, "寻常座疮", 6340110, 34, ""));

        level3.put(34, list);


        /******身体部位**********/
         // 头
        list = new ArrayList<>();
      //  list.add(new YsDetailView(3, "面部", 711101, 111, ""));
        list.add(new YsDetailView(4, "面部色斑", 71110101, 111, ""));
        list.add(new YsDetailView(4, "座疮粉刺", 71110101, 111, ""));
        list.add(new YsDetailView(4, "皮肤过敏", 71110101, 111, ""));
        list.add(new YsDetailView(4, "毛囊炎", 71110101, 111, ""));


      //  list.add(new YsDetailView(3, "口腔", 711102, 111, ""));
        list.add(new YsDetailView(4, "牙痛", 71110101, 111, ""));
        list.add(new YsDetailView(4, "口腔溃疡", 71110101, 111, ""));
        list.add(new YsDetailView(4, "牙龈出血", 71110101, 111, ""));
        list.add(new YsDetailView(4, "唇疱炎", 71110101, 111, ""));
        list.add(new YsDetailView(4, "咽炎", 71110101, 111, ""));
        list.add(new YsDetailView(4, "口臭", 71110101, 111, ""));
        list.add(new YsDetailView(4, "口角炎", 71110101, 111, ""));

      //  list.add(new YsDetailView(3, "耳朵", 711103, 111, ""));
        list.add(new YsDetailView(4, "听力下降", 71110101, 111, ""));
        list.add(new YsDetailView(4, "耳鸣", 71110101, 111, ""));
        list.add(new YsDetailView(4, "中耳炎", 71110101, 111, ""));

      //  list.add(new YsDetailView(3, "鼻子", 711104, 111, ""));
        list.add(new YsDetailView(4, "鼻炎", 71110101, 111, ""));
        list.add(new YsDetailView(4, "鼻塞", 71110101, 111, ""));
        list.add(new YsDetailView(4, "流鼻涕", 71110101, 111, ""));

       // list.add(new YsDetailView(3, "头", 711105, 111, ""));
        list.add(new YsDetailView(4, "头痛", 71110101, 111, ""));
        list.add(new YsDetailView(4, "头皮瘙痒", 71110101, 111, ""));
        list.add(new YsDetailView(4, "眩晕", 71110101, 111, ""));
        list.add(new YsDetailView(4, "发囊炎", 71110101, 111, ""));
        list.add(new YsDetailView(4, "记忆力衰退", 71110101, 111, ""));
        list.add(new YsDetailView(4, "失眠症", 71110101, 111, ""));

       // list.add(new YsDetailView(3, "眼睛", 711106, 111, ""));
        list.add(new YsDetailView(4, "沙眼", 71110101, 111, ""));
        list.add(new YsDetailView(4, "红眼病", 71110101, 111, ""));
        list.add(new YsDetailView(4, "近视眼", 71110101, 111, ""));
        list.add(new YsDetailView(4, "老花眼", 71110101, 111, ""));
        list.add(new YsDetailView(4, "干眼症", 71110101, 111, ""));
        list.add(new YsDetailView(4, "弱视", 71110101, 111, ""));
        list.add(new YsDetailView(4, "飞蚊症", 71110101, 111, ""));
        list.add(new YsDetailView(4, "眼部刺激性疼痛", 71110101, 111, ""));
        list.add(new YsDetailView(4, "眼部瘙痒", 71110101, 111, ""));
        list.add(new YsDetailView(4, "角膜炎", 71110101, 111, ""));
        list.add(new YsDetailView(4, "结膜炎", 71110101, 111, ""));
        list.add(new YsDetailView(4, "眼睛充血", 71110101, 111, ""));
        list.add(new YsDetailView(4, "视觉疲劳", 71110101, 111, ""));
        list.add(new YsDetailView(4, "眼睛灼热", 71110101, 111, ""));
        list.add(new YsDetailView(4, "黑眼圈", 71110101, 111, ""));
        list.add(new YsDetailView(4, "眼袋", 71110101, 111, ""));

        level3.put(111, list);
        // 颈
        list = new ArrayList<>();
        list.add(new YsDetailView(4, "落枕", 71110101, 112, ""));
        list.add(new YsDetailView(4, "颈椎病", 71110101, 112, ""));
        list.add(new YsDetailView(4, "脖子疼痛", 71110101, 112, ""));
        list.add(new YsDetailView(4, "扭伤", 71110101, 112, ""));

        level3.put(112, list);
        // 胸
        list = new ArrayList<>();
       //  list.add(new YsDetailView(3, "乳房", 711106, 121, ""));
        list.add(new YsDetailView(4, "乳腺增生", 71110101, 121, ""));
       // list.add(new YsDetailView(3, "心血管", 711106, 121, ""));
        list.add(new YsDetailView(4, "心脏病", 71110101, 121, ""));
        list.add(new YsDetailView(4, "高血压", 71110101, 121, ""));

        level3.put(121, list);
        // 腹

        list = new ArrayList<>();
        list.add(new YsDetailView(4, "肥胖", 711106, 122, ""));
        level3.put(122, list);//
        //
        list = new ArrayList<>();
        list.add(new YsDetailView(4, "肩周炎", 713106, 131, ""));
        list.add(new YsDetailView(4, "肌肉酸痛", 713106, 131, ""));
        list.add(new YsDetailView(4, "风湿性关节炎", 713106, 131, ""));
        list.add(new YsDetailView(4, "骨质疏松", 713106, 131, ""));
        list.add(new YsDetailView(4, "脚气", 713106, 131, ""));
        list.add(new YsDetailView(4, "皮疹", 713106, 131, ""));
        list.add(new YsDetailView(4, "牛皮廯", 713106, 131, ""));
        list.add(new YsDetailView(4, "烧伤烫伤", 713106, 131, ""));
        list.add(new YsDetailView(4, "狐臭", 713106, 131, ""));
        list.add(new YsDetailView(4, "跌打扭伤", 713106, 131, ""));
        list.add(new YsDetailView(4, "四肢疼痛", 713106, 131, ""));
        list.add(new YsDetailView(4, "手足多汗", 713106, 131, ""));
        list.add(new YsDetailView(4, "四肢外伤", 713106, 131, ""));
        list.add(new YsDetailView(4, "四肢冰凉", 713106, 131, ""));
        list.add(new YsDetailView(4, "四肢瘙痒", 713106, 131, ""));
        list.add(new YsDetailView(4, "肢体麻木", 713106, 131, ""));
        list.add(new YsDetailView(4, "韧带损伤", 713106, 131, ""));
        list.add(new YsDetailView(4, "关节炎", 713106, 131, ""));
        list.add(new YsDetailView(4, "痛风", 713106, 131, ""));
        list.add(new YsDetailView(4, "甲沟炎", 713106, 131, ""));
        list.add(new YsDetailView(4, "灰指甲", 713106, 131, ""));
        list.add(new YsDetailView(4, "腱鞘炎", 713106, 131, ""));

        level3.put(131, list);//四肢
        //
        list = new ArrayList<>();
        //list.add(new YsDetailView(3, "男生殖器", 713206, 132, ""));
        list.add(new YsDetailView(4, "尿路结石", 713106, 132, ""));
        list.add(new YsDetailView(4, "尿频尿急", 713106, 132, ""));
        list.add(new YsDetailView(4, "前列腺炎", 713106, 132, ""));
        list.add(new YsDetailView(4, "附睾炎", 713106, 132, ""));
        list.add(new YsDetailView(4, "膀胱炎", 713106, 132, ""));
        list.add(new YsDetailView(4, "前列腺肥大", 713106, 132, ""));

        //list.add(new YsDetailView(3, "女生殖器", 713206, 132, ""));
        list.add(new YsDetailView(4, "白带增多", 713106, 132, ""));
        list.add(new YsDetailView(4, "滴虫病", 713106, 132, ""));
        list.add(new YsDetailView(4, "尿道炎", 713106, 132, ""));
        list.add(new YsDetailView(4, "尿频尿急", 713106, 132, ""));
        list.add(new YsDetailView(4, "尖锐湿疹", 713106, 132, ""));
        list.add(new YsDetailView(4, "阴道炎", 713106, 132, ""));
        list.add(new YsDetailView(4, "输卵管炎", 713106, 132, ""));
        list.add(new YsDetailView(4, "宫腔炎", 713106, 132, ""));
        list.add(new YsDetailView(4, "月经不调", 713106, 132, ""));
        list.add(new YsDetailView(4, "尿路感染", 713106, 132, ""));
        list.add(new YsDetailView(4, "外阴瘙痒", 713106, 132, ""));
        list.add(new YsDetailView(4, "宫颈炎", 713106, 132, ""));
        list.add(new YsDetailView(4, "宫颈糜烂", 713106, 132, ""));
        list.add(new YsDetailView(4, "盆腔炎", 713106, 132, ""));
        list.add(new YsDetailView(4, "附件炎", 713106, 132, ""));

        level3.put(132, list);//生殖器
        list = new ArrayList<>();
        list.add(new YsDetailView(4, "疥疮", 714106, 141, ""));
        list.add(new YsDetailView(4, "皮炎", 714106, 141, ""));
        list.add(new YsDetailView(4, "湿疹", 714106, 141, ""));
        list.add(new YsDetailView(4, "荨麻疹", 714106, 141, ""));
        list.add(new YsDetailView(4, "牛皮廯", 714106, 141, ""));
        list.add(new YsDetailView(4, "背部疼痛", 714106, 141, ""));
        list.add(new YsDetailView(4, "水痘", 714106, 141, ""));
        list.add(new YsDetailView(4, "疱疹", 714106, 141, ""));
        list.add(new YsDetailView(4, "白癜风", 714106, 141, ""));

        level3.put(141, list);//背

        list = new ArrayList<>();
        list.add(new YsDetailView(4, "痔疮", 714206, 142, ""));
        list.add(new YsDetailView(4, "股藓", 714206, 142, ""));
        list.add(new YsDetailView(4, "腰椎间盘突出", 714206, 142, ""));
        list.add(new YsDetailView(4, "腰部酸痛", 714206, 142, ""));
        list.add(new YsDetailView(4, "扭伤或拉伤", 714206, 142, ""));
        list.add(new YsDetailView(4, "骨质增生", 714206, 142, ""));
        list.add(new YsDetailView(4, "腹部肥胖", 714206, 142, ""));

        level3.put(142, list);//腰臀部

        list = new ArrayList<>();

        list.add(new YsDetailView(4, "心脏病", 71510101, 151, ""));
        list.add(new YsDetailView(4, "高血压", 71510101, 151, ""));
        level3.put(151, list);//心脏

        list = new ArrayList<>();
        list.add(new YsDetailView(4, "咳嗽", 715210101, 152, ""));
        list.add(new YsDetailView(4, "哮喘", 715210101, 152, ""));
        list.add(new YsDetailView(4, "肺炎", 715210101, 152, ""));
        list.add(new YsDetailView(4, "肺气肿", 715210101, 152, ""));

        level3.put(152, list);//肺

        list = new ArrayList<>();

        list.add(new YsDetailView(4, "肾虚", 716110101, 161, ""));
        list.add(new YsDetailView(4, "肾结石", 716110101, 161, ""));

        level3.put(161, list);//肾脏

        list = new ArrayList<>();
        list.add(new YsDetailView(4, "结肠炎", 716210101, 162, ""));
        list.add(new YsDetailView(4, "便秘", 716210101, 162, ""));
        list.add(new YsDetailView(4, "肠炎", 716210101, 162, ""));
        list.add(new YsDetailView(4, "便血", 716210101, 162, ""));
        list.add(new YsDetailView(4, "痔疮", 716210101, 162, ""));

        level3.put(162, list);//大肠
        list = new ArrayList<>();

        list.add(new YsDetailView(4, "便血", 717110101, 171, ""));
        list.add(new YsDetailView(4, "肠痉挛", 717110101, 171, ""));
        list.add(new YsDetailView(4, "肠炎", 717110101, 171, ""));
        list.add(new YsDetailView(4, "疝气", 717110101, 171, ""));

        level3.put(171, list);//小肠

        list = new ArrayList<>();
        list.add(new YsDetailView(4, "膀胱炎", 717210101, 172, ""));
        list.add(new YsDetailView(4, "膀胱结石", 717210101, 172, ""));
        list.add(new YsDetailView(4, "尿频", 717210101, 172, ""));
        list.add(new YsDetailView(4, "尿急", 717210101, 172, ""));
        list.add(new YsDetailView(4, "尿痛", 717210101, 172, ""));
        list.add(new YsDetailView(4, "排尿困难", 717210101, 172, ""));

        level3.put(172, list);//膀胱

        list = new ArrayList<>();
        list.add(new YsDetailView(4, "贫血", 718110101, 181, ""));
        list.add(new YsDetailView(4, "气虚", 718110101, 181, ""));

        level3.put(181, list);//脾脏

        list = new ArrayList<>();
        list.add(new YsDetailView(4, "脂肪肝", 718210101, 182, ""));
        list.add(new YsDetailView(4, "酒精肝", 718210101, 182, ""));
        list.add(new YsDetailView(4, "肝硬化", 718210101, 182, ""));

        level3.put(182, list);//肝脏

        list = new ArrayList<>();
        list.add(new YsDetailView(4, "胆囊炎", 718210101, 182, ""));
        list.add(new YsDetailView(4, "胆结石", 718210101, 182, ""));
        list.add(new YsDetailView(4, "呕吐", 718210101, 182, ""));
        list.add(new YsDetailView(4, "消化不良胀气", 718210101, 182, ""));
        list.add(new YsDetailView(4, "食欲不振", 718210101, 182, ""));

        level3.put(191, list);//胆

        list = new ArrayList<>();
        list.add(new YsDetailView(4, "腹部疼痛", 718210101, 192, ""));
        list.add(new YsDetailView(4, "腹泻", 718210101, 192, ""));
        list.add(new YsDetailView(4, "胃炎", 718210101, 192, ""));
        list.add(new YsDetailView(4, "胃溃疡", 718210101, 192, ""));
        list.add(new YsDetailView(4, "腹胀", 718210101, 192, ""));
        list.add(new YsDetailView(4, "胃痛胃酸胃胀", 718210101, 192, ""));
        list.add(new YsDetailView(4, "胃痉挛", 718210101, 192, ""));
        list.add(new YsDetailView(4, "消化不良", 718210101, 192, ""));

        level3.put(192, list);//胃



        /********人体部位 end**********/

        /****用药人群*********/
        list = new ArrayList<>();
        list.add(new YsDetailView(4, "关节炎", 720110101, 201, ""));
        list.add(new YsDetailView(4, "痛风", 720110101, 201, ""));
        list.add(new YsDetailView(4, "老年痴呆", 720110101, 201, ""));
        list.add(new YsDetailView(4, "高血脂", 720110101, 201, ""));
        list.add(new YsDetailView(4, "高血压", 720110101, 201, ""));
        list.add(new YsDetailView(4, "骨质疏松", 720110101, 201, ""));
        list.add(new YsDetailView(4, "免疫增强剂", 720110101, 201, ""));
        list.add(new YsDetailView(4, "维生素类", 720110101, 201, ""));
        list.add(new YsDetailView(4, "老年保健滋补", 720110101, 201, ""));
        list.add(new YsDetailView(4, "延缓衰老", 720110101, 201, ""));
        list.add(new YsDetailView(4, "老年痴呆", 720110101, 201, ""));
        list.add(new YsDetailView(4, "糖尿病", 720110101, 201, ""));

        level3.put(201, list);//老人用药

        list = new ArrayList<>();
        list.add(new YsDetailView(4, "清热退烧", 720210101, 202, ""));
        list.add(new YsDetailView(4, "儿童感冒", 720210101, 202, ""));
        list.add(new YsDetailView(4, "皮炎湿疹", 720210101, 202, ""));
        list.add(new YsDetailView(4, "咳嗽气喘", 720210101, 202, ""));
        list.add(new YsDetailView(4, "儿童补益", 720210101, 202, ""));
        list.add(new YsDetailView(4, "腹痛腹泻", 720210101, 202, ""));
        list.add(new YsDetailView(4, "消化不良", 720210101, 202, ""));

        level3.put(202, list);//儿童用药

        list = new ArrayList<>();
        list.add(new YsDetailView(4, "脱发", 720310101, 203, ""));
        list.add(new YsDetailView(4, "少精弱精", 720310101, 203, ""));
        list.add(new YsDetailView(4, "前列腺", 720310101, 203, ""));
        list.add(new YsDetailView(4, "补肾壮阳", 720310101, 203, ""));
        list.add(new YsDetailView(4, "其他男科药", 720310101, 203, ""));

        level3.put(203, list);//男性用药

        list = new ArrayList<>();
        list.add(new YsDetailView(4, "孕妇用药", 720410101, 204, ""));
        list.add(new YsDetailView(4, "消化", 720410101, 204, ""));
        list.add(new YsDetailView(4, "补血", 720410101, 204, ""));
        list.add(new YsDetailView(4, "防止痔疮", 720410101, 204, ""));
        list.add(new YsDetailView(4, "维生素", 720410101, 204, ""));
        list.add(new YsDetailView(4, "补钙药", 720410101, 204, ""));
        list.add(new YsDetailView(4, "贫血", 720410101, 204, ""));
        list.add(new YsDetailView(4, "乳腺增生", 720410101, 204, ""));
        list.add(new YsDetailView(4, "月经不调", 720410101, 204, ""));
        list.add(new YsDetailView(4, "更年期综合症", 720410101, 204, ""));
        list.add(new YsDetailView(4, "卵巢疾病", 720410101, 204, ""));
        list.add(new YsDetailView(4, "外阴瘙痒", 720410101, 204, ""));
        list.add(new YsDetailView(4, "妇科炎症", 720410101, 204, ""));
        list.add(new YsDetailView(4, "痛经", 720410101, 204, ""));

        level3.put(204, list);//女性用药


    }


    public static List<YsDetailView> getListView(int index) {
        return level3.get(index);
    }


    public static List<GridDataSet> getList(int index) {
        // 1  第一页
        List<GridDataSet> list = new ArrayList();
        if (index == 1) {
            list.add(new GridDataSet(1, R.mipmap.tou, 111, "头"));
            list.add(new GridDataSet(1, R.mipmap.jin, 112, "颈"));
            list.add(new GridDataSet(1, R.mipmap.xiong, 121, "胸"));
            list.add(new GridDataSet(1, R.mipmap.fu, 122, "腹"));
            list.add(new GridDataSet(1, R.mipmap.sizhi, 131, "四肢"));
            list.add(new GridDataSet(1, R.mipmap.szq, 132, "生殖器"));
            list.add(new GridDataSet(1, R.mipmap.bei, 141, "背"));
            list.add(new GridDataSet(1, R.mipmap.yaotun, 142, "腰臀部"));
            list.add(new GridDataSet(1, R.mipmap.xinzang, 151, "心脏"));
            list.add(new GridDataSet(1, R.mipmap.fei, 152, "肺"));
            list.add(new GridDataSet(1, R.mipmap.shenzang, 161, "肾脏"));
            list.add(new GridDataSet(1, R.mipmap.dachang, 162, "大肠"));


        } else {
            // 2 第二页

            list.add(new GridDataSet(1, R.mipmap.xiaochang, 171, "小肠"));
            list.add(new GridDataSet(1, R.mipmap.pangguang, 172, "膀胱"));
            list.add(new GridDataSet(1, R.mipmap.ganpi, 181, "脾脏"));
            list.add(new GridDataSet(1, R.mipmap.ganzang, 182, "肝脏"));
            list.add(new GridDataSet(1, R.mipmap.dan, 191, "胆"));
            list.add(new GridDataSet(1, R.mipmap.wei, 192, "胃"));
        }

        return list;
    }
}
