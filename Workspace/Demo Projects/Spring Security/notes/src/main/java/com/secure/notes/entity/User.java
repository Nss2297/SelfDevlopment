package com.secure.notes.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "USERS", uniqueConstraints = {@UniqueConstraint(columnNames = "USER_NAME"), @UniqueConstraint(columnNames = "EMAIL")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;
    @NotBlank
    @Size(max = 20)
    @Column(name = "USER_NAME")
    private String userName;
    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "EMAIL")
    private String email;
    @NotBlank
    @Size(max = 120)
    @Column(name = "PASSWORD")
    @JsonIgnore
    private String password;
    @Column(name = "ACCOUNT_NON_LOCKED")
    private boolean accountNonLocked = true;
    @Column(name = "ACCOUNT_NON_EXPIRED")
    private boolean accountNonExpired = true;
    @Column(name = "CREDENTIAL_NON_EXPIRED")
    private boolean credentialNonExpired = true;
    @Column(name = "ENABLED")
    private boolean enabled = true;
    @Column(name = "CREDENTIALS_EXPIRY_DATE")
    private LocalDate credentialsExpiryDate;
    @Column(name = "ACCOUNT_EXPIRY_DATE")
    private LocalDate accountExpiryDate;
    @Column(name = "TWO_FACTOR_SECRET")
    private String twoFactorSecret;
    @Column(name = "IS_TWOFACTOR_ENABLED")
    private boolean isTwoFactorEnabled = false;
    @Column(name = "SIGN_UP_METHOD")
    private String signUpMethod;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")
    @JsonBackReference
    @ToString.Exclude
    private Role role;

    @CreationTimestamp
    @Column(name = "CREATE_DATE", updatable = false)
    private LocalDate createDate;
    @UpdateTimestamp
    @Column(name = "UPDATE_DATE")
    private LocalDate updateDate;

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }
}
