package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "conference", schema = "db_conference", catalog = "")
public class ConferenceEntity {
    private int id;
    private String name;
    private String shortDescription;
    private String detailDescription;
    private String image;
    private String timeStartAt;
    private String timeEndAt;
    private String locationId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "short_description")
    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Basic
    @Column(name = "detail_description")
    public String getDetailDescription() {
        return detailDescription;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    @Basic
    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Basic
    @Column(name = "timeStartAt")
    public String getTimeStartAt() {
        return timeStartAt;
    }

    public void setTimeStartAt(String timeStartAt) {
        this.timeStartAt = timeStartAt;
    }

    @Basic
    @Column(name = "timeEndAt")
    public String getTimeEndAt() {
        return timeEndAt;
    }

    public void setTimeEndAt(String timeEndAt) {
        this.timeEndAt = timeEndAt;
    }

    @Basic
    @Column(name = "location_id")
    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConferenceEntity that = (ConferenceEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(shortDescription, that.shortDescription) &&
                Objects.equals(detailDescription, that.detailDescription) &&
                Objects.equals(image, that.image) &&
                Objects.equals(timeStartAt, that.timeStartAt) &&
                Objects.equals(timeEndAt, that.timeEndAt) &&
                Objects.equals(locationId, that.locationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shortDescription, detailDescription, image, timeStartAt, timeEndAt, locationId);
    }
}
