package helsinki.commodities;

import java.math.BigDecimal;
import java.util.Date;

import ua.com.fielden.platform.entity.ActivatableAbstractEntity;
import ua.com.fielden.platform.entity.DynamicEntityKey;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.CompositeKeyMember;
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
import ua.com.fielden.platform.entity.annotation.mutator.BeforeChange;
import ua.com.fielden.platform.entity.annotation.mutator.Handler;
import ua.com.fielden.platform.entity.validation.MaxLengthValidator;
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
@CompanionObject(InsuranceCo.class)
@MapEntityTo
@DescTitle("Description")
@DisplayDescription
@DescRequired
public class Insurance extends ActivatableAbstractEntity<DynamicEntityKey> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(Insurance.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();
    
    @IsProperty(length = 255)
    @MapTo
    @Title(value = "Number", desc = "A unique insurance document number")
    @CompositeKeyMember(1)
    @BeforeChange(@Handler(MaxLengthValidator.class))
    private String number;

    @IsProperty
    @MapTo
    @Title(value = "Cost", desc = "Cost of this insurance")
    private BigDecimal cost;
    
    @IsProperty
    @MapTo
    @Title(value = "Issue Date", desc = "The date when this insurance was issued")
    private Date issueDate;
    
    @IsProperty
    @MapTo
    @Title(value = "Expiration date")
    private Date expirationDate;
    
    @Observable
    public Insurance setNumber(final String number) {
        this.number = number;
        return this;
    }

    public String getNumber() {
        return number;
    }

    @Observable
    public Insurance setExpirationDate(final Date expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    @Observable
    public Insurance setIssueDate(final Date issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    @Observable
    public Insurance setCost(final BigDecimal cost) {
        this.cost = cost;
        return this;
    }

    public BigDecimal getCost() {
        return cost;
    }
}
