package cn.rto.mch.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * ClassName: OperatorRoleEnums
 * Description: 操作员角色
 * Author: guanjieye
 * Date: 2023/07/17
 */
@Getter
@AllArgsConstructor
public enum OperatorRoleEnums {

    OWNER("OWNER", "拥有者"),
    ADMIN("ADMIN", "管理员"),
    GENERAL_STAFF("GENERAL_STAFF", "普通员工"),
    ;

    private String code;
    private String desc;

    public static Optional<OperatorRoleEnums> getLoginType(String code){
        if(StringUtils.isBlank(code)){
            return Optional.ofNullable(null);
        }
        Optional<OperatorRoleEnums> optional = Stream.of(OperatorRoleEnums.values()).filter(x -> Objects.equals(x.getCode(), code)).findFirst();
        return optional;
    }
}
