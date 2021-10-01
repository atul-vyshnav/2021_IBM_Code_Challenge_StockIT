package org.apache.logging.log4j.spi;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.logging.log4j.ThreadContext;

public class DefaultThreadContextStack implements ThreadContextStack {
    private static final long serialVersionUID = 5050501;
    private static final ThreadLocal<MutableThreadContextStack> stack = new ThreadLocal<>();
    private final boolean useStack;

    public DefaultThreadContextStack(boolean z) {
        this.useStack = z;
    }

    private MutableThreadContextStack getNonNullStackCopy() {
        MutableThreadContextStack mutableThreadContextStack = stack.get();
        return (MutableThreadContextStack) (mutableThreadContextStack == null ? new MutableThreadContextStack() : mutableThreadContextStack.copy());
    }

    public boolean add(String str) {
        if (!this.useStack) {
            return false;
        }
        MutableThreadContextStack nonNullStackCopy = getNonNullStackCopy();
        nonNullStackCopy.add(str);
        nonNullStackCopy.freeze();
        stack.set(nonNullStackCopy);
        return true;
    }

    public boolean addAll(Collection<? extends String> collection) {
        if (!this.useStack || collection.isEmpty()) {
            return false;
        }
        MutableThreadContextStack nonNullStackCopy = getNonNullStackCopy();
        nonNullStackCopy.addAll(collection);
        nonNullStackCopy.freeze();
        stack.set(nonNullStackCopy);
        return true;
    }

    public List<String> asList() {
        MutableThreadContextStack mutableThreadContextStack = stack.get();
        if (mutableThreadContextStack == null) {
            return Collections.emptyList();
        }
        return mutableThreadContextStack.asList();
    }

    public void clear() {
        stack.remove();
    }

    public boolean contains(Object obj) {
        MutableThreadContextStack mutableThreadContextStack = stack.get();
        return mutableThreadContextStack != null && mutableThreadContextStack.contains(obj);
    }

    public boolean containsAll(Collection<?> collection) {
        if (collection.isEmpty()) {
            return true;
        }
        MutableThreadContextStack mutableThreadContextStack = stack.get();
        if (mutableThreadContextStack == null || !mutableThreadContextStack.containsAll(collection)) {
            return false;
        }
        return true;
    }

    public ThreadContextStack copy() {
        MutableThreadContextStack mutableThreadContextStack;
        if (!this.useStack || (mutableThreadContextStack = stack.get()) == null) {
            return new MutableThreadContextStack();
        }
        return mutableThreadContextStack.copy();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (((obj instanceof DefaultThreadContextStack) && this.useStack != ((DefaultThreadContextStack) obj).useStack) || !(obj instanceof ThreadContextStack)) {
            return false;
        }
        ThreadContextStack threadContextStack = (ThreadContextStack) obj;
        MutableThreadContextStack mutableThreadContextStack = stack.get();
        if (mutableThreadContextStack != null) {
            return mutableThreadContextStack.equals(threadContextStack);
        }
        if (threadContextStack == null) {
            return true;
        }
        return false;
    }

    public int getDepth() {
        MutableThreadContextStack mutableThreadContextStack = stack.get();
        if (mutableThreadContextStack == null) {
            return 0;
        }
        return mutableThreadContextStack.getDepth();
    }

    public int hashCode() {
        int i;
        MutableThreadContextStack mutableThreadContextStack = stack.get();
        if (mutableThreadContextStack == null) {
            i = 0;
        } else {
            i = mutableThreadContextStack.hashCode();
        }
        return 31 + i;
    }

    public boolean isEmpty() {
        MutableThreadContextStack mutableThreadContextStack = stack.get();
        return mutableThreadContextStack == null || mutableThreadContextStack.isEmpty();
    }

    public Iterator<String> iterator() {
        MutableThreadContextStack mutableThreadContextStack = stack.get();
        if (mutableThreadContextStack == null) {
            return Collections.emptyList().iterator();
        }
        return mutableThreadContextStack.iterator();
    }

    public String peek() {
        MutableThreadContextStack mutableThreadContextStack = stack.get();
        if (mutableThreadContextStack == null || mutableThreadContextStack.size() == 0) {
            return null;
        }
        return mutableThreadContextStack.peek();
    }

