# SheCanCodeJavaAssociateLabs

# Collections Benchmark Results

## Observations

- ArrayList: Best for iteration and index-based access
- LinkedList: Better for frequent insert/delete operations
- HashSet: Fastest for search (O(1))
- TreeSet: Maintains sorted order but slower due to tree balancing

## Conclusion

For "recently viewed products":
👉 HashSet is optimal for uniqueness + fast lookup
👉 But ArrayList can also be used for ordered history tracking
