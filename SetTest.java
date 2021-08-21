import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SetTest {
    private static final float DEFAULT_HIGHER_CAPACITY = 0.75f; // Depends on DEFAULT_HIGHER_CAPACITY in SimpleHashSet.
    private static final float DEFAULT_LOWER_CAPACITY = 0.25f; // Depends on DEFAULT_LOWER_CAPACITY in SimpleHashSet.
    private static final int INITIAL_CAPACITY = 16; // Depends on INITIAL_CAPACITY in SimpleHashSet.

    private final SimpleHashSet set = new OpenHashSet();       //Uncomment me if you want to test OpenHashSet
//    private final SimpleHashSet set = new ClosedHashSet();   //Uncomment me if you want to test ClosedHashSet

    /**
     * call the function sanityTest 3 times, each time with a OpenHashSet with a different constructor.
     * see sanityTest doc.
     */
    @Test
    public void openHashSet_1_sanity_check() {
        sanityTest(new OpenHashSet());
        sanityTest(new OpenHashSet(0.60f, 0.40f));
        sanityTest(new OpenHashSet(new String[]{}));
    }

    /**
     * call the function aLotOfStrings_test 3 times, each time with a OpenHashSet with a different constructor.
     * see aLotOfStrings_test doc
     */
    @Test
    public void openHashSet_2_big_numbers() {
        aLotOfStrings_test(new ClosedHashSet());
        ClosedHashSet o = new ClosedHashSet(1f, 0.5f);
        aLotOfStrings_test(o);
        aLotOfStrings_test(o);
        aLotOfStrings_test(new ClosedHashSet(0.95f, 0.05f));
        aLotOfStrings_test(new ClosedHashSet(1f, 0f));
        aLotOfStrings_test(new ClosedHashSet(0.50f, 0.50f));
        java.lang.String[] data1 = {};
        aLotOfStrings_test(new ClosedHashSet(data1));
    }

    /**
     * call the function sanityTest 3 times, each time with a ClosedHashSet with a different constructor.
     * see sanityTest doc.
     */
    @Test
    public void closedHashSet_1_sanity_check() {
        sanityTest(new ClosedHashSet());
        sanityTest(new ClosedHashSet(0.60f, 0.40f));
        sanityTest(new ClosedHashSet(new String[]{}));
    }

    /**
     * call the function aLotOfStrings_test 3 times, each time with a ClosedHashSet with a different constructor.
     * see aLotOfStrings_test doc
     */
    @Test
    public void closedHashSet_2_big_numbers() {
        aLotOfStrings_test(new ClosedHashSet());
        ClosedHashSet o = new ClosedHashSet(1f, 0.5f);
        aLotOfStrings_test(o);
        aLotOfStrings_test(o);
        aLotOfStrings_test(new ClosedHashSet(0.95f, 0.05f));
        aLotOfStrings_test(new ClosedHashSet(1f, 0f));
        aLotOfStrings_test(new ClosedHashSet(0.50f, 0.50f));
        java.lang.String[] data1 = {};
        aLotOfStrings_test(new ClosedHashSet(data1));
    }

    @Test
    void addDeleteManyTimes() {
        int cap = INITIAL_CAPACITY;
        for (int i = 0; i < 100; i++) {
            set.add(Integer.toString(i));
            set.delete(Integer.toString(i));
            cap = Integer.max(1, cap / 2);
            assertEquals(cap, set.capacity());
        }
    }

    @Test
    void capacityInit() {
        assertEquals(INITIAL_CAPACITY, set.capacity());
    }

    @Test
    void addSimple() {
        for (int i = 0; i < 2000; i++) {
            assertTrue(set.add(Integer.toString(i)));
        }
    }

    @Test
    void addTwice() {
        for (int i = 0; i < 2000; i++) {
            assertTrue(set.add(Integer.toString(i)));
        }

        for (int i = 0; i < 2000; i++) {
            assertFalse(set.add(Integer.toString(i)), "These elements already in the set!");
        }
    }

    @Test
    void capacityOneUpscale() {
        for (int i = 0; i < 12; i++) {
            set.add(Integer.toString(i));
            assertEquals(INITIAL_CAPACITY, set.capacity(), "No need to upscale!\nThe Current load is " +
                    set.size() / set.capacity());
        }

        set.add("upscale!");
        assertEquals(INITIAL_CAPACITY * 2, set.capacity(), "After this add, a resize is needed\nThe Current load is " +
                set.size() / set.capacity());
    }


    @Test
    void capacityOneUpscaleDownScale() {
        for (int i = 0; i < 13; i++) {
            set.add(Integer.toString(i));
        }

        for (int i = 0; i < 5; i++) {
            set.delete(Integer.toString(i));
            assertEquals(INITIAL_CAPACITY * 2, set.capacity(), "No need to downscale!\nThe Current load is " +
                    set.size() / set.capacity());
        }
        set.delete("5");
        assertEquals(INITIAL_CAPACITY, set.capacity(), "After this deletion, a downscale resize should be made.");
    }

    @Test
    void size() {
        assertEquals(0, set.size(), "The set is empty!");
        for (int i = 0; i < 2000; i++) {
            set.add(Integer.toString(i));
            assertEquals(i + 1, set.size(), "We have entered " + i + 1 + " elements.");
        }
    }

    @Test
    void containsSimple() {
        for (int i = 0; i < 2000; i++) {
            assertTrue(set.add(Integer.toString(i)));
        }

        for (int i = 1999; i >= 0; i--) {
            assertTrue(set.contains(Integer.toString(i)));
        }
    }

    @Test
    void deleteSimple() {
        for (int i = 0; i < 2000; i++) {
            assertTrue(set.add(Integer.toString(i)));
        }

        for (int i = 1999; i >= 0; i--) {
            assertTrue(set.delete(Integer.toString(i)));
        }

        assertEquals(0, set.size);
    }

    @Test
    void deleteNotInSet() {
        for (int i = 2000; i < 4000; i++) {
            set.add(Integer.toString(i));
        }
        for (int i = 0; i < 2000; i++) {
            assertFalse(set.delete(Integer.toString(i)));
        }
    }

    // ----------------------helper functions---------------------------

    /**
     * simple test for the given SimpleSet, add one string, remove it, ....
     */
    private void sanityTest(SimpleSet hashSet) {
        final String s = "";

        //add empty string test
        assertEquals(0, hashSet.size(), "there is nothing inside");
        assertFalse(hashSet.contains(s), "there is nothing inside");
        assertTrue(hashSet.add(s), "add an item");
        assertTrue(hashSet.contains(s), "now the string should exists");
        assertEquals(1, hashSet.size(), "after we added an item, the size should be 1");
        assertFalse(hashSet.add(s), "try to add the string when its already inside");
        assertTrue(hashSet.delete(s), "delete the string");
        assertFalse(hashSet.contains(s), "now, the set should not contain the string");
        assertFalse(hashSet.delete(s), "try to delete it again, should return false");
        assertEquals(0, hashSet.size(), "the size should return to 0.");

        assertTrue(hashSet.add(s), "add an item");
        assertEquals(1, hashSet.size(), "after we added an item, the size should be 1");
        assertTrue(hashSet.contains(s), "now the string should exists");
        assertTrue(hashSet.delete(s), "delete the string");
        assertEquals(0, hashSet.size(), "the size should return to 0.");

    }

    /**
     * add 1000 strings to the given SimpleSet and then remove them. the strings are "1", "2", "3"....
     */
    private void aLotOfStrings_test(SimpleSet hashSet) {
        int iterations;  // if you fail this, try different values, I found that sometimes you can find
        // problems this way.
//		iterations = 3;
//		iterations = 5;
//		iterations = 23;
//		iterations = 100;
        iterations = 1000;

        //addition
        for (int i = 0; i < iterations; i++) {
            //(Integer.toString(i)): 1-> "1"

            // size
            assertEquals(i, hashSet.size());

            // should not contain it now
            assertFalse(hashSet.contains(Integer.toString(i)), "problem with the string " + i);

            // should be able to add it now
            assertTrue(hashSet.add(Integer.toString(i)), "problem with the string " + i);

            // should not be able to add it now
            assertFalse(hashSet.add(Integer.toString(i)), "problem with the string " + i);

            // should contain it now
            assertTrue(hashSet.contains(Integer.toString(i)), "problem with the string " + i);

            // size
            assertEquals(hashSet.size(), i + 1);
        }

        // deletion
        for (int i = 0; i < iterations; i++) {
            // check the size
            assertEquals(iterations - i, hashSet.size(), "problem with the string " + i);

            // should contain it now
            assertTrue(hashSet.contains(Integer.toString(i)), "problem with the string " + i);

            // should be able to delete it now
            assertTrue(hashSet.delete(Integer.toString(i)), "problem with the string " + i);

            // should not be able to delete it now
            assertFalse(hashSet.delete(Integer.toString(i)), "problem with the string " + i);

            // should not contain it now
            assertFalse(hashSet.contains(Integer.toString(i)), "problem with the string " + i);

            // size
            assertEquals(iterations - i - 1, hashSet.size(), "problem with the string " + i);
        }
    }
}
