package helsinki.commodities;

import helsinki.commodities.meta.InsuranceMetaModel;
import meta_models.MetaModels;
import ua.com.fielden.platform.dao.IEntityDao;
import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;

/**
 * Companion object for entity {@link Insurance}.
 *
 * @author Developers
 *
 */
public interface InsuranceCo extends IEntityDao<Insurance> {
    static final InsuranceMetaModel Insurance_ = MetaModels.Insurance;

    static final IFetchProvider<Insurance> FETCH_PROVIDER = EntityUtils.fetch(Insurance.class).with(
         Insurance_.cost().toPath(), Insurance_.issueDate().toPath(), Insurance_.expirationDate().toPath());

}
