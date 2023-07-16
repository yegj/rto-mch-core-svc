package cn.rto.mch.core.dal.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.Table;

/**
 * ClassName: OperatorExtLoginInfo
 * Description: 操作员扩展登录信息，用于联合登录等
 * Author: guanjieye
 * Date: 2023/07/14
 */
@Data
@SuperBuilder
@Table(name = "RTO_V2_OPERATOR_EXT_LOGIN_INFO")
public class OperatorExtLoginInfo  extends BaseDO {
    private static final long serialVersionUID = 183566180296663720L;

    private Long operatorId;

    /**
     * <pre>
     * thirdPlatDefaultLogin    第三方平台默认登录
     * thirdPlatUnionLogin      第三方平台联合登录(可扩展支持)
     *  thirdPlatUnionLogin_GG, thirdPlatUnionLogin_FB, thirdPlatUnionLogin_Shopify
     * operatorLoginByEmail     邮箱密码登录
     * operatorLoginByPhone     手机号码登录
     * </pre>
     */
    private String loginType;

    // 第三方平台id
    private Long thirdLoginPlatId;
    // 第三方平台名称 冗余字段
    private String thirdLoginPlatName;
    // 第三方平台 用户 id
    private String thirdUserId;
    // 第三方授权表id 为了包装 rto_user_third_auth 这个表
    private Long rtoThirdAuthId;

    // 只有 邮箱，手机号 登录才有此字段
    private String loginName;
    private String loginPwd;


    private String status;




}
