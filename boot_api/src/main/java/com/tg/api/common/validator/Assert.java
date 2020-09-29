package com.tg.api.common.validator;


import com.tg.api.common.exception.RRException;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 数据校验
 *
 * @author Mark sunlightcs@gmail.com
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new RRException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new RRException(message);
        }
    }

    public static boolean isMap(Map<String, Object> map, String name) {
        if (map.containsKey(name)) {
            if(!"".equals(map.get(name))){
                return true;
            }
        }
        return false;
    }

}
