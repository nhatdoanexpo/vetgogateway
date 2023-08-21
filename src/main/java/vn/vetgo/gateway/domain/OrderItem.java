package vn.vetgo.gateway.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * A OrderItem.
 */
@Entity
@Table(name = "order_item")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "id_item")
    private Long idItem;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "qty", precision = 21, scale = 2)
    private BigDecimal qty;

    @Column(name = "total_price", precision = 21, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "id_vg_order")
    private Long idVgOrder;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "id_item_agent")
    private Long idItemAgent;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrderItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdItem() {
        return this.idItem;
    }

    public OrderItem idItem(Long idItem) {
        this.setIdItem(idItem);
        return this;
    }

    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

    public String getItemName() {
        return this.itemName;
    }

    public OrderItem itemName(String itemName) {
        this.setItemName(itemName);
        return this;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public OrderItem price(BigDecimal price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQty() {
        return this.qty;
    }

    public OrderItem qty(BigDecimal qty) {
        this.setQty(qty);
        return this;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public OrderItem totalPrice(BigDecimal totalPrice) {
        this.setTotalPrice(totalPrice);
        return this;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getIdVgOrder() {
        return this.idVgOrder;
    }

    public OrderItem idVgOrder(Long idVgOrder) {
        this.setIdVgOrder(idVgOrder);
        return this;
    }

    public void setIdVgOrder(Long idVgOrder) {
        this.idVgOrder = idVgOrder;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public OrderItem discount(Double discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Long getIdItemAgent() {
        return this.idItemAgent;
    }

    public OrderItem idItemAgent(Long idItemAgent) {
        this.setIdItemAgent(idItemAgent);
        return this;
    }

    public void setIdItemAgent(Long idItemAgent) {
        this.idItemAgent = idItemAgent;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderItem)) {
            return false;
        }
        return id != null && id.equals(((OrderItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderItem{" +
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
