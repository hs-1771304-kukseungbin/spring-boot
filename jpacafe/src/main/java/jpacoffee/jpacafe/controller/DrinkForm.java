package jpacoffee.jpacafe.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DrinkForm {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String code;
    private String origin;
}
