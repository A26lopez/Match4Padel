package com.match4padel.match4padel_api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;


    @Entity
    @Table(name = "account_info")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class AccountInfo {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true)
        private String username;

        @Column(name = "profile_picture_url")
        private String profilePictureUrl;

        private Boolean isActive = true;

        @CreationTimestamp
        private LocalDateTime createdAt;

        private LocalDateTime lastLogin;
    }

