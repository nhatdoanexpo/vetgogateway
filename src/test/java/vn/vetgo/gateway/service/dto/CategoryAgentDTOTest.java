package vn.vetgo.gateway.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.vetgo.gateway.web.rest.TestUtil;

class CategoryAgentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryAgentDTO.class);
        CategoryAgentDTO categoryAgentDTO1 = new CategoryAgentDTO();
        categoryAgentDTO1.setId(1L);
        CategoryAgentDTO categoryAgentDTO2 = new CategoryAgentDTO();
        assertThat(categoryAgentDTO1).isNotEqualTo(categoryAgentDTO2);
        categoryAgentDTO2.setId(categoryAgentDTO1.getId());
        assertThat(categoryAgentDTO1).isEqualTo(categoryAgentDTO2);
        categoryAgentDTO2.setId(2L);
        assertThat(categoryAgentDTO1).isNotEqualTo(categoryAgentDTO2);
        categoryAgentDTO1.setId(null);
        assertThat(categoryAgentDTO1).isNotEqualTo(categoryAgentDTO2);
    }
}
