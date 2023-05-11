package com.fast.core.common.util;

import lombok.Getter;
import lombok.Setter;

import java.time.*;
import java.time.temporal.ChronoUnit;


public class DUtil {
    public static LocalDateTime MinDate;
    public static LocalDateTime MaxDate;

    static {
        try {
            MinDate = LocalDateTime.of(1970, 1, 1, 0, 0);
            MaxDate = LocalDateTime.of(2970, 1, 1, 0, 0);
        } catch (Exception ignored) {
        }
    }

    /**
     * 获取今日起始时间 示例：2012-12-12 0:0:0
     */
    public static LocalDateTime todayStart() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    }

    public static boolean afterToday(LocalDate date) {
        return date != null && date.isAfter(LocalDate.now());
    }

    public static LocalDateTime parseYmdhms(String str) {
        return LocalDateTime.parse(str, Com.YMDHMS);
    }

    public static int compare(LocalDateTime left, LocalDateTime right) {
        left = left == null ? LocalDateTime.MIN : left;
        right = right == null ? LocalDateTime.MIN : right;
        return left.compareTo(right);
    }

    /**
     * 该方法主要用于按时间范围检索时对起始和截止时间进行调整
     * 情景1、客户端传递过来的时间范围，有可能没有传递起始时间，有可能没有传递截止时间，有可能都没有传递，
     * 若Server端每次都进行这样的判断然后再拼装Sql语句未免太麻烦
     * 情景2、按天检索时，若起始和截止日期一样，其实是想查找这一天内的数据，需要对截止时间处理一下：增加一天，然后按照左闭右开的区间进行sql检索
     * <p>
     * 为了简化上述情景，可以使用该函数进行日期调整
     *
     * @param isByDay 是否按天检索
     */
    public static AdjustDateTimeResult adjustDate(LocalDateTime from, LocalDateTime to, boolean isByDay) {
        AdjustDateTimeResult ret = new AdjustDateTimeResult();
        ret.from = from == null ? MinDate : from;
        ret.to = to == null ? MaxDate : to;
        if (isByDay) {
            ret.to = ret.to.plusDays(1);
        }
        return ret;
    }

    /**
     * 该方法主要用于按时间范围检索时对起始和截止时间进行调整
     * 使用场景：客户端传递过来的时间范围，有可能没有传递起始时间，有可能没有传递截止时间，有可能都没有传递，
     * 若Server端每次都进行这样的判断未免太麻烦
     * <p>
     * 为了简化上述情景，可以使用该函数进行日期调整
     **/
    public static AdjustDateTimeResult adjustDate(LocalDateTime from, LocalDateTime to) {
        return adjustDate(from, to, false);
    }

    /**
     * 计算相差天数
     */
    public static long days(LocalDate from, LocalDate to) {
        return ChronoUnit.DAYS.between(from, to);
    }

    /**
     * 计算相差天数，注意：这是严格计算两个LocaDateTime相关的天数，就算跨天但如果相关不足1天会仍会按0计算
     */
    public static long days(LocalDateTime from, LocalDateTime to) {
        return Duration.between(from, to).toDays();
    }

    /**
     * 先转成LocalDate，再计算相关天数
     */
    public static long daysByDate(LocalDateTime from, LocalDateTime to) {
        return days(from.toLocalDate(), to.toLocalDate());
    }

    /**
     * 该方法主要用于按时间范围检索时对起始和截止时间进行调整
     * 情景1、客户端传递过来的时间范围，有可能没有传递起始时间，有可能没有传递截止时间，有可能都没有传递，
     * 若Server端每次都进行这样的判断未免太麻烦
     * 情景2、按天检索时，若起始和截止日期一样，其实是想查找这一天内的数据，需要对截止时间处理一下：增加一天，然后按照左闭右开的区间进行检索
     * <p>
     * 为了简化上述情景，可以使用该函数进行日期调整
     */
    public static AdjustDateTimeResult adjustDate(LocalDate from, LocalDate to) {
        return adjustDate(from == null ? null : LocalDateTime.of(from, LocalTime.MIN), to == null ? null : LocalDateTime.of(to, LocalTime.MIN), true);
    }

    /**
     * 判断target是否属于[from,to]
     */
    public static boolean isBetween(LocalTime target, LocalTime from, LocalTime to) {
        if (target == null) {
            target = LocalTime.MIN;
        }
        return !target.isBefore(from) && !target.isAfter(to);
    }

    /**
     * 判断target是否属于[from,to]，to可以小于等于from，表示跨天
     *
     * @see DUtil#isBetween(LocalTime, LocalTime, LocalTime)
     */
    public static boolean isBetweenCross(LocalTime target, LocalTime from, LocalTime to) {
        if (to.isAfter(from)) {
            return isBetween(target, from, to);
        } else {
            return target.isAfter(from) ||
                    isBetween(target, LocalTime.of(0, 0, 0), to);
        }
    }

    /**
     * 判断target是否属于(from,to)
     */
    public static boolean isBetweenExclude(LocalTime target, LocalTime from, LocalTime to) {
        return target.isAfter(from) && target.isBefore(to);
    }

    /**
     * 判断target是否属于[from,to)
     */
    public static boolean isBetweenRightExclude(LocalTime target, LocalTime from, LocalTime to) {
        return !target.isBefore(from) && target.isBefore(to);
    }

    /**
     * 判断target是否属于[from,to]
     */
    public static boolean isBetween(LocalDate target, LocalDate from, LocalDate to) {
        AdjustDateTimeResult d = adjustDate(from, to);
        if (target == null) {
            target = MinDate.toLocalDate();
        }
        return !target.isBefore(d.from.toLocalDate()) && target.isBefore(d.to.toLocalDate());
    }

    /**
     * 判断target是否属于[from,to]
     */
    public static boolean isBetween(LocalDateTime target, LocalDateTime from, LocalDateTime to) {
        AdjustDateTimeResult d = adjustDate(from, to);
        if (target == null) {
            target = MinDate;
        }
        return !target.isBefore(d.from) && !target.isAfter(d.to);
    }

    /**
     * desc 将unix时间戳转为LocalDateTime
     **/
    public static LocalDateTime fromUnix(long unix) {
        return LocalDateTime.ofEpochSecond(unix, 0, OffsetDateTime.now().getOffset());
    }

    /**
     * desc 将LocalDateTime转为unix时间戳
     **/
    public static int toUnix(LocalDateTime time) {
        return (int) time.toEpochSecond(OffsetDateTime.now().getOffset());
    }

    public static int nowUnix() {
        return toUnix(LocalDateTime.now());
    }

    public static int toUnix(LocalDate date) {
        ZoneId zoneId = ZoneId.systemDefault();
        return (int) date.atStartOfDay(zoneId).toEpochSecond();
    }

    /**
     * desc 转为毫秒时间戳
     **/
    public static long toMilli(LocalDateTime time) {
        return time.toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();
    }

    /**
     * desc 将毫秒时间戳转为LocalDateTime
     **/
    public static LocalDateTime fromMilli(Long milli) {
        if (milli == null) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milli), ZoneId.systemDefault());
    }

    /**
     * 计算二者时间差值，以second为单位
     *
     * @param from 起时时间（较小的时间，如果为空则视为LocalDateTime.MIN）
     * @param now  结束时间（较大的时间）
     * @return 秒数
     */
    public static long betweenSeconds(LocalDateTime from, LocalDateTime now) {
        if (from == null) {
            from = LocalDateTime.MIN;
        }
        return Duration.between(from, now).getSeconds();
    }

    @Getter
    @Setter
    public static class AdjustDateTimeResult {
        private LocalDateTime from;
        private LocalDateTime to;
    }

    @Getter
    @Setter
    public static class AdjustDateResult {
        private LocalDateTime from;
        private LocalDateTime to;
    }

    /**
     * 导表时的时间限制时间检索范围不能超过1个月 Liu xp
     */
    public static AdjustDateTimeResult adjustExcelDate(LocalDateTime startTime, LocalDateTime endTime, boolean isByDay) {
        if (startTime == null && endTime != null) {
            startTime = endTime.plusMonths(-1);
        } else if (startTime != null && endTime == null) {
            endTime = startTime.plusMonths(1);
        } else if (startTime == null) {
            endTime = LocalDateTime.now();
            startTime = endTime.plusMonths(-1);
        } else {
            if (endTime.isAfter(startTime.plusMonths(1))) {
                startTime = endTime.plusMonths(-1);
            }
        }
        return DUtil.adjustDate(startTime, endTime, isByDay);
    }

    public static AdjustDateTimeResult adjustExcelDate(LocalDate startTime, LocalDate endTime) {
        return adjustExcelDate(startTime == null ? null : LocalDateTime.of(startTime, LocalTime.MIN), endTime == null ? null : LocalDateTime.of(endTime, LocalTime.MIN), true);
    }

    public static AdjustDateTimeResult adjustExcelDate(LocalDate startTime, LocalDate endTime, boolean isByDay) {
        return adjustExcelDate(startTime == null ? null : LocalDateTime.of(startTime, LocalTime.MIN), endTime == null ? null : LocalDateTime.of(endTime, LocalTime.MIN), isByDay);
    }

    /**
     * 判断两个LocalDateTime年月日部分（也就是LocalDate）是否一样
     */
    public static boolean isSameDate(LocalDateTime left,LocalDateTime right){
        return left.toLocalDate().isEqual(right.toLocalDate());
    }
}
