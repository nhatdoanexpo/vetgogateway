package vn.vetgo.gateway.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionLogMapperTest {

    private TransactionLogMapper transactionLogMapper;

    @BeforeEach
    public void setUp() {
        transactionLogMapper = new TransactionLogMapperImpl();
    }
}
