package helsinki.commodities;

import com.google.inject.Inject;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import helsinki.security.tokens.persistent.Insurance_CanSave_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;

/**
 * DAO implementation for companion object {@link InsuranceCo}.
 *
 * @author Developers
 *
 */
@EntityType(Insurance.class)
public class InsuranceDao extends CommonEntityDao<Insurance> implements InsuranceCo {

    @Inject
    public InsuranceDao(final IFilter filter) {
        super(filter);
    }

    @Override
    @SessionRequired
    @Authorise(Insurance_CanSave_Token.class)
    public Insurance save(Insurance entity) {
        return super.save(entity);
    }

    @Override
    protected IFetchProvider<Insurance> createFetchProvider() {
        return FETCH_PROVIDER;
    }
}