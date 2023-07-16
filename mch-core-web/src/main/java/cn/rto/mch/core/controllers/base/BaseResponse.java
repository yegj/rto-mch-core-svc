package cn.rto.mch.core.controllers.base;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: BaseResponse
 * Description: TODO
 * Author: guanjieye
 * Date: 2023/07/17
 */
public class BaseResponse {

    private final static String MESSAGE = "message";//消息
    private final static String CODE = "code";//响应码


    private static ResponseEntity<Object> getEntity(Object body, HttpStatus statusCode) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        List<String> contentType = new ArrayList<String>();
        contentType.add("application/json;charset=utf-8");
        headers.put("Content-Type", contentType);
        return new ResponseEntity<Object>(body, headers, statusCode);
    }

    /**
     * 请求成功,无需返回结果集
     */
    public static ResponseEntity<Object> success() {
        Map<String, Object> result = new HashMap<String, Object>();
        return getEntity(result, HttpStatus.OK);
    }

    /**
     * 请求成功，根据msg和code来设置响应
     * 多用于 添加、修改、删除、更新等提示信息。
     */
    public static ResponseEntity<Object> success(String msg) {
        Map<String, Object> vo = new HashMap<>();
        vo.put("sucFlag", true);
        vo.put("message", msg);
//        vo.put("code", ResultEnum.SUCCESS.getCode());
        return getEntity(vo, HttpStatus.OK);
    }


    public static ResponseEntity<Object> success(String msg, Object object) {
        Map<String, Object> vo = new HashMap<>();
        vo.put("sucFlag", true);
        vo.put("message", msg);
//        vo.put("code", ResultEnum.SUCCESS.getCode());
        vo.put("data", object);
        return getEntity(vo, HttpStatus.OK);
    }


    /**
     * 请求成功,并返回请求结果集
     *
     * @param object 返回到客户端的对象
     * @return Spring mvc ResponseEntity
     */
    public static ResponseEntity<Object> success(Object object) {
        return getEntity(object, HttpStatus.OK);
    }


    /**
     * 服务器错误(new)   多用于controller 错误返回的信息展示
     *
     * @param msg  请求失败的错误信息
     * @param code
     * @return Spring mvc ResponseEntity
     */
    public static ResponseEntity<Object> serverError(String msg, Integer code) {
        Map<String, Object> vo = new HashMap<>();
        vo.put("sucFlag", false);
        vo.put("message", msg);
        vo.put("code", code);
        return getEntity(vo, HttpStatus.OK);
    }

}
