package vn.vetgo.gateway.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import vn.vetgo.gateway.domain.Distributor;
import vn.vetgo.gateway.repository.DistributorRepository;
import vn.vetgo.gateway.service.dto.DistributorDTO;
import vn.vetgo.gateway.service.mapper.DistributorMapper;

/**
 * Integration tests for the {@link DistributorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DistributorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_AGENT = 1L;
    private static final Long UPDATED_ID_AGENT = 2L;

    private static final String ENTITY_API_URL = "/api/distributors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DistributorRepository distributorRepository;

    @Autowired
    private DistributorMapper distributorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDistributorMockMvc;

    private Distributor distributor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Distributor createEntity(EntityManager em) {
        Distributor distributor = new Distributor()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .address(DEFAULT_ADDRESS)
            .idAgent(DEFAULT_ID_AGENT);
        return distributor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Distributor createUpdatedEntity(EntityManager em) {
        Distributor distributor = new Distributor()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .idAgent(UPDATED_ID_AGENT);
        return distributor;
    }

    @BeforeEach
    public void initTest() {
        distributor = createEntity(em);
    }

    @Test
    @Transactional
    void createDistributor() throws Exception {
        int databaseSizeBeforeCreate = distributorRepository.findAll().size();
        // Create the Distributor
        DistributorDTO distributorDTO = distributorMapper.toDto(distributor);
        restDistributorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(distributorDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeCreate + 1);
        Distributor testDistributor = distributorList.get(distributorList.size() - 1);
        assertThat(testDistributor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDistributor.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDistributor.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testDistributor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDistributor.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testDistributor.getIdAgent()).isEqualTo(DEFAULT_ID_AGENT);
    }

    @Test
    @Transactional
    void createDistributorWithExistingId() throws Exception {
        // Create the Distributor with an existing ID
        distributor.setId(1L);
        DistributorDTO distributorDTO = distributorMapper.toDto(distributor);

        int databaseSizeBeforeCreate = distributorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistributorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(distributorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDistributors() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList
        restDistributorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(distributor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].idAgent").value(hasItem(DEFAULT_ID_AGENT.intValue())));
    }

    @Test
    @Transactional
    void getDistributor() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get the distributor
        restDistributorMockMvc
            .perform(get(ENTITY_API_URL_ID, distributor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(distributor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.idAgent").value(DEFAULT_ID_AGENT.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingDistributor() throws Exception {
        // Get the distributor
        restDistributorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDistributor() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        int databaseSizeBeforeUpdate = distributorRepository.findAll().size();

        // Update the distributor
        Distributor updatedDistributor = distributorRepository.findById(distributor.getId()).get();
        // Disconnect from session so that the updates on updatedDistributor are not directly saved in db
        em.detach(updatedDistributor);
        updatedDistributor
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .idAgent(UPDATED_ID_AGENT);
        DistributorDTO distributorDTO = distributorMapper.toDto(updatedDistributor);

        restDistributorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, distributorDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(distributorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeUpdate);
        Distributor testDistributor = distributorList.get(distributorList.size() - 1);
        assertThat(testDistributor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDistributor.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDistributor.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testDistributor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDistributor.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testDistributor.getIdAgent()).isEqualTo(UPDATED_ID_AGENT);
    }

    @Test
    @Transactional
    void putNonExistingDistributor() throws Exception {
        int databaseSizeBeforeUpdate = distributorRepository.findAll().size();
        distributor.setId(count.incrementAndGet());

        // Create the Distributor
        DistributorDTO distributorDTO = distributorMapper.toDto(distributor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistributorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, distributorDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(distributorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDistributor() throws Exception {
        int databaseSizeBeforeUpdate = distributorRepository.findAll().size();
        distributor.setId(count.incrementAndGet());

        // Create the Distributor
        DistributorDTO distributorDTO = distributorMapper.toDto(distributor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistributorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(distributorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDistributor() throws Exception {
        int databaseSizeBeforeUpdate = distributorRepository.findAll().size();
        distributor.setId(count.incrementAndGet());

        // Create the Distributor
        DistributorDTO distributorDTO = distributorMapper.toDto(distributor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistributorMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(distributorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDistributorWithPatch() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        int databaseSizeBeforeUpdate = distributorRepository.findAll().size();

        // Update the distributor using partial update
        Distributor partialUpdatedDistributor = new Distributor();
        partialUpdatedDistributor.setId(distributor.getId());

        partialUpdatedDistributor
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .idAgent(UPDATED_ID_AGENT);

        restDistributorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDistributor.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDistributor))
            )
            .andExpect(status().isOk());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeUpdate);
        Distributor testDistributor = distributorList.get(distributorList.size() - 1);
        assertThat(testDistributor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDistributor.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDistributor.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testDistributor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDistributor.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testDistributor.getIdAgent()).isEqualTo(UPDATED_ID_AGENT);
    }

    @Test
    @Transactional
    void fullUpdateDistributorWithPatch() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        int databaseSizeBeforeUpdate = distributorRepository.findAll().size();

        // Update the distributor using partial update
        Distributor partialUpdatedDistributor = new Distributor();
        partialUpdatedDistributor.setId(distributor.getId());

        partialUpdatedDistributor
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .idAgent(UPDATED_ID_AGENT);

        restDistributorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDistributor.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDistributor))
            )
            .andExpect(status().isOk());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeUpdate);
        Distributor testDistributor = distributorList.get(distributorList.size() - 1);
        assertThat(testDistributor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDistributor.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDistributor.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testDistributor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDistributor.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testDistributor.getIdAgent()).isEqualTo(UPDATED_ID_AGENT);
    }

    @Test
    @Transactional
    void patchNonExistingDistributor() throws Exception {
        int databaseSizeBeforeUpdate = distributorRepository.findAll().size();
        distributor.setId(count.incrementAndGet());

        // Create the Distributor
        DistributorDTO distributorDTO = distributorMapper.toDto(distributor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistributorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, distributorDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(distributorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDistributor() throws Exception {
        int databaseSizeBeforeUpdate = distributorRepository.findAll().size();
        distributor.setId(count.incrementAndGet());

        // Create the Distributor
        DistributorDTO distributorDTO = distributorMapper.toDto(distributor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistributorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(distributorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDistributor() throws Exception {
        int databaseSizeBeforeUpdate = distributorRepository.findAll().size();
        distributor.setId(count.incrementAndGet());

        // Create the Distributor
        DistributorDTO distributorDTO = distributorMapper.toDto(distributor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistributorMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(distributorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDistributor() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        int databaseSizeBeforeDelete = distributorRepository.findAll().size();

        // Delete the distributor
        restDistributorMockMvc
            .perform(delete(ENTITY_API_URL_ID, distributor.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
