package com.example.sosbackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "incident_reports_pictures")
public class IncidentReportsPicturesModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "incident_report_id")
  private IncidentReportsModel incidentReport;

  @Column(name = "picture_url", nullable = false)
  private String pictureUrl;

  @Column(name = "public_id")
  private String publicId;

}
