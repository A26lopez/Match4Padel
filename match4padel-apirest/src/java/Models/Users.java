package Models;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findById", query = "SELECT u FROM Users u WHERE u.id = :id"),
    @NamedQuery(name = "Users.findByLevel", query = "SELECT u FROM Users u WHERE u.level = :level")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "level")
    private String level;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player1Id")
    private Collection<Teams> teamsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player2Id")
    private Collection<Teams> teamsCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<Reservations> reservationsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ownerId")
    private Collection<Matches> matchesCollection;
    @OneToMany(mappedBy = "player1Id")
    private Collection<Matches> matchesCollection1;
    @OneToMany(mappedBy = "player2Id")
    private Collection<Matches> matchesCollection2;
    @OneToMany(mappedBy = "player3Id")
    private Collection<Matches> matchesCollection3;
    @JoinColumn(name = "account_info_id", referencedColumnName = "id")
    @OneToOne
    private AccountInfo accountInfoId;
    @JoinColumn(name = "account_security_id", referencedColumnName = "id")
    @OneToOne
    private AccountSecurity accountSecurityId;
    @JoinColumn(name = "contact_info_id", referencedColumnName = "id")
    @OneToOne
    private ContactInfo contactInfoId;

    public Users() {
    }

    public Users(Integer id) {
        this.id = id;
    }

    public Users(Integer id, String level) {
        this.id = id;
        this.level = level;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Collection<Teams> getTeamsCollection() {
        return teamsCollection;
    }

    public void setTeamsCollection(Collection<Teams> teamsCollection) {
        this.teamsCollection = teamsCollection;
    }

    public Collection<Teams> getTeamsCollection1() {
        return teamsCollection1;
    }

    public void setTeamsCollection1(Collection<Teams> teamsCollection1) {
        this.teamsCollection1 = teamsCollection1;
    }

    public Collection<Reservations> getReservationsCollection() {
        return reservationsCollection;
    }

    public void setReservationsCollection(Collection<Reservations> reservationsCollection) {
        this.reservationsCollection = reservationsCollection;
    }

    public Collection<Matches> getMatchesCollection() {
        return matchesCollection;
    }

    public void setMatchesCollection(Collection<Matches> matchesCollection) {
        this.matchesCollection = matchesCollection;
    }

    public Collection<Matches> getMatchesCollection1() {
        return matchesCollection1;
    }

    public void setMatchesCollection1(Collection<Matches> matchesCollection1) {
        this.matchesCollection1 = matchesCollection1;
    }

    public Collection<Matches> getMatchesCollection2() {
        return matchesCollection2;
    }

    public void setMatchesCollection2(Collection<Matches> matchesCollection2) {
        this.matchesCollection2 = matchesCollection2;
    }

    public Collection<Matches> getMatchesCollection3() {
        return matchesCollection3;
    }

    public void setMatchesCollection3(Collection<Matches> matchesCollection3) {
        this.matchesCollection3 = matchesCollection3;
    }

    public AccountInfo getAccountInfoId() {
        return accountInfoId;
    }

    public void setAccountInfoId(AccountInfo accountInfoId) {
        this.accountInfoId = accountInfoId;
    }

    public AccountSecurity getAccountSecurityId() {
        return accountSecurityId;
    }

    public void setAccountSecurityId(AccountSecurity accountSecurityId) {
        this.accountSecurityId = accountSecurityId;
    }

    public ContactInfo getContactInfoId() {
        return contactInfoId;
    }

    public void setContactInfoId(ContactInfo contactInfoId) {
        this.contactInfoId = contactInfoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Users[ id=" + id + " ]";
    }

}
