package jpacoffee.jpacafe.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("E")
@Getter @Setter
public class Dessert extends Item{

    private String cake;
    private String cookie;
}
