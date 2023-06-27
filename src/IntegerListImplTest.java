import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class IntegerListImplTest {

    private IntegerListImpl out = new IntegerListImpl(1123, 456, 789, 4234, 45332);

    @Test
    @DisplayName("Добавление в конец")
    void addToEnd() {
        Integer expected = 222;
        int oldSize = out.size();
        assertEquals(expected, out.add(expected));
        assertEquals(oldSize + 1, out.size());

        Integer[] expectedList = new Integer[]{1123, 456, 789, 4234, 45332, 222};
        assertEquals(Arrays.toString(expectedList), Arrays.toString(out.toArray()));

        assertThrows(IllegalArgumentException.class, () -> out.add(null));
    }

    @Test
    @DisplayName("Добавление по индексу")
    void addByIndex() {
        Integer expected = 101;
        int oldSize = out.size();
        assertEquals(expected, out.add(2, expected));
        assertEquals(oldSize + 1, out.size());

        Integer[] expectedList = new Integer[]{1123, 456, 101, 789, 4234, 45332};
        assertEquals(Arrays.toString(expectedList), Arrays.toString(out.toArray()));

        assertThrows(WrongIndexException.class, () -> out.add(6, 333));
        assertThrows(WrongIndexException.class, () -> out.add(-1, 333));
        assertThrows(IllegalArgumentException.class, () -> out.add(2, null));
    }

    @Test
    @DisplayName("Добавление по индексу с затиранием")
    void set() {
        Integer expected = 404;
        int oldSize = out.size();
        assertEquals(expected, out.set(3, expected));
        assertEquals(oldSize, out.size());

        Integer[] expectedList = new Integer[]{1123, 456, 789, 404, 45332};
        assertEquals(Arrays.toString(expectedList), Arrays.toString(out.toArray()));

        assertThrows(WrongIndexException.class, () -> out.set(6, 333));
        assertThrows(WrongIndexException.class, () -> out.set(-1, 333));
        assertThrows(IllegalArgumentException.class, () -> out.set(2, null));
    }

    @Test
    @DisplayName("Удаление по значению")
    void removeByItem() {
        Integer expected = 1123;
        int oldSize = out.size();
        assertEquals(expected, out.remove(expected));
        assertEquals(oldSize - 1, out.size());

        Integer[] expectedList = new Integer[]{456, 789, 4234, 45332};
        assertEquals(Arrays.toString(expectedList), Arrays.toString(out.toArray()));

        assertThrows(IllegalArgumentException.class, () -> out.remove(null));
        assertThrows(ItemNotFoundException.class, () -> out.remove(111));
    }

    @Test
    @DisplayName("Удаление по индексу")
    void removeByIndex() {
        Integer expected = 456;
        int oldSize = out.size();
        assertEquals(expected, out.remove(1));
        assertEquals(oldSize - 1, out.size());

        Integer[] expectedList = new Integer[]{1123, 789, 4234, 45332};
        assertEquals(Arrays.toString(expectedList), Arrays.toString(out.toArray()));

        assertThrows(ItemNotFoundException.class, () -> out.remove(4));
        assertThrows(ItemNotFoundException.class, () -> out.remove(-1));
    }

    @Test
    @DisplayName("Наличие элемента")
    void contains() {
        assertTrue(out.contains(789));
        assertTrue(out.contains(1123));
        assertTrue(out.contains(45332));
        assertFalse(out.contains(7));
        assertFalse(out.contains(34567));

        assertThrows(IllegalArgumentException.class, () -> out.contains(null));
    }

    @Test
    @DisplayName("Получение индекса по значению")
    void indexOf() {
        assertEquals(1, out.indexOf(456));
        assertEquals(0, out.indexOf(1123));
        assertEquals(4, out.indexOf(45332));
        assertEquals(-1, out.indexOf(4546));

        assertThrows(IllegalArgumentException.class, () -> out.indexOf(null));
    }

    @Test
    @DisplayName("Получение индекса по значению с конца")
    void lastIndexOf() {
        assertEquals(2, out.lastIndexOf(789));
        assertEquals(0, out.lastIndexOf(1123));
        assertEquals(4, out.lastIndexOf(45332));
        assertEquals(-1, out.lastIndexOf(2234));

        assertThrows(IllegalArgumentException.class, () -> out.lastIndexOf(null));
    }

    @Test
    @DisplayName("Получение элемента по индексу")
    void get() {
        assertEquals(1123, out.get(0));
        assertEquals(45332, out.get(4));
        assertEquals(456, out.get(1));

        assertThrows(WrongIndexException.class, () -> out.get(-1));
        assertThrows(WrongIndexException.class, () -> out.get(5));
    }

    @Test
    @DisplayName("Совпадение массивов")
    void testEquals() {
        IntegerListImpl newList = new IntegerListImpl(1123, 456, 789, 4234, 45332);
        assertTrue(out.equals(newList));

        IntegerListImpl newList2 = new IntegerListImpl(1123, 456, 789, 4234, 45331);
        assertFalse(out.equals(newList2));

        IntegerListImpl newList3 = new IntegerListImpl(113, 456, 789, 4234, 45332);
        assertFalse(out.equals(newList3));

        assertThrows(IllegalArgumentException.class, () -> out.equals(null));
    }

    @Test
    @DisplayName("Получение размера массива")
    void size() {
        assertEquals(5, out.size());
    }

    @Test
    @DisplayName("Наличие пустого массива")
    void isEmpty() {
        assertFalse(out.isEmpty());

        out = new IntegerListImpl();
        assertTrue(out.isEmpty());
    }

    @Test
    @DisplayName("Очистка массива")
    void clear() {
        IntegerListImpl expected = new IntegerListImpl();
        out.clear();
        assertEquals(expected.toString(), out.toString());
    }

    @Test
    @DisplayName("Получение массива с элементами")
    void toArray() {
        Integer[] arr = new Integer[]{1123, 456, 789, 4234, 45332};
        assertArrayEquals(arr, out.toArray());

        out = new IntegerListImpl();
        Integer[] arr2 = new Integer[0];
        assertArrayEquals(arr2, out.toArray());
    }
}