import java.util.Arrays;
import java.util.Objects;

public class StringListImpl implements StringList {
    private String[] list;
    private int size = 0;

    public StringListImpl(String... args) {
        if (args.length > 0) {
            list = new String[args.length * 2];
            int i = 0;
            for (String arg : args) {
                checkForNull(arg);
                list[i] = arg;
                i++;
                size++;
            }
        }
    }

    @Override
    public String add(String item) {
        checkForNull(item);
        list[size] = item;
        size++;
        actualizeLength();
        return item;
    }

    @Override
    public String add(int index, String item) {
        checkIndex(index);
        checkForNull(item);
        for (int i = size; i > index; i--) {
            list[i] = list[i - 1];
        }
        list[index] = item;
        size++;
        actualizeLength();
        return item;
    }

    @Override
    public String set(int index, String item) {
        checkIndex(index);
        checkForNull(item);
        list[index] = item;
        return item;
    }

    @Override
    public String remove(String item) {
        if (item == null) {
            throw new IllegalArgumentException("Элемент не должен быть null!");
        }
        int index = this.indexOf(item);
        this.remove(index);
        return item;
    }

    @Override
    public String remove(int index) {
        try {
            checkIndex(index);
        } catch (WrongIndexException e) {
            throw new ItemNotFoundException("Элемент не найден!");
        }
        String deleted = list[index];
        for (int i = index; i < size; i++) {
            list[i] = list[i + 1];
        }
        size--;
        actualizeLength();
        return deleted;
    }

    @Override
    public boolean contains(String item) {
        checkForNull(item);
        for (int i = 0; i < size; i++) {
            if (list[i].equals(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int indexOf(String item) {
        checkForNull(item);
        for (int i = 0; i < size; i++) {
            if (list[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(String item) {
        checkForNull(item);
        for (int i = size - 1; i >= 0; i--) {
            if (list[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String get(int index) {
        checkIndex(index);
        return list[index];
    }

    @Override
    public boolean equals(StringList otherList) {
        if (otherList == null) {
            throw new IllegalArgumentException("Лист не должен быть null!");
        }
        if (otherList.getClass() != this.getClass()) {
            return false;
        }
        StringListImpl list1 = (StringListImpl) otherList;
        for (int i = 0; i < size; i++) {
            if (!Objects.equals(list[i], list1.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        list = new String[1];
        size = 0;
    }

    @Override
    public String[] toArray() {
        String[] arr = new String[size];
        for (int i = 0; i < size; i++) {
            arr[i] = list[i];
        }
        return arr;
    }

    @Override
    public String toString() {
        return Arrays.toString(toArray());
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new WrongIndexException("Такого индекса не существует!");
        }
    }

    private static void checkForNull(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Элемент не должен быть null!");
        }
    }

    private void actualizeLength() {
        if ((list.length - size) <= list.length / 4 || (list.length - size) > size * 2) {
            String[] newList = new String[size * 2 + 1];
            for (int i = 0; i < size; i++) {
                newList[i] = list[i];
            }
            list = newList;
        }
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(list);
        return result;
    }
}
