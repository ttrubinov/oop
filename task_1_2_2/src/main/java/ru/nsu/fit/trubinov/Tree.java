package ru.nsu.fit.trubinov;

import java.util.*;

/**
 * The tree implementing collection with DFS and BFS algorithms.
 *
 * @param <T> type of the elements
 */
public class Tree<T> implements Collection<T> {
    private final Node<T> root;
    private int cnt;
    private int cntChanges;

    /**
     * Creates an empty tree.
     */
    public Tree() {
        cnt = 0;
        cntChanges = 0;
        root = new Node<>();
        root.children = new ArrayList<>();
    }

    /**
     * Determines the number of elements in the tree.
     *
     * @return the number of elements in the tree
     */
    @Override
    public int size() {
        return cnt;
    }

    /**
     * Determines if the tree contains elements.
     *
     * @return true if the tree is empty
     */
    @Override
    public boolean isEmpty() {
        return cnt == 0;
    }

    /**
     * Determines if the tree contains the specified element.
     *
     * @param o element whose presence in the tree is to be tested
     * @return true if this collection contains the specified element
     */
    @Override
    public boolean contains(Object o) {
        return stream().anyMatch(elem -> elem.equals(o));
    }

    /**
     * Returns a DFS iterator over the elements in the tree.
     * Elements will be returned in depth-first order.
     *
     * @return a DFS Iterator over the elements in the tree
     */
    @Override
    public Iterator<T> iterator() {
        return new DFS();
    }

    /**
     * Returns a BFS iterator over the elements in the tree.
     * Elements will be returned in breadth-first order.
     *
     * @return a BFS Iterator over the elements in the tree
     */
    public Iterator<T> iteratorBFS() {
        return new BFS();
    }

    /**
     * Returns an array containing all elements in the tree.
     * Elements will be in depth-first order.
     *
     * @return an array containing all elements in the tree
     */
    @Override
    public Object[] toArray() {
        return toArray(new Object[cnt]);
    }

    /**
     * Returns an array containing all elements in the tree.
     * The runtime type of the returned array is that of the specified array.
     * Elements will be in depth-first order.
     *
     * @return an array containing all elements in the tree
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T1> T1[] toArray(T1[] a) {
        List<T> list = new ArrayList<>();
        for (T elem : this) {
            list.add(elem);
        }
        return (T1[]) list.toArray();
    }

    /**
     * Adds element to the tree.
     *
     * @param t element to add
     * @return true if the element is added
     * @throws NullPointerException if the element is null
     */
    @Override
    public boolean add(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        Node<T> node = new Node<>();
        node.elem = t;
        node.parent = root;
        root.children.add(node);
        cnt++;
        cntChanges++;
        return true;
    }

    /**
     * Removes a single instance of the specified element from this collection.
     *
     * @param o element to be removed from the tree
     * @return true if the element is in the tree
     * @throws NullPointerException if the element is null
     */
    @Override
    public boolean remove(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        var iterator = iterator();
        while (iterator.hasNext()) {
            T elem = iterator.next();
            if (elem.equals(o)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the tree contains all elements in the specified collection.
     *
     * @param c collection to be checked for containment in the tree
     * @return true if the tree contains all elements in the specified collection.
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object elem : c) {
            if (!contains(elem)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds all elements in the specified collection to the tree.
     *
     * @param c collection containing elements to be added to the tree
     * @return true if this collection changed as a result of the call
     * @throws NullPointerException if the collection is null
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        c.forEach(this::add);
        return true;
    }

    /**
     * Removes all the tree's elements,
     * that are also contained in the specified collection.
     *
     * @param c collection containing elements to be removed from the tree
     * @return true if this collection changed as a result of the call
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        c.forEach(this::remove);
        return true;
    }

    /**
     * Retains only the elements in the tree,
     * that are contained in the specified collection.
     *
     * @param c collection containing elements to be retained in the tree
     * @return true if this collection changed as a result of the call
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        boolean flag = false;
        ArrayList<T> toRemove = new ArrayList<>();
        for (T elem : this) {
            if (!c.contains(elem)) {
                toRemove.add(elem);
                flag = true;
            }
        }
        for (T elem : toRemove) {
            remove(elem);
        }
        return flag;
    }

    /**
     * Removes all elements from the tree.
     */
    @Override
    public void clear() {
        cnt = 0;
        cntChanges = 0;
        root.elem = null;
        root.children.clear();
    }

    private static class Node<T> {
        private T elem;
        private ArrayList<Node<T>> children;
        private Node<T> parent;

        private Node() {
            elem = null;
            children = new ArrayList<>();
            parent = null;
        }
    }

    /**
     * An iterator over the elements in the tree by depth-first search algorithm.
     */
    public class DFS implements Iterator<T> {
        private final Stack<Integer> stackOfIDs = new Stack<>();
        private final int expectedCntChanges = cntChanges;
        private Node<T> node = root;
        private int id = -1;

        /**
         * Determines if the iteration has more elements.
         *
         * @return true if the iteration has more elements
         * @throws ConcurrentModificationException if the iterator made changes
         */
        @Override
        public boolean hasNext() {
            if (cntChanges != expectedCntChanges) {
                throw new ConcurrentModificationException();
            }
            while (id + 1 == node.children.size()) {
                if (node.parent == null) {
                    return false;
                }
                node = node.parent;
                id = stackOfIDs.pop();
            }
            return true;
        }

        /**
         * Returns the next element of the tree if it exists.
         *
         * @return the next element of the tree
         * @throws NoSuchElementException if there are no more element
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            while (id + 1 == node.children.size()) {
                id = stackOfIDs.pop();
                node = node.parent;
            }
            stackOfIDs.push(id + 1);
            node = node.children.get(id + 1);
            id = -1;
            return node.elem;
        }

        /**
         * Removes current node of the tree.
         */
        @Override
        public void remove() {
            if (node.parent == null) {
                clear();
                return;
            }
            node.parent.children.remove(node);
            for (Node<T> child : node.children) {
                node.parent.children.add(child);
                child.parent = node.parent;
            }
            cnt--;
            cntChanges++;
        }
    }

    /**
     * An iterator over the elements in the tree by breadth-first search algorithm.
     */
    public class BFS implements Iterator<T> {
        private final Queue<Node<T>> queue = new LinkedList<>();
        private final int expectedCntChanges = cntChanges;
        private Node<T> node = root;


        private BFS() {
            queue.addAll(root.children);
        }

        /**
         * Determines if the iteration has more elements.
         *
         * @return true if the iteration has more elements
         * @throws ConcurrentModificationException if the iterator made changes
         */
        @Override
        public boolean hasNext() {
            if (cntChanges != expectedCntChanges) {
                throw new ConcurrentModificationException();
            }
            return (!queue.isEmpty());
        }

        /**
         * Returns the next element of the tree if it exists.
         *
         * @return the next element of the tree
         * @throws NoSuchElementException if there are no more element
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            node = queue.remove();
            queue.addAll(node.children);
            return node.elem;
        }

        /**
         * Removes current node of the tree.
         */
        @Override
        public void remove() {
            if (node.parent == null) {
                clear();
                return;
            }
            node.parent.children.remove(node);
            for (Node<T> child : node.children) {
                node.parent.children.add(child);
                child.parent = node.parent;
            }
            cnt--;
            cntChanges++;
        }
    }
}