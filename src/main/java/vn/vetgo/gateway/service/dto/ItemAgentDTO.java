package vn.vetgo.gateway.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link vn.vetgo.gateway.domain.ItemAgent} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ItemAgentDTO implements Serializable {

    private Long id;

    private Long idAgent;

    private Long idItem;

    private BigDecimal price;

    private String status;

    private Long idConfigApp;

    private Boolean paid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAgent() {
        return idAgent;
    }

    public void setIdAgent(Long idAgent) {
        this.idAgent = idAgent;
    }

    public Long getIdItem() {
        return idItem;
    }

    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getIdConfigApp() {
        return idConfigApp;
    }

    public void setIdConfigApp(Long idConfigApp) {
        this.idConfigApp = idConfigApp;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemAgentDTO)) {
            return false;
        }

        ItemAgentDTO itemAgentDTO = (ItemAgentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, itemAgentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemAgentDTO{" +
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
