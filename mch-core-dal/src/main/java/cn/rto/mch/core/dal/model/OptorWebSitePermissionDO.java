package cn.rto.mch.core.dal.model;

import cn.rto.mch.core.enums.OperatorRoleEnums;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.Table;

/**
 * ClassName: OperatorPermissionDO
 * Description: 操作员在租户下的站点的权限
 * Author: guanjieye
 * Date: 2023/07/17
 */
@Data
@SuperBuilder
@Table(name = "RTO_V2_OPTOR_WEBSITE_PERMISSION")
public class OptorWebSitePermissionDO {

    private Long operatorId;

    private Long websiteId;

    /**
     * @see OperatorRoleEnums
     */
    private String role;

}
