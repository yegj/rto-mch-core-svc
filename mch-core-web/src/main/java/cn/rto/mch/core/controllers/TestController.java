package cn.rto.mch.core.controllers;

import cn.rto.mch.core.controllers.base.BaseResponse;
import cn.rto.mch.core.manager.redis.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: TestController
 * Description: TODO
 * Author: guanjieye
 * Date: 2023/07/17
 */
@RestController
@RequestMapping(path = "/test")
public class TestController {

    @Autowired
    private RedisManager redisManager;

    @GetMapping("/get")
    @ResponseBody
    public ResponseEntity<Object> getTest(){
        return BaseResponse.success("success", redisManager.increment("test_key1", 1L));
    }
}

