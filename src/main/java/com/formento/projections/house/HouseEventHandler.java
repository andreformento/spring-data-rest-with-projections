package com.formento.projections.house;

import com.formento.projections.config.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@RepositoryEventHandler
@Component
public class HouseEventHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserSession userSession;

    public HouseEventHandler(UserSession userSession) {
        this.userSession = userSession;
    }

    @HandleBeforeCreate
    @HandleBeforeSave
    public void handleHouseCreate(House house) {
        logger.debug(house.toString());
        house.setUser(userSession.getUserSession().getUsername());
    }

}
