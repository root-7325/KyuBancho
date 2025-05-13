package com.root7325.bancho.core;

import com.root7325.bancho.service.PacketDispatcherServiceImpl;
import com.root7325.dao.UserDAOImpl;
import com.root7325.bancho.service.interfaces.IPacketDispatcherService;
import com.root7325.utils.HibernateUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kate on 03.05.2025
 * <p>
 * Service locator for managing global service instances.
 * Implements singleton pattern to provide centralized access to services.
 */
@Slf4j
@Getter
public class ServiceLocator {
    private static final ServiceLocator INSTANCE = new ServiceLocator();

    private final IPacketDispatcherService packetDispatcherService;
    private final UserDAOImpl userDAO;
    
    private ServiceLocator() {
        this.packetDispatcherService = new PacketDispatcherServiceImpl();
        this.userDAO = new UserDAOImpl(HibernateUtil.getSessionFactory());
    }
    
    public static ServiceLocator getInstance() {
        return INSTANCE;
    }
}
