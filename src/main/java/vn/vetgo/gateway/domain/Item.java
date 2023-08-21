package vn.vetgo.gateway.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * A Item.
 */
@Entity
@Table(name = "item")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "id_category")
    private Long idCategory;

    @Column(name = "org_price", precision = 21, scale = 2)
    private BigDecimal orgPrice;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Item id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Item name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public Item code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getIdCategory() {
        return this.idCategory;
    }

    public Item idCategory(Long idCategory) {
        this.setIdCategory(idCategory);
        return this;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public BigDecimal getOrgPrice() {
        return this.orgPrice;
    }

    public Item orgPrice(BigDecimal orgPrice) {
        this.setOrgPrice(orgPrice);
        return this;
    }

    public void setOrgPrice(BigDecimal orgPrice) {
        this.orgPrice = orgPrice;
    }

    public String getType() {
        return this.type;
    }

    public Item type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return this.status;
    }

    public Item status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        return id != null && id.equals(((Item) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Item{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", idCategory=" + getIdCategory() +
            ", orgPrice=" + getOrgPrice() +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
