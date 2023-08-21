package vn.vetgo.gateway.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link vn.vetgo.gateway.domain.Distributor} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DistributorDTO implements Serializable {

    private Long id;

    private String name;

    private String code;

    private String phone;

    private String email;

    private String address;

    private Long idAgent;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getIdAgent() {
        return idAgent;
    }

    public void setIdAgent(Long idAgent) {
        this.idAgent = idAgent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DistributorDTO)) {
            return false;
        }

        DistributorDTO distributorDTO = (DistributorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, distributorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DistributorDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", address='" + getAddress() + "'" +
            ", idAgent=" + getIdAgent() +
            "}";
    }
}
