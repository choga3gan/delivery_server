package com.choga3gan.delivery.store.service;

import com.choga3gan.delivery.global.utils.service.SecurityUtilService;
import com.choga3gan.delivery.store.domain.Store;
import com.choga3gan.delivery.store.exception.StoreNotEditableException;
import com.choga3gan.delivery.store.exception.StoreNotFoundException;
import com.choga3gan.delivery.store.repository.StoreRepository;
import com.choga3gan.delivery.user.domain.User;
import com.choga3gan.delivery.user.exception.UserNotFoundException;
import com.choga3gan.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreAuthorizationService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final SecurityUtilService securityUtilService;

    public void validateStoreAccess(UUID storeId) {
        User user = userRepository.findByUsername(securityUtilService.getCurrentUsername())
                .orElseThrow(UserNotFoundException::new);

        if (user.hasRole("ROLE_MANAGER") || user.hasRole("ROLE_MASTER")) return;

        if (user.hasRole("ROLE_OWNER")) {
            Store store = storeRepository.findByStoreId(storeId).orElseThrow(StoreNotFoundException::new);
            if (!store.getUser().getId().getId().equals(user.getId().getId())) {
                throw new StoreNotEditableException();
            }
        }
    }
}
