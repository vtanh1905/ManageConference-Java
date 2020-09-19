package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "partaker", schema = "db_conference", catalog = "")
public class PartakerEntity {
    private int id;
    private Integer conferenceId;
    private Integer userId;
    private Integer isApproved;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "conference_id")
    public Integer getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Integer conferenceId) {
        this.conferenceId = conferenceId;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "isApproved")
    public Integer getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Integer isApproved) {
        this.isApproved = isApproved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartakerEntity that = (PartakerEntity) o;
        return id == that.id &&
                Objects.equals(conferenceId, that.conferenceId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(isApproved, that.isApproved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, conferenceId, userId, isApproved);
    }
}
