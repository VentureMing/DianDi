package com.diandi.util.restful;

import com.diandi.exception.BaseRuntimeException;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author shangmingyu
 * @Description: restful 风格 API 返回结果固定结构
 * @date 2018/5/6 15:12
 */
@Getter
@Setter
public class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = 4637028942587795332L;


    /**
     * 结果状态为成功
     */
    public static final int SUCCESS = 0;

    /**
     * 结果状态为失败
     */
    public static final int FAIL = -1;

    public int code = SUCCESS;
    private String msg = "success";
    private T data;

    /**
     * 返回成功的构造函数
     */
    public ResultBean(T data) {
        this.data = data;
    }

    /**
     * 返回错误（获取数据错误）的构造函数
     */
    public ResultBean(BaseRuntimeException e) {
        this.msg = e.getMessage();
        this.code = e.getCode();
    }

    /**
     * 自定义信息和代码
     */
    public ResultBean(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }
}
