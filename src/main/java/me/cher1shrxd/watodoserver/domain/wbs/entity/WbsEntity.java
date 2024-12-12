package me.cher1shrxd.watodoserver.domain.wbs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import me.cher1shrxd.watodoserver.domain.project.entity.ProjectEntity;
import me.cher1shrxd.watodoserver.domain.task.entity.TaskEntity;

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
    private List<TaskEntity> schedules;

    @OneToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @JsonIgnore
    private ProjectEntity project;
}

