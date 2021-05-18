package net.tack.school.notes.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:application.properties")
@MapperScan("net.tack.school.notes.database.mybatis")
@MapperScan("net.tack.school.notes.debug")
public class ConfigSpring {

}