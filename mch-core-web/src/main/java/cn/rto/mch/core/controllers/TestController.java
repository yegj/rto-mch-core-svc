package cn.rto.mch.core.controllers;

import cn.sino.common.vo.RestResponseVO;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.rto.mch.core.manager.redis.RedisManager;

import java.net.URI;

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
    public RestResponseVO<Long> getTest() {
        return RestResponseVO.success(redisManager.increment("test_key1", 1L));
    }

    @GetMapping("/get1")
    public ResponseEntity<Long> getTest1() {
        return ResponseEntity.status(HttpStatus.SC_OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(redisManager.increment("test_key1", 1L));
    }

    @GetMapping("/get_redirect")
    public ResponseEntity<Long> get_redirect() {
        return ResponseEntity.status(HttpStatus.SC_MOVED_TEMPORARILY).location(URI.create("http://www.baidu.com")).build();
    }
}

