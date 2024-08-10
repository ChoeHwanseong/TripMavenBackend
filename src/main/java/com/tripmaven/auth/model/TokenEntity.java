package com.tripmaven.auth.model;

import java.time.LocalDateTime;

import com.tripmaven.members.model.MembersEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "Token")
public class TokenEntity {

    @Id
    @SequenceGenerator(name="seq_tokens",sequenceName = "seq_tokens",allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_tokens")
	private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memebers_id", referencedColumnName = "id", nullable = false)
    private MembersEntity members;

    @Column(name = "token_value", nullable = false, length = 255)
    private String tokenValue;

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "expires_in", nullable = false)
    private long expiresIn;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(length = 50)
    private String status;

}
