package helsinki.security.tokens.persistent;

import static java.lang.String.format;

import helsinki.commodities.Insurance;
import helsinki.security.tokens.UsersAndPersonnelModuleToken;
import ua.com.fielden.platform.security.tokens.Template;

/**
 * A security token for entity {@link Insurance} to guard Save.
 *
 * @author Developers
 *
 */
public class Insurance_CanSave_Token extends UsersAndPersonnelModuleToken {
    public final static String TITLE = format(Template.SAVE.forTitle(), Insurance.ENTITY_TITLE);
    public final static String DESC = format(Template.SAVE.forDesc(), Insurance.ENTITY_TITLE);
}