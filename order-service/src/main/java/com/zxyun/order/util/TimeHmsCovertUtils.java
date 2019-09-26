package com.zxyun.order.util;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @des: 时间转换相关(传入的数据格式必须对应，否则返回空)，数据格式自行检验（HmsPattern），格式不对会造成数据出错
 * @Author: given
 * @Date 2019/7/12 13:43
 */
public class TimeHmsCovertUtils {
    private static final String[] HH_MM_REGEX = new String[]{"([0-1][0-9]|(2[0-3]))", "([0-5][0-9])$"};

    private static final String[] HH_MM_SS_REGEX = new String[]{"([0-1][0-9]|(2[0-3]))","([0-5][0-9])","([0-5][0-9])$"};

    private static final String[] MM_SS_REGEX =  new String[]{"([0-5][0-9])", "([0-5][0-9])$"};

    private static final String[] HH_MM_HH_MM_REGEX = new String[]{"([0-1][0-9]|(2[0-3])):([0-5][0-9])", "([0-2][0-9]|(2[0-3])):([0-5][0-9])"};

    /**
     * 获取时间精度为小时（四舍五入）默认“:”符号分割
     * @param time
     * @param pattern
     * @return
     */
    public static BigDecimal toHour (String time, HmsPattern pattern) {
        return toHour(time, pattern, HmsSplitPattern.SYMBOL1);
    }

    /**
     * 获取时间精度为分钟（四舍五入）默认“:”符号分割
     * @param time
     * @param pattern
     * @return
     */
    public static BigDecimal toMinute (String time, HmsPattern pattern) {
        return toMinute(time, pattern, HmsSplitPattern.SYMBOL1);
    }

    /**
     * 获取时间精度为秒（四舍五入）默认“:”符号分割
     * @param time
     * @param pattern
     * @return
     */
    public static BigDecimal toSeconds (String time, HmsPattern pattern) {
        return toSeconds(time, pattern, HmsSplitPattern.SYMBOL1);
    }

    /**
     * 获取时间精度为小时（四舍五入）自定义符号分割
     * @param time
     * @param pattern
     * @param hmsSplitPattern
     * @return
     */
    public static BigDecimal toHour (String time, HmsPattern pattern, HmsSplitPattern hmsSplitPattern) {
        Function<List<Integer>, BigDecimal> function;

        switch (pattern) {
            case HH_MM:
                function = (splitTimes) -> {
                    int minute = splitTimes.get(0)*60 + splitTimes.get(1);

                    return new BigDecimal(minute).divide(new BigDecimal("60"), 2, BigDecimal.ROUND_HALF_UP);
                };
                break;
            case MM_SS:
                function = (splitTimes) -> {
                    long seconds = splitTimes.get(0)*60 + splitTimes.get(1);

                    return new BigDecimal(seconds).divide(new BigDecimal("3600"), 2, BigDecimal.ROUND_HALF_UP);
                };
                break;
            case HH_MM_SS:
                function = (splitTimes) -> {
                    long seconds1 = splitTimes.get(0)*3600 + splitTimes.get(1)*60 + splitTimes.get(2);

                    return new BigDecimal(seconds1).divide(new BigDecimal("3600"), 2,  BigDecimal.ROUND_HALF_UP);
                };
                break;
            default:
                return BigDecimal.ZERO;
        }

        return hmsExecute(time, pattern, hmsSplitPattern, function);
    }

