package com.piu.sys.utils;

import com.piu.base.utils.SpringContextHolder;
import com.piu.sys.dao.DictDao;
import com.piu.sys.entity.Dict;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DictUtils {

    private static DictDao dictDao = SpringContextHolder.getBean(DictDao.class);


    public static final String CACHE_DICT_MAP = "dictMap";

    public static final String CACHE_DICT_MAP_PID = "dictMapPId";


    public static String getDictLabel(String value, String type, String defaultValue) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)) {
            for (Dict dict : getDictList(type)) {
                if (type.equals(dict.getType()) && value.equals(dict.getValue())) {
                    return dict.getLabel();
                }
            }
        }
        return defaultValue;
    }

    public static List<Dict> getDictList(String type) {
        @SuppressWarnings("unchecked")
        /* 从缓存中取得所有的数据字典项，保存在dictMap映射对象中。 */
                Map<String, List<Dict>> dictMap = new HashMap<>();
        /* 如果dictMap在缓存中不存在。从数据库中重新取，并将数据库中的字典项绑定至dictMap中。 */
        /* 从数据库中取出所有的字典项，遍历全部项，对各字典项进行处理，保存至dictMap中 */
        for (Dict dict : dictDao.findAllList(new Dict())) {
            /* 取得dictMap中，当前字典项类型对应的字典列表，如果列表为空，表示尚未保存访字典。 */
            List<Dict> dictList = dictMap.get(dict.getType());
            if (dictList != null) {
                /*
                 * 如果当前字典类型存在，将当前字典项保存至列表中（并不是直接放到dictMap），
                 * 因为dictList对象中新增了一个元素，所以dictMap中对应的值也做了变化。
                 */
                dictList.add(dict);
            } else {
                dictList = new ArrayList<>();
                dictList.add(dict);
                /* 当前字典项不存在，对list进行初始化后，保存至dictMap */
                dictMap.put(dict.getType(), dictList);
            }

        }
        List<Dict> dictList = dictMap.get(type);
        if (dictList == null) {
            dictList = new ArrayList<>();
        }
        return dictList;
    }


}
