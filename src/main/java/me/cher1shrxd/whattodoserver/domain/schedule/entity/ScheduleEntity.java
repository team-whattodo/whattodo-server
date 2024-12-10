package me.cher1shrxd.whattodoserver.domain.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import me.cher1shrxd.whattodoserver.domain.sprint.entity.SprintEntity;
import me.cher1shrxd.whattodoserver.domain.wbs.entity.WbsEntity;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "schedules")
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "schedule_title", nullable = false)
    private String title;

    @Column(name = "schedule_detail", nullable = false)
    private String detail;

    @Column(name = "branch_sha")
    private String sha;

    @Column(name = "is_done", nullable = false)
    private boolean isDone;

    @Column(name = "schedule_start", nullable = false)
    private String start;

    @Column(name = "schedule_deadline", nullable = false)
    private String deadline;

    @ManyToOne
    @JoinColumn(name = "sprint_id")
    private SprintEntity sprint;

    @ManyToOne
    @JoinColumn(name = "wbs_id")
    private WbsEntity wbs;
}