    /**
     * 获取时间精度为分钟（四舍五入）自定义符号分割
     * @param time
     * @param pattern
     * @param hmsSplitPattern
     * @return
     */
    public static BigDecimal toMinute (String time, HmsPattern pattern, HmsSplitPattern hmsSplitPattern) {

        Function<List<Integer>, BigDecimal> function;

        switch (pattern) {
            case HH_MM:
                function = (splitTimes) -> {
                    int minute = splitTimes.get(0)*60 + splitTimes.get(1);

                    return new BigDecimal(minute);
                };
                break;
            case MM_SS:
                function = (splitTimes) -> {
                    long seconds = splitTimes.get(0)*60 + splitTimes.get(1);

                    return new BigDecimal(seconds).divide(new BigDecimal("60"), 2,  BigDecimal.ROUND_HALF_UP);
                };
                break;
            case HH_MM_SS:
                function = (splitTimes) -> {
                    long seconds1 = splitTimes.get(0)*3600 + splitTimes.get(1)*60 + splitTimes.get(2);

                    return new BigDecimal(seconds1).divide(new BigDecimal("60"), 2, BigDecimal.ROUND_HALF_UP).setScale(2);
                };
                break;
            default:
                return BigDecimal.ZERO;
        }

        return hmsExecute(time, pattern, hmsSplitPattern, function);
    }

    /**
     * 获取时间精度为秒（四舍五入）自定义符号分割
     * @param time
     * @param pattern
     * @param hmsSplitPattern
     * @return
     */
    public static BigDecimal toSeconds (String time, HmsPattern pattern, HmsSplitPattern hmsSplitPattern) {

        Function<List<Integer>, BigDecimal> function;

        switch (pattern) {
            case HH_MM:
                function = (splitTimes) -> {
                    long seconds0 = splitTimes.get(0)*3600 + splitTimes.get(1)*60;

                    return new BigDecimal(seconds0);
                };
                break;
            case MM_SS:
                function = (splitTimes) -> {
                    long seconds1 = splitTimes.get(0)*60 + splitTimes.get(1);

                    return new BigDecimal(seconds1);
                };
                break;
            case HH_MM_SS:
                function = (splitTimes) -> {
                    long seconds2 = splitTimes.get(0)*3600 + splitTimes.get(1)*60 + splitTimes.get(2);

                    return new BigDecimal(seconds2);
                };
                break;
            default:
                return BigDecimal.ZERO;
        }

        return hmsExecute(time, pattern, hmsSplitPattern, function);
    }

    /**
     * 转换成秒（不同分割符号）
     * @param time
     * @param hmsSplitPattern
     * @return
     */
    public static BigDecimal HHMMtoSeconds (String time, HmsSplitPattern hmsSplitPattern) {

        Function<List<Integer>, BigDecimal> function = (splitTimes) -> {
            long seconds0 = splitTimes.get(0)*3600 + splitTimes.get(1)*60;

            return new BigDecimal(seconds0);
        };

        return hmsExecute(time, HmsPattern.HH_MM, hmsSplitPattern, function);
    }

    /**
     * 转换成秒（不同分割符号）
     * @param time
     * @param hmsSplitPattern
     * @return
     */
    public static BigDecimal HHMMSStoSeconds (String time, HmsSplitPattern hmsSplitPattern) {

        Function<List<Integer>, BigDecimal> function = (splitTimes) -> {
            long seconds2 = splitTimes.get(0)*3600 + splitTimes.get(1)*60 + splitTimes.get(2);

            return new BigDecimal(seconds2);
        };

        return hmsExecute(time, HmsPattern.HH_MM_SS, hmsSplitPattern, function);
    }

    /**
     * 转换成秒（不同分割符号）
     * @param time
     * @param hmsSplitPattern
     * @return
     */
    public static BigDecimal MMSStoSeconds (String time, HmsSplitPattern hmsSplitPattern) {

        Function<List<Integer>, BigDecimal> function = (splitTimes) -> {
            long seconds1 = splitTimes.get(0)*60 + splitTimes.get(1);

            return new BigDecimal(seconds1);
        };

        return hmsExecute(time, HmsPattern.MM_SS, hmsSplitPattern, function);
    }

