package Models;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "work_info")
@NamedQueries({
    @NamedQuery(name = "WorkInfo.findAll", query = "SELECT w FROM WorkInfo w"),
    @NamedQuery(name = "WorkInfo.findById", query = "SELECT w FROM WorkInfo w WHERE w.id = :id"),
    @NamedQuery(name = "WorkInfo.findByRole", query = "SELECT w FROM WorkInfo w WHERE w.role = :role"),
    @NamedQuery(name = "WorkInfo.findByHireDateStart", query = "SELECT w FROM WorkInfo w WHERE w.hireDateStart = :hireDateStart"),
    @NamedQuery(name = "WorkInfo.findByHireDateEnd", query = "SELECT w FROM WorkInfo w WHERE w.hireDateEnd = :hireDateEnd"),
    @NamedQuery(name = "WorkInfo.findBySalary", query = "SELECT w FROM WorkInfo w WHERE w.salary = :salary"),
    @NamedQuery(name = "WorkInfo.findBySocialSecurityNumber", query = "SELECT w FROM WorkInfo w WHERE w.socialSecurityNumber = :socialSecurityNumber"),
    @NamedQuery(name = "WorkInfo.findByBankAccount", query = "SELECT w FROM WorkInfo w WHERE w.bankAccount = :bankAccount")})
public class WorkInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "role")
    private String role;
    @Basic(optional = false)
    @Column(name = "hire_date_start")
    @Temporal(TemporalType.DATE)
    private Date hireDateStart;
    @Column(name = "hire_date_end")
    @Temporal(TemporalType.DATE)
    private Date hireDateEnd;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "salary")
    private BigDecimal salary;
    @Basic(optional = false)
    @Column(name = "social_security_number")
    private String socialSecurityNumber;
    @Basic(optional = false)
    @Column(name = "bank_account")
    private String bankAccount;
    @OneToOne(mappedBy = "workInfoId")
    private Workers workers;

    public WorkInfo() {
    }

    public WorkInfo(Integer id) {
        this.id = id;
    }

    public WorkInfo(Integer id, String role, Date hireDateStart, BigDecimal salary, String socialSecurityNumber, String bankAccount) {
        this.id = id;
        this.role = role;
        this.hireDateStart = hireDateStart;
        this.salary = salary;
        this.socialSecurityNumber = socialSecurityNumber;
        this.bankAccount = bankAccount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getHireDateStart() {
        return hireDateStart;
    }

    public void setHireDateStart(Date hireDateStart) {
        this.hireDateStart = hireDateStart;
    }

    public Date getHireDateEnd() {
        return hireDateEnd;
    }

    public void setHireDateEnd(Date hireDateEnd) {
        this.hireDateEnd = hireDateEnd;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Workers getWorkers() {
        return workers;
    }

    public void setWorkers(Workers workers) {
        this.workers = workers;
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
        if (!(object instanceof WorkInfo)) {
            return false;
        }
        WorkInfo other = (WorkInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.WorkInfo[ id=" + id + " ]";
    }

}
