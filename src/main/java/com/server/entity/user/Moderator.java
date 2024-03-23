package com.server.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Schema(description = "Админ")
public class Moderator {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Идентификатор администратора")
    private int id;

    @Column(nullable = false, unique = true)
    @NotBlank
    @Size(min=2, max=50)
    @Schema(description = "Имя администратора")
    private String name;

    @OneToOne
    @JoinColumn(name = "api_users_id")
    private ApiUsers apiUsers;

}
