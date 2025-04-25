package com.todojira.ServiceTask.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Size(min = 3, max = 15, message = "title must have min 3, max 15")
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

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Historic> historicList = new ArrayList<>();

    ///private Long createdBy;

    ///private Long assignee; //api-user url

}
