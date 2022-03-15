package helsinki.commodities;

import java.math.BigDecimal;

import ua.com.fielden.platform.annotations.GenerateMetaModel;
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
@CompanionObject(VehicleCo.class)
@MapEntityTo
@DescTitle("Description")
@DisplayDescription
@DescRequired
@GenerateMetaModel
public class Vehicle extends ActivatableAbstractEntity<DynamicEntityKey> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(Vehicle.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();
    
    @IsProperty
    @MapTo
    @Title(value = "Color", desc = "Color of this vehicle")
    private String color;
    
    @IsProperty
    @MapTo
    @Title(value = "Cost", desc = "Cost of this vehicle")
    private BigDecimal cost;
    
    @IsProperty
    @MapTo
    @Title(value = "Insurance", desc = "Insurance issued for this vehicle")
    private Insurance insurance;

    @Observable
    public Vehicle setInsurance(final Insurance insurance) {
        this.insurance = insurance;
        return this;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    @Observable
    public Vehicle setCost(final BigDecimal cost) {
        this.cost = cost;
        return this;
    }

    public BigDecimal getCost() {
        return cost;
    }

    @Observable
    public Vehicle setColor(final String color) {
        this.color = color;
        return this;
    }

    public String getColor() {
        return color;
    }
}
