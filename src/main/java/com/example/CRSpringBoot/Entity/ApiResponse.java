package com.example.CRSpringBoot.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "api_response")
public class ApiResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "response")
    private String response;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public ApiResponse(Long id, String userId, String response, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.response = response;
        this.timestamp = timestamp;
    }

    public ApiResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "id=" + id +
                ", userId=" + userId +
                ", response='" + response + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
