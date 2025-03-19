package Models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "courts")
@NamedQueries({
    @NamedQuery(name = "Courts.findAll", query = "SELECT c FROM Courts c"),
    @NamedQuery(name = "Courts.findById", query = "SELECT c FROM Courts c WHERE c.id = :id"),
    @NamedQuery(name = "Courts.findByName", query = "SELECT c FROM Courts c WHERE c.name = :name"),
    @NamedQuery(name = "Courts.findByStatus", query = "SELECT c FROM Courts c WHERE c.status = :status"),
    @NamedQuery(name = "Courts.findByZone", query = "SELECT c FROM Courts c WHERE c.zone = :zone"),
    @NamedQuery(name = "Courts.findByType", query = "SELECT c FROM Courts c WHERE c.type = :type"),
    @NamedQuery(name = "Courts.findByPricePerMatch", query = "SELECT c FROM Courts c WHERE c.pricePerMatch = :pricePerMatch"),
    @NamedQuery(name = "Courts.findByCreatedAt", query = "SELECT c FROM Courts c WHERE c.createdAt = :createdAt"),
    @NamedQuery(name = "Courts.findByUpdatedAt", query = "SELECT c FROM Courts c WHERE c.updatedAt = :updatedAt")})
public class Courts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "zone")
    private String zone;
    @Basic(optional = false)
    @Column(name = "type")
    private String type;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "price_per_match")
    private BigDecimal pricePerMatch;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courtId")
    private Collection<Reservations> reservationsCollection;

    public Courts() {
    }

    public Courts(Integer id) {
        this.id = id;
    }

    public Courts(Integer id, String name, String status, String zone, String type, BigDecimal pricePerMatch) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.zone = zone;
        this.type = type;
        this.pricePerMatch = pricePerMatch;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPricePerMatch() {
        return pricePerMatch;
    }

    public void setPricePerMatch(BigDecimal pricePerMatch) {
        this.pricePerMatch = pricePerMatch;
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

    public Collection<Reservations> getReservationsCollection() {
        return reservationsCollection;
    }

    public void setReservationsCollection(Collection<Reservations> reservationsCollection) {
        this.reservationsCollection = reservationsCollection;
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
        if (!(object instanceof Courts)) {
            return false;
        }
        Courts other = (Courts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Courts[ id=" + id + " ]";
    }

}
