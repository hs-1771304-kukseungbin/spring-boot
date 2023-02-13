package jpacoffee.jpacafe.repository;

import jakarta.persistence.EntityManager;
import jpacoffee.jpacafe.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    //상품 등록,저장
    public void save(Item item) {
        if(item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    //상품 조회
    public Item findONe(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
