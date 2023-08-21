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
import vn.vetgo.gateway.domain.OrderItem;
import vn.vetgo.gateway.repository.OrderItemRepository;
import vn.vetgo.gateway.service.dto.OrderItemDTO;
import vn.vetgo.gateway.service.mapper.OrderItemMapper;

/**
 * Integration tests for the {@link OrderItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrderItemResourceIT {

    private static final Long DEFAULT_ID_ITEM = 1L;
    private static final Long UPDATED_ID_ITEM = 2L;

    private static final String DEFAULT_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_QTY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QTY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRICE = new BigDecimal(2);

    private static final Long DEFAULT_ID_VG_ORDER = 1L;
    private static final Long UPDATED_ID_VG_ORDER = 2L;

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;

    private static final Long DEFAULT_ID_ITEM_AGENT = 1L;
    private static final Long UPDATED_ID_ITEM_AGENT = 2L;

    private static final String ENTITY_API_URL = "/api/order-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderItemMockMvc;

    private OrderItem orderItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderItem createEntity(EntityManager em) {
        OrderItem orderItem = new OrderItem()
            .idItem(DEFAULT_ID_ITEM)
            .itemName(DEFAULT_ITEM_NAME)
            .price(DEFAULT_PRICE)
            .qty(DEFAULT_QTY)
            .totalPrice(DEFAULT_TOTAL_PRICE)
            .idVgOrder(DEFAULT_ID_VG_ORDER)
            .discount(DEFAULT_DISCOUNT)
            .idItemAgent(DEFAULT_ID_ITEM_AGENT);
        return orderItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderItem createUpdatedEntity(EntityManager em) {
        OrderItem orderItem = new OrderItem()
            .idItem(UPDATED_ID_ITEM)
            .itemName(UPDATED_ITEM_NAME)
            .price(UPDATED_PRICE)
            .qty(UPDATED_QTY)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .idVgOrder(UPDATED_ID_VG_ORDER)
            .discount(UPDATED_DISCOUNT)
            .idItemAgent(UPDATED_ID_ITEM_AGENT);
        return orderItem;
    }

    @BeforeEach
    public void initTest() {
        orderItem = createEntity(em);
    }

    @Test
    @Transactional
    void createOrderItem() throws Exception {
        int databaseSizeBeforeCreate = orderItemRepository.findAll().size();
        // Create the OrderItem
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);
        restOrderItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeCreate + 1);
        OrderItem testOrderItem = orderItemList.get(orderItemList.size() - 1);
        assertThat(testOrderItem.getIdItem()).isEqualTo(DEFAULT_ID_ITEM);
        assertThat(testOrderItem.getItemName()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testOrderItem.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testOrderItem.getQty()).isEqualByComparingTo(DEFAULT_QTY);
        assertThat(testOrderItem.getTotalPrice()).isEqualByComparingTo(DEFAULT_TOTAL_PRICE);
        assertThat(testOrderItem.getIdVgOrder()).isEqualTo(DEFAULT_ID_VG_ORDER);
        assertThat(testOrderItem.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testOrderItem.getIdItemAgent()).isEqualTo(DEFAULT_ID_ITEM_AGENT);
    }

    @Test
    @Transactional
    void createOrderItemWithExistingId() throws Exception {
        // Create the OrderItem with an existing ID
        orderItem.setId(1L);
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        int databaseSizeBeforeCreate = orderItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrderItems() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList
        restOrderItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].idItem").value(hasItem(DEFAULT_ID_ITEM.intValue())))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(sameNumber(DEFAULT_QTY))))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(sameNumber(DEFAULT_TOTAL_PRICE))))
            .andExpect(jsonPath("$.[*].idVgOrder").value(hasItem(DEFAULT_ID_VG_ORDER.intValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].idItemAgent").value(hasItem(DEFAULT_ID_ITEM_AGENT.intValue())));
    }

    @Test
    @Transactional
    void getOrderItem() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get the orderItem
        restOrderItemMockMvc
            .perform(get(ENTITY_API_URL_ID, orderItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderItem.getId().intValue()))
            .andExpect(jsonPath("$.idItem").value(DEFAULT_ID_ITEM.intValue()))
            .andExpect(jsonPath("$.itemName").value(DEFAULT_ITEM_NAME))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.qty").value(sameNumber(DEFAULT_QTY)))
            .andExpect(jsonPath("$.totalPrice").value(sameNumber(DEFAULT_TOTAL_PRICE)))
            .andExpect(jsonPath("$.idVgOrder").value(DEFAULT_ID_VG_ORDER.intValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.idItemAgent").value(DEFAULT_ID_ITEM_AGENT.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingOrderItem() throws Exception {
        // Get the orderItem
        restOrderItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrderItem() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();

        // Update the orderItem
        OrderItem updatedOrderItem = orderItemRepository.findById(orderItem.getId()).get();
        // Disconnect from session so that the updates on updatedOrderItem are not directly saved in db
        em.detach(updatedOrderItem);
        updatedOrderItem
            .idItem(UPDATED_ID_ITEM)
            .itemName(UPDATED_ITEM_NAME)
            .price(UPDATED_PRICE)
            .qty(UPDATED_QTY)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .idVgOrder(UPDATED_ID_VG_ORDER)
            .discount(UPDATED_DISCOUNT)
            .idItemAgent(UPDATED_ID_ITEM_AGENT);
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(updatedOrderItem);

        restOrderItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderItemDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
        OrderItem testOrderItem = orderItemList.get(orderItemList.size() - 1);
        assertThat(testOrderItem.getIdItem()).isEqualTo(UPDATED_ID_ITEM);
        assertThat(testOrderItem.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testOrderItem.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testOrderItem.getQty()).isEqualByComparingTo(UPDATED_QTY);
        assertThat(testOrderItem.getTotalPrice()).isEqualByComparingTo(UPDATED_TOTAL_PRICE);
        assertThat(testOrderItem.getIdVgOrder()).isEqualTo(UPDATED_ID_VG_ORDER);
        assertThat(testOrderItem.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testOrderItem.getIdItemAgent()).isEqualTo(UPDATED_ID_ITEM_AGENT);
    }

    @Test
    @Transactional
    void putNonExistingOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();
        orderItem.setId(count.incrementAndGet());

        // Create the OrderItem
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderItemDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();
        orderItem.setId(count.incrementAndGet());

        // Create the OrderItem
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();
        orderItem.setId(count.incrementAndGet());

        // Create the OrderItem
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderItemMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrderItemWithPatch() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();

        // Update the orderItem using partial update
        OrderItem partialUpdatedOrderItem = new OrderItem();
        partialUpdatedOrderItem.setId(orderItem.getId());

        partialUpdatedOrderItem
            .idItem(UPDATED_ID_ITEM)
            .price(UPDATED_PRICE)
            .qty(UPDATED_QTY)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .discount(UPDATED_DISCOUNT)
            .idItemAgent(UPDATED_ID_ITEM_AGENT);

        restOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderItem.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderItem))
            )
            .andExpect(status().isOk());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
        OrderItem testOrderItem = orderItemList.get(orderItemList.size() - 1);
        assertThat(testOrderItem.getIdItem()).isEqualTo(UPDATED_ID_ITEM);
        assertThat(testOrderItem.getItemName()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testOrderItem.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testOrderItem.getQty()).isEqualByComparingTo(UPDATED_QTY);
        assertThat(testOrderItem.getTotalPrice()).isEqualByComparingTo(UPDATED_TOTAL_PRICE);
        assertThat(testOrderItem.getIdVgOrder()).isEqualTo(DEFAULT_ID_VG_ORDER);
        assertThat(testOrderItem.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testOrderItem.getIdItemAgent()).isEqualTo(UPDATED_ID_ITEM_AGENT);
    }

    @Test
    @Transactional
    void fullUpdateOrderItemWithPatch() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();

        // Update the orderItem using partial update
        OrderItem partialUpdatedOrderItem = new OrderItem();
        partialUpdatedOrderItem.setId(orderItem.getId());

        partialUpdatedOrderItem
            .idItem(UPDATED_ID_ITEM)
            .itemName(UPDATED_ITEM_NAME)
            .price(UPDATED_PRICE)
            .qty(UPDATED_QTY)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .idVgOrder(UPDATED_ID_VG_ORDER)
            .discount(UPDATED_DISCOUNT)
            .idItemAgent(UPDATED_ID_ITEM_AGENT);

        restOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderItem.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderItem))
            )
            .andExpect(status().isOk());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
        OrderItem testOrderItem = orderItemList.get(orderItemList.size() - 1);
        assertThat(testOrderItem.getIdItem()).isEqualTo(UPDATED_ID_ITEM);
        assertThat(testOrderItem.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testOrderItem.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testOrderItem.getQty()).isEqualByComparingTo(UPDATED_QTY);
        assertThat(testOrderItem.getTotalPrice()).isEqualByComparingTo(UPDATED_TOTAL_PRICE);
        assertThat(testOrderItem.getIdVgOrder()).isEqualTo(UPDATED_ID_VG_ORDER);
        assertThat(testOrderItem.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testOrderItem.getIdItemAgent()).isEqualTo(UPDATED_ID_ITEM_AGENT);
    }

    @Test
    @Transactional
    void patchNonExistingOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();
        orderItem.setId(count.incrementAndGet());

        // Create the OrderItem
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orderItemDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();
        orderItem.setId(count.incrementAndGet());

        // Create the OrderItem
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();
        orderItem.setId(count.incrementAndGet());

        // Create the OrderItem
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrderItem() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        int databaseSizeBeforeDelete = orderItemRepository.findAll().size();

        // Delete the orderItem
        restOrderItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, orderItem.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
