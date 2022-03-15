package helsinki.commodities;

import helsinki.commodities.meta.HouseMetaModel;
import meta_models.MetaModels;
import ua.com.fielden.platform.dao.IEntityDao;
import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;

/**
 * Companion object for entity {@link House}.
 *
 * @author Developers
 *
 */
public interface HouseCo extends IEntityDao<House> {
    static final HouseMetaModel House_ = MetaModels.House;

    static final IFetchProvider<House> FETCH_PROVIDER = EntityUtils.fetch(House.class).with(
         House_.area, House_.address, House_.insurance.toString());

}
