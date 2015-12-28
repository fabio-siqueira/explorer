package br.com.company.explorer.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Fábio Siqueira on 12/22/15.
 */
@Entity
public class Land implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Integer ZERO = 0;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Integer topLimit;

    @Column(nullable = false)
    private Integer rightLimit;

    @OneToMany(mappedBy = "land", cascade={CascadeType.ALL}, orphanRemoval=true)
    private Set<Probe> probes = new HashSet<>();

    public Land() {
    }

    public Land(Integer topLimit, Integer rightLimit) {
        this.topLimit = topLimit;
        this.rightLimit = rightLimit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTopLimit() {
        return topLimit;
    }

    public void setTopLimit(Integer topLimit) {
        this.topLimit = topLimit;
    }

    public Integer getRightLimit() {
        return rightLimit;
    }

    public void setRightLimit(Integer rightLimit) {
        this.rightLimit = rightLimit;
    }

    public Set<Probe> getProbes() {
        return probes;
    }

    public void setProbes(Set<Probe> probes) {
        this.probes = probes;
    }

    public Probe getProbeById(Long probeId) {
        for (Probe probe : probes) {
            if (probe.getId() == probeId) {
                return probe;
            }
        }
        return null;
    }
}
