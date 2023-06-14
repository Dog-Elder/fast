package com.fast.core.common.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * VO
 *
 * @Author: 黄嘉浩
 * @Date: 2023-06-14 15:30
 **/
@Data
public class Vo implements Serializable {
    private Map<String, Object> lovMap = new HashMap<>();
    public void putLovMap(String key,Object value){
        lovMap.put(key, value);
    }
}
