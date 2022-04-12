package helsinki.test_user;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import meta_models.MetaModels;
import ua.com.fielden.platform.security.user.meta.UserMetaModel;


/**
 *
 * These tests cover the entity meta-model and its use for entity graph traversal in order to reference entity properties.
 *
 * @author TG Team
 *
 */
public class UserMetaModelTest {

    @Test
    public void user_meta_model() {
        UserMetaModel User_ = MetaModels.User;
        assertEquals("active", User_.active().toPath());
        assertEquals("basedOnUser", User_.basedOnUser().toPath());
        assertEquals("basedOnUser.active", User_.basedOnUser().active().toPath());
        assertEquals("basedOnUser.basedOnUser", User_.basedOnUser().basedOnUser().toPath());
        assertEquals("basedOnUser.basedOnUser.active", User_.basedOnUser().basedOnUser().active().toPath());
        assertEquals("basedOnUser.basedOnUser.basedOnUser", User_.basedOnUser().basedOnUser().basedOnUser().toPath());
    }

}
