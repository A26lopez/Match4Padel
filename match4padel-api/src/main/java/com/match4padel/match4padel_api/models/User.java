package com.match4padel.match4padel_api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level level;

    @OneToOne
    @JoinColumn(name = "account_info_id", nullable = false, unique = true)
    private AccountInfo accountInfo;

    @OneToOne
    @JoinColumn(name = "contact_info_id", nullable = false, unique = true)
    private ContactInfo contactInfo;

    @OneToOne
    @JoinColumn(name = "account_security_id", nullable = false, unique = true)
    private AccountSecurity accountSecurity;

    public enum Level {
        BEGINNER, INTERMEDIATE, ADVANCED
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public AccountSecurity getAccountSecurity() {
        return accountSecurity;
    }

    public void setAccountSecurity(AccountSecurity accountSecurity) {
        this.accountSecurity = accountSecurity;
    }
    
    
}
