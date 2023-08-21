package vn.vetgo.gateway.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link vn.vetgo.gateway.domain.ConfigApp} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConfigAppDTO implements Serializable {

    private Long id;

    private String firebase;

    private String sheetId;

    private String status;

    private Long idCustomer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirebase() {
        return firebase;
    }

    public void setFirebase(String firebase) {
        this.firebase = firebase;
    }

    public String getSheetId() {
        return sheetId;
    }

    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConfigAppDTO)) {
            return false;
        }

        ConfigAppDTO configAppDTO = (ConfigAppDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, configAppDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConfigAppDTO{" +
            "id=" + getId() +
            ", firebase='" + getFirebase() + "'" +
            ", sheetId='" + getSheetId() + "'" +
            ", status='" + getStatus() + "'" +
            ", idCustomer=" + getIdCustomer() +
            "}";
    }
}
