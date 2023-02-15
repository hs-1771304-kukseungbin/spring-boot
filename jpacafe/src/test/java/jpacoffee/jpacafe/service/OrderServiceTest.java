package jpacoffee.jpacafe.service;

import jakarta.persistence.EntityManager;
import jpacoffee.jpacafe.domain.Address;
import jpacoffee.jpacafe.domain.Member;
import jpacoffee.jpacafe.domain.Order;
import jpacoffee.jpacafe.domain.OrderStatus;
import jpacoffee.jpacafe.domain.item.Drink;
import jpacoffee.jpacafe.domain.item.Item;
import jpacoffee.jpacafe.exception.NotEnoughStockException;
import jpacoffee.jpacafe.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();

        Drink drink = createDrink("커피원두", 10000, 10);

        int orderCount = 2;

        //when

        Long orderId = orderService.order(member.getId(), drink.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 명확해야 한다.", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 10000 * orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.", 8, drink.getStockQuantity());


    }


    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();
        Item item = createDrink("커피원두", 10000, 10);

        int orderCount = 11;
        //when
        orderService.order(member.getId(), item.getId(), orderCount);
        //then
        fail("재고 수량 부족 예외가 발생하야 한다.");
        }
    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Drink drink = createDrink("커피 원두", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), drink.getId(), orderCount);
        //when
        orderService.cancelOrder(orderId);
        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 CANCEL이다.", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 최소된 상품은 그만큼 재고가 증가해야 한다.", 10, drink.getStockQuantity());

    }

//    @Test
//    public void 상품주문_재고수량초과() throws Exception {
//        //given
//
//        //when
//
//        //then
//
//        }
    private Drink createDrink(String name, int price, int stockQuantity) {
        Drink drink = new Drink();
        drink.setName(name);
        drink.setPrice(price);
        drink.setStockQuantity(stockQuantity);
        em.persist(drink);
        return drink;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강동", "1111"));
        em.persist(member);
        return member;
    }
}
