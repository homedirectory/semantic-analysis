package helsinki.commodities;

import com.google.inject.Inject;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import helsinki.security.tokens.persistent.House_CanSave_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;

/**
 * DAO implementation for companion object {@link HouseCo}.
 *
 * @author Developers
 *
 */
@EntityType(House.class)
public class HouseDao extends CommonEntityDao<House> implements HouseCo {

    @Inject
    public HouseDao(final IFilter filter) {
        super(filter);
    }

    @Override
    @SessionRequired
    @Authorise(House_CanSave_Token.class)
    public House save(House entity) {
        return super.save(entity);
    }

    @Override
    protected IFetchProvider<House> createFetchProvider() {
        return FETCH_PROVIDER;
    }
}