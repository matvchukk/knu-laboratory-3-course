package main.utils;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicMarkableReference;
import main.utils.Utils;
public class CustomSkipList {
    public class Node{
        final int value;
        final int level;
        final AtomicMarkableReference<Node>[] next;

        boolean toRemove;

        public int getValue() {
            return value;
        }

        public AtomicMarkableReference<Node>[] getNext() {
            return next;
        }

        @SuppressWarnings("unchecked")
        public Node(int value,int level, int maxLevel){
            this.value = value;
            this.level = level;
            this.next = (AtomicMarkableReference<Node>[])
                    new AtomicMarkableReference<?>[maxLevel];
            this.toRemove = false;
        }

        @Override
        public String toString(){
            return Integer.toString(value);
        }
    }

    final AtomicMarkableReference<Node> header;
    final AtomicInteger currentListLevel;
    final AtomicInteger size;
    final int maxLevel;

    public CustomSkipList(int maxLevel){
        this.currentListLevel = new AtomicInteger(0);
        this.size = new AtomicInteger(0);
        this.maxLevel = maxLevel;
        Node headerNode = new Node(Integer.MAX_VALUE, 0, maxLevel);
        header = new AtomicMarkableReference<>(headerNode, true);
        for (int i = 0; i < maxLevel; i++){
            headerNode.next[i] = header;
        }
    }

    public int size(){
        return size.get();
    }

    public AtomicInteger getSize(){
        return size;
    }

    public AtomicMarkableReference<Node> getHeader(){
        return header;
    }

    public boolean contains (int value){
        AtomicMarkableReference<Node> currentListElement = this.header;
        AtomicMarkableReference<Node> previousListElement = this.header;
        Node currentNode = currentListElement.getReference();

        int level = currentListLevel.get() - 1;

        for (; level >= 0;level--){
            currentListElement = currentNode.next[level];
            currentNode = currentListElement.getReference();

            while (currentNode.value < value){
                if(level == currentListLevel.get() - 1 && currentListElement == this.header){
                    return false;
                }
                previousListElement = currentListElement;
                currentListElement = currentNode.next[level];
                currentNode = currentListElement.getReference();
            }

            currentListElement = previousListElement;
            currentNode = currentListElement.getReference();
        }

        currentListElement = currentNode.next[0];
        currentNode = currentListElement.getReference();

        return currentNode.value == value;
    }

    public synchronized boolean add(Integer inputValue){
        Node[] update = new Node[maxLevel];
        Node currentNode = formUpdateArray(update, inputValue);
        int levels = currentListLevel.get();

        if(currentNode.value == inputValue) {
            return false;
        } else {
            int newLevel = Utils.getRandomLevel(this.maxLevel);

            if (newLevel >= levels) {
                currentListLevel.incrementAndGet();
                levels += 1;
                newLevel = levels - 1;
                update[newLevel] = header.getReference();
            }

            Node newNode = new Node(inputValue, newLevel, maxLevel);
            AtomicMarkableReference<Node> atomicNewNode =
                    new AtomicMarkableReference<>(newNode, true);

            for (int level = 0; level < levels; level++) {

                if(update[level].toRemove && level == 0){
                    return add(inputValue);
                }

                if(update[level].toRemove){
                    return true;
                }

                if(level > 0){
                    if(update[level - 1].next[level - 1].getReference().toRemove){
                        return true;
                    }
                }

                newNode.next[level] = update[level].next[level];
                update[level].next[level] = atomicNewNode;
            }

            size.incrementAndGet();

            return true;
        }
    }

    private Node formUpdateArray(Node[] update, Integer value){
        AtomicMarkableReference<Node> currentListElement = this.header;
        AtomicMarkableReference<Node> previousListElement;
        Node currentNode = currentListElement.getReference();

        for(int level = currentListLevel.get() - 1; level >= 0; level--){
            previousListElement = currentListElement;
            currentListElement = currentNode.next[level];
            currentNode = currentListElement.getReference();

            while (currentNode.value < value){
                if(currentNode.toRemove){
                    unlink(previousListElement.getReference(), level);
                    return formUpdateArray(update,value);
                }
                previousListElement = currentListElement;
                currentListElement = currentNode.next[level];
                currentNode = currentListElement.getReference();
            }
            update[level] = previousListElement.getReference();

            currentListElement = previousListElement;
            currentNode = currentListElement.getReference();
        }

        currentListElement = currentNode.next[0];
        currentNode = currentListElement.getReference();

        return currentNode;
    }



    public boolean remove(Integer inputValue){
        Node[] update = new Node[maxLevel];
        Node currentNode = formUpdateArray(update, inputValue);
        int levels = currentListLevel.get();

        if (currentNode.value == inputValue) {
            currentNode.toRemove = true;
            //for (int level = 0; level < levels; level++) {
            for (int level = levels - 1; level >= 0; level--) {
                update[level].next[level] = currentNode.next[level];
                if (update[level].next[level] == null) {
                    update[level].next[level] = header;
                }
            }

            size.decrementAndGet();

            //delete excess levels of the tower
            while (levels > 1 && header.getReference().next[levels] == header) {
                levels = currentListLevel.decrementAndGet();
            }

            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        AtomicMarkableReference<Node> current = header;
        Node currentNode = current.getReference();
        current = currentNode.next[0];
        currentNode = current.getReference();

        StringBuilder out = new StringBuilder();
        out.append("[");
        while(current != header){
            out.append(currentNode.value);
            current = currentNode.next[0];

            currentNode = current.getReference();
            if(current != header){
                out.append(" ");
            }
        }
        out.append("]");

        return out.toString();
    }

    private void unlink(Node previousElement, int level){
        previousElement.next[level] = previousElement.next[level].getReference().next[level];
    }
}