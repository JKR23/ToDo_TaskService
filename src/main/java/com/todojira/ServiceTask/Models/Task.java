package com.todojira.ServiceTask.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull(message = "title cannot be null")
    @NotBlank(message = "title cannot be blank")
    @Size(min = 3, max = 100, message = "title must have min 3, max 100")
    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "id_priority")
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "id_status")
    private Status status;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long createdBy;

    ///private Long assignee; //api-user url

}
