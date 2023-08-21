package vn.vetgo.gateway.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link vn.vetgo.gateway.domain.TransactionLog} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransactionLogDTO implements Serializable {

    private Long id;

    private Long idItemAgent;

    private BigDecimal price;

    private ZonedDateTime createdDate;

    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdItemAgent() {
        return idItemAgent;
    }

    public void setIdItemAgent(Long idItemAgent) {
        this.idItemAgent = idItemAgent;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionLogDTO)) {
            return false;
        }

        TransactionLogDTO transactionLogDTO = (TransactionLogDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transactionLogDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionLogDTO{" +
            "id=" + getId() +
            ", idItemAgent=" + getIdItemAgent() +
            ", price=" + getPrice() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
