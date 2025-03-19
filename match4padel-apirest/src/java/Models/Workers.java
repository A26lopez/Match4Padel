package Models;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "workers")
@NamedQueries({
    @NamedQuery(name = "Workers.findAll", query = "SELECT w FROM Workers w"),
    @NamedQuery(name = "Workers.findById", query = "SELECT w FROM Workers w WHERE w.id = :id")})
public class Workers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "account_info_id", referencedColumnName = "id")
    @OneToOne
    private AccountInfo accountInfoId;
    @JoinColumn(name = "account_security_id", referencedColumnName = "id")
    @OneToOne
    private AccountSecurity accountSecurityId;
    @JoinColumn(name = "contact_info_id", referencedColumnName = "id")
    @OneToOne
    private ContactInfo contactInfoId;
    @JoinColumn(name = "work_info_id", referencedColumnName = "id")
    @OneToOne
    private WorkInfo workInfoId;

    public Workers() {
    }

    public Workers(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public WorkInfo getWorkInfoId() {
        return workInfoId;
    }

    public void setWorkInfoId(WorkInfo workInfoId) {
        this.workInfoId = workInfoId;
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
        if (!(object instanceof Workers)) {
            return false;
        }
        Workers other = (Workers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Workers[ id=" + id + " ]";
    }

}
