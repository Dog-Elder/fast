package com.fast.core.common.util;

import cn.hutool.core.collection.ListUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Com {
    public static final String UnknownErr = "未知错误";
    public static final String Ok = "操作成功";
    public static final String BadArg = "参数非法";
    public static final String NoAuth = "无权操作";
    public static final String TestTip = "测试账户不支持此操作";


    //以1开头11位手机号
    public static final String PatternPhone = "^1[0-9]{10}$";
    public static final String PatternPhoneOrEmpty = "^(\\d{1,20})|(\\s?)$";
    public static final String PatternRatio = "[1-9]\\d*x[1-9]\\d*";

    //ip或域名
    public static final String PatternIpOrDomain = "^(?:(?:\\w+\\.)+[a-zA-Z]+)$|^(?:(?:25[0-5]|2[0-4]\\d|(?:(?:1\\d{2})|(?:[1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|(?:(?:1\\d{2})|(?:[1-9]?\\d)))$";

    //ip
    public static final String PatternIp = "^(?:(?:[1-9]?\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\.){3}(?:[1-9]?\\d|1\\d{2}|2[0-4]\\d|25[0-5])$";
    // https?://(域名|ip)(:端口)?(/路径)*
    public static final String PatternUrl = "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    public static final String IdNo = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";

    /**
     * HH:mm:ss"正则
     */
    public static final String PatternHHmmss = "^([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
    public static final String Require = "{display}必填";
    public static final String MaxLen = "{display}不能超过{max}个字符";
    public static final String MinLen = "{display}不能超过{min}个字符";
    public static final String MinMaxLen = "{display}不能少于{min}个字符且不能多于{max}个字符";
    public static final String MinMaxValue = "{display}应该在[{min}]-[{max}]之间";
    public static final String MinValue = "{display}不能小于{value}";
    public static final String MaxValue = "{display}不能大于{value}";
    public static final String WrongPattern = "{display}格式不正确";
    public static final String BadArgDisplay = "{display}参数非法";
    public static final String NotRequire = "{display}不能填写";


    public static final DateTimeFormatter YMD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter MDHM = DateTimeFormatter.ofPattern("MM-dd HH:mm");
    public static final DateTimeFormatter HMS = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter YMDHMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter YMDHMS_PURE = DateTimeFormatter.ofPattern("yyMMddHHmmss");
    public static final DateTimeFormatter YMDHMSSSS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");

    @JsonIgnoreType
    public class MyMixInForIgnoreType {
    }

    public static Map<DayOfWeek, String> DayOfWeekMap = new LinkedHashMap<DayOfWeek, String>() {
        {
            put(DayOfWeek.MONDAY, "周一");
            put(DayOfWeek.TUESDAY, "周二");
            put(DayOfWeek.WEDNESDAY, "周三");
            put(DayOfWeek.THURSDAY, "周四");
            put(DayOfWeek.FRIDAY, "周五");
            put(DayOfWeek.SATURDAY, "周六");
            put(DayOfWeek.SUNDAY, "周日");
        }
    };

    public static Map<Integer, String> AreaCodeMap = new LinkedHashMap<Integer, String>() {
        {
            put(1, "+1");
            put(7, "+7");
            put(20, "+20");
            put(27, "+27");
            put(30, "+30");
            put(31, "+31");
            put(32, "+32");
            put(33, "+33");
            put(34, "+34");
            put(36, "+36");
            put(39, "+39");
            put(40, "+40");
            put(41, "+41");
            put(43, "+43");
            put(44, "+44");
            put(45, "+45");
            put(46, "+46");
            put(47, "+47");
            put(48, "+48");
            put(49, "+49");
            put(51, "+51");
            put(52, "+52");
            put(53, "+53");
            put(54, "+54");
            put(55, "+55");
            put(56, "+56");
            put(57, "+57");
            put(58, "+58");
            put(60, "+60");
            put(61, "+61");
            put(62, "+62");
            put(63, "+63");
            put(64, "+64");
            put(65, "+65");
            put(66, "+66");
            put(81, "+81");
            put(82, "+82");
            put(84, "+84");
            put(86, "+86"); // 默认是中国
            put(90, "+90");
            put(91, "+91");
            put(92, "+92");
            put(93, "+93");
            put(94, "+94");
            put(95, "+95");
            put(98, "+98");
            put(212, "+212");
            put(213, "+213");
            put(216, "+216");
            put(218, "+218");
            put(220, "+220");
            put(221, "+221");
            put(222, "+222");
            put(223, "+223");
            put(224, "+224");
            put(225, "+225");
            put(226, "+226");
            put(227, "+227");
            put(228, "+228");
            put(229, "+229");
            put(230, "+230");
            put(231, "+231");
            put(232, "+232");
            put(233, "+233");
            put(234, "+234");
            put(235, "+235");
            put(236, "+236");
            put(237, "+237");
            put(238, "+238");
            put(239, "+239");
            put(240, "+240");
            put(241, "+241");
            put(242, "+242");
            put(243, "+243");
            put(244, "+244");
            put(245, "+245");
            put(247, "+247");
            put(248, "+248");
            put(249, "+249");
            put(250, "+250");
            put(251, "+251");
            put(252, "+252");
            put(253, "+253");
            put(254, "+254");
            put(255, "+255");
            put(256, "+256");
            put(257, "+257");
            put(258, "+258");
            put(260, "+260");
            put(261, "+261");
            put(262, "+262");
            put(263, "+263");
            put(264, "+264");
            put(265, "+265");
            put(266, "+266");
            put(267, "+267");
            put(268, "+268");
            put(269, "+269");
            put(290, "+290");
            put(291, "+291");
            put(297, "+297");
            put(298, "+298");
            put(299, "+299");
            put(336, "+336");
            put(338, "+338");
            put(350, "+350");
            put(351, "+351");
            put(352, "+352");
            put(353, "+353");
            put(354, "+354");
            put(355, "+355");
            put(356, "+356");
            put(357, "+357");
            put(358, "+358");
            put(359, "+359");
            put(370, "+370");
            put(371, "+371");
            put(372, "+372");
            put(373, "+373");
            put(374, "+374");
            put(376, "+376");
            put(377, "+377");
            put(380, "+380");
            put(381, "+381");
            put(385, "+385");
            put(386, "+386");
            put(387, "+387");
            put(389, "+389");
            put(396, "+396");
            put(420, "+420");
            put(421, "+421");
            put(423, "+423");
            put(500, "+500");
            put(501, "+501");
            put(502, "+502");
            put(503, "+503");
            put(504, "+504");
            put(505, "+505");
            put(506, "+506");
            put(507, "+507");
            put(508, "+508");
            put(509, "+509");
            put(590, "+590");
            put(591, "+591");
            put(592, "+592");
            put(593, "+593");
            put(594, "+594");
            put(595, "+595");
            put(596, "+596");
            put(597, "+597");
            put(598, "+598");
            put(670, "+670");
            put(671, "+671");
            put(673, "+673");
            put(674, "+674");
            put(676, "+676");
            put(677, "+677");
            put(678, "+678");
            put(679, "+679");
            put(682, "+682");
            put(683, "+683");
            put(684, "+684");
            put(685, "+685");
            put(686, "+686");
            put(688, "+688");
            put(850, "+850");
            put(852, "+852");
            put(853, "+853");
            put(855, "+855");
            put(856, "+856");
            put(880, "+880");
            put(886, "+886");
            put(960, "+960");
            put(961, "+961");
            put(962, "+962");
            put(963, "+963");
            put(964, "+964");
            put(965, "+965");
            put(966, "+966");
            put(967, "+967");
            put(968, "+968");
            put(970, "+970");
            put(971, "+971");
            put(972, "+972");
            put(973, "+973");
            put(974, "+974");
            put(975, "+975");
            put(976, "+976");
            put(977, "+977");
            put(993, "+993");
            put(994, "+994");
            put(995, "+995");
            put(996, "+996");
            put(998, "+998");
            put(1808, "+1808");
            put(1809, "+1809");
            put(1876, "+1876");
            put(1907, "+1907");
            put(6723, "+6723");
            put(64672, "+64672");
            put(619162, "+619162");
            put(619164, "+619164");

        }
    };


    public static final List<String> OrderDirections = ListUtil.of("desc", "asc");
    public static String Desc = OrderDirections.get(0);
    public static String Asc = OrderDirections.get(1);
    public static String DbFailMsg = "数据库操作失败";


    /**
     * 金额字段的整数位数
     */
    public static final int MoneyPrecision = 15;

    /**
     * 金额字段的小数位数
     */
    public static final int MoneyScale = 2;

    /**
     * 非金额小数字段的整数位数
     */
    public static final int DecimalPrecision = 13;

    /**
     * 非金额小数字段的小数位数
     */
    public static final int DecimalScale = 8;

    public static final int PassLen = 24;
    public static final int ShortLen = 64;
    public static final int MediumLen = 255;
    public static final int LongLen = 600;

    public static final Map<String, String> BankMap = new HashMap<String, String>() {

        private static final long serialVersionUID = -2851670404925698068L;

        {
            put("icbc", "工商银行");
            put("ccb", "建设银行");
            put("abc", "农业银行");
            put("comm", "交通银行");
            put("cmb", "招商银行");
            put("spdb", "浦发银行");
            put("cib", "兴业银行");
            put("boc", "中国银行");
            put("post", "邮政储蓄");
            put("ecitic", "中信银行");
            put("cmbc", "民生银行");
            put("ceb", "光大银行");
            put("hxb", "华夏银行");
            put("pab", "平安银行");
            put("gdb", "广发银行");
        }
    };
}
