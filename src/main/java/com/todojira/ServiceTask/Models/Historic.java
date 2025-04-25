package com.todojira.ServiceTask.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Historic implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull(message = "actionType cannot be null")
    @NotBlank(message = "actionType cannot be blank")
    @Size(max = 16, message = "actionType must have max 16")
    private String actionType;  // ex: TASK_CREATED, STATUS_CHANGED, PRIORITY_CHANGED

    @ManyToOne
    @JoinColumn(name = "id_task")
    private Task task;
}
