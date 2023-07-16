package cn.rto.mch.core.dal.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * ClassName: BaseDAO
 * Description: TODO
 * Author: guanjieye
 * Date: 2023/07/17
 */
public interface BaseDAO <T> extends Mapper<T>, MySqlMapper<T> {
}
