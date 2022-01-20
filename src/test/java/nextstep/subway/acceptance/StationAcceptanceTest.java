package nextstep.subway.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("지하철역 관리 기능")
class StationAcceptanceTest extends AcceptanceTest {
    /**
     * Scenario: 지하철역 생성
     * When 지하철역 생성을 요청 하면
     * Then 지하철역 생성이 성공한다.
     */
    @DisplayName("지하철역 생성")
    @Test
    void createStation() {
    }

    /**
     * Scenario: 지하철역 목록 조회
     * Given 강남역 지하철역 생성을 요청 하고
     * Given 역삼역 지하철역 생성을 요청 하고
     * When 지하철역 목록 조회를 요청 하면
     * Then 강남역, 역삼역이 포함된 지하철역 목록을 응답받는다
     */
    @DisplayName("지하철역 목록 조회")
    @Test
    void getStations() {
    }

    /**
     * Scenario: 지하철역 삭제
     * Given 강남역 지하철역 생성을 요청 하고
     * When 강남역 지하철역 삭제를 요청 하면
     * Then 강남역 지하철역 삭제가 성공한다.
     */
    @DisplayName("지하철역 삭제")
    @Test
    void deleteStation() {
    }
}
