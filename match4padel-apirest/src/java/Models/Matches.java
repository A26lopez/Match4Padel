package Models;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "matches")
@NamedQueries({
    @NamedQuery(name = "Matches.findAll", query = "SELECT m FROM Matches m"),
    @NamedQuery(name = "Matches.findById", query = "SELECT m FROM Matches m WHERE m.id = :id"),
    @NamedQuery(name = "Matches.findByStatus", query = "SELECT m FROM Matches m WHERE m.status = :status"),
    @NamedQuery(name = "Matches.findByLevel", query = "SELECT m FROM Matches m WHERE m.level = :level")})
public class Matches implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "level")
    private String level;
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    @OneToOne(optional = false)
    private Reservations reservationId;
    @JoinColumn(name = "tournament_id", referencedColumnName = "id")
    @ManyToOne
    private Tournaments tournamentId;
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users ownerId;
    @JoinColumn(name = "player_1_id", referencedColumnName = "id")
    @ManyToOne
    private Users player1Id;
    @JoinColumn(name = "player_2_id", referencedColumnName = "id")
    @ManyToOne
    private Users player2Id;
    @JoinColumn(name = "player_3_id", referencedColumnName = "id")
    @ManyToOne
    private Users player3Id;

    public Matches() {
    }

    public Matches(Integer id) {
        this.id = id;
    }

    public Matches(Integer id, String status, String level) {
        this.id = id;
        this.status = status;
        this.level = level;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Reservations getReservationId() {
        return reservationId;
    }

    public void setReservationId(Reservations reservationId) {
        this.reservationId = reservationId;
    }

    public Tournaments getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Tournaments tournamentId) {
        this.tournamentId = tournamentId;
    }

    public Users getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Users ownerId) {
        this.ownerId = ownerId;
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

    public Users getPlayer3Id() {
        return player3Id;
    }

    public void setPlayer3Id(Users player3Id) {
        this.player3Id = player3Id;
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
        if (!(object instanceof Matches)) {
            return false;
        }
        Matches other = (Matches) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Matches[ id=" + id + " ]";
    }

}
