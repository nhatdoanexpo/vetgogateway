package vn.vetgo.gateway.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link vn.vetgo.gateway.domain.Item} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ItemDTO implements Serializable {

    private Long id;

    private String name;

    private String code;

    private Long idCategory;

    private BigDecimal orgPrice;

    private String type;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public BigDecimal getOrgPrice() {
        return orgPrice;
    }

    public void setOrgPrice(BigDecimal orgPrice) {
        this.orgPrice = orgPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemDTO)) {
            return false;
        }

        ItemDTO itemDTO = (ItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, itemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemDTO{" +
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
