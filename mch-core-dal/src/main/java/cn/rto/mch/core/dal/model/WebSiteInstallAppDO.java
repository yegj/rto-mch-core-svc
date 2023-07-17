package cn.rto.mch.core.dal.model;

import cn.rto.mch.core.dal.base.BaseDO;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.Table;
import java.util.Date;

/**
 * ClassName: WebSiteInstallAppDO
 * Description: <pre>
 *     站点 安装 应用的结果，
 *     有可能后面卸载等
 * </pre>
 * Author: guanjieye
 * Date: 2023/07/14
 */
@Data
@SuperBuilder
@Table(name = "RTO_V2_WEBSITE_INSTALL_APP")
public class WebSiteInstallAppDO  extends BaseDO {
    private static final long serialVersionUID = 6100417088366878122L;

    private Long appId;
    // 冗余字段
    private String appName;


    // 初始(SaaS或Shopify 安装app) 操作员 id
    private Long installOperatorId;
    // 安装完成时间
    private Date installSuccTime;
    // 卸载的 操作员id
    private Long unloadOperatorId;
    // 卸载完成时间
    private Date unloadSuccTime;




    // 旧套餐等级
    private String oldBillingCode;
    // 新套餐等级
    private String billingCode;

    private String status;


    /**
     * @see WebSiteDO#rtoUserShopifyAuthId
     */
    private Long rtoUserShopifyAuthId;

    /**
     * @see  WebSiteDO#rtoShopifyInfoId
     */
    private Long rtoShopifyInfoId;

}
