package vn.vetgo.gateway.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link vn.vetgo.gateway.domain.OrderItem} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderItemDTO implements Serializable {

    private Long id;

    private Long idItem;

    private String itemName;

    private BigDecimal price;

    private BigDecimal qty;

    private BigDecimal totalPrice;

    private Long idVgOrder;

    private Double discount;

    private Long idItemAgent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdItem() {
        return idItem;
    }

    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getIdVgOrder() {
        return idVgOrder;
    }

    public void setIdVgOrder(Long idVgOrder) {
        this.idVgOrder = idVgOrder;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Long getIdItemAgent() {
        return idItemAgent;
    }

    public void setIdItemAgent(Long idItemAgent) {
        this.idItemAgent = idItemAgent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderItemDTO)) {
            return false;
        }

        OrderItemDTO orderItemDTO = (OrderItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderItemDTO{" +
            "id=" + getId() +
            ", idItem=" + getIdItem() +
            ", itemName='" + getItemName() + "'" +
            ", price=" + getPrice() +
            ", qty=" + getQty() +
            ", totalPrice=" + getTotalPrice() +
            ", idVgOrder=" + getIdVgOrder() +
            ", discount=" + getDiscount() +
            ", idItemAgent=" + getIdItemAgent() +
            "}";
    }
}
