package me.cher1shrxd.whattodoserver.domain.wbs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import me.cher1shrxd.whattodoserver.domain.project.entity.ProjectEntity;
import me.cher1shrxd.whattodoserver.domain.schedule.entity.ScheduleEntity;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "wbs")
public class WbsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "wbs_id")
    private String id;

    @Column(name = "wbs_title")
    private String title;

    @Column(name = "wbs_detail")
    private String detail;

    @OneToMany(mappedBy = "wbs", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleEntity> schedules;

    @OneToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private ProjectEntity project;
}

