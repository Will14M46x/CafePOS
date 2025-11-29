package com.cafepos.app.events;

import com.cafepos.domain.Order;

public sealed interface OrderEvent permits OrderCreated, OrderPaid { }

