package jpacoffee.jpacafe.service;

import jpacoffee.jpacafe.domain.Delivery;
import jpacoffee.jpacafe.domain.Member;
import jpacoffee.jpacafe.domain.Order;
import jpacoffee.jpacafe.domain.OrderItem;
import jpacoffee.jpacafe.domain.item.Item;
import jpacoffee.jpacafe.repository.ItemRepository;
import jpacoffee.jpacafe.repository.MemberRepository;
import jpacoffee.jpacafe.repository.OrderRepository;
import jpacoffee.jpacafe.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    //취소

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }
    //검색

    public List<Order> findOrder(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }
}
