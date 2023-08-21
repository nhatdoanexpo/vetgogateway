package vn.vetgo.gateway.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A VgOrder.
 */
@Entity
@Table(name = "vg_order")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VgOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "id_customer")
    private Long idCustomer;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "total_amount", precision = 21, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "id_partner")
    private Long idPartner;

    @Column(name = "attributes")
    private String attributes;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VgOrder id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCustomer() {
        return this.idCustomer;
    }

    public VgOrder idCustomer(Long idCustomer) {
        this.setIdCustomer(idCustomer);
        return this;
    }

    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getOrderType() {
        return this.orderType;
    }

    public VgOrder orderType(String orderType) {
        this.setOrderType(orderType);
        return this;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Boolean getPaid() {
        return this.paid;
    }

    public VgOrder paid(Boolean paid) {
        this.setPaid(paid);
        return this;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }

    public VgOrder totalAmount(BigDecimal totalAmount) {
        this.setTotalAmount(totalAmount);
        return this;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public VgOrder createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getIdPartner() {
        return this.idPartner;
    }

    public VgOrder idPartner(Long idPartner) {
        this.setIdPartner(idPartner);
        return this;
    }

    public void setIdPartner(Long idPartner) {
        this.idPartner = idPartner;
    }

    public String getAttributes() {
        return this.attributes;
    }

    public VgOrder attributes(String attributes) {
        this.setAttributes(attributes);
        return this;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VgOrder)) {
            return false;
        }
        return id != null && id.equals(((VgOrder) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VgOrder{" +
            "id=" + getId() +
            ", idCustomer=" + getIdCustomer() +
            ", orderType='" + getOrderType() + "'" +
            ", paid='" + getPaid() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", idPartner=" + getIdPartner() +
            ", attributes='" + getAttributes() + "'" +
            "}";
    }
}
