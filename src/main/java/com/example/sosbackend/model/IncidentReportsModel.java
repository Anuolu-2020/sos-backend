package com.example.sosbackend.model;

import java.util.ArrayList;
import java.util.List;

import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;

import jakarta.persistence.*;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Table(name = "incident_reports", indexes = {
    @Index(name = "idx_incident_coordinates", columnList = "coordinates")
})
@Entity
@Data
@Check(constraints = "type_of_incident IN ('fire', 'health', 'security', 'others')")
@DynamicInsert
public class IncidentReportsModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "type_of_incident", nullable = false)
  private String typeOfIncident;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "is_addressed", nullable = false)
  @ColumnDefault("false")
  private Boolean isAddressed;

  @OneToMany(mappedBy = "incidentReport", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JsonManagedReference
  private List<IncidentReportsVideosModel> videos = new ArrayList<>();

  @OneToMany(mappedBy = "incidentReport", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JsonManagedReference
  private List<IncidentReportsPicturesModel> pictures = new ArrayList<>();

  @Column(columnDefinition = "geography(Point,4326)")
  private Point<G2D> coordinates;

}
