import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.licious.userSegmentation"})
@EnableJpaRepositories(basePackages="com.licious.userSegmentation.repository")
@EnableTransactionManagement
@EntityScan(basePackages="com.licious.userSegmentation.dao")
public class Application {

    static Logger log = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        log.info("entry point testing log");
        SpringApplication.run(Application.class, args);
    }

}
