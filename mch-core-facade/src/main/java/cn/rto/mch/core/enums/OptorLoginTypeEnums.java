package cn.rto.mch.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * ClassName: OptorLoginTypeEnums
 * Description: 操作员登录方式
 * Author: guanjieye
 * Date: 2023/07/17
 */
@Getter
@AllArgsConstructor
public enum OptorLoginTypeEnums {


    EC_PLAT_DEFAULT_LOGIN         ("EC_PLAT_DEFAULT_LOGIN"         ,  "第三方电商默认登录"),
    THIRD_PLAT_UNION_LOGIN_GG     ("THIRD_PLAT_UNION_LOGIN_GG"     ,  "Google联合登录"),
    THIRD_PLAT_UNION_LOGIN_FB     ("THIRD_PLAT_UNION_LOGIN_FB"     ,  "Facabook联合登录"),
    THIRD_PLAT_UNION_LOGIN_SHOPIFY("THIRD_PLAT_UNION_LOGIN_SHOPIFY",  "Shopify联合登录"),
    OPTOR_LOGIN_BY_EMAIL          ("OPTOR_LOGIN_BY_EMAIL"          ,  "邮箱密码登录"),
    OPTOR_LOGIN_BY_PHONE          ("OPTOR_LOGIN_BY_PHONE "         ,  "手机号码登录"),
    ;

    private final String typeCode;
    private final String desc;

    public static Optional<OptorLoginTypeEnums> getLoginType(String code){
        if(StringUtils.isBlank(code)){
            return Optional.ofNullable(null);
        }
        Optional<OptorLoginTypeEnums> optional = Stream.of(OptorLoginTypeEnums.values()).filter(x -> Objects.equals(x.getTypeCode(), code)).findFirst();
        return optional;
    }

}
