package me.cher1shrxd.watodoserver.domain.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import me.cher1shrxd.watodoserver.domain.sprint.entity.SprintEntity;
import me.cher1shrxd.watodoserver.domain.wbs.entity.WbsEntity;


@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "schedules")
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "schedule_title", nullable = false)
    private String title;

    @Column(name = "schedule_detail", nullable = false)
    private String detail;

    @Column(name = "branch_name", unique = true)
    private String branch;

    @Column(name = "is_done", nullable = false)
    private boolean isDone = false;

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
