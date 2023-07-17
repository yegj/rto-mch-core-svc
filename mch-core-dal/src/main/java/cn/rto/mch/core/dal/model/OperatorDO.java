package cn.rto.mch.core.dal.model;

import cn.rto.mch.core.dal.base.BaseDO;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.Table;

/**
 * ClassName: OperatorDO
 * Description: 租户操作员
 * Author: guanjieye
 * Date: 2023/07/14
 */
@Data
@SuperBuilder
@Table(name = "RTO_V2_OPERATOR")
public class OperatorDO extends BaseDO {

    private static final long serialVersionUID = -2280000672347473191L;

    private Long tenantId;

    private String operatorName;

    /**
     * 操作员创建的来源
     * <pre>
     * 1、从shopify 为 website 安装app；
     * 2、SaaS上创建操作员；
     * 3、Shopify店员A，为website_1安装app_1后，但是店员B也有website_1的权限，
     * 所以，店员B从Shopify 安装 app_1进入SaaS，也会默认创建一个操作员，同时包含一个默认登录的扩展信息，后续店员B在SaaS上面的操作也可以记录;
     * </pre>
     */
    private String sourceEvent;

    private String status;
}
