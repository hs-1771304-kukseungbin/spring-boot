package jpacoffee.jpacafe.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("D")
@Getter @Setter
public class Drink extends Item {
    private String code;
    private String origin;
}