    public String pop() {
        if (!this.useStack) {
            return "";
        }
        MutableThreadContextStack mutableThreadContextStack = stack.get();
        if (mutableThreadContextStack == null || mutableThreadContextStack.size() == 0) {
            throw new NoSuchElementException("The ThreadContext stack is empty");
        }
        MutableThreadContextStack mutableThreadContextStack2 = (MutableThreadContextStack) mutableThreadContextStack.copy();
        String pop = mutableThreadContextStack2.pop();
        mutableThreadContextStack2.freeze();
        stack.set(mutableThreadContextStack2);
        return pop;
    }

    public void push(String str) {
        if (this.useStack) {
            add(str);
        }
    }

    public boolean remove(Object obj) {
        MutableThreadContextStack mutableThreadContextStack;
        if (!this.useStack || (mutableThreadContextStack = stack.get()) == null || mutableThreadContextStack.size() == 0) {
            return false;
        }
        MutableThreadContextStack mutableThreadContextStack2 = (MutableThreadContextStack) mutableThreadContextStack.copy();
        boolean remove = mutableThreadContextStack2.remove(obj);
        mutableThreadContextStack2.freeze();
        stack.set(mutableThreadContextStack2);
        return remove;
    }

    public boolean removeAll(Collection<?> collection) {
        MutableThreadContextStack mutableThreadContextStack;
        if (!this.useStack || collection.isEmpty() || (mutableThreadContextStack = stack.get()) == null || mutableThreadContextStack.isEmpty()) {
            return false;
        }
        MutableThreadContextStack mutableThreadContextStack2 = (MutableThreadContextStack) mutableThreadContextStack.copy();
        boolean removeAll = mutableThreadContextStack2.removeAll(collection);
        mutableThreadContextStack2.freeze();
        stack.set(mutableThreadContextStack2);
        return removeAll;
    }

    public boolean retainAll(Collection<?> collection) {
        MutableThreadContextStack mutableThreadContextStack;
        if (!this.useStack || collection.isEmpty() || (mutableThreadContextStack = stack.get()) == null || mutableThreadContextStack.isEmpty()) {
            return false;
        }
        MutableThreadContextStack mutableThreadContextStack2 = (MutableThreadContextStack) mutableThreadContextStack.copy();
        boolean retainAll = mutableThreadContextStack2.retainAll(collection);
        mutableThreadContextStack2.freeze();
        stack.set(mutableThreadContextStack2);
        return retainAll;
    }

    public int size() {
        MutableThreadContextStack mutableThreadContextStack = stack.get();
        if (mutableThreadContextStack == null) {
            return 0;
        }
        return mutableThreadContextStack.size();
    }

    public Object[] toArray() {
        MutableThreadContextStack mutableThreadContextStack = stack.get();
        if (mutableThreadContextStack == null) {
            return new String[0];
        }
        return mutableThreadContextStack.toArray(new Object[mutableThreadContextStack.size()]);
    }

    public <T> T[] toArray(T[] tArr) {
        MutableThreadContextStack mutableThreadContextStack = stack.get();
        if (mutableThreadContextStack != null) {
            return mutableThreadContextStack.toArray(tArr);
        }
        if (tArr.length > 0) {
            tArr[0] = null;
        }
        return tArr;
    }

    public String toString() {
        MutableThreadContextStack mutableThreadContextStack = stack.get();
        if (mutableThreadContextStack == null) {
            return "[]";
        }
        return mutableThreadContextStack.toString();
    }

    public void trim(int i) {
        if (i >= 0) {
            MutableThreadContextStack mutableThreadContextStack = stack.get();
            if (mutableThreadContextStack != null) {
                MutableThreadContextStack mutableThreadContextStack2 = (MutableThreadContextStack) mutableThreadContextStack.copy();
                mutableThreadContextStack2.trim(i);
                mutableThreadContextStack2.freeze();
                stack.set(mutableThreadContextStack2);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Maximum stack depth cannot be negative");
    }

    public ThreadContext.ContextStack getImmutableStackOrNull() {
        return stack.get();
    }
}
