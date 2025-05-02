package com.example.sosbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "incident_reports_pictures")
@Data
public class IncidentReportsPicturesModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "incident_report_id")
  @JsonBackReference
  private IncidentReportsModel incidentReport;

  @Column(name = "url", nullable = false)
  private String url;

  @Column(name = "key", nullable = false)
  private String key;

}
