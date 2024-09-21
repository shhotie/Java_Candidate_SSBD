package com.ssb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;


@Entity
@Table(name = "earthquake_events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EarthquakeEvent {

    @Id
    @Column(nullable = false)
    private String eventID;

    @Column(nullable = true)
    private OffsetDateTime datetime; // Change LocalDate to OffsetDateTime for consistency with the parsing

    @Column(nullable = true)
    private Double latitude;

    @Column(nullable = true)
    private Double longitude;

    @Column(nullable = true)
    private Double magnitude;

    @Column(nullable = true)
    private String mag_type;

    @Column(nullable = true)
    private Double depth;

    @Column(nullable = true)
    private Integer phasecount;

    @Column(nullable = true)
    private Double azimuth_gap;

    @Column(nullable = true)
    private String location;

    @Column(nullable = true)
    private String agency;

    @Column(nullable = true)
    private OffsetDateTime datetimeFM; // Changed from LocalDateTime to OffsetDateTime

    @Column(nullable = true)
    private Double latFM;

    @Column(nullable = true)
    private Double lonFM;

    @Column(nullable = true)
    private Double magFM;

    @Column(nullable = true)
    private String magTypeFM;

    @Column(nullable = true)
    private Double depthFM;

    @Column(nullable = true)
    private Integer phasecountFM;

    @Column(nullable = true)
    private Double azgapFM;

    @Column(nullable = true)
    private Double scalarMoment;

    @Column(nullable = true)
    private Double Mrr;

    @Column(nullable = true)
    private Double Mtt;

    @Column(nullable = true)
    private Double Mpp;

    @Column(nullable = true)
    private Double Mrt;

    @Column(nullable = true)
    private Double Mrp;

    @Column(nullable = true)
    private Double Mtp;

    @Column(nullable = true)
    private Double varianceReduction;

    @Column(nullable = true)
    private Double doubleCouple;

    @Column(nullable = true)
    private Double clvd;

    @Column(nullable = true)
    private Integer strikeNP1;

    @Column(nullable = true)
    private Integer dipNP1;

    @Column(nullable = true)
    private Integer rakeNP1;

    @Column(nullable = true)
    private Integer strikeNP2;

    @Column(nullable = true)
    private Integer dipNP2;

    @Column(nullable = true)
    private Integer rakeNP2;

    @Column(nullable = true)
    private Double misfit;
}