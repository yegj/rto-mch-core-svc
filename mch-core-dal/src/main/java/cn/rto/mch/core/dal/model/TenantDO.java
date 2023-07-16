package cn.rto.mch.core.dal.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.Table;
import java.util.Date;

/**
 * ClassName: TenantDO
 * Description: 租户
 * Author: guanjieye
 * Date: 2023/07/14
 */
@Data
@SuperBuilder
@Table(name = "RTO_V2_TENANT")
public class TenantDO extends BaseDO {

    private static final long serialVersionUID = 8953279179737916214L;

    private String tenantCode;

    private String tenantName;

    private String merchantId;

    private String status;

    // 创建站点时(SaaS或Shopify 安装app) 操作员 id
    private Long createOperatorId;
    // 创建完成时间n
    private Date createSuccTime;

    // 从SaaS创建 或 从Shopify等电商平台安装时创建
    private String createFrom;

}


