package app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Objects;


@Configuration
@PropertySource("classpath:application.yml")
public class SpringMailConfig {


    private Environment env;

    @Autowired
    public SpringMailConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(env.getProperty("spring:mail:host"));
        mailSender.setPort(587);
        mailSender.setUsername(env.getProperty("spring:mail:username"));
        mailSender.setPassword(env.getProperty("spring:mail:password"));

        return mailSender;
    }

}