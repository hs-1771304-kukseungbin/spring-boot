package jpacoffee.jpacafe.repository;

import jpacoffee.jpacafe.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}


