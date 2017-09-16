package com.formento.projections.house;

import static com.google.common.base.Preconditions.checkNotNull;

import com.formento.projections.config.UserSession;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
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
    public void handleHouseCreate(@NotNull final House house) {
        logger.debug(house.toString());
        house.setUser(userSession.getUserSession().getUsername());
    }

    @HandleBeforeDelete
    public void handleHouseDelete(@NotNull final House house) {
        checkNotNull(house.getId());
        handleHouseDelete(house.getId());
    }

    @HandleBeforeDelete
    public void handleHouseDelete(@NotNull final String id) {
        // validate id
        logger.debug("validate before delete house " + id);
    }

}
