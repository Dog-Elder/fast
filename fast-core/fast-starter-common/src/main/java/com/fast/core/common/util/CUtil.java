package com.fast.core.common.util;


import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import com.fast.core.common.util.bean.BUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 集合工具类
 */
public class CUtil {
    /**
     * 判断一个Collection是否为空， 包含List，Set，Queue
     * 判断是否为null
     * 判断是size是否为0
     * @param coll 要判断的Collection
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Collection<?> coll) {
        return CollectionUtils.isEmpty(coll);
    }

    /**
     * 判断一个Collection是否非空，包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * 判断一个对象数组是否为空
     *
     * @param objects 要判断的对象数组
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Object[] objects) {
        return com.fast.core.common.util.Util.isNull(objects) || (objects.length == 0);
    }

    /**
     * 判断一个对象数组是否非空
     *
     * @param objects 要判断的对象数组
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

    /**
     * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return Util.isNull(map) || CollectionUtils.isEmpty(map);
    }

    /**
     * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }


    /**
     * 随机从列表中返回
     *
     * @param list  目标
     * @param count 随机个数
     * @param <T>   list
     * @return 随机选择的list
     */
    public static <T> List<T> random(List<T> list, int count) {
        List<T> listRandom = new ArrayList<>();
        // 随机取出n条不重复的数据,这里我设置随机取 count 条数据
        for (int i = count; i >= 1; i--) {
            Random random = new Random();
            Math.random();
            // 在数组大小之间产生一个随机数 j
            int j = random.nextInt(list.size() - 1);
            // 取得list 中下标为j 的数据存储到 listRandom 中
            listRandom.add(list.get(j));
            // 把已取到的数据移除,避免下次再次取到出现重复
            list.remove(j);
        }
        return listRandom;
    }

    /**
     * 找出两个集合的不同元素,使用HashSet,数据量越大性能优势体现越为明显,亲测和使用hashmap时不分上下，甚至更快
     *
     * @param collection1 集合一
     * @param collection2 集合二
     * @return Collection 不同元素
     */
    public static <T> Collection<T> getDiffByHashSet(Collection<T> collection1, Collection<T> collection2) {
        Collection<T> temp = new ArrayList<>();
        // hashSet底层通过hashMap实现的
        Set<T> set = new HashSet<>((int) ((float) (collection1.size() + collection2.size()) / 0.75F + 1.0F));
        for (T s1 : collection1) {
            set.add(s1);
        }
        for (T s2 : collection2) {
            // 添加不成功说明元素共有
            if (!set.add(s2)) {
                temp.add(s2);
            }
            set.add(s2);
        }
        // 删除set中存在temp的元素
        set.removeAll(temp);
        return set;
    }

    /**
     * 得到重复的元素
     *
     * @Date: 2022/4/14 14:03
     * @return: java.util.List<T>
     **/
    public static <T> List<T> getDuplicateElements(Collection<T> list) {
        return list.stream()
                .collect(Collectors.toMap(e -> e, e -> 1, Integer::sum)) //  获得元素出现频率的 Map，键为元素，值为元素出现的次数
                .entrySet().stream() //  Set<Entry>转换为Stream<Entry>
                .filter(entry -> entry.getValue() > 1) //  过滤出元素出现次数大于 1 的 entry
                .map(Map.Entry::getKey) //  获得 entry 的键（重复元素）对应的 Stream
                .collect(Collectors.toList()); //  转化为 List
    }


    public static <T> Set<T> setOf(T... elements) {
        return new HashSet<>(ListUtil.of(elements));
    }


    /**
     * List转 Map
     *
     * @param collection: 集合
     * @param mapper:     属性
     * @Date: 2022/4/20 23:14
     * @return: java.util.Map<R, T> <R属性值,T对象>
     **/
    public static <T, R> Map<R, T> toMap(Collection<T> collection, Function<? super T, ? extends R> mapper) {
        return toMap(collection.stream(), mapper);
    }

