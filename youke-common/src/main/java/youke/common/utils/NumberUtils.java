package youke.common.utils;

import java.math.BigDecimal;

public class NumberUtils {
    //判断是否为整数
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //判断是否为浮点型
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //判断是否为数字
    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    //元转分
    public static String fromYuanToFen(String yuan) {
        if (NumberUtils.isNumber(yuan)) {
            BigDecimal fen = new BigDecimal(yuan);
            BigDecimal mul = new BigDecimal(100);
            BigDecimal multiply = fen.multiply(mul);
            //对数据从小数点的第0位开始向上取整
            multiply = multiply.setScale(0, BigDecimal.ROUND_HALF_UP);
            return multiply.toString();
        } else {
            throw new NumberFormatException("该数据不是数值类型，类型不匹配");
        }
    }

    //分转元
    public static String fromFenToYuan(String fen) {
        if (NumberUtils.isNumber(fen)) {
            BigDecimal yuan = new BigDecimal(fen);
            BigDecimal div = new BigDecimal(100);
            return yuan.divide(div, 2, BigDecimal.ROUND_HALF_UP).toString();
        } else {
            throw new NumberFormatException("该数据不是数值类型，类型不匹配");
        }
    }

    public static void main(String[] args) {
        String value = "100";
        System.out.println(fromFenToYuan(value));
        String value2 = "111.1161";
        System.out.println(fromYuanToFen(value2));
    }
}
