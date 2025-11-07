package com.choga3gan.delivery.order.service;

import com.choga3gan.delivery.category.domain.Category;
import com.choga3gan.delivery.category.repository.CategoryRepository;
import com.choga3gan.delivery.order.domain.Order;
import com.choga3gan.delivery.order.dto.OrderItemRequest;
import com.choga3gan.delivery.order.dto.OrderRequest;
import com.choga3gan.delivery.order.dto.OrderResponse;
import com.choga3gan.delivery.order.repository.OrderRepository;
import com.choga3gan.delivery.product.domain.Product;
import com.choga3gan.delivery.product.repository.ProductRepository;
import com.choga3gan.delivery.store.domain.ServiceTime;
import com.choga3gan.delivery.store.domain.Store;
import com.choga3gan.delivery.store.repository.StoreRepository;
import com.choga3gan.delivery.user.domain.User;
import com.choga3gan.delivery.user.repository.UserRepository;
import com.choga3gan.delivery.user.test.MockUser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@Transactional
public class OrderControllerTest {
    @Autowired
    OrderService orderService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    private OrderRequest orderRequest;


    @BeforeEach
    void setup() {
        Category category1 = categoryRepository.save(new Category("한식"));
        Category category2 = categoryRepository.save(new Category("분식"));
        User testUser = userRepository.save(User.builder()
                .username("test_user")
                .email("user@test.org")
                .password("123456")
                .build());
        Store store1 = storeRepository.save(Store.builder()
                .storeName("매장1")
                .address("서울시 강남구")
                .telNum("010-1111-1111")
                        .serviceTime(ServiceTime.builder().startTime(LocalTime.of(9, 0) ).endTime(LocalTime.of(18,0)).build())
                .user(testUser)
                .categories(List.of(category1))
                .serviceTime(ServiceTime.builder().startTime(LocalTime.of(9, 0)).endTime(LocalTime.of(21, 0)).build())
                .build());
        Store store2 = storeRepository.save(Store.builder()
                .storeName("매장2")
                .address("서울시 서초구")
                .telNum("010-2222-2222")
                .serviceTime(ServiceTime.builder().startTime(LocalTime.of(9, 0)).endTime(LocalTime.of(18,0)).build())
                .user(testUser)
                .categories(List.of(category2))
                .serviceTime(ServiceTime.builder().startTime(LocalTime.of(10, 0)).endTime(LocalTime.of(22, 0)).build())
                .build());

        Product product1 = productRepository.save(Product.builder()
                .categories(List.of(category2))
                .store(store1)
                .productName("떡볶이")
                .price(3000).build());

        OrderItemRequest items = new OrderItemRequest(product1.getProductId(), 10);

        orderRequest = OrderRequest.builder()
                .name("테스트 사용자")
                .email("test@test.org")
                .address("테스트 주소")
                .items(List.of(items))
                .build();
    }

    @Test
    @DisplayName("주문 등록 테스트")
    @MockUser
    void orderCreateTest() throws Exception {
//        String body = objectMapper.writeValueAsString(orderRequest);
//        String res = mockMvc.perform(post("/v1/orders")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(body))
//                .andDo(print())
//                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
//
//        System.out.println("response:" + res);
        //OrderResponse r =  objectMapper.readValue(res, OrderResponse.class);
        //UUID orderId = r.orderId();

        //mockMvc.perform(patch("/v1/orders/" + orderId.toString() + "/status?accept=true"))
         //       .andDo(print());
    }

    @Test
    void acceptOrderTest() throws Exception {
        UUID orderId = UUID.fromString("ee978742-e5e5-44cb-8b3c-b063b12168a5");
        Order order = orderRepository.findById(orderId).orElse(null);
        order.orderAccept();
        orderRepository.save(order);
        System.out.println(order);

    }

    @Test
    void orderUpdateTest() throws Exception {
        String body = objectMapper.writeValueAsString(orderRequest);

        mockMvc.perform(patch("/v1/orders/{orderId}")
        );
    }
}
