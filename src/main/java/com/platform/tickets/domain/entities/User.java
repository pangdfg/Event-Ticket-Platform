package com.platform.tickets.domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "id",updatable = false,nullable = false)
    private UUID id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "email",nullable = false)
    private String email;

    @OneToMany(mappedBy = "organizer",cascade = CascadeType.ALL)
    private List<Event> organizedEvents = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_attending_events",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> attendingEvents = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_staffing_events",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> staffingEvents = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at",updatable = false,nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id.equals(user.id) && name.equals(user.name) && email.equals(user.email) && createdAt.equals(user.createdAt) && updatedAt.equals(user.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, createdAt, updatedAt);
    }
}
