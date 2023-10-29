package docker.application.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import docker.domain.ipr.IprJpaRepository;
import docker.domain.ipr.IprJpaRepositoryImpl;
import docker.domain.ipr.IprRepository;
import docker.domain.ipr.IprRepositoryImpl;

import docker.domain.ipr.SomeJpaRepository;
import docker.domain.ipr.SomeJpaRepositoryImpl;
import ipr.api.ipr.api.IprApiController;
import ipr.api.ipr.api.IprApiDelegate;
import ipr.api.ipr.api.SomeApiController;
import ipr.api.ipr.api.SomeApiDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.DefaultJpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class Config {

    @Autowired
    private DataSource dataSource;

    @Bean
    public ObjectMapper objectMapper() {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.disable(DeserializationFeature.ACCEPT_FLOAT_AS_INT);

        mapper.enable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper;
    }

    @Bean
    public IprRepository iprRepository() {
        return new IprRepositoryImpl(jdbcTemplate());
    }


    @Bean
    public IprApiController iprApiController(@Autowired(required = false) IprApiDelegate delegate) {
        return new IprApiController(delegate);
    }

    @Bean
    public SomeApiController someApiController(@Autowired(required = false) SomeApiDelegate delegate) {
        return new SomeApiController(delegate);
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("docker.domain");
        em.setJpaDialect(new DefaultJpaDialect());

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public IprJpaRepository iprJpaRepository() {
        return new IprJpaRepositoryImpl(entityManagerFactory().getNativeEntityManagerFactory());
    }

    @Bean
    public SomeJpaRepository someJpaRepository() {
        return new SomeJpaRepositoryImpl(entityManagerFactory().getNativeEntityManagerFactory());
    }
}
