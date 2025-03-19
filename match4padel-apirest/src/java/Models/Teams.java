package Models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "teams")
@NamedQueries({
    @NamedQuery(name = "Teams.findAll", query = "SELECT t FROM Teams t"),
    @NamedQuery(name = "Teams.findById", query = "SELECT t FROM Teams t WHERE t.id = :id"),
    @NamedQuery(name = "Teams.findByName", query = "SELECT t FROM Teams t WHERE t.name = :name"),
    @NamedQuery(name = "Teams.findByIsActive", query = "SELECT t FROM Teams t WHERE t.isActive = :isActive"),
    @NamedQuery(name = "Teams.findByPicture", query = "SELECT t FROM Teams t WHERE t.picture = :picture"),
    @NamedQuery(name = "Teams.findByCreatedAt", query = "SELECT t FROM Teams t WHERE t.createdAt = :createdAt")})
public class Teams implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "picture")
    private String picture;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinTable(name = "team_tournament", joinColumns = {
        @JoinColumn(name = "team_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "tournament_id", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Tournaments> tournamentsCollection;
    @JoinColumn(name = "player_1_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users player1Id;
    @JoinColumn(name = "player_2_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users player2Id;

    public Teams() {
    }

    public Teams(Integer id) {
        this.id = id;
    }

    public Teams(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Collection<Tournaments> getTournamentsCollection() {
        return tournamentsCollection;
    }

    public void setTournamentsCollection(Collection<Tournaments> tournamentsCollection) {
        this.tournamentsCollection = tournamentsCollection;
    }

    public Users getPlayer1Id() {
        return player1Id;
    }

    public void setPlayer1Id(Users player1Id) {
        this.player1Id = player1Id;
    }

    public Users getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(Users player2Id) {
        this.player2Id = player2Id;
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
        if (!(object instanceof Teams)) {
            return false;
        }
        Teams other = (Teams) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Teams[ id=" + id + " ]";
    }

}
