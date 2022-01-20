package nextstep.subway.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.utils.PracticeDatabaseCleanup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("지하철역 인수테스트 실습")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PracticeStationAcceptanceTest {
    @LocalServerPort
    int port;

    @Autowired
    private PracticeDatabaseCleanup databaseCleanup;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        databaseCleanup.execute();
    }

    /**
     * Scenario: 지하철 역을 생성한다.
     * When: 지하철 역을 생성 요청한다.
     * Then: 지하철 역이 생성된다.
     */
    @Test
    void 지하철역을_생성() {
        // given
        final String 판교역 = "판교역";

        // when
        ExtractableResponse<Response> response = 지하철_역_등록_요청(판교역);

        // then
        지하철_역_등록됨(response);
    }

    /**
     * Scenario: 지하철역 목록을 조회한다.
     * Given: 지하철 역이 이미 등록되어 있다.
     * When: 모든 지하철 역 목록을 요청한다.
     * Then: 모든 지하철 역 목록이 반환된다.
     */
    @Test
    void 지하철역_목록을_조회() {
        //given
        final String 강남역 = "강남역";
        지하철_역_등록되어_있음(강남역);

        final String 판교역 = "판교역";
        지하철_역_등록되어_있음(판교역);

        // when
        ExtractableResponse<Response> response = 지하철_역_목록_조회_요청();

        지하철_역_목록_조회됨(강남역, 판교역, response);
    }

    private ExtractableResponse<Response> 지하철_역_등록_요청(String name) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);

        return RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/stations")
                .then().log().all()
                .extract();
    }

    private void 지하철_역_등록됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    private void 지하철_역_등록되어_있음(String name) {
        ExtractableResponse<Response> createResponse = 지하철_역_등록_요청(name);
    }

    private ExtractableResponse<Response> 지하철_역_목록_조회_요청() {
        return RestAssured.given().log().all()
                .when()
                .get("/stations")
                .then().log().all()
                .extract();
    }

    private void 지하철_역_목록_조회됨(String 강남역, String 판교역, ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        List<String> stationNames = response.jsonPath().getList("name");
        assertThat(stationNames).contains(강남역, 판교역);
    }
}
