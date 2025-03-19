package Models;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "contact_info")
@NamedQueries({
    @NamedQuery(name = "ContactInfo.findAll", query = "SELECT c FROM ContactInfo c"),
    @NamedQuery(name = "ContactInfo.findById", query = "SELECT c FROM ContactInfo c WHERE c.id = :id"),
    @NamedQuery(name = "ContactInfo.findByFirstName", query = "SELECT c FROM ContactInfo c WHERE c.firstName = :firstName"),
    @NamedQuery(name = "ContactInfo.findByLastName", query = "SELECT c FROM ContactInfo c WHERE c.lastName = :lastName"),
    @NamedQuery(name = "ContactInfo.findByDni", query = "SELECT c FROM ContactInfo c WHERE c.dni = :dni"),
    @NamedQuery(name = "ContactInfo.findByEmail", query = "SELECT c FROM ContactInfo c WHERE c.email = :email"),
    @NamedQuery(name = "ContactInfo.findByPhoneNumber", query = "SELECT c FROM ContactInfo c WHERE c.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "ContactInfo.findByBirthDate", query = "SELECT c FROM ContactInfo c WHERE c.birthDate = :birthDate"),
    @NamedQuery(name = "ContactInfo.findByCity", query = "SELECT c FROM ContactInfo c WHERE c.city = :city"),
    @NamedQuery(name = "ContactInfo.findByPostalCode", query = "SELECT c FROM ContactInfo c WHERE c.postalCode = :postalCode"),
    @NamedQuery(name = "ContactInfo.findByCountry", query = "SELECT c FROM ContactInfo c WHERE c.country = :country")})
public class ContactInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "first_name")
    private String firstName;
    @Basic(optional = false)
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "dni")
    private String dni;
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "phone_number")
    private String phoneNumber;
    @Basic(optional = false)
    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Basic(optional = false)
    @Lob
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @Column(name = "city")
    private String city;
    @Basic(optional = false)
    @Column(name = "postal_code")
    private String postalCode;
    @Basic(optional = false)
    @Column(name = "country")
    private String country;
    @OneToOne(mappedBy = "contactInfoId")
    private Workers workers;
    @OneToOne(mappedBy = "contactInfoId")
    private Users users;

    public ContactInfo() {
    }

    public ContactInfo(Integer id) {
        this.id = id;
    }

    public ContactInfo(Integer id, String firstName, String lastName, String phoneNumber, Date birthDate, String address, String city, String postalCode, String country) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
        if (!(object instanceof ContactInfo)) {
            return false;
        }
        ContactInfo other = (ContactInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.ContactInfo[ id=" + id + " ]";
    }

}
