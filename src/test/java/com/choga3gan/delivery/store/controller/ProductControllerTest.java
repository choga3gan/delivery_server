package com.choga3gan.delivery.store.controller;

import com.choga3gan.delivery.category.domain.Category;
import com.choga3gan.delivery.category.repository.CategoryRepository;
import com.choga3gan.delivery.product.dto.ProductRequest;
import com.choga3gan.delivery.product.repository.ProductRepository;
import com.choga3gan.delivery.store.domain.ServiceTime;
import com.choga3gan.delivery.store.domain.Store;
import com.choga3gan.delivery.store.repository.StoreRepository;
import com.choga3gan.delivery.user.domain.User;
import com.choga3gan.delivery.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("local")
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

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
    private UserRepository userRepository;

    private Category category1;
    private Category category2;
    private Store store1;
    private Store store2;

    @BeforeEach
    public void setup() {
        category1 = categoryRepository.save(new Category("한식"));
        category2 = categoryRepository.save(new Category("분식"));
        User testUser = userRepository.save(User.builder()
                .username("test_user")
                .email("user@test.org")
                .password("123456")
                .build());
        store1 = storeRepository.save(Store.builder()
                .storeName("매장1")
                .address("서울시 강남구")
                .telNum("010-1111-1111")
                .user(testUser)
                .categories(List.of(category1))
                .serviceTime(ServiceTime.builder().startTime(LocalTime.of(9, 0)).endTime(LocalTime.of(21, 0)).build())
                .build());
        store2 = storeRepository.save(Store.builder()
                .storeName("매장2")
                .address("서울시 서초구")
                .telNum("010-2222-2222")
                .user(testUser)
                .categories(List.of(category2))
                .serviceTime(ServiceTime.builder().startTime(LocalTime.of(10, 0)).endTime(LocalTime.of(22, 0)).build())
                .build());
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"OWNER"})
    @DisplayName("신규 상품 추가 테스트")
    public void createProduct() throws Exception {
        ProductRequest request = ProductRequest.builder()
                .categoryIds(List.of(category2.getCategoryId()))
                .productName("떡볶이")
                .price(3000.0)
                .open(true)
                .productImg("https://picsum.photos/200")
                .description("둘이 먹다 하나가 죽어도 모르는 떡볶이")
                .build();

        mockMvc.perform(post("/v1/stores/{storeId}/products", store1.getStoreId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").isNotEmpty());
    }
}
