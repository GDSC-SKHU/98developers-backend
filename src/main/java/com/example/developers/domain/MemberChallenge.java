package com.example.developers.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_challenge")
public class MemberChallenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;

    @Column(nullable = true)
    private String image;

    @Column
    @ColumnDefault("false")
    private Boolean isSuccess;

    public void imageUpdate(String imageSrc) {
        this.image = imageSrc;
    }
}