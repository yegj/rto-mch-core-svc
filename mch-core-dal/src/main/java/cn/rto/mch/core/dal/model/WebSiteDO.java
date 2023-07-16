package cn.rto.mch.core.dal.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.Table;

/**
 * ClassName: WebSite
 * Description: 店铺，站点
 * Author: guanjieye
 * Date: 2023/07/14
 */
@Data
@SuperBuilder
@Table(name = "RTO_V2_WEBSITE")
public class WebSiteDO extends BaseDO {

    private static final long serialVersionUID = 6993243117125547766L;

    private String websiteName;

    private String websiteTitle;

    private String websiteUrl;

    private Long tenantId;


    private String status;

    // 关联 shopify 的 shop信息，为了以后替换 其内部tenantUserId，所以用此字段包装
    // 目前数据库里面 1 website :N shopify info，这个是错误的，
    // 因为shopifyInfo里面的shop其实只有一份信息，
    // 1、割接完成之后，应该放在website对象，2、割接完成之前，website_install_app里面也有一份
    private Long rtoShopifyInfoId;

    // 关联 website 安装(授权)信息id(shopify的shop详细信息)，为了后续替换tenantUserId，所以用此字段包装
    // 目前数据库 一个 website安装一个应用，创建一个 shopifyAuth，1(website) : N(shopifyAuth) 这个是错误的，
    // 因为shopifyInfo里面的shop其实只有一份信息
    // 1、割接完成之后，应该放在website对象，2、割接完成之前，website_install_app里面也有一份
    private Long rtoUserShopifyAuthId;

    // 关联 auth-core token id，也是为了替换 其内部 tenantUserId
    // 目前数据库里面 1 website : 1 shopifyToken
    private Long thirdAuthTokenId;

}
