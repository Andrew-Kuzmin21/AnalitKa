package com.kuzmin.Project_i.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Accessors(chain = true)
@Data
@Entity(name = "users")
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    private Long id;

    @Email(message = "Некорректный email")
    @Column(name = "user_email", length = 50, unique = true)
    private String email;

    @NotBlank(message = "Пароль обязателен")
    @Size(min = 8, message = "Пароль минимум 8 символов")
    @Column(name = "user_password", length = 100, nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Dashboard> dashboards;

    @OneToMany(mappedBy = "user")
    private List<Segment> segments;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }
}
