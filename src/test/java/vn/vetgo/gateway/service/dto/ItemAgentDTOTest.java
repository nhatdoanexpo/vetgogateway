package vn.vetgo.gateway.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.vetgo.gateway.web.rest.TestUtil;

class ItemAgentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemAgentDTO.class);
        ItemAgentDTO itemAgentDTO1 = new ItemAgentDTO();
        itemAgentDTO1.setId(1L);
        ItemAgentDTO itemAgentDTO2 = new ItemAgentDTO();
        assertThat(itemAgentDTO1).isNotEqualTo(itemAgentDTO2);
        itemAgentDTO2.setId(itemAgentDTO1.getId());
        assertThat(itemAgentDTO1).isEqualTo(itemAgentDTO2);
        itemAgentDTO2.setId(2L);
        assertThat(itemAgentDTO1).isNotEqualTo(itemAgentDTO2);
        itemAgentDTO1.setId(null);
        assertThat(itemAgentDTO1).isNotEqualTo(itemAgentDTO2);
    }
}
