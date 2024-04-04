package world.evgereo.household.householdapi.security.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import world.evgereo.household.householdapi.service.HouseService;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class HouseAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext>  {
    private final HouseService houseService;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        return new AuthorizationDecision(
                authentication.get().getPrincipal().equals(
                    houseService.getHouse(Long.valueOf(context.getVariables().get("houseId")))
                    .getOwner().getId()));
    }
}
