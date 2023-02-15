package jpacoffee.jpacafe.controller;

import jpacoffee.jpacafe.domain.item.Drink;
import jpacoffee.jpacafe.domain.item.Item;
import jpacoffee.jpacafe.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new DrinkForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(DrinkForm form) {
        Drink drink = new Drink();
        drink.setName(form.getName());
        drink.setPrice(form.getPrice());
        drink.setStockQuantity(form.getStockQuantity());
        drink.setCode(form.getCode());
        drink.setOrigin(form.getOrigin());

        itemService.saveItem(drink);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Drink item = (Drink) itemService.findOne(itemId);

        DrinkForm form = new DrinkForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setCode(item.getCode());
        form.setOrigin(item.getOrigin());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable String itemId, @ModelAttribute("form") DrinkForm form) {

        Drink drink = new Drink();
        drink.setId(form.getId());
        drink.setName(form.getName());
        drink.setPrice(form.getPrice());
        drink.setStockQuantity(form.getStockQuantity());
        drink.setCode(form.getCode());
        drink.setOrigin(form.getOrigin());

        itemService.saveItem(drink);
        return "redirect:/items";
    }
}
