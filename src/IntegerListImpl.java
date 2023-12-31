import java.util.Arrays;
import java.util.Objects;

public class IntegerListImpl implements IntegerList {
    private Integer[] list;
    private int size = 0;

    public IntegerListImpl(Integer... args) {
        if (args.length > 0) {
            list = new Integer[args.length * 2];
            int i = 0;
            for (Integer arg : args) {
                checkForNull(arg);
                list[i] = arg;
                i++;
                size++;
            }
        }
    }

    @Override
    public Integer add(Integer item) {
        checkForNull(item);
        list[size] = item;
        size++;
        actualizeLength();
        return item;
    }

    @Override
    public Integer add(int index, Integer item) {
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
    public Integer set(int index, Integer item) {
        checkIndex(index);
        checkForNull(item);
        list[index] = item;
        return item;
    }

    @Override
    public Integer remove(Integer item) {
        checkForNull(item);
        int index = this.indexOf(item);
        this.remove(index);
        return item;
    }

    @Override
    public Integer remove(int index) {
        try {
            checkIndex(index);
        } catch (WrongIndexException e) {
            throw new ItemNotFoundException("Элемент не найден!");
        }
        Integer deleted = list[index];
        for (int i = index; i < size; i++) {
            list[i] = list[i + 1];
        }
        size--;
        actualizeLength();
        return deleted;
    }

    @Override
    public boolean contains(Integer item) {
        checkForNull(item);
        Integer[] listCopy = toArray();
        sortInsertion(listCopy);
        return binarySearch(listCopy, item);
    }

    private static boolean binarySearch(Integer[] arr, int item) {
        int min = 0;
        int max = arr.length - 1;
        while (min <= max) {
            int mid = (min + max) / 2;
            if (item == arr[mid]) {
                return true;
            }
            if (item < arr[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }

    private static void sortInsertion(Integer[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i;
            while (j > 0 && arr[j - 1] >= temp) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = temp;
        }
    }

    @Override
    public int indexOf(Integer item) {
        checkForNull(item);
        for (int i = 0; i < size; i++) {
            if (list[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) {
        checkForNull(item);
        for (int i = size - 1; i >= 0; i--) {
            if (list[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Integer get(int index) {
        checkIndex(index);
        return list[index];
    }

    @Override
    public boolean equals(IntegerList otherList) {
        if (otherList == null) {
            throw new IllegalArgumentException("Лист не должен быть null!");
        }
        if (otherList.getClass() != this.getClass()) {
            return false;
        }
        IntegerListImpl list1 = (IntegerListImpl) otherList;
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
        list = new Integer[1];
        size = 0;
    }

    @Override
    public Integer[] toArray() {
        Integer[] arr = new Integer[size];
        for (int i = 0; i < size; i++) {
            arr[i] = list[i];
        }
        //System.out.println(Arrays.toString(arr));
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

    private static void checkForNull(Integer s) {
        if (s == null) {
            throw new IllegalArgumentException("Элемент не должен быть null!");
        }
    }

    private void actualizeLength() {
        if ((list.length - size) <= list.length / 4 || (list.length - size) > size * 2) {
            Integer[] newList = new Integer[size * 2 + 1];
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
