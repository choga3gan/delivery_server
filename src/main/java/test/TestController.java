package test;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

// @Tag: 해당 컨트롤러(리소스) 그룹에 대한 설명
@Tag(name = "사용자 관리 API", description = "사용자 정보를 조회하고 생성하는 기능 제공")
@RestController
@RequestMapping("/api/v1/users")
public class TestController {

    // @Operation: 각 API 메서드(엔드포인트)에 대한 설명
    @Operation(summary = "단일 사용자 조회", description = "ID를 기반으로 특정 사용자의 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public String getUserById(
            // @Parameter: 입력 파라미터에 대한 설명 및 예시
            @Parameter(description = "조회할 사용자의 고유 ID", example = "1")
            @PathVariable Long id) {
        return "User ID: " + id;
    }

    // ... 다른 API 메서드
}