    /**
     * 计算时间区间 仅支持hh:mm hh:mm 格式的时间
     * @param time
     * @param hmsSplitPattern
     * @return 长度为2，第一个：开始时间s 第二个：结束时间s
     */
    public static List<BigDecimal> HHMMHHMMtoSeconds (String time, HmsSplitPattern hmsSplitPattern) {

        Function<List<String>, List<BigDecimal>> function = (splitTimes) -> {
            BigDecimal start = HHMMtoSeconds(splitTimes.get(0), HmsSplitPattern.SYMBOL1);

            BigDecimal end = HHMMtoSeconds(splitTimes.get(1), HmsSplitPattern.SYMBOL1);

            List<BigDecimal> bigDecimals = new ArrayList<>();
            bigDecimals.add(start);
            bigDecimals.add(end);

            return bigDecimals;
        };

        return hmExecute(time, hmsSplitPattern, function);
    }

    /**
     * 获取今天对应的时间
     * @param time
     * @param pattern
     * @param hmsSplitPattern
     * @return
     */
    public static DateTime toAsTodayDateTime(String time, HmsPattern pattern, HmsSplitPattern hmsSplitPattern) {
        Function<List<Integer>, DateTime> function;

        DateTime dateTime = new DateTime();

        switch (pattern) {
            case HH_MM:
                function = (splitTimes) -> {
                   return new DateTime(dateTime.getYear(), dateTime.monthOfYear().get(), dateTime.getDayOfMonth(), splitTimes.get(0),  splitTimes.get(1),0);
                };
                break;
            case MM_SS:
                function = (splitTimes) -> {
                    return new DateTime(dateTime.getYear(), dateTime.monthOfYear().get(), dateTime.getDayOfMonth(), 0, splitTimes.get(0),  splitTimes.get(1));
                };
                break;
            case HH_MM_SS:
                function = (splitTimes) -> {
                    return new DateTime(dateTime.getYear(), dateTime.monthOfYear().get(), dateTime.getDayOfMonth(), splitTimes.get(0),  splitTimes.get(1), splitTimes.get(2));
                };
                break;
            default:
                return dateTime;
        }

        return hmsExecute(time, pattern, hmsSplitPattern, function);
    }

    public static Date toAsTodayDate (String time, HmsPattern pattern, HmsSplitPattern hmsSplitPattern) {
        DateTime dateTime = toAsTodayDateTime(time, pattern, hmsSplitPattern);

        return dateTime.toDate();
    }

    /**
     * 今天的最早时间
     * @return
     */
    public static DateTime toDayFirstTime () {
        return toAsTodayDateTime("00:00:00", HmsPattern.HH_MM_SS, HmsSplitPattern.SYMBOL1);
    }

    /**
     * 今天的最晚时间
     * @return
     */
    public static DateTime toDayEndTime () {
        return toAsTodayDateTime("23:59:59", HmsPattern.HH_MM_SS, HmsSplitPattern.SYMBOL1);
    }

    public static BigDecimal toCurrentSeconds () {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Calendar calendar = Calendar.getInstance();

        String dateName = df.format(calendar.getTime());

        return HHMMSStoSeconds(dateName, HmsSplitPattern.SYMBOL1);
    }

    /**
     * 当前时间超过或等于传入的时间
     * @param time
     * @param hmsSplitPattern
     * @return
     */
    public static boolean currentSecondsIsOverHHMM (String time, HmsSplitPattern hmsSplitPattern) {
        BigDecimal currentSeconds = toCurrentSeconds();

        BigDecimal compareSeconds = HHMMtoSeconds(time, hmsSplitPattern);

        return currentSeconds.compareTo(compareSeconds) >= 0;
    }

    /**
     * 当前时间超过或等于传入的时间
     * @param time
     * @param currentSeconds 当前时间 秒为单位
     * @param hmsSplitPattern
     * @return
     */
    public static boolean currentSecondsIsOverHHMM (String time, BigDecimal currentSeconds,  HmsSplitPattern hmsSplitPattern) {

        BigDecimal compareSeconds = HHMMtoSeconds(time, hmsSplitPattern);

        return currentSeconds.compareTo(compareSeconds) >= 0;
    }

