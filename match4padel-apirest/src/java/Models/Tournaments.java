package Models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "tournaments")
@NamedQueries({
    @NamedQuery(name = "Tournaments.findAll", query = "SELECT t FROM Tournaments t"),
    @NamedQuery(name = "Tournaments.findById", query = "SELECT t FROM Tournaments t WHERE t.id = :id"),
    @NamedQuery(name = "Tournaments.findByName", query = "SELECT t FROM Tournaments t WHERE t.name = :name"),
    @NamedQuery(name = "Tournaments.findByPicture", query = "SELECT t FROM Tournaments t WHERE t.picture = :picture"),
    @NamedQuery(name = "Tournaments.findByStartDate", query = "SELECT t FROM Tournaments t WHERE t.startDate = :startDate"),
    @NamedQuery(name = "Tournaments.findByEndDate", query = "SELECT t FROM Tournaments t WHERE t.endDate = :endDate"),
    @NamedQuery(name = "Tournaments.findByInscriptionPrice", query = "SELECT t FROM Tournaments t WHERE t.inscriptionPrice = :inscriptionPrice"),
    @NamedQuery(name = "Tournaments.findByPrize", query = "SELECT t FROM Tournaments t WHERE t.prize = :prize"),
    @NamedQuery(name = "Tournaments.findByLevel", query = "SELECT t FROM Tournaments t WHERE t.level = :level"),
    @NamedQuery(name = "Tournaments.findByStatus", query = "SELECT t FROM Tournaments t WHERE t.status = :status"),
    @NamedQuery(name = "Tournaments.findByCreatedAt", query = "SELECT t FROM Tournaments t WHERE t.createdAt = :createdAt"),
    @NamedQuery(name = "Tournaments.findByUpdatedAt", query = "SELECT t FROM Tournaments t WHERE t.updatedAt = :updatedAt")})
public class Tournaments implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "picture")
    private String picture;
    @Basic(optional = false)
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "inscription_price")
    private BigDecimal inscriptionPrice;
    @Basic(optional = false)
    @Column(name = "prize")
    private BigDecimal prize;
    @Basic(optional = false)
    @Column(name = "level")
    private String level;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @ManyToMany(mappedBy = "tournamentsCollection")
    private Collection<Teams> teamsCollection;
    @OneToMany(mappedBy = "tournamentId")
    private Collection<Matches> matchesCollection;

    public Tournaments() {
    }

    public Tournaments(Integer id) {
        this.id = id;
    }

    public Tournaments(Integer id, String name, Date startDate, Date endDate, BigDecimal inscriptionPrice, BigDecimal prize, String level, String status) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.inscriptionPrice = inscriptionPrice;
        this.prize = prize;
        this.level = level;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getInscriptionPrice() {
        return inscriptionPrice;
    }

    public void setInscriptionPrice(BigDecimal inscriptionPrice) {
        this.inscriptionPrice = inscriptionPrice;
    }

    public BigDecimal getPrize() {
        return prize;
    }

    public void setPrize(BigDecimal prize) {
        this.prize = prize;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Collection<Teams> getTeamsCollection() {
        return teamsCollection;
    }

    public void setTeamsCollection(Collection<Teams> teamsCollection) {
        this.teamsCollection = teamsCollection;
    }

    public Collection<Matches> getMatchesCollection() {
        return matchesCollection;
    }

    public void setMatchesCollection(Collection<Matches> matchesCollection) {
        this.matchesCollection = matchesCollection;
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
        if (!(object instanceof Tournaments)) {
            return false;
        }
        Tournaments other = (Tournaments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Tournaments[ id=" + id + " ]";
    }

}
