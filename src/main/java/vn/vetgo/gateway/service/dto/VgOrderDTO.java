package vn.vetgo.gateway.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link vn.vetgo.gateway.domain.VgOrder} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VgOrderDTO implements Serializable {

    private Long id;

    private Long idCustomer;

    private String orderType;

    private Boolean paid;

    private BigDecimal totalAmount;

    private ZonedDateTime createdDate;

    private Long idPartner;

    private String attributes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getIdPartner() {
        return idPartner;
    }

    public void setIdPartner(Long idPartner) {
        this.idPartner = idPartner;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VgOrderDTO)) {
            return false;
        }

        VgOrderDTO vgOrderDTO = (VgOrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vgOrderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VgOrderDTO{" +
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
