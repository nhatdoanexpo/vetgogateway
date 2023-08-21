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
import vn.vetgo.gateway.domain.ConfigApp;
import vn.vetgo.gateway.repository.ConfigAppRepository;
import vn.vetgo.gateway.service.dto.ConfigAppDTO;
import vn.vetgo.gateway.service.mapper.ConfigAppMapper;

/**
 * Integration tests for the {@link ConfigAppResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConfigAppResourceIT {

    private static final String DEFAULT_FIREBASE = "AAAAAAAAAA";
    private static final String UPDATED_FIREBASE = "BBBBBBBBBB";

    private static final String DEFAULT_SHEET_ID = "AAAAAAAAAA";
    private static final String UPDATED_SHEET_ID = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_CUSTOMER = 1L;
    private static final Long UPDATED_ID_CUSTOMER = 2L;

    private static final String ENTITY_API_URL = "/api/config-apps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConfigAppRepository configAppRepository;

    @Autowired
    private ConfigAppMapper configAppMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConfigAppMockMvc;

    private ConfigApp configApp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConfigApp createEntity(EntityManager em) {
        ConfigApp configApp = new ConfigApp()
            .firebase(DEFAULT_FIREBASE)
            .sheetId(DEFAULT_SHEET_ID)
            .status(DEFAULT_STATUS)
            .idCustomer(DEFAULT_ID_CUSTOMER);
        return configApp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConfigApp createUpdatedEntity(EntityManager em) {
        ConfigApp configApp = new ConfigApp()
            .firebase(UPDATED_FIREBASE)
            .sheetId(UPDATED_SHEET_ID)
            .status(UPDATED_STATUS)
            .idCustomer(UPDATED_ID_CUSTOMER);
        return configApp;
    }

    @BeforeEach
    public void initTest() {
        configApp = createEntity(em);
    }

    @Test
    @Transactional
    void createConfigApp() throws Exception {
        int databaseSizeBeforeCreate = configAppRepository.findAll().size();
        // Create the ConfigApp
        ConfigAppDTO configAppDTO = configAppMapper.toDto(configApp);
        restConfigAppMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configAppDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ConfigApp in the database
        List<ConfigApp> configAppList = configAppRepository.findAll();
        assertThat(configAppList).hasSize(databaseSizeBeforeCreate + 1);
        ConfigApp testConfigApp = configAppList.get(configAppList.size() - 1);
        assertThat(testConfigApp.getFirebase()).isEqualTo(DEFAULT_FIREBASE);
        assertThat(testConfigApp.getSheetId()).isEqualTo(DEFAULT_SHEET_ID);
        assertThat(testConfigApp.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testConfigApp.getIdCustomer()).isEqualTo(DEFAULT_ID_CUSTOMER);
    }

    @Test
    @Transactional
    void createConfigAppWithExistingId() throws Exception {
        // Create the ConfigApp with an existing ID
        configApp.setId(1L);
        ConfigAppDTO configAppDTO = configAppMapper.toDto(configApp);

        int databaseSizeBeforeCreate = configAppRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfigAppMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configAppDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfigApp in the database
        List<ConfigApp> configAppList = configAppRepository.findAll();
        assertThat(configAppList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConfigApps() throws Exception {
        // Initialize the database
        configAppRepository.saveAndFlush(configApp);

        // Get all the configAppList
        restConfigAppMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(configApp.getId().intValue())))
            .andExpect(jsonPath("$.[*].firebase").value(hasItem(DEFAULT_FIREBASE)))
            .andExpect(jsonPath("$.[*].sheetId").value(hasItem(DEFAULT_SHEET_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].idCustomer").value(hasItem(DEFAULT_ID_CUSTOMER.intValue())));
    }

    @Test
    @Transactional
    void getConfigApp() throws Exception {
        // Initialize the database
        configAppRepository.saveAndFlush(configApp);

        // Get the configApp
        restConfigAppMockMvc
            .perform(get(ENTITY_API_URL_ID, configApp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(configApp.getId().intValue()))
            .andExpect(jsonPath("$.firebase").value(DEFAULT_FIREBASE))
            .andExpect(jsonPath("$.sheetId").value(DEFAULT_SHEET_ID))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.idCustomer").value(DEFAULT_ID_CUSTOMER.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingConfigApp() throws Exception {
        // Get the configApp
        restConfigAppMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingConfigApp() throws Exception {
        // Initialize the database
        configAppRepository.saveAndFlush(configApp);

        int databaseSizeBeforeUpdate = configAppRepository.findAll().size();

        // Update the configApp
        ConfigApp updatedConfigApp = configAppRepository.findById(configApp.getId()).get();
        // Disconnect from session so that the updates on updatedConfigApp are not directly saved in db
        em.detach(updatedConfigApp);
        updatedConfigApp.firebase(UPDATED_FIREBASE).sheetId(UPDATED_SHEET_ID).status(UPDATED_STATUS).idCustomer(UPDATED_ID_CUSTOMER);
        ConfigAppDTO configAppDTO = configAppMapper.toDto(updatedConfigApp);

        restConfigAppMockMvc
            .perform(
                put(ENTITY_API_URL_ID, configAppDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configAppDTO))
            )
            .andExpect(status().isOk());

        // Validate the ConfigApp in the database
        List<ConfigApp> configAppList = configAppRepository.findAll();
        assertThat(configAppList).hasSize(databaseSizeBeforeUpdate);
        ConfigApp testConfigApp = configAppList.get(configAppList.size() - 1);
        assertThat(testConfigApp.getFirebase()).isEqualTo(UPDATED_FIREBASE);
        assertThat(testConfigApp.getSheetId()).isEqualTo(UPDATED_SHEET_ID);
        assertThat(testConfigApp.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testConfigApp.getIdCustomer()).isEqualTo(UPDATED_ID_CUSTOMER);
    }

    @Test
    @Transactional
    void putNonExistingConfigApp() throws Exception {
        int databaseSizeBeforeUpdate = configAppRepository.findAll().size();
        configApp.setId(count.incrementAndGet());

        // Create the ConfigApp
        ConfigAppDTO configAppDTO = configAppMapper.toDto(configApp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfigAppMockMvc
            .perform(
                put(ENTITY_API_URL_ID, configAppDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configAppDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfigApp in the database
        List<ConfigApp> configAppList = configAppRepository.findAll();
        assertThat(configAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConfigApp() throws Exception {
        int databaseSizeBeforeUpdate = configAppRepository.findAll().size();
        configApp.setId(count.incrementAndGet());

        // Create the ConfigApp
        ConfigAppDTO configAppDTO = configAppMapper.toDto(configApp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigAppMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configAppDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfigApp in the database
        List<ConfigApp> configAppList = configAppRepository.findAll();
        assertThat(configAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConfigApp() throws Exception {
        int databaseSizeBeforeUpdate = configAppRepository.findAll().size();
        configApp.setId(count.incrementAndGet());

        // Create the ConfigApp
        ConfigAppDTO configAppDTO = configAppMapper.toDto(configApp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigAppMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configAppDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConfigApp in the database
        List<ConfigApp> configAppList = configAppRepository.findAll();
        assertThat(configAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConfigAppWithPatch() throws Exception {
        // Initialize the database
        configAppRepository.saveAndFlush(configApp);

        int databaseSizeBeforeUpdate = configAppRepository.findAll().size();

        // Update the configApp using partial update
        ConfigApp partialUpdatedConfigApp = new ConfigApp();
        partialUpdatedConfigApp.setId(configApp.getId());

        partialUpdatedConfigApp.firebase(UPDATED_FIREBASE);

        restConfigAppMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfigApp.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfigApp))
            )
            .andExpect(status().isOk());

        // Validate the ConfigApp in the database
        List<ConfigApp> configAppList = configAppRepository.findAll();
        assertThat(configAppList).hasSize(databaseSizeBeforeUpdate);
        ConfigApp testConfigApp = configAppList.get(configAppList.size() - 1);
        assertThat(testConfigApp.getFirebase()).isEqualTo(UPDATED_FIREBASE);
        assertThat(testConfigApp.getSheetId()).isEqualTo(DEFAULT_SHEET_ID);
        assertThat(testConfigApp.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testConfigApp.getIdCustomer()).isEqualTo(DEFAULT_ID_CUSTOMER);
    }

    @Test
    @Transactional
    void fullUpdateConfigAppWithPatch() throws Exception {
        // Initialize the database
        configAppRepository.saveAndFlush(configApp);

        int databaseSizeBeforeUpdate = configAppRepository.findAll().size();

        // Update the configApp using partial update
        ConfigApp partialUpdatedConfigApp = new ConfigApp();
        partialUpdatedConfigApp.setId(configApp.getId());

        partialUpdatedConfigApp.firebase(UPDATED_FIREBASE).sheetId(UPDATED_SHEET_ID).status(UPDATED_STATUS).idCustomer(UPDATED_ID_CUSTOMER);

        restConfigAppMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfigApp.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfigApp))
            )
            .andExpect(status().isOk());

        // Validate the ConfigApp in the database
        List<ConfigApp> configAppList = configAppRepository.findAll();
        assertThat(configAppList).hasSize(databaseSizeBeforeUpdate);
        ConfigApp testConfigApp = configAppList.get(configAppList.size() - 1);
        assertThat(testConfigApp.getFirebase()).isEqualTo(UPDATED_FIREBASE);
        assertThat(testConfigApp.getSheetId()).isEqualTo(UPDATED_SHEET_ID);
        assertThat(testConfigApp.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testConfigApp.getIdCustomer()).isEqualTo(UPDATED_ID_CUSTOMER);
    }

    @Test
    @Transactional
    void patchNonExistingConfigApp() throws Exception {
        int databaseSizeBeforeUpdate = configAppRepository.findAll().size();
        configApp.setId(count.incrementAndGet());

        // Create the ConfigApp
        ConfigAppDTO configAppDTO = configAppMapper.toDto(configApp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfigAppMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, configAppDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(configAppDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfigApp in the database
        List<ConfigApp> configAppList = configAppRepository.findAll();
        assertThat(configAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConfigApp() throws Exception {
        int databaseSizeBeforeUpdate = configAppRepository.findAll().size();
        configApp.setId(count.incrementAndGet());

        // Create the ConfigApp
        ConfigAppDTO configAppDTO = configAppMapper.toDto(configApp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigAppMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(configAppDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfigApp in the database
        List<ConfigApp> configAppList = configAppRepository.findAll();
        assertThat(configAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConfigApp() throws Exception {
        int databaseSizeBeforeUpdate = configAppRepository.findAll().size();
        configApp.setId(count.incrementAndGet());

        // Create the ConfigApp
        ConfigAppDTO configAppDTO = configAppMapper.toDto(configApp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigAppMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(configAppDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConfigApp in the database
        List<ConfigApp> configAppList = configAppRepository.findAll();
        assertThat(configAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConfigApp() throws Exception {
        // Initialize the database
        configAppRepository.saveAndFlush(configApp);

        int databaseSizeBeforeDelete = configAppRepository.findAll().size();

        // Delete the configApp
        restConfigAppMockMvc
            .perform(delete(ENTITY_API_URL_ID, configApp.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConfigApp> configAppList = configAppRepository.findAll();
        assertThat(configAppList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
