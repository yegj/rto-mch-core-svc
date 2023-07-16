package cn.rto.mch.core.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * ClassName: RtoMerchantCoreApplication
 * Description: 启动类
 * Author: guanjieye
 * Date: 2023/07/14
 */

@SpringBootApplication(scanBasePackages = {"cn.rto.mch.core"})
@MapperScan(basePackages = {"cn.rto.mch.core.dal.mapper"})

@Slf4j
public class RtoMerchantCoreApplication {


    public static void main(String[] args) {
        log.info("--------------------- Application Merchant-Core-Service Starting . --------------------- ");
        SpringApplication.run(RtoMerchantCoreApplication.class, args);
        log.info("--------------------- Application Merchant-Core-Service start complete. ---------------------");
    }
}
