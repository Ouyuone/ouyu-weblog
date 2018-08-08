package tech.ouyu.gateway.zuul;

import com.alibaba.fastjson.JSON;
import online.paly.mvc.result.Result;
import org.springframework.http.HttpStatus;

/**
 * Created by Administrator on 2018/7/26.
 */
public interface CList {
    /**
     * ErrorResult 错误返回
     */
    interface ErrorResult {

        /**
         * 默认失败返回 Result
         */
        Result ERROR_RESULT = new Result(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

        /**
         * 默认失败返回 String
         */
        String ERROR_JSON_RESULT = JSON.toJSONString(ERROR_RESULT);
    }
}
