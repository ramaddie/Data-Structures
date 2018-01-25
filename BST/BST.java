import java.util.Collection;
import java.util.List;
import java.util.ArrayList; // for preorder/postorder/inorder
import java.util.Queue; //for levelorder
import java.util.LinkedList; //for levelorder

/**
 * Your implementation of a binary search tree.
 *
 * @author Maddie Ravichandran
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST.
     * YOU DO NOT NEED TO IMPLEMENT THIS CONSTRUCTOR!
     */
    public BST() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Initializes the BST with the data in the Collection. The data in the BST
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null");
        }
        for (T d : data) {
            add(d);
        }
    }

    @Override
    public void add(T data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("Data to add can't be null");
        }
        root = add(root, data);
    }

    /**
     * adds the data to the current node
     * @param data the data to be added
     * @param node the node where we are adding
     * @return node that is updated with the element added
     */
    private BSTNode<T> add(BSTNode<T> node, T data) {
        if (node == null) {
            size++;
            BSTNode<T> newNode = new BSTNode<>(data);
            return newNode;
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(add(node.getLeft(), data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(add(node.getRight(), data));
        }
        return node;
    }

    @Override
    public T remove(T data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("data to remove can't be null");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        root = remove(root, data, dummy);
        return dummy.getData();
    }

    /**
     * removes the data in the current node, dummy node is what will be returned
     * @param data the data to be added
     * @param node the node where we are removing
     * @param dummy node where removed data will be stored
     * @return node that has updated removal
     * @throws java.util.NoSuchElementException if data not in tree
     */
    private BSTNode<T> remove(BSTNode<T> node, T data, BSTNode<T> dummy)
        throws java.util.NoSuchElementException {
        if (node == null) {
            throw new java.util.NoSuchElementException("Data not in tree");
        }
        int compare = data.compareTo(node.getData());
        if (compare == 0) {
            dummy.setData(node.getData());
            size--;
            if (node.getLeft() != null && node.getRight() != null) {
                BSTNode<T> left = node.getLeft();
                BSTNode<T> right = node.getRight();
                node = removeMin(right, node);
                BSTNode<T> newRight = node.getRight();
                node.setLeft(left);
                node.setRight(right);
                if (node.getRight().getData() == node.getData()) {
                    node.setRight(newRight);
                }
            } else if (node.getLeft() == null && node.getRight() == null) {
                node = null;
            } else if (node.getLeft() != null) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        } else if (compare > 0) {
            node.setRight(remove(node.getRight(), data, dummy));
        } else {
            node.setLeft(remove(node.getLeft(), data, dummy));
        }
        return node;
    }
    /**
     * removes the min of current subtree, goes right first, then all left
     * @param parent the parent of the subtree
     * @param current the current subree
     * @return node that has updated removal
     */
    private BSTNode<T> removeMin(BSTNode<T> current, BSTNode<T> parent) {
        if (current == null) {
            return null;
        } else if (current.getLeft() == null) {
            if (parent != root) {
                parent.setLeft(current.getRight());
            }
            return current;
        }
        return removeMin(current.getLeft(), current);
    }

    @Override
    public T get(T data) throws IllegalArgumentException {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data can't be null");
        }
        return get(root, data);
    }
    /**
     * gets the node holding the data
     * @param data the data to be got
     * @param node the node where we are getting
     * @return node that has the data to get
     * @throws java.util.NoSuchElementException if data not in tree
     */
    private T get(BSTNode<T> node, T data)
        throws java.util.NoSuchElementException {
        if (node == null) {
            throw new java.util.NoSuchElementException("Data not found");
        } else if (data.compareTo(node.getData()) == 0) {
            return node.getData();
        } else if (data.compareTo(node.getData()) > 0) {
            return get(node.getRight(), data);
        } else if (data.compareTo(node.getData()) < 0) {
            return get(node.getLeft(), data);
        }
        return node.getData();
    }

    @Override
    public boolean contains(T data) throws IllegalArgumentException {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data can't be null");
        }
        return contains(data, root);
    }
    /**
     * returns boolean whether data is in tree
     * @param data the data we are looking for in tree
     * @param node the node we are starting traversal in
     * @return true if data is in tree, false otherwise
     */
    private boolean contains(T data, BSTNode<T> node) {
        if (node == null) {
            return false;
        } else if (data.compareTo(node.getData()) == 0) {
            return true;
        } else if (data.compareTo(node.getData()) > 0) {
            return contains(data, node.getRight());
        } else if (data.compareTo(node.getData()) < 0) {
            return contains(data, node.getLeft());
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        List<T> list = new ArrayList<T>();
        preorder(root, list);
        return  list;
    }
    /**
     * Generates preorder traversal of tree
     * @param node we are currently traversing
     * @param list containing nodes in preorder visited
     */
    private void preorder(BSTNode<T> node, List<T> list) {
        if (node != null) {
            list.add(node.getData());
            preorder(node.getLeft(), list);
            preorder(node.getRight(), list);
        }
    }

    @Override
    public List<T> postorder() {
        List<T> list = new ArrayList<T>();
        postorder(root, list);
        return list;
    }
    /**
     * Generates postorder traversal of tree
     * @param node we are currently traversing
     * @param list containing nodes in postorder visited
     */
    public void postorder(BSTNode<T> node, List<T> list) {
        if (node != null) {
            postorder(node.getLeft(), list);
            postorder(node.getRight(), list);
            list.add(node.getData());
        }
    }

    @Override
    public List<T> inorder() {
        List<T> list = new ArrayList<T>();
        inorder(root, list);
        return list;
    }
    /**
     * Generates inorder traversal of tree
     * @param node we are currently traversing
     * @param list containing nodes in order visited
     */
    private void inorder(BSTNode<T> node, List<T> list) {
        if (node != null) {
            inorder(node.getLeft(), list);
            list.add(node.getData());
            inorder(node.getRight(), list);
        }
    }

    @Override
    public List<T> levelorder() {
        List<T> list = new LinkedList<>();
        Queue<BSTNode<T>> queue = new LinkedList<>();
        if (root == null) {
            return list;
        }
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> tempNode = queue.poll();
            list.add(tempNode.getData());
            if (tempNode.getLeft() != null) {
                queue.add(tempNode.getLeft());
            }
            if (tempNode.getRight() != null) {
                queue.add(tempNode.getRight());
            }
        }
        return list;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        int answer = height(root);
        return answer;
    }
    /**
     * Calculates height of tree recursively
     * @param current node we calculate height from
     * @return height of tree
     */
    private int height(BSTNode<T> current) {
        if (current == null) {
            return -1;
        } else {
            return (1
                + Math.max(height(current.getLeft()),
                height(current.getRight())));
        }
    }

    @Override
    public BSTNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }

}
