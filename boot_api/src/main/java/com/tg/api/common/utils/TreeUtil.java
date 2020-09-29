package com.tg.api.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author copyright by
 * @author wuji.zhang
 * @version R0.0.100
 * @category 普通list集合转树形结构list集合
 * 集合中对象 vo类必须有 id,pid,children 三个字段
 * @since 2017/06/12
 */
public class TreeUtil {

    private static Logger log = LoggerFactory.getLogger(TreeUtil.class);

    /**
     * 普通list集合转map,id为键,对象为value
     *
     * @param
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws Exception
     * @return LinkedHashMap    带顺序的map集合
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static LinkedHashMap getMap(List list) throws IllegalArgumentException, IllegalAccessException {
        LinkedHashMap map = new LinkedHashMap();
        for (Object obj : list) {
            Field idField = getDeclaredField(obj, "id");

            if (!idField.isAccessible()) {
                idField.setAccessible(true);
            }

            int id = -1;

            id = (int) idField.get(obj);

            map.put(id, obj);
        }
        return map;
    }

    /**
     * map集合转树形结构list,利用递归:
     * 1,遍历map,获取每个value(即对象)的pid(父id),
     * 2,如果pid等于参数top,则放入树形list集合第一级,
     * 并且将id作为top,和map一起递归调用本方法,获得该对象的children:即pid等于当前对象id 的对象的集合;
     * 如果pid不等于top,继续循环,判断下一个对象;
     * 此方法能将pid等于top的对象,以及该类对象的所有(多级)子类对象,封装到带顺序的LinkedList;
     * 如果某个对象不与top有任何关系(pid且多级pid都不等于top),则不会放入带顺序的LinkedList;
     *
     * @param map 带顺序的map集合
     * @param top 指定的顶级父id
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws Exception
     * @return LinkedList    带顺序的LinkedList
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static LinkedList getTree(LinkedHashMap map, int top) throws IllegalArgumentException, IllegalAccessException {
        LinkedList treeList = new LinkedList();
        // 遍历map的key集合,即id集合
        for (Object idObj : map.keySet()) {
            //根据id获取对象
            int id = (int) idObj;
            Object obj = map.get(id);
            // 获取对象的父id
            Field pidField = getDeclaredField(obj, "upUserId");
            if (!pidField.isAccessible()) {
                pidField.setAccessible(true);
            }
            int pid = (int) pidField.get(obj);
            // 如果父id为top,则
            if (pid == top) {
                //1,加入当前list集合第一级
                treeList.add(obj);
                //2,设置该对象的子集
                Field childrenField = getDeclaredField(obj, "children");

                if (!childrenField.isAccessible()) {
                    childrenField.setAccessible(true);
                }
                //递归
                List children = getTree(map, id);
                childrenField.set(obj, children);
            }
        }
        return treeList;
    }

    /**
     * 循环向上转型, 获取字段
     *
     * @param object    子类对象
     * @param fieldName 字段名
     * @return
     */
    private static Field getDeclaredField(Object object, String fieldName) {
        Field field = null;

        Class<?> clazz = object.getClass();

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                e.printStackTrace();
                //这里甚么都不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会进入
            }
        }
        return null;
    }

    /**
     * 对外公开的方法: 普通list转树形结构list
     *
     * @param list   普通list
     * @param topPid 指定的顶级父id
     * @throws Exception
     * @return List    树形结构list
     */
    @SuppressWarnings("rawtypes")
    public static List getTree(List list, int topPid) {
        try {
            LinkedHashMap map = getMap(list);
            List tree = getTree(map, topPid);
            return tree;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList();
    }

}
