package com.owoonan.owoonan.domain.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.owoonan.owoonan.domain.routine.domain.Routine;
import com.owoonan.owoonan.domain.user.domain.vo.ProviderType;
import com.owoonan.owoonan.domain.user.domain.vo.RoleType;
import com.owoonan.owoonan.domain.workout.domain.Workout;
import com.owoonan.owoonan.global.common.BaseEntity;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity {

    @JsonIgnore
    @Column(name = "USER_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @Id
    @Column(name = "USER_ID", length = 64, unique = true)
    @NotNull
    private String userId;

    @Column(name = "USERNAME", length = 100)
    @NotNull
    private String username;

    @JsonIgnore
    @Column(name = "PASSWORD", length = 128)
    @NotNull
    private String password;

    @Column(name = "EMAIL", length = 512, unique = true)
    @NotNull
    private String email;

    @Column(length = 20, unique = true)
    @NotNull
    private String nickname;

    @NotNull
    private Double weight;

    @NotNull
    private Double height;


    @Column(name = "EMAIL_VERIFIED_YN", length = 1)
    @NotNull
    private String emailVerifiedYn;

    @Column(name = "PROFILE_IMAGE_URL", length = 512)
    @NotNull
    private String profileImageUrl;

    @Column(name = "PROVIDER_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProviderType providerType;

    @Column(name = "ROLE_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleType roleType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Routine> routines = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Workout> workouts = new ArrayList<>();

    @Builder
    public User(
            @NotNull String userId,
            @NotNull String username,
            @NotNull String email,
            @NotNull String emailVerifiedYn,
            @NotNull String profileImageUrl,
            @NotNull ProviderType providerType,
            @NotNull RoleType roleType
    ) {
        this.userId = userId;
        this.username = username;
        this.password = "NO_PASS";
        this.email = email != null ? email : "NO_EMAIL";
        this.emailVerifiedYn = emailVerifiedYn;
        this.profileImageUrl = profileImageUrl != null ? profileImageUrl : "";
        this.providerType = providerType;
        this.roleType = roleType;
    }

    public User(
            @NotNull String userId,
            @NotNull String username,
            @NotNull String email
    ) {
        this.userId = userId;
        this.username = username;
        this.email = email != null ? email : "NO_EMAIL";
    }
}
