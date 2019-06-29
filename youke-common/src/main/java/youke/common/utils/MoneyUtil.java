package youke.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MoneyUtil {
    /**
     * Translates the String repre sentation of a BigDecimal into a BigDecimal.
     */
    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    /**
     * 分转元
     *
     * @param fen 分
     * @return 元
     */
    public static String fenToYuan(String fen) {
        // 第二个参数表示精度,就是保留小数点之后多少位；
        // 第三个参数表示精确方法,进位和舍位的标志位
        BigDecimal divide = new BigDecimal(fen).divide(ONE_HUNDRED, 2, 7);
        return divide.toString();
    }

    /**
     * 元转分
     *
     * @param yuan 元
     * @return 分
     */
    public static String yuanToFen(String yuan) {
        BigDecimal multiply = new BigDecimal(yuan).multiply(ONE_HUNDRED);
        return String.valueOf(multiply.intValue());
    }

    /**
     * 获取区间随机金额
     *
     * @param min
     * @param max
     * @return
     */
    public static Integer getRandomMoney(Integer min, Integer max) {
        return new Random().nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 金额散列算法,最后一个数额会较大,弃用(多线程环境下无影响)
     *
     * @param num   散列数量
     * @param money 散列金额
     * @return
     */
    public static double[] getMoney(double money, int num) {
        Random r = new Random();
        DecimalFormat format = new DecimalFormat(".##");

        double middle = Double.parseDouble(format.format(money / num));
        double[] dou = new double[num];
        double redMoney = 0;
        double nextMoney = money;
        double sum = 0;
        int index = 0;
        for (int i = num; i > 0; i--) {
            if (i == 1) {
                dou[index] = nextMoney;
            } else {
                while (true) {
                    String str = format.format(r.nextDouble() * nextMoney);
                    redMoney = Double.parseDouble(str);
                    if (redMoney > 0 && redMoney < middle) {
                        break;
                    }
                }
                nextMoney = Double.parseDouble(format.format(nextMoney - redMoney));
                sum = sum + redMoney;
                dou[index] = redMoney;
                middle = Double.parseDouble(format.format(nextMoney / (i - 1)));
                index++;
            }
        }
        return dou;
    }

    /**
     * 金额散列算法
     *
     * @param num   散列数量
     * @param money 散列金额
     * @return
     */
    public static List<Double> getMoneys(int num, double money) {
        List<Double> array = new ArrayList<>();
        Random r = new Random();
        double sum = 0;
        for (int i = 0; i < num; i++) {
            array.add(r.nextDouble() * money + 0.01 * num * money);//经过小小的计算，这样使最小的钱尽量接近0.01；num越大，此计算越没有用
            sum += array.get(i);
        }
        for (int i = 0; i < array.size(); i++) {
            array.set(i, array.get(i) / sum * money);
        }
        Collections.sort(array);
        for (int i = 0; i < array.size(); i++) {//将钱进行分配；
            if (array.get(i) <= 0.01) {//不足0.01的直接给0.01；
                //z-=0.01;
                array.set(i, 0.01);
            } else if (i == array.size() - 1) {
                //array.set(i, (int)(z*100)*1.0/100);//将剩余的一点money给最后一个人，因为排序，最后一个人最大份，所以最后分配的肯定是正数
                BigDecimal he = new BigDecimal("0");
                for (int j = 0; j < array.size() - 1; j++) {
                    BigDecimal b = new BigDecimal(Double.toString(array.get(j)));
                    he = he.add(b);
                }
                BigDecimal moneyb = new BigDecimal(Double.toString(money));
                array.set(i, moneyb.subtract(he).doubleValue());
            } else {
                array.set(i, (int) (array.get(i) * 100) * 1.0 / 100);
                //z-=array.get(i);
            }
        }
        Collections.shuffle(array);
        return array;
    }

    public static Integer getCutPrice(int num, double money) {
        List<Double> moneys = getMoneys(num, money);
        return moneys.get(new Random().nextInt(moneys.size()) % (moneys.size() + 1)).intValue();
    }

    public static void main(String[] args) {
        int num = 10;
        double money = 10000;
        double result = 0;
        while (num > 0) {
            Integer value = getCutPrice(num, money);
            System.out.println(value);
            money -= value;
            result += value;
            num--;
        }
        System.out.println(result);
    }
}
