package vn.vetgo.gateway.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.vetgo.gateway.web.rest.TestUtil;

class VgOrderDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VgOrderDTO.class);
        VgOrderDTO vgOrderDTO1 = new VgOrderDTO();
        vgOrderDTO1.setId(1L);
        VgOrderDTO vgOrderDTO2 = new VgOrderDTO();
        assertThat(vgOrderDTO1).isNotEqualTo(vgOrderDTO2);
        vgOrderDTO2.setId(vgOrderDTO1.getId());
        assertThat(vgOrderDTO1).isEqualTo(vgOrderDTO2);
        vgOrderDTO2.setId(2L);
        assertThat(vgOrderDTO1).isNotEqualTo(vgOrderDTO2);
        vgOrderDTO1.setId(null);
        assertThat(vgOrderDTO1).isNotEqualTo(vgOrderDTO2);
    }
}
