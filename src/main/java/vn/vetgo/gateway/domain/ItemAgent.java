package vn.vetgo.gateway.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * A ItemAgent.
 */
@Entity
@Table(name = "item_agent")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ItemAgent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "id_agent")
    private Long idAgent;

    @Column(name = "id_item")
    private Long idItem;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "status")
    private String status;

    @Column(name = "id_config_app")
    private Long idConfigApp;

    @Column(name = "paid")
    private Boolean paid;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ItemAgent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAgent() {
        return this.idAgent;
    }

    public ItemAgent idAgent(Long idAgent) {
        this.setIdAgent(idAgent);
        return this;
    }

    public void setIdAgent(Long idAgent) {
        this.idAgent = idAgent;
    }

    public Long getIdItem() {
        return this.idItem;
    }

    public ItemAgent idItem(Long idItem) {
        this.setIdItem(idItem);
        return this;
    }

    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public ItemAgent price(BigDecimal price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return this.status;
    }

    public ItemAgent status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getIdConfigApp() {
        return this.idConfigApp;
    }

    public ItemAgent idConfigApp(Long idConfigApp) {
        this.setIdConfigApp(idConfigApp);
        return this;
    }

    public void setIdConfigApp(Long idConfigApp) {
        this.idConfigApp = idConfigApp;
    }

    public Boolean getPaid() {
        return this.paid;
    }

    public ItemAgent paid(Boolean paid) {
        this.setPaid(paid);
        return this;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemAgent)) {
            return false;
        }
        return id != null && id.equals(((ItemAgent) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemAgent{" +
            "id=" + getId() +
            ", idAgent=" + getIdAgent() +
            ", idItem=" + getIdItem() +
            ", price=" + getPrice() +
            ", status='" + getStatus() + "'" +
            ", idConfigApp=" + getIdConfigApp() +
            ", paid='" + getPaid() + "'" +
            "}";
    }
}
