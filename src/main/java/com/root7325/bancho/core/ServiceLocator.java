package com.root7325.bancho.core;

import com.root7325.bancho.service.PacketDispatcherServiceImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author kate on 03.05.2025
 * <p>
 * Service locator for managing global service instances.
 * Implements singleton pattern to provide centralized access to services.
 */
@Getter
public class ServiceLocator {
    private static final ServiceLocator INSTANCE = new ServiceLocator();

    private final PacketDispatcherServiceImpl packetDispatcherService = new PacketDispatcherServiceImpl();

    public static ServiceLocator getInstance() {
        return INSTANCE;
    }
}
