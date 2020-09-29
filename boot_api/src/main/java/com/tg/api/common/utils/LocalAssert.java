package com.tg.api.common.utils;

import com.tg.api.common.exception.RRException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 断言工具（内部扩展）
 * @author SH
 * @version 1.0
 */
public final class LocalAssert extends Assert {

    /**
     * 断言两个字符串相等
     * @param value 字符串值
     * @param anotherValue 另一个字符串值
     * @param message 错误提示
     * @return void
     * @throws RRException
     * @author
     *
     */
    public static void equals(String value, String anotherValue, String message, Object... args) throws RRException {
        if (!StringUtils.equals(value, anotherValue)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言两个字符串相同（做trim处理之后比较）
     * @param value 字符串值
     * @param anotherValue 另一个字符串值
     * @param message 错误提示
     * @return void
     * @author
     *
     */
    public static void equalsTrim(String value, String anotherValue, String message, Object... args) throws RRException {
        if (!StringUtils.equals(StringUtils.trimToNull(value), StringUtils.trimToNull(anotherValue))) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言两个字符串不相同（做trim处理之后比较）
     * @param value 字符串值
     * @param anotherValue 另一个字符串值
     * @param message 错误提示
     * @return void
     * @author
     *
     */
    public static void notEqualsTrim(String value, String anotherValue, String message, Object... args) throws RRException {
        if (StringUtils.equals(StringUtils.trimToNull(value), StringUtils.trimToNull(anotherValue))) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言两个对象相等
     * @param value 对象
     * @param anotherValue 另一个对象
     * @param message 错误提示
     * @return void
     * @throws RRException
     * @author
     *
     */
    public static void equals(Object value, Object anotherValue, String message, Object... args) throws RRException {
        if (ObjectUtils.notEqual(value, anotherValue)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 如果满足条件，就开始断言：
     * 断言两个对象相等
     * @param trueOrFalse 布尔条件
     * @param value 对象
     * @param anotherValue 另一个对象
     * @param message 错误提示
     * @return void
     * @throws RRException
     * @author
     *
     */
    public static void equalsIf(Boolean trueOrFalse, Object value, Object anotherValue, String message, Object... args) throws RRException {
        if (trueOrFalse && ObjectUtils.notEqual(value, anotherValue)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言两个对象不相等
     * @param value 对象
     * @param anotherValue 另一个对象
     * @param message 错误提示
     * @return void
     * @throws RRException
     * @author
     *
     */
    public static void notEquals(Object value, Object anotherValue, String message, Object... args) throws RRException {
        if (ObjectUtils.equals(value, anotherValue)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言两个字符串不相等
     * @param value 字符串值
     * @param anotherValue 另一个字符串值
     * @param message 错误提示
     * @return void
     * @throws RRException
     * @author
     *
     */
    public static void notEquals(String value, String anotherValue, String message, Object... args) throws RRException {
        if (StringUtils.equals(value, anotherValue)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言两个字符串不相等
     * @param value 字符串值
     * @param anotherValue 另一个字符串值
     * @param message 错误提示
     * @return void
     * @throws RRException
     * @author
     *
     */
    public static void notEqualsValue(String value, String anotherValue, String message, Object... args) throws RRException {
        if (!StringUtils.equals(value, anotherValue)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言两个字符串相同（如果有字母，大小写不敏感）
     * @param value 字符串值
     * @param anotherValue 另一个字符串值
     * @param message 错误提示
     * @return void
     * @throws RRException
     * @author
     *
     */
    public static void equalsIgnoreCase(String value, String anotherValue, String message, Object... args) throws RRException {
        if (!StringUtils.equalsIgnoreCase(value, anotherValue)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言两个字符串不同（如果有字母，大小写不敏感）
     * @param value 字符串值
     * @param anotherValue 另一个字符串值
     * @param message 错误提示
     * @return void
     * @throws RRException
     * @author
     *
     */
    public static void notEqualsIgnoreCase(String value, String anotherValue, String message, Object... args) throws RRException {
        if (StringUtils.equalsIgnoreCase(value, anotherValue)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言字符串值非空，并且非空白字符串
     * @param value
     * @param message
     * @return void
     * @throws RRException
     * @author
     *
     */
    public static void notBlank(String value, String message, Object... args) throws RRException {
        if (StringUtils.isBlank(value)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 如果满足条件，就开始断言：
     * 断言字符串值非空，并且非空白字符串
     * @param trueOrFalse 布尔条件
     * @param value 字符串
     * @param message 提示语
     * @return void
     * @throws RRException
     * @author
     *      */
    public static void notBlankIf(Boolean trueOrFalse, String value, String message, Object... args) throws RRException {
        if (trueOrFalse && StringUtils.isBlank(value)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言字符串值非空，并且非空白字符串
     * @param value
     * @param code
     * @return void
     * @throws RRException
     * @author
     *
 /*    *//*
    public static void notBlank(String value, Codable code, Object... args) throws RRException {
        if (StringUtils.isBlank(value)) {
            throw new RRException(code, args);
        }
    }*/

    /**
     * 断言字符串值为空或空串
     * @param value
     * @param message
     * @return void
     * @throws RRException
     * @author
     *
     */
    public static void isBlank(String value, String message, Object... args) throws RRException {
        if (StringUtils.isNotBlank(value)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 如果满足条件，就开始断言：
     * 断言字符串值非空
     * @param trueOrFalse 布尔条件
     * @param value 字符串
     * @param message 提示语
     * @return void
     * @throws RRException
     * @author
     *      */
    public static void notEmptyIf(Boolean trueOrFalse, String value, String message, Object... args) throws RRException {
        if (trueOrFalse && StringUtils.isEmpty(value)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言字符串值非空
     * @param value
     * @param message
     * @return void
     * @throws RRException
     * @author
     *
     */
    public static void notEmpty(String value, String message, Object... args) throws RRException {
        if (StringUtils.isEmpty(value)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言字符串值空
     * @param value
     * @param message
     * @return void
     * @throws RRException
     * @author
     *
     */
    public static void isEmpty(String value, String message, Object... args) throws RRException {
        if (StringUtils.isNotEmpty(value)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言字符串值非空
     * @param value
     * @param message
     * @return void
     * @throws RRException
     * @author
     *
     */
    public static void isNotEmpty(StringBuffer value, String message, Object... args) throws RRException {
        if (value == null || value.length() == 0) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言字符串值空
     * @param value
     * @param message
     * @return void
     * @throws RRException
     * @author
     *
     */
    public static void isEmpty(StringBuffer value, String message, Object... args) throws RRException {
        if (value != null && value.length() > 0) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言为真、是
     * @param booleanValue 布尔值
     * @param message 错误提示
     * @return void
     * @throws RRException
     * @author
     *      */
    public static void isTrue(Boolean booleanValue, String message, Object... args) throws RRException {
        if (!booleanValue) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 如果符合前置条件，则开始断言为真、是
     * @param preCondition 前置条件
     * @param booleanValue 布尔值
     * @param message 错误提示
     * @return void
     * @throws RRException
     * @author
     *      */
    public static void isTrueIf(Boolean preCondition, Boolean booleanValue, String message, Object... args) throws RRException {
        if (preCondition && !booleanValue) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言布尔值是false
     * @param booleanValue 被断言的值
     * @param message 提示语
     * @throws RRException
     * @author
     */
    public static void isFalse(Boolean booleanValue, String message, Object... args) throws RRException {
        if (booleanValue == null || booleanValue == true) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 如果满足条件，就开始断言：
     * 断言布尔值是false
     * @param trueOrFalse 布尔条件
     * @param value 被断言的值
     * @param message 提示语
     * @return void
     * @throws RRException
     * @author
     *      */
    public static void isFalseIf(Boolean trueOrFalse, Boolean value, String message, Object... args) throws RRException {
        if (trueOrFalse && (value == null || value == true)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言列表为空
     * @param collection
     * @param message
     * @throws RRException
     */
    public static void isEmpty(Collection<?> collection, String message, Object... args) throws RRException {
        if (collection != null && !collection.isEmpty()) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言列表为空
     * @param collection
     * @param message
     * @throws RRException
     */
    public static void isEmptyList(Collection<?> collection, String message, Object... args) throws RRException {
        if (CollectionUtils.isEmpty(collection)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言列表不为空
     * @param collection
     * @param message
     * @throws RRException
     */
    public static void notEmpty(Collection<?> collection, String message, Object... args) throws RRException {
        if (CollectionUtils.isEmpty(collection)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言数组为空
     * @param array
     * @param message
     * @throws RRException
     */
    public static void isEmpty(Object[] array, String message, Object... args) throws RRException {
        if (array != null && array.length > 0) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言数组不为空
     * @param array
     * @param message
     * @throws RRException
     */
    public static void isNotEmpty(Object[] array, String message, Object... args) throws RRException {
        if (array == null || array.length == 0) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言int数组不为空
     * @param array
     * @param message
     * @throws RRException
     */
    public static void notEmpty(int[] array, String message, Object... args) throws RRException {
        if (array == null || array.length == 0) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言列表不为空
     * @param list
     * @param message
     * @throws RRException
     * @author
     */
    public static void isNotEmpty(Collection<?> list, String message, Object... args) throws RRException {
        if (list == null || list.isEmpty()) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言指定的元素包含在数组内
     * @param value
     * @param array
     * @return void
     * @throws RRException
     * @author
     *
     */
    public static void isInclude(Object value, Object[] array, String message, Object... args) throws RRException {
        if (!ArrayUtils.contains(array, value)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言指定的元素不包含在数组内
     * @param value
     * @param array
     * @param message
     * @return void
     * @throws RRException
     * @author
     *
     */
    public static void notInclude(Object value, Object[] array, String message, Object... args) throws RRException {
        if (ArrayUtils.contains(array, value)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言指定的值包含在字符串数组内（忽略大小写）
     * @param value
     * @param array
     * @param message
     * @return void
     * @throws RRException
     * @author
     *
     */
    public static void isIncludeIgnoreCase(String value, String[] array, String message, Object... args) throws RRException {
        LocalAssert.notEmpty(array, "array，不能为空");
        boolean findFarget = false;
        for (String s : array) {
            if (value == s || (value != null && value.equalsIgnoreCase(s))) {
                findFarget = true;
                break;
            }
        }
        if (!findFarget) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言Map包含指定的属性
     * @param map
     * @param keyOfFind
     * @return void
     * @throws RRException
     * @author
     *      */
    public static void containKey(Map map, Object keyOfFind, String message, Object... args) throws RRException {
        if (!map.containsKey(keyOfFind)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言某整数大于指定整数
     * @param realValue 业务数值
     * @param compareTo 参照数值
     * @param message 提示语
     * @return void
     * @throws RRException
     * @author
     *
     */
    public static void intGreaterThan(Number realValue, Number compareTo, String message, Object... args) throws RRException {
        if (realValue.longValue() <= compareTo.longValue()) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言某整数大于指定整数
     * @param trueOrFalse 布尔条件
     * @param realValue 业务数值
     * @param compareTo 参照数值
     * @param message 提示语
     * @return void
     * @throws RRException
     * @author
     *
     */
    public static void intGreaterThanIf(Boolean trueOrFalse, Number realValue, Number compareTo, String message, Object... args) throws RRException {
        if (trueOrFalse && realValue.longValue() <= compareTo.longValue()) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言某整数大于或等于指定整数
     * @param realValue 业务数值
     * @param compareTo 参照数值
     * @param message 提示语
     * @return void
     * @throws RRException
     * @author
     *
     */
    public static void intGreaterEqual(Number realValue, Number compareTo, String message, Object... args) throws RRException {
        if (realValue.longValue() < compareTo.longValue()) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 如果满足条件，就开始断言：
     * 断言某整数大于或等于指定整数
     * @param trueOrFalse 布尔条件
     * @param realValue 业务数值
     * @param compareTo 参照数值
     * @param message 提示语
     * @return void
     * @throws RRException
     * @author
     *
     */
    public static void intGreaterEqualsIf(Boolean trueOrFalse, Number realValue, Number compareTo, String message, Object... args) throws RRException {
        if (trueOrFalse && realValue.longValue() < compareTo.longValue()) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言某整数等于指定整数
     * @param realValue 业务数值
     * @param compareTo 参照数值
     * @param message 提示语
     * @return void
     * @throws RRException
     * @author
     *
     */
    public static void intEqual(Number realValue, Number compareTo, String message, Object... args) throws RRException {
        if (realValue.longValue() != compareTo.longValue()) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言某整数等于指定整数
     * @param realValue 业务数值
     * @param compareTo 参照数值
     * @param code 错误码
     * @return void
     * @throws RRException
     * @author
     *      */
   /* public static void intEqual(Number realValue, Number compareTo, Codable code, Object... args) throws RRException {
        if (realValue.longValue() != compareTo.longValue()) {
            throw new RRException(code, args);
        }
    }
*/
    /**
     * 如果满足条件，就开始断言：
     * 断言某整数等于指定整数
     * @param trueOrFalse 布尔条件
     * @param realValue 业务数值
     * @param compareTo 参照数值
     * @param message 提示语
     * @return void
     * @throws RRException
     * @author
     *
     */
    public static void intEqual(Boolean trueOrFalse, Number realValue, Number compareTo, String message, Object... args) throws RRException {
        if (trueOrFalse && realValue.longValue() != compareTo.longValue()) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言某整数不等于指定整数
     * @param realValue 业务数值
     * @param compareTo 参照数值
     * @param message 提示语
     * @return void
     * @throws RRException
     * @author
     *      */
    public static void intNotEqual(Number realValue, Number compareTo, String message, Object... args) throws RRException {
        if (realValue.longValue() == compareTo.longValue()) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言某整数不等于指定整数
     * @param realValue 业务数值
     * @param compareTo 参照数值
     * @param code 错误码
     * @return void
     * @throws RRException
     * @author
     *      */
   /* public static void intNotEqual(Number realValue, Number compareTo, Codable code, Object... args) throws RRException {
        if (realValue.longValue() == compareTo.longValue()) {
            throw new RRException(code, args);
        }
    }*/

    /**
     * 如果满足条件，就开始断言：
     * 断言某整数不等于指定整数
     * @param trueOrFalse 布尔条件
     * @param realValue 业务数值
     * @param compareTo 参照数值
     * @param message 提示语
     * @return void
     * @throws RRException
     * @author
     *      */
    public static void intNotEqual(Boolean trueOrFalse, Number realValue, Number compareTo, String message, Object... args) throws RRException {
        if (trueOrFalse && realValue.longValue() == compareTo.longValue()) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言某整数小于指定整数
     * @param realValue 业务数值
     * @param compareTo 参照数值
     * @param message 提示语
     * @return void
     * @throws RRException
     * @author
     *      */
    public static void intLessThan(Number realValue, Number compareTo, String message, Object... args) throws RRException {
        if (realValue.longValue() >= compareTo.longValue()) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 如果满足条件，就开始断言：
     * 断言某整数小于指定整数
     * @param trueOrFalse 布尔条件
     * @param realValue 业务数值
     * @param compareTo 参照数值
     * @param message 提示语
     * @return void
     * @throws RRException
     * @author
     *      */
    public static void intLessThan(Boolean trueOrFalse, Number realValue, Number compareTo, String message, Object... args) throws RRException {
        if (trueOrFalse && realValue.longValue() >= compareTo.longValue()) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言某整数小于或等于指定整数
     * @param realValue 业务数值
     * @param compareTo 参照数值
     * @param message 提示语
     * @return void
     * @throws RRException
     * @author
     *      */
    public static void intLessEqual(Number realValue, Number compareTo, String message, Object... args) throws RRException {
        if (realValue.longValue() > compareTo.longValue()) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 如果满足条件，就开始断言：
     * 断言某整数小于或等于指定整数
     * @param trueOrFalse 布尔条件
     * @param realValue 业务数值
     * @param compareTo 参照数值
     * @param message 提示语
     * @return void
     * @throws RRException
     * @author
     *      */
    public static void intLessEqualsIf(Boolean trueOrFalse, Number realValue, Number compareTo, String message, Object... args) throws RRException {
        if (trueOrFalse && realValue.longValue() > compareTo.longValue()) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言某整数在指定数值区间以内
     * @param realValue 业务数值
     * @param maxCompareTo 参照数值
     * @param message 提示语
     * @return void
     * @throws RRException
     * @author
     *      */
    public static void intRange(Number realValue, Number minCompareTo, Number maxCompareTo, String message, Object... args) throws RRException {
        long value = realValue.longValue();
        if (value < minCompareTo.longValue() || value > maxCompareTo.longValue()) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 如果满足条件，就开始断言：
     * 断言某整数在指定数值区间以内。
     * @param trueOrFalse 布尔条件
     * @param realValue 业务数值
     * @param maxCompareTo 参照数值
     * @param message 提示语
     * @return void
     * @throws RRException
     * @author
     *      */
    public static void intRangeIf(Boolean trueOrFalse,
                                  Number realValue,
                                  Number minCompareTo,
                                  Number maxCompareTo,
                                  String message,
                                  Object... args) throws RRException {
        if (trueOrFalse) {
            long value = realValue.longValue();
            if (value < minCompareTo.longValue() || value > maxCompareTo.longValue()) {
                throw new RRException(LocalStringUtils.fomart(message, args));
            }
        }
    }

    /**
     * 字符串长度限定验证（是否可空、最小长度、最大长度）
     * @param stringValue 字符串
     * @param fieldName 属性名称
     * @param allowNull 是否可空（true是、false否）
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @throws RRException
     */
    public static void stringLength(CharSequence stringValue, String fieldName, boolean allowNull, int minLength, int maxLength) throws RRException {
        if (stringValue == null && allowNull) {
            return;
        }
        if (stringValue == null && !allowNull) {
            throw new RRException(fieldName + "，不能为空！");
        }
        if (stringValue.toString().trim().length() < minLength) {
            throw new RRException(fieldName + "，最少" + minLength + "个字符！");
        }
        if (stringValue.toString().trim().length() > maxLength) {
            throw new RRException(fieldName + "，最多" + maxLength + "个字符！");
        }
    }

    /**
     * 字符串长度限定验证（是否可空、最大长度）<p/>
     * [说明]:最小长度不限
     * @param stringValue 字符串
     * @param fieldName 属性名称
     * @param allowNull 是否可空（true是、false否）
     * @param maxLength 最大长度
     * @throws RRException
     */
    public static void stringLength(CharSequence stringValue, String fieldName, boolean allowNull, int maxLength) throws RRException {
        stringLength(stringValue, fieldName, allowNull, 0, maxLength);
    }

    /**
     * 字符串长度限定验证（最大长度）<p/>
     * [说明]:可空，最小长度不限
     * @param stringValue 字符串
     * @param fieldName 属性名称
     * @param maxLength 最大长度
     * @throws RRException
     */
    public static void stringLength(CharSequence stringValue, String fieldName, int maxLength) throws RRException {
        stringLength(stringValue, fieldName, true, 0, maxLength);
    }

    /**
     * 字符串长度限定验证（最小长度，最大长度）<p/>
     * [说明]:可空，最小长度不限
     * @param stringValue 字符串
     * @param fieldName 属性名称
     * @param maxLength 最大长度
     * @throws RRException
     */
    public static void stringLength(CharSequence stringValue, String fieldName, int minLength, int maxLength) throws RRException {
        stringLength(stringValue, fieldName, true, minLength, maxLength);
    }

    /**
     * 断言是数字
     * @param value 字符串值
     * @param allowNull 是否可空（true是、false否）
     * @param fieldName 字段名称
     * @throws RRException
     */
    public static void isDigits(String value, boolean allowNull, String fieldName, Object... args) throws RRException {
        if (value == null && !allowNull) {
            throw new RRException(LocalStringUtils.fomart(fieldName + "，不能为空", args));
        }
        if (!NumberUtils.isDigits(value)) {
            throw new RRException(LocalStringUtils.fomart(fieldName + "，必须是数字", args));
        }
    }

    /**
     * 断言是数值
     * @param value 字符串值
     * @param allowNull 是否可空（true是、false否）
     * @param fieldName 字段名称
     * @throws RRException
     */
    public static void isNumber(String value, boolean allowNull, String fieldName, Object... args) throws RRException {
        if (value == null && !allowNull) {
            throw new RRException(LocalStringUtils.fomart(fieldName + "，不能为空", args));
        }
        if (!NumberUtils.isNumber(value)) {
            throw new RRException(LocalStringUtils.fomart(fieldName + "，必须是数值", args));
        }
    }

    /**
     * 断言字符串匹配指定正则表达式
     * @param value
     * @param regex
     * @param message
     * @throws RRException
     */
    public static void matchRegex(CharSequence value, String regex, String message, Object... args) throws RRException {
        if (!Pattern.matches(regex, value)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言字符串不匹配指定正则表达式
     * @param value
     * @param regex
     * @param message
     * @throws RRException
     */
    public static void notMatchRegex(CharSequence value, String regex, String message, Object... args) throws RRException {
        if (Pattern.matches(regex, value)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    public static void main(String[] args) throws RRException {
        LocalAssert.notEquals("123", "123", "11");
        //matchRegex("3131121100", "[0-9]{10}|[0-9]{12}", "发票代码，必须是10位或12位纯数字！");
        //notMatchRegex("31311211K", "[0-9]{8}", "发票号码，必须是8位纯数字！");
        //notNull(null, "{}，必须是{}位{}{}！", "发票代码", "8", "纯", "数字");
    }

    /**
     * 断言对象非空
     * @param value
     * @param message
     * @param args
     * @throws RRException
     * @author
     */
    public static void notNull(Object value, String message, Object... args) throws RRException {
        if (value == null) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 如果满足条件，就开始断言；
     * 断言对象非空
     * @param trueOrFalse 布尔条件
     * @param value 字符串
     * @param message 提示语
     * @return void
     * @throws RRException
     * @author
     *      */
    public static void notNullIf(Boolean trueOrFalse, Object value, String message, Object... args) throws RRException {
        if (trueOrFalse && value == null) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言对象非空
     * @param value
     * @param code
     * @param args
     * @throws RRException
     * @author
     */
   /* public static void notNull(Object value, Codable code, Object... args) throws RRException {
        if (value == null) {
            throw new RRException(code, args);
        }
    }*/

    /**
     * 如果满足条件，就开始断言；
     * 断言集合非空
     * @param trueOrFalse 布尔条件
     * @param value 集合
     * @param message 提示语
     * @return void
     * @throws RRException
     * @author
     *      */
    public static void notEmpty(Boolean trueOrFalse, Collection<?> value, String message, Object... args) throws RRException {
        if (trueOrFalse && CollectionUtils.isEmpty(value)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言数组至少有非空元素（(非空元素：不是空白串，不是空列表，不是空数组，不是null）
     * @param array 字符串数组
     * @param message 提示语
     * @param args 提示语占位值
     * @author
     *      */
    public static void notEmptyAny(Object[] array, String message, Object... args) {
        if (ArrayUtils.isEmpty(array)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
        boolean hasNotEmplty = false;
        for (Object value : array) {
            if ((value instanceof CharSequence) && StringUtils.isNotEmpty(value.toString())) {
                hasNotEmplty = true;
                break;
            } else if ((value instanceof Collection) && ((Collection) value).size() > 0) {
                hasNotEmplty = true;
                break;
            } else if (value != null && value.getClass().isArray() && ArrayUtils.getLength(value) > 0) {
                hasNotEmplty = true;
                break;
            } else if (!(value instanceof CharSequence) && value != null) {
                hasNotEmplty = true;
                break;
            }
        }
        if (!hasNotEmplty) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言数组至少有一个非空元素(非空元素：不是空白串，不是空列表，不是空数组，不是null)
     * @param array 字符串数组
     * @param message 提示语
     * @param args 提示语占位值
     * @author
     *      */
    public static void notBlankAny(Object[] array, String message, Object... args) {
        if (ArrayUtils.isEmpty(array)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
        boolean hasNotBlank = false;
        for (Object value : array) {
            if ((value instanceof CharSequence) && StringUtils.isNotBlank(value.toString())) {
                hasNotBlank = true;
                break;
            } else if ((value instanceof Collection) && ((Collection) value).size() > 0) {
                hasNotBlank = true;
                break;
            } else if (value != null && value.getClass().isArray() && ArrayUtils.getLength(value) > 0) {
                hasNotBlank = true;
                break;
            } else if (!(value instanceof CharSequence) && value != null) {
                hasNotBlank = true;
                break;
            }
        }
        if (!hasNotBlank) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

    /**
     * 断言数组里全部元素非空（元素不是空白串，不是空列表，不是空数组，不是null）
     * @param array 字符串数组
     * @param message 提示语
     * @param args 提示语占位值
     * @author
     *      */
    public static void notBlankAll(Object[] array, String message, Object... args) {
        if (ArrayUtils.isEmpty(array)) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
        boolean hasBlank = false;
        for (Object value : array) {
            if ((value instanceof CharSequence) && StringUtils.isBlank(value.toString())) {
                hasBlank = true;
                break;
            } else if ((value instanceof Collection) && ((Collection) value).size() == 0) {
                hasBlank = true;
                break;
            } else if (value != null && value.getClass().isArray() && ArrayUtils.getLength(value) == 0) {
                hasBlank = true;
                break;
            }  else if (!(value instanceof CharSequence) && value == null) {
                hasBlank = true;
                break;
            }
        }
        if (hasBlank) {
            throw new RRException(LocalStringUtils.fomart(message, args));
        }
    }

}