package com.skt.treal.openavatar.build.api.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class OpenAvatarConfig {

	
	@Autowired
	private ApplicationContext applicationContext;
	@Value( "${spring.datasource.driver-class-name}" )
	private String driverClassName;
	@Value( "${spring.datasource.url}" )
	private String url;
	@Value( "${spring.datasource.username}" )
	private String username;
	@Value( "${spring.datasource.password}" )
	private String password;
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName( driverClassName );
		dataSource.setUrl( url );
		dataSource.setUsername( username );
		dataSource.setPassword( password );
		return dataSource;

	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		System.out.println( applicationContext.getResources("classpath:/").toString() );
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource( dataSource() );
		sqlSessionFactoryBean.setMapperLocations( applicationContext.getResources( "classpath:/mappers/*.xml" ) );
		
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public SqlSession sqlSession() {
		SqlSessionTemplate sqlSessionTemplate = null;
		try {
			sqlSessionTemplate = new SqlSessionTemplate( sqlSessionFactory() );
		} catch( Exception e ) {
			e.printStackTrace();
		}
		return sqlSessionTemplate;
	}

}
