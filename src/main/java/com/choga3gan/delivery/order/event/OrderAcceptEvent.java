package com.choga3gan.delivery.order.event;

import java.util.UUID;

public record OrderAcceptEvent(
        UUID orderId
) { }
