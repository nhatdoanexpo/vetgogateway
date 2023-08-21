package vn.vetgo.gateway.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.vetgo.gateway.web.rest.TestUtil;

class TransactionLogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionLog.class);
        TransactionLog transactionLog1 = new TransactionLog();
        transactionLog1.setId(1L);
        TransactionLog transactionLog2 = new TransactionLog();
        transactionLog2.setId(transactionLog1.getId());
        assertThat(transactionLog1).isEqualTo(transactionLog2);
        transactionLog2.setId(2L);
        assertThat(transactionLog1).isNotEqualTo(transactionLog2);
        transactionLog1.setId(null);
        assertThat(transactionLog1).isNotEqualTo(transactionLog2);
    }
}
