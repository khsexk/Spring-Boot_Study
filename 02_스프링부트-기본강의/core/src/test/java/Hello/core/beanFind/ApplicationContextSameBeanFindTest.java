package Hello.core.beanFind;

import Hello.core.AppConfig;
import Hello.core.member.MemberRepository;
import Hello.core.member.MemoryMemberRepository;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

public class ApplicationContextSameBeanFindTest {
    AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SampleAppConfig.class);

    @Test
    @DisplayName("중복된 타입 오류 발생 테스트")
    void findBeanByTypeDuplicate(){
        assertThrows(NoUniqueBeanDefinitionException.class, () ->
                annotationConfigApplicationContext.getBean(MemoryMemberRepository.class));
    }

    @Test
    @DisplayName("중복된 타입 테스트 해결")
    void findBeanByName(){
        MemberRepository memberRepository = annotationConfigApplicationContext.getBean("memberRepository1", MemberRepository.class);

        Assertions.assertThat(memberRepository).isInstanceOf(MemberRepository.class);
    }

    @Test
    @DisplayName("해당 타입 모두 조회")
    void findAllBeanByType(){
        Map<String, MemberRepository> beansOfType = annotationConfigApplicationContext.getBeansOfType(MemberRepository.class);

        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }
    }

    @Configuration
    static class SampleAppConfig {
        @Bean
        public MemberRepository memberRepository1(){
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }

}
