import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class StringListImplTest {

    private StringListImpl out = new StringListImpl("abc", "def", "ghi", "jkl", "mno");

    @Test
    @DisplayName("Добавление в конец")
    void addToEnd() {
        String expected = "prs";
        int oldSize = out.size();
        assertEquals(expected, out.add(expected));
        assertEquals(oldSize + 1, out.size());

        String[] expectedList = new String[]{"abc", "def", "ghi", "jkl", "mno", "prs"};
        assertEquals(Arrays.toString(expectedList), Arrays.toString(out.toArray()));

        assertThrows(IllegalArgumentException.class, () -> out.add(null));
    }

    @Test
    @DisplayName("Добавление по индексу")
    void addByIndex() {
        String expected = "hello";
        int oldSize = out.size();
        assertEquals(expected, out.add(2, expected));
        assertEquals(oldSize + 1, out.size());

        String[] expectedList = new String[]{"abc", "def", "hello", "ghi", "jkl", "mno"};
        assertEquals(Arrays.toString(expectedList), Arrays.toString(out.toArray()));

        assertThrows(WrongIndexException.class, () -> out.add(6, "123"));
        assertThrows(WrongIndexException.class, () -> out.add(-1, "123"));
        assertThrows(IllegalArgumentException.class, () -> out.add(2, null));
    }

    @Test
    @DisplayName("Добавление по индексу с затиранием")
    void set() {
        String expected = "qwerty";
        int oldSize = out.size();
        assertEquals(expected, out.set(3, expected));
        assertEquals(oldSize, out.size());

        String[] expectedList = new String[]{"abc", "def", "ghi", "qwerty", "mno"};
        assertEquals(Arrays.toString(expectedList), Arrays.toString(out.toArray()));

        assertThrows(WrongIndexException.class, () -> out.set(6, "123"));
        assertThrows(WrongIndexException.class, () -> out.set(-1, "123"));
        assertThrows(IllegalArgumentException.class, () -> out.set(2, null));
    }

    @Test
    @DisplayName("Удаление по значению")
    void removeByItem() {
        String expected = "abc";
        int oldSize = out.size();
        assertEquals(expected, out.remove("abc"));
        assertEquals(oldSize - 1, out.size());

        String[] expectedList = new String[]{"def", "ghi", "jkl", "mno"};
        assertEquals(Arrays.toString(expectedList), Arrays.toString(out.toArray()));

        assertThrows(IllegalArgumentException.class, () -> out.remove(null));
        assertThrows(ItemNotFoundException.class, () -> out.remove("deF"));

    }

    @Test
    @DisplayName("Удаление по индексу")
    void removeByIndex() {
        String expected = "def";
        int oldSize = out.size();
        assertEquals(expected, out.remove(1));
        assertEquals(oldSize - 1, out.size());

        String[] expectedList = new String[]{"abc", "ghi", "jkl", "mno"};
        assertEquals(Arrays.toString(expectedList), Arrays.toString(out.toArray()));

        assertThrows(ItemNotFoundException.class, () -> out.remove(4));
        assertThrows(ItemNotFoundException.class, () -> out.remove(-1));
    }

    @Test
    @DisplayName("Наличие элемента")
    void contains() {
        assertTrue(out.contains("abc"));
        assertFalse(out.contains("ab"));
        assertFalse(out.contains("Abc"));
        assertTrue(out.contains("mno"));

        assertThrows(IllegalArgumentException.class, () -> out.contains(null));
    }

    @Test
    @DisplayName("Получение индекса по значению")
    void indexOf() {
        assertEquals(1, out.indexOf("def"));
        assertEquals(0, out.indexOf("abc"));
        assertEquals(4, out.indexOf("mno"));
        assertEquals(-1, out.indexOf("de"));

        assertThrows(IllegalArgumentException.class, () -> out.indexOf(null));
    }

    @Test
    @DisplayName("Получение индекса по значению с конца")
    void lastIndexOf() {
        assertEquals(2, out.lastIndexOf("ghi"));
        assertEquals(0, out.lastIndexOf("abc"));
        assertEquals(4, out.lastIndexOf("mno"));
        assertEquals(-1, out.lastIndexOf("hi"));

        assertThrows(IllegalArgumentException.class, () -> out.lastIndexOf(null));
    }

    @Test
    @DisplayName("Получение элемента по индексу")
    void get() {
        assertEquals("abc", out.get(0));
        assertEquals("mno", out.get(4));
        assertEquals("def", out.get(1));

        assertThrows(WrongIndexException.class, () -> out.get(-1));
        assertThrows(WrongIndexException.class, () -> out.get(5));
    }

    @Test
    @DisplayName("Совпадение массивов")
    void testEquals() {
        StringListImpl newList = new StringListImpl("abc", "def", "ghi", "jkl", "mno");
        assertTrue(out.equals(newList));

        StringListImpl newList2 = new StringListImpl("abc", "def", "ghi", "jkl", "mn");
        assertFalse(out.equals(newList2));

        StringListImpl newList3 = new StringListImpl("ac", "def", "ghi", "jkl", "mno");
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

        out = new StringListImpl();
        assertTrue(out.isEmpty());
    }

    @Test
    @DisplayName("Очистка массива")
    void clear() {
        StringListImpl expected = new StringListImpl();
        out.clear();
        assertEquals(expected.toString(), out.toString());
    }

    @Test
    @DisplayName("Получение массива с элементами")
    void toArray() {
        String[] arr = new String[]{"abc", "def", "ghi", "jkl", "mno"};
        assertArrayEquals(arr, out.toArray());

        out = new StringListImpl();
        String[] arr2 = new String[0];
        assertArrayEquals(arr2, out.toArray());
    }
}