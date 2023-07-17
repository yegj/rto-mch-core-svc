package cn.rto.mch.core.dal.model;

import cn.rto.mch.core.dal.base.BaseDO;
import cn.rto.mch.core.enums.OptorLoginTypeEnums;
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
     * @see OptorLoginTypeEnums
     * <pre>
     * thirdPlatDefaultLogin        第三方平台默认登录
     * thirdPlatUnionLogin_xxx      第三方平台联合登录(可扩展支持)
     *  thirdPlatUnionLogin_GG, thirdPlatUnionLogin_FB, thirdPlatUnionLogin_Shopify
     * operatorLoginByEmail         邮箱密码登录
     * operatorLoginByPhone         手机号码登录
     * </pre>
     */
    private String loginType;

    // 第三方平台id
    private Long thirdPlatId;
    // 第三方平台名称 冗余字段
    private String thirdPlatName;
    // 店员在第三方平台 user_id(token记录在auth服务)
    private String thirdUserId;
    // 操作员在不同平台的头像地址
    private String picture;

    // 只有 邮箱，手机号 方式登录，才有此字段
    private String loginName;
    private String loginPwd;

    /**
     * 第三方平台的用户信息
     * <pre>
     * 例如 shopify {json}
     * </pre>
     */
    private String thirdUserInfo;

    private String status;


    // 第三方授权表id 为了包装 rto_user_third_auth 这个表
    private Long rtoThirdAuthId;




}
