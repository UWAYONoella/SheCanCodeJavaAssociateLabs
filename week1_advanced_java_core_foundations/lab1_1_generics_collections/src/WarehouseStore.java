import java.util.ArrayList;
import java.util.List;

public class WarehouseStore<T extends Product> {

    private List<T> items = new ArrayList<>();

    public void addItem(T item) {
        items.add(item);
    }

    public boolean removeItem(String id) {
        return items.removeIf(item -> item.getId().equals(id));
    }

    public List<T> findByCategory(String category) {
        List<T> result = new ArrayList<>();
        for (T item : items) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                result.add(item);
            }
        }
        return result;
    }

    public void displayAll() {
        for (T item : items) {
            System.out.println(item);
        }
    }
}