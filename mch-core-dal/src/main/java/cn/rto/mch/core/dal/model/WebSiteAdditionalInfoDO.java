package cn.rto.mch.core.dal.model;

import cn.rto.mch.core.dal.base.BaseDO;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.Table;

/**
 * ClassName: WebSiteAdditionalInfoDO
 * Description: 站点附加信息
 * Author: guanjieye
 * Date: 2023/07/17
 */
@Data
@SuperBuilder
@Table(name = "RTO_V2_WEBSITE_ADDITIONAL_INFO")
public class WebSiteAdditionalInfoDO extends BaseDO {

    private Long websiteId;

    // 店铺在 第三方平台 等级
    private String shopPlan;

    private String shopOwner;

    // 第三方平台 和 站点运营者 沟通的邮件
    private String email;
    // 站点和消费客户沟通的邮件
    private String customerEmail;

    //shopify等电商平台，店铺运营者的电话
    private String phone;

    // 站点自己的域名
    private String domain;
    // myshopify 域名
    private String myshopifyDomain;

    private String address;
    private String city;
    private String province;
    private String country;


    // 第三方平台，对站点授权时的信息，当做扩展信息使用 json字符串
    private String thirdWebsiteExtInfo;



}
