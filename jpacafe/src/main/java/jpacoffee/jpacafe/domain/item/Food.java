package jpacoffee.jpacafe.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("F")
@Getter @Setter
public class Food extends Item{

    private String sandwich;
    private String pasta;
}
