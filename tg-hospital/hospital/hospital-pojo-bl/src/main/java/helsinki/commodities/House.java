package helsinki.commodities;

import java.math.BigDecimal;

import ua.com.fielden.platform.entity.ActivatableAbstractEntity;
import ua.com.fielden.platform.entity.DynamicEntityKey;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.DescRequired;
import ua.com.fielden.platform.entity.annotation.DescTitle;
import ua.com.fielden.platform.entity.annotation.DisplayDescription;
import ua.com.fielden.platform.entity.annotation.IsProperty;
import ua.com.fielden.platform.entity.annotation.KeyTitle;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.MapEntityTo;
import ua.com.fielden.platform.entity.annotation.MapTo;
import ua.com.fielden.platform.entity.annotation.Observable;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.utils.Pair;

/**
 * Master entity object.
 *
 * @author Developers
 *
 */
@KeyType(DynamicEntityKey.class)
@KeyTitle("Key")
@CompanionObject(HouseCo.class)
@MapEntityTo
@DescTitle("Description")
@DisplayDescription
@DescRequired
public class House extends ActivatableAbstractEntity<DynamicEntityKey> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(House.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();
    
    @IsProperty
    @MapTo
    @Title(value = "Area")
    private BigDecimal area;
    
    @IsProperty
    @MapTo
    @Title(value = "Address")
    private String address;

    @IsProperty
    @MapTo
    @Title(value = "Insurance", desc = "Insurance issued for this house")
    private Insurance insurance;

    @Observable
    public House setInsurance(final Insurance insurance) {
        this.insurance = insurance;
        return this;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    @Observable
    public House setAddress(final String address) {
        this.address = address;
        return this;
    }

    public String getAddress() {
        return address;
    }

    @Observable
    public House setArea(final BigDecimal area) {
        this.area = area;
        return this;
    }

    public BigDecimal getArea() {
        return area;
    }
}
