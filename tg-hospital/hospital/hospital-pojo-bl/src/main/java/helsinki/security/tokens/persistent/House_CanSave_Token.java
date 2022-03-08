package helsinki.security.tokens.persistent;

import static java.lang.String.format;

import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.security.tokens.Template;
import helsinki.commodities.House;
import helsinki.security.tokens.UsersAndPersonnelModuleToken;

/**
 * A security token for entity {@link House} to guard Save.
 *
 * @author Developers
 *
 */
public class House_CanSave_Token extends UsersAndPersonnelModuleToken {
    public final static String TITLE = format(Template.SAVE.forTitle(), House.ENTITY_TITLE);
    public final static String DESC = format(Template.SAVE.forDesc(), House.ENTITY_TITLE);
}