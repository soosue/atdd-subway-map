package nextstep.subway.applicaion.dto;

import nextstep.subway.domain.Line;

import java.time.LocalDateTime;
import java.util.List;

public class LineResponse {
    private Long id;
    private String name;
    private String color;
    private List<StationResponse> stations;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private LineResponse(Long id, String name, String color, List<StationResponse> stations, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.stations = stations;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static LineResponse of(Line line) {
        return new LineResponse(
                line.getId(),
                line.getName(),
                line.getColor(),
                StationResponse.listOf(line.getStations()),
                line.getCreatedDate(),
                line.getModifiedDate()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public List<StationResponse> getStations() {
        return stations;
    }
}
