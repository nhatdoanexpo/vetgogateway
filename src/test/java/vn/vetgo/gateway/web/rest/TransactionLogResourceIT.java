package vn.vetgo.gateway.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.vetgo.gateway.web.rest.TestUtil.sameInstant;
import static vn.vetgo.gateway.web.rest.TestUtil.sameNumber;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import vn.vetgo.gateway.IntegrationTest;
import vn.vetgo.gateway.domain.TransactionLog;
import vn.vetgo.gateway.repository.TransactionLogRepository;
import vn.vetgo.gateway.service.dto.TransactionLogDTO;
import vn.vetgo.gateway.service.mapper.TransactionLogMapper;

/**
 * Integration tests for the {@link TransactionLogResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TransactionLogResourceIT {

    private static final Long DEFAULT_ID_ITEM_AGENT = 1L;
    private static final Long UPDATED_ID_ITEM_AGENT = 2L;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/transaction-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TransactionLogRepository transactionLogRepository;

    @Autowired
    private TransactionLogMapper transactionLogMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionLogMockMvc;

    private TransactionLog transactionLog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionLog createEntity(EntityManager em) {
        TransactionLog transactionLog = new TransactionLog()
            .idItemAgent(DEFAULT_ID_ITEM_AGENT)
            .price(DEFAULT_PRICE)
            .createdDate(DEFAULT_CREATED_DATE)
            .note(DEFAULT_NOTE);
        return transactionLog;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionLog createUpdatedEntity(EntityManager em) {
        TransactionLog transactionLog = new TransactionLog()
            .idItemAgent(UPDATED_ID_ITEM_AGENT)
            .price(UPDATED_PRICE)
            .createdDate(UPDATED_CREATED_DATE)
            .note(UPDATED_NOTE);
        return transactionLog;
    }

    @BeforeEach
    public void initTest() {
        transactionLog = createEntity(em);
    }

    @Test
    @Transactional
    void createTransactionLog() throws Exception {
        int databaseSizeBeforeCreate = transactionLogRepository.findAll().size();
        // Create the TransactionLog
        TransactionLogDTO transactionLogDTO = transactionLogMapper.toDto(transactionLog);
        restTransactionLogMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionLogDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TransactionLog in the database
        List<TransactionLog> transactionLogList = transactionLogRepository.findAll();
        assertThat(transactionLogList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionLog testTransactionLog = transactionLogList.get(transactionLogList.size() - 1);
        assertThat(testTransactionLog.getIdItemAgent()).isEqualTo(DEFAULT_ID_ITEM_AGENT);
        assertThat(testTransactionLog.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testTransactionLog.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTransactionLog.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    void createTransactionLogWithExistingId() throws Exception {
        // Create the TransactionLog with an existing ID
        transactionLog.setId(1L);
        TransactionLogDTO transactionLogDTO = transactionLogMapper.toDto(transactionLog);

        int databaseSizeBeforeCreate = transactionLogRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionLogMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionLog in the database
        List<TransactionLog> transactionLogList = transactionLogRepository.findAll();
        assertThat(transactionLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTransactionLogs() throws Exception {
        // Initialize the database
        transactionLogRepository.saveAndFlush(transactionLog);

        // Get all the transactionLogList
        restTransactionLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].idItemAgent").value(hasItem(DEFAULT_ID_ITEM_AGENT.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }

    @Test
    @Transactional
    void getTransactionLog() throws Exception {
        // Initialize the database
        transactionLogRepository.saveAndFlush(transactionLog);

        // Get the transactionLog
        restTransactionLogMockMvc
            .perform(get(ENTITY_API_URL_ID, transactionLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transactionLog.getId().intValue()))
            .andExpect(jsonPath("$.idItemAgent").value(DEFAULT_ID_ITEM_AGENT.intValue()))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }

    @Test
    @Transactional
    void getNonExistingTransactionLog() throws Exception {
        // Get the transactionLog
        restTransactionLogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTransactionLog() throws Exception {
        // Initialize the database
        transactionLogRepository.saveAndFlush(transactionLog);

        int databaseSizeBeforeUpdate = transactionLogRepository.findAll().size();

        // Update the transactionLog
        TransactionLog updatedTransactionLog = transactionLogRepository.findById(transactionLog.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionLog are not directly saved in db
        em.detach(updatedTransactionLog);
        updatedTransactionLog.idItemAgent(UPDATED_ID_ITEM_AGENT).price(UPDATED_PRICE).createdDate(UPDATED_CREATED_DATE).note(UPDATED_NOTE);
        TransactionLogDTO transactionLogDTO = transactionLogMapper.toDto(updatedTransactionLog);

        restTransactionLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transactionLogDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionLogDTO))
            )
            .andExpect(status().isOk());

        // Validate the TransactionLog in the database
        List<TransactionLog> transactionLogList = transactionLogRepository.findAll();
        assertThat(transactionLogList).hasSize(databaseSizeBeforeUpdate);
        TransactionLog testTransactionLog = transactionLogList.get(transactionLogList.size() - 1);
        assertThat(testTransactionLog.getIdItemAgent()).isEqualTo(UPDATED_ID_ITEM_AGENT);
        assertThat(testTransactionLog.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testTransactionLog.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTransactionLog.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void putNonExistingTransactionLog() throws Exception {
        int databaseSizeBeforeUpdate = transactionLogRepository.findAll().size();
        transactionLog.setId(count.incrementAndGet());

        // Create the TransactionLog
        TransactionLogDTO transactionLogDTO = transactionLogMapper.toDto(transactionLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transactionLogDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionLog in the database
        List<TransactionLog> transactionLogList = transactionLogRepository.findAll();
        assertThat(transactionLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransactionLog() throws Exception {
        int databaseSizeBeforeUpdate = transactionLogRepository.findAll().size();
        transactionLog.setId(count.incrementAndGet());

        // Create the TransactionLog
        TransactionLogDTO transactionLogDTO = transactionLogMapper.toDto(transactionLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionLog in the database
        List<TransactionLog> transactionLogList = transactionLogRepository.findAll();
        assertThat(transactionLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransactionLog() throws Exception {
        int databaseSizeBeforeUpdate = transactionLogRepository.findAll().size();
        transactionLog.setId(count.incrementAndGet());

        // Create the TransactionLog
        TransactionLogDTO transactionLogDTO = transactionLogMapper.toDto(transactionLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionLogMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionLogDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransactionLog in the database
        List<TransactionLog> transactionLogList = transactionLogRepository.findAll();
        assertThat(transactionLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransactionLogWithPatch() throws Exception {
        // Initialize the database
        transactionLogRepository.saveAndFlush(transactionLog);

        int databaseSizeBeforeUpdate = transactionLogRepository.findAll().size();

        // Update the transactionLog using partial update
        TransactionLog partialUpdatedTransactionLog = new TransactionLog();
        partialUpdatedTransactionLog.setId(transactionLog.getId());

        partialUpdatedTransactionLog.idItemAgent(UPDATED_ID_ITEM_AGENT).price(UPDATED_PRICE);

        restTransactionLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransactionLog.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransactionLog))
            )
            .andExpect(status().isOk());

        // Validate the TransactionLog in the database
        List<TransactionLog> transactionLogList = transactionLogRepository.findAll();
        assertThat(transactionLogList).hasSize(databaseSizeBeforeUpdate);
        TransactionLog testTransactionLog = transactionLogList.get(transactionLogList.size() - 1);
        assertThat(testTransactionLog.getIdItemAgent()).isEqualTo(UPDATED_ID_ITEM_AGENT);
        assertThat(testTransactionLog.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testTransactionLog.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTransactionLog.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    void fullUpdateTransactionLogWithPatch() throws Exception {
        // Initialize the database
        transactionLogRepository.saveAndFlush(transactionLog);

        int databaseSizeBeforeUpdate = transactionLogRepository.findAll().size();

        // Update the transactionLog using partial update
        TransactionLog partialUpdatedTransactionLog = new TransactionLog();
        partialUpdatedTransactionLog.setId(transactionLog.getId());

        partialUpdatedTransactionLog
            .idItemAgent(UPDATED_ID_ITEM_AGENT)
            .price(UPDATED_PRICE)
            .createdDate(UPDATED_CREATED_DATE)
            .note(UPDATED_NOTE);

        restTransactionLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransactionLog.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransactionLog))
            )
            .andExpect(status().isOk());

        // Validate the TransactionLog in the database
        List<TransactionLog> transactionLogList = transactionLogRepository.findAll();
        assertThat(transactionLogList).hasSize(databaseSizeBeforeUpdate);
        TransactionLog testTransactionLog = transactionLogList.get(transactionLogList.size() - 1);
        assertThat(testTransactionLog.getIdItemAgent()).isEqualTo(UPDATED_ID_ITEM_AGENT);
        assertThat(testTransactionLog.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testTransactionLog.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTransactionLog.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void patchNonExistingTransactionLog() throws Exception {
        int databaseSizeBeforeUpdate = transactionLogRepository.findAll().size();
        transactionLog.setId(count.incrementAndGet());

        // Create the TransactionLog
        TransactionLogDTO transactionLogDTO = transactionLogMapper.toDto(transactionLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transactionLogDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transactionLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionLog in the database
        List<TransactionLog> transactionLogList = transactionLogRepository.findAll();
        assertThat(transactionLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransactionLog() throws Exception {
        int databaseSizeBeforeUpdate = transactionLogRepository.findAll().size();
        transactionLog.setId(count.incrementAndGet());

        // Create the TransactionLog
        TransactionLogDTO transactionLogDTO = transactionLogMapper.toDto(transactionLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transactionLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionLog in the database
        List<TransactionLog> transactionLogList = transactionLogRepository.findAll();
        assertThat(transactionLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransactionLog() throws Exception {
        int databaseSizeBeforeUpdate = transactionLogRepository.findAll().size();
        transactionLog.setId(count.incrementAndGet());

        // Create the TransactionLog
        TransactionLogDTO transactionLogDTO = transactionLogMapper.toDto(transactionLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionLogMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transactionLogDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransactionLog in the database
        List<TransactionLog> transactionLogList = transactionLogRepository.findAll();
        assertThat(transactionLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransactionLog() throws Exception {
        // Initialize the database
        transactionLogRepository.saveAndFlush(transactionLog);

        int databaseSizeBeforeDelete = transactionLogRepository.findAll().size();

        // Delete the transactionLog
        restTransactionLogMockMvc
            .perform(delete(ENTITY_API_URL_ID, transactionLog.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionLog> transactionLogList = transactionLogRepository.findAll();
        assertThat(transactionLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
