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
import vn.vetgo.gateway.domain.CategoryAgent;
import vn.vetgo.gateway.repository.CategoryAgentRepository;
import vn.vetgo.gateway.service.dto.CategoryAgentDTO;
import vn.vetgo.gateway.service.mapper.CategoryAgentMapper;

/**
 * Integration tests for the {@link CategoryAgentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategoryAgentResourceIT {

    private static final Long DEFAULT_ID_AGENT = 1L;
    private static final Long UPDATED_ID_AGENT = 2L;

    private static final Long DEFAULT_ID_CATEGORY = 1L;
    private static final Long UPDATED_ID_CATEGORY = 2L;

    private static final String ENTITY_API_URL = "/api/category-agents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategoryAgentRepository categoryAgentRepository;

    @Autowired
    private CategoryAgentMapper categoryAgentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoryAgentMockMvc;

    private CategoryAgent categoryAgent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryAgent createEntity(EntityManager em) {
        CategoryAgent categoryAgent = new CategoryAgent().idAgent(DEFAULT_ID_AGENT).idCategory(DEFAULT_ID_CATEGORY);
        return categoryAgent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryAgent createUpdatedEntity(EntityManager em) {
        CategoryAgent categoryAgent = new CategoryAgent().idAgent(UPDATED_ID_AGENT).idCategory(UPDATED_ID_CATEGORY);
        return categoryAgent;
    }

    @BeforeEach
    public void initTest() {
        categoryAgent = createEntity(em);
    }

    @Test
    @Transactional
    void createCategoryAgent() throws Exception {
        int databaseSizeBeforeCreate = categoryAgentRepository.findAll().size();
        // Create the CategoryAgent
        CategoryAgentDTO categoryAgentDTO = categoryAgentMapper.toDto(categoryAgent);
        restCategoryAgentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoryAgentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CategoryAgent in the database
        List<CategoryAgent> categoryAgentList = categoryAgentRepository.findAll();
        assertThat(categoryAgentList).hasSize(databaseSizeBeforeCreate + 1);
        CategoryAgent testCategoryAgent = categoryAgentList.get(categoryAgentList.size() - 1);
        assertThat(testCategoryAgent.getIdAgent()).isEqualTo(DEFAULT_ID_AGENT);
        assertThat(testCategoryAgent.getIdCategory()).isEqualTo(DEFAULT_ID_CATEGORY);
    }

    @Test
    @Transactional
    void createCategoryAgentWithExistingId() throws Exception {
        // Create the CategoryAgent with an existing ID
        categoryAgent.setId(1L);
        CategoryAgentDTO categoryAgentDTO = categoryAgentMapper.toDto(categoryAgent);

        int databaseSizeBeforeCreate = categoryAgentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryAgentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoryAgentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoryAgent in the database
        List<CategoryAgent> categoryAgentList = categoryAgentRepository.findAll();
        assertThat(categoryAgentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCategoryAgents() throws Exception {
        // Initialize the database
        categoryAgentRepository.saveAndFlush(categoryAgent);

        // Get all the categoryAgentList
        restCategoryAgentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryAgent.getId().intValue())))
            .andExpect(jsonPath("$.[*].idAgent").value(hasItem(DEFAULT_ID_AGENT.intValue())))
            .andExpect(jsonPath("$.[*].idCategory").value(hasItem(DEFAULT_ID_CATEGORY.intValue())));
    }

    @Test
    @Transactional
    void getCategoryAgent() throws Exception {
        // Initialize the database
        categoryAgentRepository.saveAndFlush(categoryAgent);

        // Get the categoryAgent
        restCategoryAgentMockMvc
            .perform(get(ENTITY_API_URL_ID, categoryAgent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoryAgent.getId().intValue()))
            .andExpect(jsonPath("$.idAgent").value(DEFAULT_ID_AGENT.intValue()))
            .andExpect(jsonPath("$.idCategory").value(DEFAULT_ID_CATEGORY.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingCategoryAgent() throws Exception {
        // Get the categoryAgent
        restCategoryAgentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCategoryAgent() throws Exception {
        // Initialize the database
        categoryAgentRepository.saveAndFlush(categoryAgent);

        int databaseSizeBeforeUpdate = categoryAgentRepository.findAll().size();

        // Update the categoryAgent
        CategoryAgent updatedCategoryAgent = categoryAgentRepository.findById(categoryAgent.getId()).get();
        // Disconnect from session so that the updates on updatedCategoryAgent are not directly saved in db
        em.detach(updatedCategoryAgent);
        updatedCategoryAgent.idAgent(UPDATED_ID_AGENT).idCategory(UPDATED_ID_CATEGORY);
        CategoryAgentDTO categoryAgentDTO = categoryAgentMapper.toDto(updatedCategoryAgent);

        restCategoryAgentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoryAgentDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoryAgentDTO))
            )
            .andExpect(status().isOk());

        // Validate the CategoryAgent in the database
        List<CategoryAgent> categoryAgentList = categoryAgentRepository.findAll();
        assertThat(categoryAgentList).hasSize(databaseSizeBeforeUpdate);
        CategoryAgent testCategoryAgent = categoryAgentList.get(categoryAgentList.size() - 1);
        assertThat(testCategoryAgent.getIdAgent()).isEqualTo(UPDATED_ID_AGENT);
        assertThat(testCategoryAgent.getIdCategory()).isEqualTo(UPDATED_ID_CATEGORY);
    }

    @Test
    @Transactional
    void putNonExistingCategoryAgent() throws Exception {
        int databaseSizeBeforeUpdate = categoryAgentRepository.findAll().size();
        categoryAgent.setId(count.incrementAndGet());

        // Create the CategoryAgent
        CategoryAgentDTO categoryAgentDTO = categoryAgentMapper.toDto(categoryAgent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryAgentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoryAgentDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoryAgentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoryAgent in the database
        List<CategoryAgent> categoryAgentList = categoryAgentRepository.findAll();
        assertThat(categoryAgentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategoryAgent() throws Exception {
        int databaseSizeBeforeUpdate = categoryAgentRepository.findAll().size();
        categoryAgent.setId(count.incrementAndGet());

        // Create the CategoryAgent
        CategoryAgentDTO categoryAgentDTO = categoryAgentMapper.toDto(categoryAgent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryAgentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoryAgentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoryAgent in the database
        List<CategoryAgent> categoryAgentList = categoryAgentRepository.findAll();
        assertThat(categoryAgentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategoryAgent() throws Exception {
        int databaseSizeBeforeUpdate = categoryAgentRepository.findAll().size();
        categoryAgent.setId(count.incrementAndGet());

        // Create the CategoryAgent
        CategoryAgentDTO categoryAgentDTO = categoryAgentMapper.toDto(categoryAgent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryAgentMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoryAgentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategoryAgent in the database
        List<CategoryAgent> categoryAgentList = categoryAgentRepository.findAll();
        assertThat(categoryAgentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategoryAgentWithPatch() throws Exception {
        // Initialize the database
        categoryAgentRepository.saveAndFlush(categoryAgent);

        int databaseSizeBeforeUpdate = categoryAgentRepository.findAll().size();

        // Update the categoryAgent using partial update
        CategoryAgent partialUpdatedCategoryAgent = new CategoryAgent();
        partialUpdatedCategoryAgent.setId(categoryAgent.getId());

        partialUpdatedCategoryAgent.idAgent(UPDATED_ID_AGENT).idCategory(UPDATED_ID_CATEGORY);

        restCategoryAgentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategoryAgent.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategoryAgent))
            )
            .andExpect(status().isOk());

        // Validate the CategoryAgent in the database
        List<CategoryAgent> categoryAgentList = categoryAgentRepository.findAll();
        assertThat(categoryAgentList).hasSize(databaseSizeBeforeUpdate);
        CategoryAgent testCategoryAgent = categoryAgentList.get(categoryAgentList.size() - 1);
        assertThat(testCategoryAgent.getIdAgent()).isEqualTo(UPDATED_ID_AGENT);
        assertThat(testCategoryAgent.getIdCategory()).isEqualTo(UPDATED_ID_CATEGORY);
    }

    @Test
    @Transactional
    void fullUpdateCategoryAgentWithPatch() throws Exception {
        // Initialize the database
        categoryAgentRepository.saveAndFlush(categoryAgent);

        int databaseSizeBeforeUpdate = categoryAgentRepository.findAll().size();

        // Update the categoryAgent using partial update
        CategoryAgent partialUpdatedCategoryAgent = new CategoryAgent();
        partialUpdatedCategoryAgent.setId(categoryAgent.getId());

        partialUpdatedCategoryAgent.idAgent(UPDATED_ID_AGENT).idCategory(UPDATED_ID_CATEGORY);

        restCategoryAgentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategoryAgent.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategoryAgent))
            )
            .andExpect(status().isOk());

        // Validate the CategoryAgent in the database
        List<CategoryAgent> categoryAgentList = categoryAgentRepository.findAll();
        assertThat(categoryAgentList).hasSize(databaseSizeBeforeUpdate);
        CategoryAgent testCategoryAgent = categoryAgentList.get(categoryAgentList.size() - 1);
        assertThat(testCategoryAgent.getIdAgent()).isEqualTo(UPDATED_ID_AGENT);
        assertThat(testCategoryAgent.getIdCategory()).isEqualTo(UPDATED_ID_CATEGORY);
    }

    @Test
    @Transactional
    void patchNonExistingCategoryAgent() throws Exception {
        int databaseSizeBeforeUpdate = categoryAgentRepository.findAll().size();
        categoryAgent.setId(count.incrementAndGet());

        // Create the CategoryAgent
        CategoryAgentDTO categoryAgentDTO = categoryAgentMapper.toDto(categoryAgent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryAgentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categoryAgentDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoryAgentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoryAgent in the database
        List<CategoryAgent> categoryAgentList = categoryAgentRepository.findAll();
        assertThat(categoryAgentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategoryAgent() throws Exception {
        int databaseSizeBeforeUpdate = categoryAgentRepository.findAll().size();
        categoryAgent.setId(count.incrementAndGet());

        // Create the CategoryAgent
        CategoryAgentDTO categoryAgentDTO = categoryAgentMapper.toDto(categoryAgent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryAgentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoryAgentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoryAgent in the database
        List<CategoryAgent> categoryAgentList = categoryAgentRepository.findAll();
        assertThat(categoryAgentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategoryAgent() throws Exception {
        int databaseSizeBeforeUpdate = categoryAgentRepository.findAll().size();
        categoryAgent.setId(count.incrementAndGet());

        // Create the CategoryAgent
        CategoryAgentDTO categoryAgentDTO = categoryAgentMapper.toDto(categoryAgent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryAgentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoryAgentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategoryAgent in the database
        List<CategoryAgent> categoryAgentList = categoryAgentRepository.findAll();
        assertThat(categoryAgentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategoryAgent() throws Exception {
        // Initialize the database
        categoryAgentRepository.saveAndFlush(categoryAgent);

        int databaseSizeBeforeDelete = categoryAgentRepository.findAll().size();

        // Delete the categoryAgent
        restCategoryAgentMockMvc
            .perform(delete(ENTITY_API_URL_ID, categoryAgent.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoryAgent> categoryAgentList = categoryAgentRepository.findAll();
        assertThat(categoryAgentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
