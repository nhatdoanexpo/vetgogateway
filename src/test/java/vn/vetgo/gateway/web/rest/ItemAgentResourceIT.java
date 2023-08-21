package vn.vetgo.gateway.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.vetgo.gateway.web.rest.TestUtil.sameNumber;

import java.math.BigDecimal;
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
import vn.vetgo.gateway.domain.ItemAgent;
import vn.vetgo.gateway.repository.ItemAgentRepository;
import vn.vetgo.gateway.service.dto.ItemAgentDTO;
import vn.vetgo.gateway.service.mapper.ItemAgentMapper;

/**
 * Integration tests for the {@link ItemAgentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ItemAgentResourceIT {

    private static final Long DEFAULT_ID_AGENT = 1L;
    private static final Long UPDATED_ID_AGENT = 2L;

    private static final Long DEFAULT_ID_ITEM = 1L;
    private static final Long UPDATED_ID_ITEM = 2L;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_CONFIG_APP = 1L;
    private static final Long UPDATED_ID_CONFIG_APP = 2L;

    private static final Boolean DEFAULT_PAID = false;
    private static final Boolean UPDATED_PAID = true;

    private static final String ENTITY_API_URL = "/api/item-agents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ItemAgentRepository itemAgentRepository;

    @Autowired
    private ItemAgentMapper itemAgentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restItemAgentMockMvc;

    private ItemAgent itemAgent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemAgent createEntity(EntityManager em) {
        ItemAgent itemAgent = new ItemAgent()
            .idAgent(DEFAULT_ID_AGENT)
            .idItem(DEFAULT_ID_ITEM)
            .price(DEFAULT_PRICE)
            .status(DEFAULT_STATUS)
            .idConfigApp(DEFAULT_ID_CONFIG_APP)
            .paid(DEFAULT_PAID);
        return itemAgent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemAgent createUpdatedEntity(EntityManager em) {
        ItemAgent itemAgent = new ItemAgent()
            .idAgent(UPDATED_ID_AGENT)
            .idItem(UPDATED_ID_ITEM)
            .price(UPDATED_PRICE)
            .status(UPDATED_STATUS)
            .idConfigApp(UPDATED_ID_CONFIG_APP)
            .paid(UPDATED_PAID);
        return itemAgent;
    }

    @BeforeEach
    public void initTest() {
        itemAgent = createEntity(em);
    }

    @Test
    @Transactional
    void createItemAgent() throws Exception {
        int databaseSizeBeforeCreate = itemAgentRepository.findAll().size();
        // Create the ItemAgent
        ItemAgentDTO itemAgentDTO = itemAgentMapper.toDto(itemAgent);
        restItemAgentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemAgentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ItemAgent in the database
        List<ItemAgent> itemAgentList = itemAgentRepository.findAll();
        assertThat(itemAgentList).hasSize(databaseSizeBeforeCreate + 1);
        ItemAgent testItemAgent = itemAgentList.get(itemAgentList.size() - 1);
        assertThat(testItemAgent.getIdAgent()).isEqualTo(DEFAULT_ID_AGENT);
        assertThat(testItemAgent.getIdItem()).isEqualTo(DEFAULT_ID_ITEM);
        assertThat(testItemAgent.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testItemAgent.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testItemAgent.getIdConfigApp()).isEqualTo(DEFAULT_ID_CONFIG_APP);
        assertThat(testItemAgent.getPaid()).isEqualTo(DEFAULT_PAID);
    }

    @Test
    @Transactional
    void createItemAgentWithExistingId() throws Exception {
        // Create the ItemAgent with an existing ID
        itemAgent.setId(1L);
        ItemAgentDTO itemAgentDTO = itemAgentMapper.toDto(itemAgent);

        int databaseSizeBeforeCreate = itemAgentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemAgentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemAgentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemAgent in the database
        List<ItemAgent> itemAgentList = itemAgentRepository.findAll();
        assertThat(itemAgentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllItemAgents() throws Exception {
        // Initialize the database
        itemAgentRepository.saveAndFlush(itemAgent);

        // Get all the itemAgentList
        restItemAgentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemAgent.getId().intValue())))
            .andExpect(jsonPath("$.[*].idAgent").value(hasItem(DEFAULT_ID_AGENT.intValue())))
            .andExpect(jsonPath("$.[*].idItem").value(hasItem(DEFAULT_ID_ITEM.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].idConfigApp").value(hasItem(DEFAULT_ID_CONFIG_APP.intValue())))
            .andExpect(jsonPath("$.[*].paid").value(hasItem(DEFAULT_PAID.booleanValue())));
    }

    @Test
    @Transactional
    void getItemAgent() throws Exception {
        // Initialize the database
        itemAgentRepository.saveAndFlush(itemAgent);

        // Get the itemAgent
        restItemAgentMockMvc
            .perform(get(ENTITY_API_URL_ID, itemAgent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(itemAgent.getId().intValue()))
            .andExpect(jsonPath("$.idAgent").value(DEFAULT_ID_AGENT.intValue()))
            .andExpect(jsonPath("$.idItem").value(DEFAULT_ID_ITEM.intValue()))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.idConfigApp").value(DEFAULT_ID_CONFIG_APP.intValue()))
            .andExpect(jsonPath("$.paid").value(DEFAULT_PAID.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingItemAgent() throws Exception {
        // Get the itemAgent
        restItemAgentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingItemAgent() throws Exception {
        // Initialize the database
        itemAgentRepository.saveAndFlush(itemAgent);

        int databaseSizeBeforeUpdate = itemAgentRepository.findAll().size();

        // Update the itemAgent
        ItemAgent updatedItemAgent = itemAgentRepository.findById(itemAgent.getId()).get();
        // Disconnect from session so that the updates on updatedItemAgent are not directly saved in db
        em.detach(updatedItemAgent);
        updatedItemAgent
            .idAgent(UPDATED_ID_AGENT)
            .idItem(UPDATED_ID_ITEM)
            .price(UPDATED_PRICE)
            .status(UPDATED_STATUS)
            .idConfigApp(UPDATED_ID_CONFIG_APP)
            .paid(UPDATED_PAID);
        ItemAgentDTO itemAgentDTO = itemAgentMapper.toDto(updatedItemAgent);

        restItemAgentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, itemAgentDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemAgentDTO))
            )
            .andExpect(status().isOk());

        // Validate the ItemAgent in the database
        List<ItemAgent> itemAgentList = itemAgentRepository.findAll();
        assertThat(itemAgentList).hasSize(databaseSizeBeforeUpdate);
        ItemAgent testItemAgent = itemAgentList.get(itemAgentList.size() - 1);
        assertThat(testItemAgent.getIdAgent()).isEqualTo(UPDATED_ID_AGENT);
        assertThat(testItemAgent.getIdItem()).isEqualTo(UPDATED_ID_ITEM);
        assertThat(testItemAgent.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testItemAgent.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testItemAgent.getIdConfigApp()).isEqualTo(UPDATED_ID_CONFIG_APP);
        assertThat(testItemAgent.getPaid()).isEqualTo(UPDATED_PAID);
    }

    @Test
    @Transactional
    void putNonExistingItemAgent() throws Exception {
        int databaseSizeBeforeUpdate = itemAgentRepository.findAll().size();
        itemAgent.setId(count.incrementAndGet());

        // Create the ItemAgent
        ItemAgentDTO itemAgentDTO = itemAgentMapper.toDto(itemAgent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemAgentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, itemAgentDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemAgentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemAgent in the database
        List<ItemAgent> itemAgentList = itemAgentRepository.findAll();
        assertThat(itemAgentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchItemAgent() throws Exception {
        int databaseSizeBeforeUpdate = itemAgentRepository.findAll().size();
        itemAgent.setId(count.incrementAndGet());

        // Create the ItemAgent
        ItemAgentDTO itemAgentDTO = itemAgentMapper.toDto(itemAgent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemAgentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemAgentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemAgent in the database
        List<ItemAgent> itemAgentList = itemAgentRepository.findAll();
        assertThat(itemAgentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamItemAgent() throws Exception {
        int databaseSizeBeforeUpdate = itemAgentRepository.findAll().size();
        itemAgent.setId(count.incrementAndGet());

        // Create the ItemAgent
        ItemAgentDTO itemAgentDTO = itemAgentMapper.toDto(itemAgent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemAgentMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemAgentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItemAgent in the database
        List<ItemAgent> itemAgentList = itemAgentRepository.findAll();
        assertThat(itemAgentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateItemAgentWithPatch() throws Exception {
        // Initialize the database
        itemAgentRepository.saveAndFlush(itemAgent);

        int databaseSizeBeforeUpdate = itemAgentRepository.findAll().size();

        // Update the itemAgent using partial update
        ItemAgent partialUpdatedItemAgent = new ItemAgent();
        partialUpdatedItemAgent.setId(itemAgent.getId());

        partialUpdatedItemAgent.idAgent(UPDATED_ID_AGENT).price(UPDATED_PRICE).status(UPDATED_STATUS).idConfigApp(UPDATED_ID_CONFIG_APP);

        restItemAgentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItemAgent.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItemAgent))
            )
            .andExpect(status().isOk());

        // Validate the ItemAgent in the database
        List<ItemAgent> itemAgentList = itemAgentRepository.findAll();
        assertThat(itemAgentList).hasSize(databaseSizeBeforeUpdate);
        ItemAgent testItemAgent = itemAgentList.get(itemAgentList.size() - 1);
        assertThat(testItemAgent.getIdAgent()).isEqualTo(UPDATED_ID_AGENT);
        assertThat(testItemAgent.getIdItem()).isEqualTo(DEFAULT_ID_ITEM);
        assertThat(testItemAgent.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testItemAgent.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testItemAgent.getIdConfigApp()).isEqualTo(UPDATED_ID_CONFIG_APP);
        assertThat(testItemAgent.getPaid()).isEqualTo(DEFAULT_PAID);
    }

    @Test
    @Transactional
    void fullUpdateItemAgentWithPatch() throws Exception {
        // Initialize the database
        itemAgentRepository.saveAndFlush(itemAgent);

        int databaseSizeBeforeUpdate = itemAgentRepository.findAll().size();

        // Update the itemAgent using partial update
        ItemAgent partialUpdatedItemAgent = new ItemAgent();
        partialUpdatedItemAgent.setId(itemAgent.getId());

        partialUpdatedItemAgent
            .idAgent(UPDATED_ID_AGENT)
            .idItem(UPDATED_ID_ITEM)
            .price(UPDATED_PRICE)
            .status(UPDATED_STATUS)
            .idConfigApp(UPDATED_ID_CONFIG_APP)
            .paid(UPDATED_PAID);

        restItemAgentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItemAgent.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItemAgent))
            )
            .andExpect(status().isOk());

        // Validate the ItemAgent in the database
        List<ItemAgent> itemAgentList = itemAgentRepository.findAll();
        assertThat(itemAgentList).hasSize(databaseSizeBeforeUpdate);
        ItemAgent testItemAgent = itemAgentList.get(itemAgentList.size() - 1);
        assertThat(testItemAgent.getIdAgent()).isEqualTo(UPDATED_ID_AGENT);
        assertThat(testItemAgent.getIdItem()).isEqualTo(UPDATED_ID_ITEM);
        assertThat(testItemAgent.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testItemAgent.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testItemAgent.getIdConfigApp()).isEqualTo(UPDATED_ID_CONFIG_APP);
        assertThat(testItemAgent.getPaid()).isEqualTo(UPDATED_PAID);
    }

    @Test
    @Transactional
    void patchNonExistingItemAgent() throws Exception {
        int databaseSizeBeforeUpdate = itemAgentRepository.findAll().size();
        itemAgent.setId(count.incrementAndGet());

        // Create the ItemAgent
        ItemAgentDTO itemAgentDTO = itemAgentMapper.toDto(itemAgent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemAgentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, itemAgentDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemAgentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemAgent in the database
        List<ItemAgent> itemAgentList = itemAgentRepository.findAll();
        assertThat(itemAgentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchItemAgent() throws Exception {
        int databaseSizeBeforeUpdate = itemAgentRepository.findAll().size();
        itemAgent.setId(count.incrementAndGet());

        // Create the ItemAgent
        ItemAgentDTO itemAgentDTO = itemAgentMapper.toDto(itemAgent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemAgentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemAgentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemAgent in the database
        List<ItemAgent> itemAgentList = itemAgentRepository.findAll();
        assertThat(itemAgentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamItemAgent() throws Exception {
        int databaseSizeBeforeUpdate = itemAgentRepository.findAll().size();
        itemAgent.setId(count.incrementAndGet());

        // Create the ItemAgent
        ItemAgentDTO itemAgentDTO = itemAgentMapper.toDto(itemAgent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemAgentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemAgentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItemAgent in the database
        List<ItemAgent> itemAgentList = itemAgentRepository.findAll();
        assertThat(itemAgentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteItemAgent() throws Exception {
        // Initialize the database
        itemAgentRepository.saveAndFlush(itemAgent);

        int databaseSizeBeforeDelete = itemAgentRepository.findAll().size();

        // Delete the itemAgent
        restItemAgentMockMvc
            .perform(delete(ENTITY_API_URL_ID, itemAgent.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemAgent> itemAgentList = itemAgentRepository.findAll();
        assertThat(itemAgentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
