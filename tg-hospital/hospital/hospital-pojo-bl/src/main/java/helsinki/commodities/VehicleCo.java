package helsinki.commodities;

import helsinki.commodities.meta.VehicleMetaModel;
import meta_models.MetaModels;
import ua.com.fielden.platform.dao.IEntityDao;
import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;

/**
 * Companion object for entity {@link Vehicle}.
 *
 * @author Developers
 *
 */
public interface VehicleCo extends IEntityDao<Vehicle> {
    static final VehicleMetaModel Vehicle_ = MetaModels.Vehicle;

    static final IFetchProvider<Vehicle> FETCH_PROVIDER = EntityUtils.fetch(Vehicle.class).with(
         Vehicle_.color().toPath(), Vehicle_.cost().toPath(), Vehicle_.insurance().toPath());

}
