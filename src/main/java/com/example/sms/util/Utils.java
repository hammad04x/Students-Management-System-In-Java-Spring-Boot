package com.example.sms.util;

import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static <T> Map<String, Object> preparePageResult (Page<T> page) {
        Map<String, Object> pageResult = new HashMap<>();
        pageResult.put("pageSize", page.getSize());
        pageResult.put("pageNo", page.getNumber()+1);
        pageResult.put("totalRecords", page.getTotalElements());
        pageResult.put("pageCount", page.getTotalPages());
        return pageResult;
    }
}
