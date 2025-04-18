
package com.example.sosbackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "incident_reports_videos")
public class IncidentReportsVideosModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "incident_report_id")
  private IncidentReportsModel incidentReport;

  @Column(name = "video_url", nullable = false)
  private String videoUrl;

  @Column(name = "public_id")
  private String publicId;

}
