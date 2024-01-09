package com.example.jpa.board.entity;

import com.example.jpa.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn
    private User user;

    @Column
    private String title;

    @Column
    private String contents;

    @Column
    private LocalDateTime createdAt;

    @ColumnDefault("0")
    private Integer hits = 0;

    @ColumnDefault("false")
    private boolean isDeleted = false;
}
