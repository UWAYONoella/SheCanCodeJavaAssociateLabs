import java.util.*;

public class CollectionBenchmark {

    public static void main(String[] args) {

        int size = 1000;

        List<Product> arrayList = new ArrayList<>();
        List<Product> linkedList = new LinkedList<>();
        Set<Product> hashSet = new HashSet<>();
        Set<Product> treeSet = new TreeSet<>(Comparator.comparing(Product::getId));


        // INSERTION TEST

        long start, end;

        // ArrayList
        start = System.nanoTime();
        for (int i = 0; i < size; i++) {
            arrayList.add(new Product("P" + i, "Item" + i, "Cat", (double) i));
        }
        end = System.nanoTime();
        System.out.println("ArrayList Insert: " + (end - start));

        // LinkedList
        start = System.nanoTime();
        for (int i = 0; i < size; i++) {
            linkedList.add(new Product("P" + i, "Item" + i, "Cat", (double) i));
        }
        end = System.nanoTime();
        System.out.println("LinkedList Insert: " + (end - start));

        // HashSet
        start = System.nanoTime();
        for (int i = 0; i < size; i++) {
            hashSet.add(new Product("P" + i, "Item" + i, "Cat", (double) i));
        }
        end = System.nanoTime();
        System.out.println("HashSet Insert: " + (end - start));

        // TreeSet
        start = System.nanoTime();
        for (int i = 0; i < size; i++) {
            treeSet.add(new Product("P" + i, "Item" + i, "Cat", (double) i));
        }
        end = System.nanoTime();
        System.out.println("TreeSet Insert: " + (end - start));


        // LOOKUP TEST

        start = System.nanoTime();
        arrayList.contains(arrayList.get(500));
        end = System.nanoTime();
        System.out.println("ArrayList Lookup: " + (end - start));

        start = System.nanoTime();
        linkedList.contains(linkedList.get(500));
        end = System.nanoTime();
        System.out.println("LinkedList Lookup: " + (end - start));

        start = System.nanoTime();
        hashSet.contains(new Product("P500", "Item500", "Cat", 500.0));
        end = System.nanoTime();
        System.out.println("HashSet Lookup: " + (end - start));

        start = System.nanoTime();
        treeSet.contains(new Product("P500", "Item500", "Cat", 500.0));
        end = System.nanoTime();
        System.out.println("TreeSet Lookup: " + (end - start));

        // ITERATION TEST

        start = System.nanoTime();
        for (Product p : arrayList) {}
        end = System.nanoTime();
        System.out.println("ArrayList Iterate: " + (end - start));

        start = System.nanoTime();
        for (Product p : linkedList) {}
        end = System.nanoTime();
        System.out.println("LinkedList Iterate: " + (end - start));

        start = System.nanoTime();
        for (Product p : hashSet) {}
        end = System.nanoTime();
        System.out.println("HashSet Iterate: " + (end - start));

        start = System.nanoTime();
        for (Product p : treeSet) {}
        end = System.nanoTime();
        System.out.println("TreeSet Iterate: " + (end - start));
    }
}