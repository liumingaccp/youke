package youke.common.utils;

import youke.common.redis.RedisUtil;

import java.util.HashMap;
import java.util.Random;
import java.util.Stack;
import java.util.UUID;

public class IDUtil {

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static int getGrowId(Integer activeId) {
        if (RedisUtil.hasKey("RANDOM_ID")) {
            Integer randomNum = (Integer) RedisUtil.hget("RANDOM_ID", activeId + "");
            if (randomNum != null && randomNum != 0) {
                Integer randomId = new Random().nextInt(3);
                RedisUtil.hset("RANDOM_ID", activeId + "", randomNum + randomId);
                return randomNum + randomId;
            } else {
                int ran = new Random().nextInt(100);
                HashMap<String, Object> map = new HashMap<>();
                map.put(activeId + "", ran);
                RedisUtil.hmset("RANDOM_ID", map);
                return ran;
            }
        } else {
            int ran = new Random().nextInt(100);
            HashMap<String, Object> map = new HashMap<>();
            map.put(activeId + "", ran);
            RedisUtil.hmset("RANDOM_ID", map);
            return ran;
        }
    }

    public static String getRandomIntId() {
        Long endTime = 1514736000000L;
        Long newTime = System.currentTimeMillis() - endTime;
        int ran = new Random().nextInt(100);
        String randStr = newTime + (ran < 10 ? "0" + ran : String.valueOf(ran));
        return randStr;
    }

    public static String getRandomId() {
        return _10_to_62(Long.valueOf(getRandomIntId()));
    }

    public static String getDykId() {
        return "dyk" + getRandomId();
    }

    public static final char[] array = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M'};

    public static String _10_to_62(long number) {
        Long rest = number;
        Stack<Character> stack = new Stack<Character>();
        StringBuilder result = new StringBuilder(0);
        while (rest != 0) {
            stack.add(array[new Long((rest - (rest / 62) * 62)).intValue()]);
            rest = rest / 62;
        }
        for (; !stack.isEmpty(); ) {
            result.append(stack.pop());
        }
        return result.toString();
    }

    public static long _62_to_10(String sixty_str) {
        int multiple = 1;
        long result = 0;
        Character c;
        for (int i = 0; i < sixty_str.length(); i++) {
            c = sixty_str.charAt(sixty_str.length() - i - 1);
            result += _62_value(c) * multiple;
            multiple = multiple * 62;
        }
        return result;
    }

    private static int _62_value(Character c) {
        for (int i = 0; i < array.length; i++) {
            if (c == array[i]) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(getGrowId(5));
    }
}
