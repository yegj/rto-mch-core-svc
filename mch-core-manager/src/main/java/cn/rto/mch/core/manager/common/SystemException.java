package cn.rto.mch.core.manager.common;

/**
 * ClassName: SystemException
 * Description: TODO
 * Author: guanjieye
 * Date: 2023/07/17
 */
public class SystemException extends RuntimeException{
    public SystemException(String message, Throwable e) {
        super(message, e);
    }
}
