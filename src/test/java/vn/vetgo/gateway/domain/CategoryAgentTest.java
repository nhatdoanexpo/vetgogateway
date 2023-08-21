package vn.vetgo.gateway.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.vetgo.gateway.web.rest.TestUtil;

class CategoryAgentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryAgent.class);
        CategoryAgent categoryAgent1 = new CategoryAgent();
        categoryAgent1.setId(1L);
        CategoryAgent categoryAgent2 = new CategoryAgent();
        categoryAgent2.setId(categoryAgent1.getId());
        assertThat(categoryAgent1).isEqualTo(categoryAgent2);
        categoryAgent2.setId(2L);
        assertThat(categoryAgent1).isNotEqualTo(categoryAgent2);
        categoryAgent1.setId(null);
        assertThat(categoryAgent1).isNotEqualTo(categoryAgent2);
    }
}
