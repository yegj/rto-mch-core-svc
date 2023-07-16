package cn.rto.mch.core.dal.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * ClassName: DataSourceConfig
 * Description: 项目数据源配置
 * Author: guanjieye
 * Date: 2021/09/07
 */
@Configuration
public class DataSourceConfig {

    @Primary
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*Mapper.xml"));
        bean.setTypeAliasesPackage("cn.rto.mch.core.dal.model");
        return bean.getObject();
    }

    @Primary
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager initTransactionManager(DataSource  dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "transactionTemplate")
    TransactionTemplate transactionTemplate(PlatformTransactionManager
                                                    transactionManager) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        //传播行为5 表示当前方法必须运行在事务中
        transactionTemplate.setPropagationBehaviorName("PROPAGATION_REQUIRED");
        //使用底层数据库默认的隔离级别
        transactionTemplate.setIsolationLevelName("ISOLATION_DEFAULT");
        return transactionTemplate;
    }

    @Primary
    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate initSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
