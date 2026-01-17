package com.algaworks.algadelivery.delivery.tracking.domain.model;

import com.algaworks.algadelivery.delivery.tracking.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryTest {

    @Test
    public void shouldChangeToPlaced() {
        // 1. Arrange: Definimos um tempo fixo para o teste
        Delivery delivery = Delivery.draft();
        OffsetDateTime now = OffsetDateTime.now();

        // 2. Act: Passamos o tempo explicitamente para os métodos
        delivery.editPreparationDetails(createdValidPreparationDetails(), now);
        delivery.place(now);

        // 3. Assert
        assertEquals(DeliveryStatus.WAITING_FOR_COURIER, delivery.getStatus());

        // Agora o teste é muito mais rigoroso: comparamos o valor exato
        assertEquals(now, delivery.getPlacedAt());
        assertEquals(now.plus(createdValidPreparationDetails().getExpectedDeliveryTime()),
                delivery.getExpectedDeliveryAt());
    }@Test
    public void shouldChangeToPlaced() {
        // 1. Arrange: Definimos um tempo fixo para o teste
        Delivery delivery = Delivery.draft();
        OffsetDateTime now = OffsetDateTime.now();

        // 2. Act: Passamos o tempo explicitamente para os métodos
        delivery.editPreparationDetails(createdValidPreparationDetails(), now);
        delivery.place(now);

        // 3. Assert
        assertEquals(DeliveryStatus.WAITING_FOR_COURIER, delivery.getStatus());

        // Teste mais rigoroso: comparamos o valor exato
        assertEquals(now, delivery.getPlacedAt());
        assertEquals(now.plus(createdValidPreparationDetails().getExpectedDeliveryTime()),
                delivery.getExpectedDeliveryAt());
    }

    @Test
    public void shouldNotPlaceWhenDetailsAreMissing() {
        // Arrange
        Delivery delivery = Delivery.draft();
        OffsetDateTime now = OffsetDateTime.now();

        // Act & Assert
        // O teste agora passa o parâmetro 'now' para o método place
        assertThrows(DomainException.class, () -> {
            delivery.place(now);
        });

        // Verificamos se o estado do objeto permaneceu íntegro (não foi alterado)
        assertEquals(DeliveryStatus.DRAFT, delivery.getStatus());
        assertNull(delivery.getPlacedAt());
    }



    private Delivery.PreparationDetails createdValidPreparationDetails() {
        ContactPoint sender = ContactPoint.builder()
                .zipCode("00000-000")
                .street("Rua São Paulo")
                .number("100")
                .complement("Sala 401")
                .name("João Silva")
                .phone("(11) 90000-1234")
                .build();


        ContactPoint recipient = ContactPoint.builder()
                .zipCode("12331-342")
                .street("Rua Brasil")
                .number("500")
                .complement("")
                .name("Maria Silva")
                .phone("(11) 91345-1332")
                .build();



        return Delivery.PreparationDetails.builder()
                .sender(sender)
                .recipient(recipient)
                .distanceFee(new BigDecimal("15.00"))
                .courierPayout(new BigDecimal("5.00"))
                .expectedDeliveryTime(Duration.ofHours(5))
                .build();
    }

}