    /**
     * 集合 流 转 Map
     *
     * @param stream: T流
     * @param mapper: 属性
     * @Date: 2022/4/20 23:14
     * @return: java.util.Map<R, T> <R属性值,T对象>
     **/
    private static <T, R> Map<R, T> toMap(Stream<T> stream, Function<? super T, ? extends R> mapper) {
        return stream.collect(Collectors.toMap(mapper, Function.identity(), (k1, k2) -> k1));
    }


    /**
     * 对集合进行分组
     *
     * @param collection:集合
     * @param mapper:属性
     * @Date: 2022/4/21 20:21
     * @return: java.util.Map<R, java.util.List < T>>
     **/
    public static <T, R> Map<R, List<T>> toGrouping(Collection<T> collection, Function<? super T, ? extends R> mapper) {
        return toGrouping(collection.stream(), mapper);
    }

    /**
     * 对流进行分组
     *
     * @param stream:流
     * @param mapper:属性
     * @Date: 2022/4/21 20:21
     * @return: java.util.Map<R, java.util.List < T>>
     **/
    public static <T, R> Map<R, List<T>> toGrouping(Stream<T> stream, Function<? super T, ? extends R> mapper) {
        return stream.collect(Collectors.groupingBy(mapper));
    }

    /**
     * 对集合降序排序
     *
     * @param elements: 集合
     * @param mapper:   属性
     * @Date: 2022/4/21 23:18
     * @return: java.util.List<T>
     **/
    public static <T, U extends Comparable<? super U>> List<T> desc(Collection<T> elements, Function<? super T, ? extends U> mapper) {
        if (isEmpty(elements)) {
            return ListUtil.empty();
        }
        return sort(elements.stream(), mapper, true);
    }

    /**
     * 对集合升序排序
     *
     * @param elements: 集合
     * @param mapper:   属性
     * @Date: 2022/4/21 23:18
     * @return: java.util.List<T>
     **/
    public static <T, U extends Comparable<? super U>> List<T> asc(Collection<T> elements, Function<? super T, ? extends U> mapper) {
        if (isEmpty(elements)) {
            return ListUtil.empty();
        }
        return sort(elements.stream(), mapper, false);
    }

    /**
     * 对集合排序
     *
     * @param stream: 流
     * @param mapper: 属性
     * @param desc:   降序
     * @Date: 2022/4/21 23:18
     * @return: java.util.List<T>
     **/
    private static <T, U extends Comparable<? super U>> List<T> sort(Stream<T> stream, Function<? super T, ? extends U> mapper, boolean desc) {
        if (desc) {
            return stream.sorted(Comparator.comparing(mapper).reversed()).collect(Collectors.toList());
        }
        return stream.sorted(Comparator.comparing(mapper)).collect(Collectors.toList());
    }

    /**
     * 对集合排序
     *
     * @param elements: 集合
     * @param mapper: 属性
     * @param desc:   降序
     **/
    public static <T, U extends Comparable<? super U>> List<T> sort(Collection<T> elements, Function<? super T, ? extends U> mapper, boolean desc) {
        Stream<T> stream = elements.stream();
        if (desc) {
            return stream.sorted(Comparator.comparing(mapper).reversed()).collect(Collectors.toList());
        }
        return stream.sorted(Comparator.comparing(mapper)).collect(Collectors.toList());
    }

    /**
     * desc 对集合List进行排序
     *
     * @param orderDirection 排序方向 asc升序，desc降序
     * @param orderField     排序字段
     **/
    static <T> List<T> sort(List<T> data, String orderField, String orderDirection) {

        if (data.size() == 0 || StringUtils.isBlank(orderField) || StringUtils.isBlank(orderDirection)) {
            return data;
        }

        if (!Com.OrderDirections.contains(orderDirection.toLowerCase())) {
            LogUtil.err(new Exception("排序方向" + orderDirection + "非法"));
            return data;
        }

        Class<?> clazz = data.get(0).getClass();
        final Method getter = ReflectUtil.getMethod(clazz, "get" + SUtil.capitalized(orderField));
        if (getter == null) {
            return data;
        }
        Class<?> returnType = getter.getReturnType();
        if (Comparable.class.isAssignableFrom(returnType) || returnType.isPrimitive()) {
            data.sort((e1, e2) -> {
                try {
                    Comparable val1 = (Comparable) getter.invoke(e1);
                    Comparable val2 = (Comparable) getter.invoke(e2);
                    if (Objects.equals(val1, val2)) {
                        return 0;
                    }
                    int r = val1 == null ? -1 : (val2 == null ? 1 : val1.compareTo(val2));
                    return orderDirection.equals("asc") ? r : -r;
                } catch (Exception e) {
                    LogUtil.err(e);
                }
                return 0;
            });
        } else {
            LogUtil.err(new Exception(orderField + "不支持排序"));
        }

        return data;
    }

