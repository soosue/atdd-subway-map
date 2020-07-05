package nextstep.subway.line.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class LineStation {
    private Long preStationId;
    private Long stationId;
    private Integer distance;
    private Integer duration;

    protected LineStation() {
    }

    public LineStation(Long preStationId, Long stationId, Integer distance, Integer duration) {
        this.stationId = stationId;
        this.preStationId = preStationId;
        this.distance = distance;
        this.duration = duration;
    }

    public boolean isPreStationOf(LineStation other) {
        return Objects.equals(this.stationId, other.preStationId);
    }

    public void changePreStation(final LineStation preLineStation) {
        this.preStationId = preLineStation.getStationId();
    }

    public Long getPreStationId() {
        return preStationId;
    }

    public Long getStationId() {
        return stationId;
    }

    public Integer getDistance() {
        return distance;
    }

    public Integer getDuration() {
        return duration;
    }
}
