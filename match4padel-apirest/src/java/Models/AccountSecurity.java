package Models;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "account_security")
@NamedQueries({
    @NamedQuery(name = "AccountSecurity.findAll", query = "SELECT a FROM AccountSecurity a"),
    @NamedQuery(name = "AccountSecurity.findById", query = "SELECT a FROM AccountSecurity a WHERE a.id = :id"),
    @NamedQuery(name = "AccountSecurity.findByPasswordHash", query = "SELECT a FROM AccountSecurity a WHERE a.passwordHash = :passwordHash"),
    @NamedQuery(name = "AccountSecurity.findBySalt", query = "SELECT a FROM AccountSecurity a WHERE a.salt = :salt"),
    @NamedQuery(name = "AccountSecurity.findByToken", query = "SELECT a FROM AccountSecurity a WHERE a.token = :token")})
public class AccountSecurity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "password_hash")
    private String passwordHash;
    @Basic(optional = false)
    @Column(name = "salt")
    private String salt;
    @Column(name = "token")
    private String token;
    @OneToOne(mappedBy = "accountSecurityId")
    private Workers workers;
    @OneToOne(mappedBy = "accountSecurityId")
    private Users users;

    public AccountSecurity() {
    }

    public AccountSecurity(Integer id) {
        this.id = id;
    }

    public AccountSecurity(Integer id, String passwordHash, String salt) {
        this.id = id;
        this.passwordHash = passwordHash;
        this.salt = salt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Workers getWorkers() {
        return workers;
    }

    public void setWorkers(Workers workers) {
        this.workers = workers;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
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
        if (!(object instanceof AccountSecurity)) {
            return false;
        }
        AccountSecurity other = (AccountSecurity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.AccountSecurity[ id=" + id + " ]";
    }

}
