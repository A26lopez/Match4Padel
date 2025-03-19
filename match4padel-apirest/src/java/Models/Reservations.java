package Models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "reservations")
@NamedQueries({
    @NamedQuery(name = "Reservations.findAll", query = "SELECT r FROM Reservations r"),
    @NamedQuery(name = "Reservations.findById", query = "SELECT r FROM Reservations r WHERE r.id = :id"),
    @NamedQuery(name = "Reservations.findByDate", query = "SELECT r FROM Reservations r WHERE r.date = :date"),
    @NamedQuery(name = "Reservations.findByStartTime", query = "SELECT r FROM Reservations r WHERE r.startTime = :startTime"),
    @NamedQuery(name = "Reservations.findByEndTime", query = "SELECT r FROM Reservations r WHERE r.endTime = :endTime"),
    @NamedQuery(name = "Reservations.findByDuration", query = "SELECT r FROM Reservations r WHERE r.duration = :duration"),
    @NamedQuery(name = "Reservations.findByStatus", query = "SELECT r FROM Reservations r WHERE r.status = :status"),
    @NamedQuery(name = "Reservations.findByStatusDate", query = "SELECT r FROM Reservations r WHERE r.statusDate = :statusDate"),
    @NamedQuery(name = "Reservations.findByPaid", query = "SELECT r FROM Reservations r WHERE r.paid = :paid")})
public class Reservations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Column(name = "start_time")
    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Basic(optional = false)
    @Column(name = "end_time")
    @Temporal(TemporalType.TIME)
    private Date endTime;
    @Basic(optional = false)
    @Column(name = "duration")
    private int duration;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Column(name = "status_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusDate;
    @Column(name = "paid")
    private Boolean paid;
    @JoinColumn(name = "court_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Courts courtId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users userId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reservationId")
    private Collection<Payments> paymentsCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "reservationId")
    private Matches matches;

    public Reservations() {
    }

    public Reservations(Integer id) {
        this.id = id;
    }

    public Reservations(Integer id, Date date, Date startTime, Date endTime, int duration, String status) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Courts getCourtId() {
        return courtId;
    }

    public void setCourtId(Courts courtId) {
        this.courtId = courtId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Collection<Payments> getPaymentsCollection() {
        return paymentsCollection;
    }

    public void setPaymentsCollection(Collection<Payments> paymentsCollection) {
        this.paymentsCollection = paymentsCollection;
    }

    public Matches getMatches() {
        return matches;
    }

    public void setMatches(Matches matches) {
        this.matches = matches;
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
        if (!(object instanceof Reservations)) {
            return false;
        }
        Reservations other = (Reservations) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Reservations[ id=" + id + " ]";
    }

}
