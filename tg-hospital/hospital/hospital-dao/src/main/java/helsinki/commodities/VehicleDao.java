package helsinki.commodities;

import com.google.inject.Inject;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import helsinki.security.tokens.persistent.Vehicle_CanSave_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;

/**
 * DAO implementation for companion object {@link VehicleCo}.
 *
 * @author Developers
 *
 */
@EntityType(Vehicle.class)
public class VehicleDao extends CommonEntityDao<Vehicle> implements VehicleCo {

    @Inject
    public VehicleDao(final IFilter filter) {
        super(filter);
    }

    @Override
    @SessionRequired
    @Authorise(Vehicle_CanSave_Token.class)
    public Vehicle save(Vehicle entity) {
        return super.save(entity);
    }

    @Override
    protected IFetchProvider<Vehicle> createFetchProvider() {
        return FETCH_PROVIDER;
    }
}