    /**
     * 自定义分割时间计算
     * @param time
     * @param pattern
     * @param hmsSplitPattern
     * @param function
     * @param <R>
     * @return
     */
    public static <R> R hmsExecute (String time, HmsPattern pattern, HmsSplitPattern hmsSplitPattern, Function<List<Integer>, R> function) {

//        if (!pattern.validate(time, hmsSplitPattern.getSpiltSymbol())) {
//            return null;
//        }
        if (time == null) {
            return null;
        }

        List<Integer> splitTimes = Arrays.stream(time.split(hmsSplitPattern.getSpiltSymbol()))
                .filter(e -> e != null)
                .map(e -> Integer.parseInt(e.replaceAll(" ", "")))
                .collect(Collectors.toList());

        return function.apply(splitTimes);
    }

    /**
     * 自定义分割时间计算 暂时仅支持hh:mm hh:mm 格式的时间并且中间分隔符号不能为“:”
     * @param time
     * @param hmsSplitPattern
     * @param function
     * @param <R>
     * @return
     */
    public static <R> R hmExecute (String time, HmsSplitPattern hmsSplitPattern, Function<List<String>, R> function) {
        if (hmsSplitPattern == HmsSplitPattern.SYMBOL1) {
            return null;
        }

//        if (!HmsPattern.HH_MM_HH_MM.validate(time, hmsSplitPattern.getSpiltSymbol())) {
//            return null;
//        }

        List<String> splitTimes = Arrays.stream(time.split(hmsSplitPattern.getSpiltSymbol()))
                .filter(e -> e != null)
                .map(e -> e.replaceAll(" ", ""))
                .collect(Collectors.toList());

        return function.apply(splitTimes);
    }

    /**
     * 时间格式枚举
     */
    public enum HmsPattern {
        //hh 符号 mm
        HH_MM ("HH_mm") {
            @Override
            protected String getRegex(String spiltSymbol) {
                return Arrays.stream(HH_MM_REGEX).collect(Collectors.joining(spiltSymbol));
            }
        },

        //hh 符号 mm 符号 ss
        HH_MM_SS ("HH_MM_SS") {
            @Override
            protected String getRegex(String spiltSymbol) {
                return Arrays.stream(HH_MM_SS_REGEX).collect(Collectors.joining(spiltSymbol));
            }
        },

        //mm 符号 ss
        MM_SS ("MM_SS") {
            @Override
            protected String getRegex(String spiltSymbol) {
                return Arrays.stream(MM_SS_REGEX).collect(Collectors.joining(spiltSymbol));
            }
        },

        //hh:mm 符号 hh:mm
        HH_MM_HH_MM ("HH_MM_HH_MM") {
            @Override
            protected String getRegex(String spiltSymbol) {
                return Arrays.stream(HH_MM_HH_MM_REGEX).collect(Collectors.joining(spiltSymbol));
            }
        },

        ;

        private String pattern;

        HmsPattern(String pattern) {
            this.pattern = pattern;
        }

        public String getPattern() {
            return pattern;
        }

        protected abstract String getRegex (String spiltSymbol);

        /**
         * 单个时间格式校验
         * @param time
         * @param spiltSymbol
         * @return
         */
        public boolean validate (String time, String spiltSymbol) {
            //去除空格
            time = time.replaceAll(" ", "");

            return Pattern.matches(getRegex(spiltSymbol), time);
        }

        /**
         * 多个时间数据格式校验
         * @param times
         * @param spiltSymbol
         * @return
         */
        public boolean validate (Collection<String> times, String spiltSymbol) {
            String regex = getRegex(spiltSymbol);

            Iterator<String> iterator = times.iterator();

            while (iterator.hasNext()) {
                //去除空格
                String time = iterator.next().replaceAll(" ", "");

                if (!Pattern.matches(regex, time)) {
                    System.out.println(time);
                    return false;
                }
            }

            return true;
        }
    }

