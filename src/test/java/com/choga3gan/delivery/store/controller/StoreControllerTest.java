package com.choga3gan.delivery.store.controller;

import com.choga3gan.delivery.category.domain.Category;
import com.choga3gan.delivery.category.repository.CategoryRepository;
import com.choga3gan.delivery.store.domain.ServiceTime;
import com.choga3gan.delivery.store.domain.Store;
import com.choga3gan.delivery.store.dto.ServiceTimeDto;
import com.choga3gan.delivery.store.dto.StoreRequest;
import com.choga3gan.delivery.store.repository.StoreRepository;
import com.choga3gan.delivery.user.domain.Role;
import com.choga3gan.delivery.user.domain.User;
import com.choga3gan.delivery.user.repository.UserRepository;
import com.choga3gan.delivery.user.test.MockUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("local")
@ExtendWith(MockitoExtension.class)
public class StoreControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StoreRepository storeRepository;

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
    @WithMockUser(username = "test_user", roles = {"CUSTOMER"})
    @DisplayName("매장 생성 테스트")
    void testCreateStoreWithCustomerUser() throws Exception {
        ServiceTimeDto serviceTimeRequest =
                ServiceTimeDto.builder()
                        .startTime(LocalTime.parse("09:00"))
                        .endTime(LocalTime.parse("21:00"))
                        .build();
        StoreRequest request = StoreRequest.builder()
                .storeName("테스트 매장")
                .address("서울시 강남구")
                .telNum("010-1234-5678")
                .categoryIds(List.of(category1.getCategoryId(), category2.getCategoryId()))
                .serviceTime(serviceTimeRequest)
                .build();

        mockMvc.perform(post("/v1/stores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.storeId").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"CUSTOMER"})
    @DisplayName("모든 매장 조회 테스트")
    void testGetAllStores() throws Exception {
        mockMvc.perform(get("/v1/stores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].storeId").isNotEmpty())
                .andExpect(jsonPath("$.content[*].storeName").value(Matchers.containsInAnyOrder("매장1", "매장2")));

    }

    @Test
    @WithMockUser(username = "test_user", roles = {"CUSTOMER"})
    @DisplayName("단일 매장 조회")
    void testGetStore() throws Exception {
        mockMvc.perform(get("/v1/stores/{storeId}", store1.getStoreId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.storeId").value(store1.getStoreId().toString()))
                .andExpect(jsonPath("$.storeName").value(store1.getStoreName()));
    }

    // TODO : 직원 추가, 삭제 관련 기능 테스트

}
