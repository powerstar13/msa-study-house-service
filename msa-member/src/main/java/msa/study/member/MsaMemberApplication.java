package msa.study.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class MsaMemberApplication {
    
    private static String active; // active profile
    
    // static 변수에는 @Value 어노테이션이 동작하지 않는다. setter 메서드를 이용하여 값이 할당되도록 하면 된다.
    @Value("${spring.profiles.active}")
    private void setActive(String value) {
        active = value;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(MsaMemberApplication.class, args);
    
        log.warn(String.format(" ::: [Member Domain Application Run] SUCCESS * ACTIVE PROFILE --> %s ::: ", active));
    }

}