    /**
     * 取T属性list
     *
     * @param elements:集合
     * @param mapper:属性
     * @return: java.util.List<R>
     **/
    public static <T, R> List<R> getPropertyList(Collection<T> elements, Function<? super T, ? extends R> mapper) {
        return getPropertyList(elements.stream(), mapper);
    }

    /**
     * 取T属性list
     *
     * @param stream    : 流
     * @param mapper:属性
     * @return: java.util.List<R>
     **/
    public static <T, R> List<R> getPropertyList(Stream<T> stream, Function<? super T, ? extends R> mapper) {
        return stream.map(mapper).collect(Collectors.toList());
    }

    /**
     * 取T属性Set
     *
     * @param elements:集合
     * @param mapper:属性
     * @return: java.util.Set<R>
     **/
    public static <T, R> Set<R> getPropertySet(Collection<T> elements, Function<? super T, ? extends R> mapper) {
        return getPropertySet(elements.stream(), mapper);
    }

    /**
     * 取T属性Set
     *
     * @param stream    : 流
     * @param mapper:属性
     * @return: java.util.Set<R>
     **/
    public static <T, R> Set<R> getPropertySet(Stream<T> stream, Function<? super T, ? extends R> mapper) {
        return stream.map(mapper).collect(Collectors.toSet());
    }

    /**
     * 将From类型的List复制成To类型的List
     * 复制list,忽略值为null的属性<br>
     * 如果sourceList为空或者T没有无参构造,则返回空列表
     *
     * 注意: copy集合是无法直接返回正确的Page参数 ,如有分页需求 可以使用
     * @see PageUtils#copy(List, Class)
     *
     * @param sourceList  源集合
     * @param targetClazz 目标类型
     */
    public static <S, T> List<T> copy(Collection<S> sourceList, Class<T> targetClazz) {
        List<T> list = new ArrayList<>();
        if (sourceList == null || sourceList.size() < 1) {
            return list;
        }
        Assert.notNull(targetClazz, "TargetClazz must not be null");
        try {
            targetClazz.getDeclaredConstructor().newInstance();
        } catch (Throwable e) {
            LogUtil.err(e);
            return list;
        }

        Class<?> sourceClass = null;
        Optional<S> f = sourceList.stream().findFirst();
        if (f.isPresent()) {
            sourceClass = f.get().getClass();
        }
        if (sourceClass == null) {
            return list;
        }
        HashMap<PropertyDescriptor, PropertyDescriptor> pdMap = BUtil.getPropertyDescriptorsMap(sourceClass, targetClazz);
        sourceList.forEach(source -> {
            try {
                T target = targetClazz.getDeclaredConstructor().newInstance();
                BUtil.copy(source, target, pdMap, true);
                list.add(target);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return list;
    }

    /**
     * List<jsonObjectString> 转 list
     * 由Redis中返回的List<String> jsonObjectString 无法通过常规代码进行转换
     *
     * @Date: 2022/9/22 22:15
     * @param source: jsonObjectString集合
     * @param target: 目标集合
     * @return: java.util.List<T>
     **/
    public static <T>List<T> jsonListStrToList(List<String> source,Class<T> target){
        List<T> res = new ArrayList<>();
        if (isEmpty(source)) {
            return res;
        }
        source.forEach(ele->{
            res.add(JSONUtil.toBean(ele, target));
        });
        return res;
    }
}
