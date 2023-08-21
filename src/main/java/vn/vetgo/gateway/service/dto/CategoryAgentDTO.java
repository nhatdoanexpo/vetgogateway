package vn.vetgo.gateway.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link vn.vetgo.gateway.domain.CategoryAgent} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoryAgentDTO implements Serializable {

    private Long id;

    private Long idAgent;

    private Long idCategory;

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

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryAgentDTO)) {
            return false;
        }

        CategoryAgentDTO categoryAgentDTO = (CategoryAgentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, categoryAgentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryAgentDTO{" +
            "id=" + getId() +
            ", idAgent=" + getIdAgent() +
            ", idCategory=" + getIdCategory() +
            "}";
    }
}
