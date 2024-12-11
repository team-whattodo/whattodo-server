package me.cher1shrxd.watodoserver.domain.sprint.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import me.cher1shrxd.watodoserver.domain.project.entity.ProjectEntity;
import me.cher1shrxd.watodoserver.domain.schedule.entity.ScheduleEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "sprints")
public class SprintEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "sprint_id")
    private String id;

    @Column(name = "sprint_title")
    private String title;

    @Column(name = "sprint_detail")
    private String detail;

    @Column(name = "sprint_start", nullable = false)
    private String start;

    @Column(name = "sprint_deadline", nullable = false)
    private String deadline;

    @OneToMany(mappedBy = "sprint", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleEntity> schedules = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private ProjectEntity project;
}


