package vn.vetgo.gateway.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A CategoryAgent.
 */
@Entity
@Table(name = "category_agent")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoryAgent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "id_agent")
    private Long idAgent;

    @Column(name = "id_category")
    private Long idCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CategoryAgent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAgent() {
        return this.idAgent;
    }

    public CategoryAgent idAgent(Long idAgent) {
        this.setIdAgent(idAgent);
        return this;
    }

    public void setIdAgent(Long idAgent) {
        this.idAgent = idAgent;
    }

    public Long getIdCategory() {
        return this.idCategory;
    }

    public CategoryAgent idCategory(Long idCategory) {
        this.setIdCategory(idCategory);
        return this;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryAgent)) {
            return false;
        }
        return id != null && id.equals(((CategoryAgent) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryAgent{" +
            "id=" + getId() +
            ", idAgent=" + getIdAgent() +
            ", idCategory=" + getIdCategory() +
            "}";
    }
}
