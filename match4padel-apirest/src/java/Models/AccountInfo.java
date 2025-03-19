package Models;

import java.io.Serializable;
import java.util.Date;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "account_info")
@NamedQueries({
    @NamedQuery(name = "AccountInfo.findAll", query = "SELECT a FROM AccountInfo a"),
    @NamedQuery(name = "AccountInfo.findById", query = "SELECT a FROM AccountInfo a WHERE a.id = :id"),
    @NamedQuery(name = "AccountInfo.findByUsername", query = "SELECT a FROM AccountInfo a WHERE a.username = :username"),
    @NamedQuery(name = "AccountInfo.findByProfilePicture", query = "SELECT a FROM AccountInfo a WHERE a.profilePicture = :profilePicture"),
    @NamedQuery(name = "AccountInfo.findByIsActive", query = "SELECT a FROM AccountInfo a WHERE a.isActive = :isActive"),
    @NamedQuery(name = "AccountInfo.findByCreatedAt", query = "SELECT a FROM AccountInfo a WHERE a.createdAt = :createdAt"),
    @NamedQuery(name = "AccountInfo.findByLastLogin", query = "SELECT a FROM AccountInfo a WHERE a.lastLogin = :lastLogin")})
public class AccountInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Column(name = "profile_picture")
    private String profilePicture;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "last_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;
    @OneToOne(mappedBy = "accountInfoId")
    private Workers workers;
    @OneToOne(mappedBy = "accountInfoId")
    private Users users;

    public AccountInfo() {
    }

    public AccountInfo(Integer id) {
        this.id = id;
    }

    public AccountInfo(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
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
        if (!(object instanceof AccountInfo)) {
            return false;
        }
        AccountInfo other = (AccountInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.AccountInfo[ id=" + id + " ]";
    }

}
