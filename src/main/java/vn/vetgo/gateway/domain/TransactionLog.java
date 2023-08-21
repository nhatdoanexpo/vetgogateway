package vn.vetgo.gateway.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A TransactionLog.
 */
@Entity
@Table(name = "transaction_log")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransactionLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "id_item_agent")
    private Long idItemAgent;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "note")
    private String note;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TransactionLog id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdItemAgent() {
        return this.idItemAgent;
    }

    public TransactionLog idItemAgent(Long idItemAgent) {
        this.setIdItemAgent(idItemAgent);
        return this;
    }

    public void setIdItemAgent(Long idItemAgent) {
        this.idItemAgent = idItemAgent;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public TransactionLog price(BigDecimal price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public TransactionLog createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getNote() {
        return this.note;
    }

    public TransactionLog note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionLog)) {
            return false;
        }
        return id != null && id.equals(((TransactionLog) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionLog{" +
            "id=" + getId() +
            ", idItemAgent=" + getIdItemAgent() +
            ", price=" + getPrice() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
