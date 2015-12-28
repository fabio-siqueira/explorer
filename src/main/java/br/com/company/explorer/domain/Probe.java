package br.com.company.explorer.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Fábio Siqueira on 12/17/15.
 */
@Entity
public class Probe implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @ManyToOne
    private Land land;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Integer latitude = 0;

    @Column(nullable = false)
    private Integer longitude = 0;

    @Column(nullable = false)
    private CardinalDirection direction = CardinalDirection.NORTH;

    public Probe() {}

    public Probe(Integer latitude, Integer longitude, CardinalDirection direction) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.direction = direction;
    }

    public Probe(Integer latitude, Integer longitude, CardinalDirection direction, Land land) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.direction = direction;
        this.land = land;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public CardinalDirection getDirection() {
        return direction;
    }

    public void setDirection(CardinalDirection direction) {
        this.direction = direction;
    }

    public Land getLand() {
        return land;
    }

    public void setLand(Land land) {
        this.land = land;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.latitude);
        sb.append(" ");
        sb.append(this.longitude);
        sb.append(" ");
        sb.append(this.direction);
        return sb.toString();
    }
}
