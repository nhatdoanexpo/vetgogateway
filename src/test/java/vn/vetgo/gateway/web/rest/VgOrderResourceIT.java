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
import vn.vetgo.gateway.domain.VgOrder;
import vn.vetgo.gateway.repository.VgOrderRepository;
import vn.vetgo.gateway.service.dto.VgOrderDTO;
import vn.vetgo.gateway.service.mapper.VgOrderMapper;

/**
 * Integration tests for the {@link VgOrderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VgOrderResourceIT {

    private static final Long DEFAULT_ID_CUSTOMER = 1L;
    private static final Long UPDATED_ID_CUSTOMER = 2L;

    private static final String DEFAULT_ORDER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PAID = false;
    private static final Boolean UPDATED_PAID = true;

    private static final BigDecimal DEFAULT_TOTAL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_AMOUNT = new BigDecimal(2);

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_ID_PARTNER = 1L;
    private static final Long UPDATED_ID_PARTNER = 2L;

    private static final String DEFAULT_ATTRIBUTES = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vg-orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VgOrderRepository vgOrderRepository;

    @Autowired
    private VgOrderMapper vgOrderMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVgOrderMockMvc;

    private VgOrder vgOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VgOrder createEntity(EntityManager em) {
        VgOrder vgOrder = new VgOrder()
            .idCustomer(DEFAULT_ID_CUSTOMER)
            .orderType(DEFAULT_ORDER_TYPE)
            .paid(DEFAULT_PAID)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .createdDate(DEFAULT_CREATED_DATE)
            .idPartner(DEFAULT_ID_PARTNER)
            .attributes(DEFAULT_ATTRIBUTES);
        return vgOrder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VgOrder createUpdatedEntity(EntityManager em) {
        VgOrder vgOrder = new VgOrder()
            .idCustomer(UPDATED_ID_CUSTOMER)
            .orderType(UPDATED_ORDER_TYPE)
            .paid(UPDATED_PAID)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .createdDate(UPDATED_CREATED_DATE)
            .idPartner(UPDATED_ID_PARTNER)
            .attributes(UPDATED_ATTRIBUTES);
        return vgOrder;
    }

    @BeforeEach
    public void initTest() {
        vgOrder = createEntity(em);
    }

    @Test
    @Transactional
    void createVgOrder() throws Exception {
        int databaseSizeBeforeCreate = vgOrderRepository.findAll().size();
        // Create the VgOrder
        VgOrderDTO vgOrderDTO = vgOrderMapper.toDto(vgOrder);
        restVgOrderMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vgOrderDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VgOrder in the database
        List<VgOrder> vgOrderList = vgOrderRepository.findAll();
        assertThat(vgOrderList).hasSize(databaseSizeBeforeCreate + 1);
        VgOrder testVgOrder = vgOrderList.get(vgOrderList.size() - 1);
        assertThat(testVgOrder.getIdCustomer()).isEqualTo(DEFAULT_ID_CUSTOMER);
        assertThat(testVgOrder.getOrderType()).isEqualTo(DEFAULT_ORDER_TYPE);
        assertThat(testVgOrder.getPaid()).isEqualTo(DEFAULT_PAID);
        assertThat(testVgOrder.getTotalAmount()).isEqualByComparingTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testVgOrder.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testVgOrder.getIdPartner()).isEqualTo(DEFAULT_ID_PARTNER);
        assertThat(testVgOrder.getAttributes()).isEqualTo(DEFAULT_ATTRIBUTES);
    }

    @Test
    @Transactional
    void createVgOrderWithExistingId() throws Exception {
        // Create the VgOrder with an existing ID
        vgOrder.setId(1L);
        VgOrderDTO vgOrderDTO = vgOrderMapper.toDto(vgOrder);

        int databaseSizeBeforeCreate = vgOrderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVgOrderMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vgOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VgOrder in the database
        List<VgOrder> vgOrderList = vgOrderRepository.findAll();
        assertThat(vgOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVgOrders() throws Exception {
        // Initialize the database
        vgOrderRepository.saveAndFlush(vgOrder);

        // Get all the vgOrderList
        restVgOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vgOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].idCustomer").value(hasItem(DEFAULT_ID_CUSTOMER.intValue())))
            .andExpect(jsonPath("$.[*].orderType").value(hasItem(DEFAULT_ORDER_TYPE)))
            .andExpect(jsonPath("$.[*].paid").value(hasItem(DEFAULT_PAID.booleanValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(sameNumber(DEFAULT_TOTAL_AMOUNT))))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].idPartner").value(hasItem(DEFAULT_ID_PARTNER.intValue())))
            .andExpect(jsonPath("$.[*].attributes").value(hasItem(DEFAULT_ATTRIBUTES)));
    }

    @Test
    @Transactional
    void getVgOrder() throws Exception {
        // Initialize the database
        vgOrderRepository.saveAndFlush(vgOrder);

        // Get the vgOrder
        restVgOrderMockMvc
            .perform(get(ENTITY_API_URL_ID, vgOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vgOrder.getId().intValue()))
            .andExpect(jsonPath("$.idCustomer").value(DEFAULT_ID_CUSTOMER.intValue()))
            .andExpect(jsonPath("$.orderType").value(DEFAULT_ORDER_TYPE))
            .andExpect(jsonPath("$.paid").value(DEFAULT_PAID.booleanValue()))
            .andExpect(jsonPath("$.totalAmount").value(sameNumber(DEFAULT_TOTAL_AMOUNT)))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.idPartner").value(DEFAULT_ID_PARTNER.intValue()))
            .andExpect(jsonPath("$.attributes").value(DEFAULT_ATTRIBUTES));
    }

    @Test
    @Transactional
    void getNonExistingVgOrder() throws Exception {
        // Get the vgOrder
        restVgOrderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVgOrder() throws Exception {
        // Initialize the database
        vgOrderRepository.saveAndFlush(vgOrder);

        int databaseSizeBeforeUpdate = vgOrderRepository.findAll().size();

        // Update the vgOrder
        VgOrder updatedVgOrder = vgOrderRepository.findById(vgOrder.getId()).get();
        // Disconnect from session so that the updates on updatedVgOrder are not directly saved in db
        em.detach(updatedVgOrder);
        updatedVgOrder
            .idCustomer(UPDATED_ID_CUSTOMER)
            .orderType(UPDATED_ORDER_TYPE)
            .paid(UPDATED_PAID)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .createdDate(UPDATED_CREATED_DATE)
            .idPartner(UPDATED_ID_PARTNER)
            .attributes(UPDATED_ATTRIBUTES);
        VgOrderDTO vgOrderDTO = vgOrderMapper.toDto(updatedVgOrder);

        restVgOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vgOrderDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vgOrderDTO))
            )
            .andExpect(status().isOk());

        // Validate the VgOrder in the database
        List<VgOrder> vgOrderList = vgOrderRepository.findAll();
        assertThat(vgOrderList).hasSize(databaseSizeBeforeUpdate);
        VgOrder testVgOrder = vgOrderList.get(vgOrderList.size() - 1);
        assertThat(testVgOrder.getIdCustomer()).isEqualTo(UPDATED_ID_CUSTOMER);
        assertThat(testVgOrder.getOrderType()).isEqualTo(UPDATED_ORDER_TYPE);
        assertThat(testVgOrder.getPaid()).isEqualTo(UPDATED_PAID);
        assertThat(testVgOrder.getTotalAmount()).isEqualByComparingTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testVgOrder.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testVgOrder.getIdPartner()).isEqualTo(UPDATED_ID_PARTNER);
        assertThat(testVgOrder.getAttributes()).isEqualTo(UPDATED_ATTRIBUTES);
    }

    @Test
    @Transactional
    void putNonExistingVgOrder() throws Exception {
        int databaseSizeBeforeUpdate = vgOrderRepository.findAll().size();
        vgOrder.setId(count.incrementAndGet());

        // Create the VgOrder
        VgOrderDTO vgOrderDTO = vgOrderMapper.toDto(vgOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVgOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vgOrderDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vgOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VgOrder in the database
        List<VgOrder> vgOrderList = vgOrderRepository.findAll();
        assertThat(vgOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVgOrder() throws Exception {
        int databaseSizeBeforeUpdate = vgOrderRepository.findAll().size();
        vgOrder.setId(count.incrementAndGet());

        // Create the VgOrder
        VgOrderDTO vgOrderDTO = vgOrderMapper.toDto(vgOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVgOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vgOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VgOrder in the database
        List<VgOrder> vgOrderList = vgOrderRepository.findAll();
        assertThat(vgOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVgOrder() throws Exception {
        int databaseSizeBeforeUpdate = vgOrderRepository.findAll().size();
        vgOrder.setId(count.incrementAndGet());

        // Create the VgOrder
        VgOrderDTO vgOrderDTO = vgOrderMapper.toDto(vgOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVgOrderMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vgOrderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VgOrder in the database
        List<VgOrder> vgOrderList = vgOrderRepository.findAll();
        assertThat(vgOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVgOrderWithPatch() throws Exception {
        // Initialize the database
        vgOrderRepository.saveAndFlush(vgOrder);

        int databaseSizeBeforeUpdate = vgOrderRepository.findAll().size();

        // Update the vgOrder using partial update
        VgOrder partialUpdatedVgOrder = new VgOrder();
        partialUpdatedVgOrder.setId(vgOrder.getId());

        partialUpdatedVgOrder
            .idCustomer(UPDATED_ID_CUSTOMER)
            .paid(UPDATED_PAID)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .idPartner(UPDATED_ID_PARTNER)
            .attributes(UPDATED_ATTRIBUTES);

        restVgOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVgOrder.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVgOrder))
            )
            .andExpect(status().isOk());

        // Validate the VgOrder in the database
        List<VgOrder> vgOrderList = vgOrderRepository.findAll();
        assertThat(vgOrderList).hasSize(databaseSizeBeforeUpdate);
        VgOrder testVgOrder = vgOrderList.get(vgOrderList.size() - 1);
        assertThat(testVgOrder.getIdCustomer()).isEqualTo(UPDATED_ID_CUSTOMER);
        assertThat(testVgOrder.getOrderType()).isEqualTo(DEFAULT_ORDER_TYPE);
        assertThat(testVgOrder.getPaid()).isEqualTo(UPDATED_PAID);
        assertThat(testVgOrder.getTotalAmount()).isEqualByComparingTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testVgOrder.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testVgOrder.getIdPartner()).isEqualTo(UPDATED_ID_PARTNER);
        assertThat(testVgOrder.getAttributes()).isEqualTo(UPDATED_ATTRIBUTES);
    }

    @Test
    @Transactional
    void fullUpdateVgOrderWithPatch() throws Exception {
        // Initialize the database
        vgOrderRepository.saveAndFlush(vgOrder);

        int databaseSizeBeforeUpdate = vgOrderRepository.findAll().size();

        // Update the vgOrder using partial update
        VgOrder partialUpdatedVgOrder = new VgOrder();
        partialUpdatedVgOrder.setId(vgOrder.getId());

        partialUpdatedVgOrder
            .idCustomer(UPDATED_ID_CUSTOMER)
            .orderType(UPDATED_ORDER_TYPE)
            .paid(UPDATED_PAID)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .createdDate(UPDATED_CREATED_DATE)
            .idPartner(UPDATED_ID_PARTNER)
            .attributes(UPDATED_ATTRIBUTES);

        restVgOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVgOrder.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVgOrder))
            )
            .andExpect(status().isOk());

        // Validate the VgOrder in the database
        List<VgOrder> vgOrderList = vgOrderRepository.findAll();
        assertThat(vgOrderList).hasSize(databaseSizeBeforeUpdate);
        VgOrder testVgOrder = vgOrderList.get(vgOrderList.size() - 1);
        assertThat(testVgOrder.getIdCustomer()).isEqualTo(UPDATED_ID_CUSTOMER);
        assertThat(testVgOrder.getOrderType()).isEqualTo(UPDATED_ORDER_TYPE);
        assertThat(testVgOrder.getPaid()).isEqualTo(UPDATED_PAID);
        assertThat(testVgOrder.getTotalAmount()).isEqualByComparingTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testVgOrder.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testVgOrder.getIdPartner()).isEqualTo(UPDATED_ID_PARTNER);
        assertThat(testVgOrder.getAttributes()).isEqualTo(UPDATED_ATTRIBUTES);
    }

    @Test
    @Transactional
    void patchNonExistingVgOrder() throws Exception {
        int databaseSizeBeforeUpdate = vgOrderRepository.findAll().size();
        vgOrder.setId(count.incrementAndGet());

        // Create the VgOrder
        VgOrderDTO vgOrderDTO = vgOrderMapper.toDto(vgOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVgOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vgOrderDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vgOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VgOrder in the database
        List<VgOrder> vgOrderList = vgOrderRepository.findAll();
        assertThat(vgOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVgOrder() throws Exception {
        int databaseSizeBeforeUpdate = vgOrderRepository.findAll().size();
        vgOrder.setId(count.incrementAndGet());

        // Create the VgOrder
        VgOrderDTO vgOrderDTO = vgOrderMapper.toDto(vgOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVgOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vgOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VgOrder in the database
        List<VgOrder> vgOrderList = vgOrderRepository.findAll();
        assertThat(vgOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVgOrder() throws Exception {
        int databaseSizeBeforeUpdate = vgOrderRepository.findAll().size();
        vgOrder.setId(count.incrementAndGet());

        // Create the VgOrder
        VgOrderDTO vgOrderDTO = vgOrderMapper.toDto(vgOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVgOrderMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vgOrderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VgOrder in the database
        List<VgOrder> vgOrderList = vgOrderRepository.findAll();
        assertThat(vgOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVgOrder() throws Exception {
        // Initialize the database
        vgOrderRepository.saveAndFlush(vgOrder);

        int databaseSizeBeforeDelete = vgOrderRepository.findAll().size();

        // Delete the vgOrder
        restVgOrderMockMvc
            .perform(delete(ENTITY_API_URL_ID, vgOrder.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VgOrder> vgOrderList = vgOrderRepository.findAll();
        assertThat(vgOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
