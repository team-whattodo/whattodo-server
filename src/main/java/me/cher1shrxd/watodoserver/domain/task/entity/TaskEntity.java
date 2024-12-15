package me.cher1shrxd.watodoserver.domain.task.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "task")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "task_title", nullable = false)
    private String title;


    @Column(name = "task_branch", unique = true)
    private String branch;

    @Column(name = "is_done", nullable = false)
    private boolean isDone = false;

    @Column(name = "task_start", nullable = false)
    private String start;

    @Column(name = "task_deadline", nullable = false)
    private String deadline;

    @ManyToOne
    @JoinColumn(name = "sprint_id")
    @JsonIgnore
    private SprintEntity sprint;

    @ManyToOne
    @JoinColumn(name = "wbs_id")
    @JsonIgnore
    private WbsEntity wbs;
}
