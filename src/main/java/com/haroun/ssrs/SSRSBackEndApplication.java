package com.haroun.ssrs;

import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.repository.AppRoleRepository;
import com.haroun.ssrs.repository.AppUserRepository;
import com.haroun.ssrs.utils.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SSRSBackEndApplication implements CommandLineRunner {

    private final DataGenerator dataGenerator;

    public SSRSBackEndApplication(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    public static void main(String[] args) {
        SpringApplication.run(SSRSBackEndApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello World!");
        dataGenerator.generateData();
    }
}
