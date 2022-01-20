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
        // given
        String 강남역 = "강남역";

        // when
        ExtractableResponse<Response> response = 지하철역_생성_요청(강남역);

        // then
        지하철역_생성됨(response);
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
        // given
        String 강남역 = "강남역";
        지하철역_이미_등록되어_있음(강남역);

        String 역삼역 = "역삼역";
        지하철역_이미_등록되어_있음(역삼역);

        // when
        ExtractableResponse<Response> response = 지하철역_목록_조회_요청();

        // then
        지하철역_목록_조회됨(강남역, 역삼역, response);
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
        // given
        String 강남역 = "강남역";
        ExtractableResponse<Response> createResponse = 지하철역_생성_요청(강남역);
        String uri = createResponse.header("Location");

        // when
        ExtractableResponse<Response> response = 지하철역_삭제_요청(uri);

        // then
        지하철역_삭제됨(response);
    }

    private ExtractableResponse<Response> 지하철역_생성_요청(String name) {
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

    private ExtractableResponse<Response> 지하철역_목록_조회_요청() {
        return RestAssured.given().log().all()
                .when()
                .get("/stations")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 지하철역_삭제_요청(String uri) {
        return RestAssured.given().log().all()
                .when()
                .delete(uri)
                .then().log().all()
                .extract();
    }

    private void 지하철역_이미_등록되어_있음(String name) {
        ExtractableResponse<Response> createResponse = 지하철역_생성_요청(name);
    }

    private void 지하철역_생성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    private void 지하철역_목록_조회됨(String 강남역, String 역삼역, ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("name")).contains(강남역, 역삼역);
    }

    private void 지하철역_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
