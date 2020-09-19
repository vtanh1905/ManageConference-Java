package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "location", schema = "db_conference", catalog = "")
public class LocationEntity {
    private String id;
    private String name;
    private String address;
    private Integer maxPeople;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "maxPeople")
    public Integer getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationEntity that = (LocationEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(maxPeople, that.maxPeople);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, maxPeople);
    }

    @Override
    public String toString() {
        return name;
    }
}
