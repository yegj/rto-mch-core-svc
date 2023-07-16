package cn.rto.mch.core.dal.model;

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
     * shopify等平台，和店铺运营者的联系邮件
     */
    private String email;

    /**
     * shopify等平台，店铺运营者的电话
     */
    private String phone;

    private String status;
}
