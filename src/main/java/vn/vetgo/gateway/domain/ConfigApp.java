package vn.vetgo.gateway.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A ConfigApp.
 */
@Entity
@Table(name = "config_app")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConfigApp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "firebase")
    private String firebase;

    @Column(name = "sheet_id")
    private String sheetId;

    @Column(name = "status")
    private String status;

    @Column(name = "id_customer")
    private Long idCustomer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ConfigApp id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirebase() {
        return this.firebase;
    }

    public ConfigApp firebase(String firebase) {
        this.setFirebase(firebase);
        return this;
    }

    public void setFirebase(String firebase) {
        this.firebase = firebase;
    }

    public String getSheetId() {
        return this.sheetId;
    }

    public ConfigApp sheetId(String sheetId) {
        this.setSheetId(sheetId);
        return this;
    }

    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }

    public String getStatus() {
        return this.status;
    }

    public ConfigApp status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getIdCustomer() {
        return this.idCustomer;
    }

    public ConfigApp idCustomer(Long idCustomer) {
        this.setIdCustomer(idCustomer);
        return this;
    }

    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConfigApp)) {
            return false;
        }
        return id != null && id.equals(((ConfigApp) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConfigApp{" +
            "id=" + getId() +
            ", firebase='" + getFirebase() + "'" +
            ", sheetId='" + getSheetId() + "'" +
            ", status='" + getStatus() + "'" +
            ", idCustomer=" + getIdCustomer() +
            "}";
    }
}