    public enum HmsSplitPattern {

        SYMBOL1 (":"),

        SYMBOL2 ("-"),

        SYMBOL3 ("~"),
        ;

        private String spiltSymbol;

        HmsSplitPattern(String spiltSymbol) {
            this.spiltSymbol = spiltSymbol;
        }

        public String getSpiltSymbol() {
            return spiltSymbol;
        }
    }


    public static void main (String [] args) {
        String time = "10~29~39";
        String time1 = "10~34";
        String time2 = "29~30";
        String time3 = "10:39-10:59";
//
//        System.out.println(HmsPattern.HH_MM.validate(time));
//        System.out.println(HmsPattern.HH_MM_SS.validate(time));
        System.out.println(HmsPattern.HH_MM_SS.validate(time, HmsSplitPattern.SYMBOL3.getSpiltSymbol()));
        System.out.println(HmsPattern.HH_MM_HH_MM.validate(time3, HmsSplitPattern.SYMBOL2.getSpiltSymbol()));

//        System.out.println(TimeHmsCovertUtils.toHour(time, HmsPattern.HH_MM_SS, HmsSplitPattern.SYMBOL3));
//        System.out.println(TimeHmsCovertUtils.toHour(time1, HmsPattern.HH_MM, HmsSplitPattern.SYMBOL3));
//        System.out.println(TimeHmsCovertUtils.toHour(time2, HmsPattern.MM_SS, HmsSplitPattern.SYMBOL3));
//
//        System.out.println(TimeHmsCovertUtils.toMinute(time, HmsPattern.HH_MM_SS, HmsSplitPattern.SYMBOL3));
//        System.out.println(TimeHmsCovertUtils.toMinute(time1, HmsPattern.HH_MM, HmsSplitPattern.SYMBOL3));
//        System.out.println(TimeHmsCovertUtils.toMinute(time2, HmsPattern.MM_SS, HmsSplitPattern.SYMBOL3));
//
//        System.out.println(TimeHmsCovertUtils.toSeconds(time, HmsPattern.HH_MM_SS, HmsSplitPattern.SYMBOL3));
//        System.out.println(TimeHmsCovertUtils.toSeconds(time1, HmsPattern.HH_MM, HmsSplitPattern.SYMBOL3));
//        System.out.println(TimeHmsCovertUtils.toSeconds(time2, HmsPattern.MM_SS, HmsSplitPattern.SYMBOL3));
//
//        System.out.println(TimeHmsCovertUtils.toAsTodayDateTime(time, HmsPattern.HH_MM_SS, HmsSplitPattern.SYMBOL3).toString("yyyy-MM-dd HH:mm:ss"));
//        System.out.println(TimeHmsCovertUtils.toAsTodayDateTime(time1, HmsPattern.HH_MM, HmsSplitPattern.SYMBOL3).toString("yyyy-MM-dd HH:mm:ss"));
//        System.out.println(TimeHmsCovertUtils.toAsTodayDateTime(time2, HmsPattern.MM_SS, HmsSplitPattern.SYMBOL3).toString("yyyy-MM-dd HH:mm:ss"));
//
//        System.out.println(TimeHmsCovertUtils.toAsTodayDate(time, HmsPattern.HH_MM_SS, HmsSplitPattern.SYMBOL3));
//        System.out.println(TimeHmsCovertUtils.toAsTodayDate(time1, HmsPattern.HH_MM, HmsSplitPattern.SYMBOL3));
//        System.out.println(TimeHmsCovertUtils.toAsTodayDate(time2, HmsPattern.MM_SS, HmsSplitPattern.SYMBOL3));
//
//        System.out.println(TimeHmsCovertUtils.toDayFirstTime().toString("yyyy-MM-dd HH:mm:ss"));
//        System.out.println(TimeHmsCovertUtils.toDayEndTime().toString("yyyy-MM-dd HH:mm:ss"));

    }
}
