package com.diandi.manager.properties;

import lombok.Data;

/**
 * @author shangmingyu
 * @Description: 数据库参数配置
 * @date 2018/5/6 19:13
 */
@Data
public class DBProperties {
    /**
     * 数据库数字间隔字符
     */
    private String stringFiledSplitCharacterForNumber;

    /**
     * 数据库字符串间隔字符
     */
    private String stringFiledSplitCharacterForString;
}
