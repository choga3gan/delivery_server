// src/main/java/com/choga3gan/delivery/LogTestController.java
package test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogTestController {

    // 1. Logger 생성: "com.example.demo" 패키지 로거를 사용
    private static final Logger log = LoggerFactory.getLogger(LogTestController.class);

    @GetMapping("/test-log")
    public String testLog() {
        // 2. 다양한 레벨의 로그 기록
        log.trace("TRACE 레벨 로그: 매우 상세한 추적 정보");
        log.debug("DEBUG 레벨 로그: 개발 및 디버깅 정보");
        log.info("INFO 레벨 로그: 애플리케이션의 주요 흐름 정보");
        log.warn("WARN 레벨 로그: 잠재적인 문제 경고");
        log.error("ERROR 레벨 로그: 오류 발생!");

        // 3. 특정 로거 테스트 (예: com.example.myapp 로거)
        Logger myAppLog = LoggerFactory.getLogger("com.example.myapp");
        myAppLog.debug("MyApp Debug: com.example.myapp에 지정된 레벨로 출력됩니다.");

        return "로그가 콘솔과 파일에 기록되었습니다. (로그 레벨: INFO)";
    }
}