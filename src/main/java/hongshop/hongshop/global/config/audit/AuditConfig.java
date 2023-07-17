package hongshop.hongshop.global.config.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @fileName AuditConfig
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary  등록자와 수정자를 처리해주기 위해 AuditorAware을 bean으로 등록한다.
 *           => 등록자와 수정자에 대해 공통 속성으로 처리한 클래스에서
 *              "@EntityListeners(value = {AuditingEntityListener.class})" 해당 어노테이션을 붙이면 된다.
 **/

@Configuration
@EnableJpaAuditing  // JPA Audit 기능을 활성화한다.
public class AuditConfig {

    @Bean
    public AuditorAware<Long> auditorProvider(){
        return new AuditorAwareImpl();
    }

}
