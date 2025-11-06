package com.choga3gan.delivery.order.service;

import com.choga3gan.delivery.global.security.UserDetailsImpl;
import com.choga3gan.delivery.global.utils.exception.UnauthorizedException;
import com.choga3gan.delivery.order.domain.Order;
import com.choga3gan.delivery.order.domain.RoleCheck;
import com.choga3gan.delivery.order.repository.OrderRepository;
import com.choga3gan.delivery.product.domain.Product;
import com.choga3gan.delivery.product.repository.ProductRepository;
import com.choga3gan.delivery.store.repository.StoreRepository;
import com.choga3gan.delivery.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderRoleCheckService implements RoleCheck {
    private final OrderRepository  orderRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    @Override
    public void check(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(UnauthorizedException::new);
        UUID productId = order.getOrderItems().getFirst().getProductId();
        Product product = productRepository.findById(productId).orElseThrow(UnauthorizedException::new);
        UUID ownerID = product.getStore().getUser().getId().getId();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {
            User user = userDetails.getUser();
            boolean isAdmin = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER") || a.getAuthority().equals("ROLE_MASTER"));

            // 주문자가 또는 가게 주인이 아니거나 관리자가 아니라면
            UUID userId = user.getId().getId();
            if (!userId.equals(order.getUserId()) && !userId.equals(ownerID) && !isAdmin) {
                throw new UnauthorizedException();
            }
        }

    }
}
