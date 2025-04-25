package com.todojira.ServiceTask.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Priority implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull(message = "name cannot be null")
    @NotBlank(message = "name cannot be blank")
    @Size(min = 3, max = 15, message = "name must have min 3, max 15")
    private String name;

    @OneToMany(mappedBy = "priority")
    private List<Task> task;
}
