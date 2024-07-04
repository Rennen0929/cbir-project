package dev.rennen.webapp.utils;

import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * 用于实现相似度排序功能
 * @param <T>
 */
public class BoundedPriorityQueue<T> {
    private PriorityQueue<T> queue;
    private int maxSize;

    public BoundedPriorityQueue(int maxSize, Comparator<T> comparator) {
        this.maxSize = maxSize;
        this.queue = new PriorityQueue<>(comparator);
    }

    public boolean add(T element) {
        if (queue.size() < maxSize) {
            queue.add(element);
            return true;
        } else {
            T smallest = queue.peek();
            if (queue.comparator().compare(element, smallest) > 0) {
                queue.poll();
                queue.add(element);
                return true;
            }
            return false;
        }
    }

    public boolean addAll(Collection<T> elements) {
        boolean changed = false;
        for (T element : elements) {
            changed |= add(element);
        }
        return changed;
    }

    public T poll() {
        return queue.poll();
    }

    public T peek() {
        return queue.peek();
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public List<T> toList() {
        return List.copyOf(queue);
    }
}
