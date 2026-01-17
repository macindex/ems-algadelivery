package com.algaworks.algadelivery.delivery.tracking.domain.service;

import com.algaworks.algadelivery.delivery.tracking.domain.exception.DomainException;
import com.algaworks.algadelivery.delivery.tracking.domain.model.Delivery;
import com.algaworks.algadelivery.delivery.tracking.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryCheckpointService {

    private final DeliveryRepository deliveryRepository;
    private final Clock clock; // Injetado para garantir testabilidade do tempo

    public void place(UUID deliveryId) {
        Delivery delivery = findOrThrow(deliveryId);

        // Passamos o timestamp gerado pelo clock
        delivery.place(OffsetDateTime.now(clock));

        deliveryRepository.save(delivery);
    }

    public void pickUp(UUID deliveryId, UUID courierId) {
        Delivery delivery = findOrThrow(deliveryId);

        // Sugestão: Atualize o método pickUp na entidade para também aceitar OffsetDateTime
        delivery.pickUp(courierId, OffsetDateTime.now(clock));

        deliveryRepository.save(delivery);
    }

    public void complete(UUID deliveryId) {
        Delivery delivery = findOrThrow(deliveryId);

        // Sugestão: Atualize markAsDelivered na entidade para aceitar OffsetDateTime
        delivery.markAsDelivered(OffsetDateTime.now(clock));

        deliveryRepository.save(delivery);
    }

    // Método privado para evitar repetição de código (DRY) e melhorar mensagens de erro
    private Delivery findOrThrow(UUID deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DomainException("Delivery não encontrado com o ID: " + deliveryId));
    }
}
