package com.diandi.controller;


import com.diandi.manager.properties.ReaderProperties;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Reader;

/**
 * Created on 2017/12/26.
 * 该家族中的结果数据是依附于博文的，博主无效登录即可获取
 *
 * @author DuanJiaNing
 */
public class BaseArticleController extends BaseCheckController {

    @Autowired
    protected ReaderProperties readerProperties;

}
