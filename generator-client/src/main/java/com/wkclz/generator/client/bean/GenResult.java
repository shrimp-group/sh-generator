package com.wkclz.generator.client.bean;

import lombok.Data;

/**
 * Description:
 * Created: wangkaicun @ 2017-10-20 下午9:11
 */
@Data
public class GenResult<T> {

    private Integer code = 500;
    private String msg = "success";
    private T data = (T) "error";

    public boolean isSuccess() {
        return this.code != null && this.code == 200;
    }

}
