package COMP2450;

import ca.umanitoba.cs.comp2450.stack.impl.BadStack1;
import ca.umanitoba.cs.comp2450.stack.impl.BadStack2;
import ca.umanitoba.cs.comp2450.stack.impl.BadStack3;
import ca.umanitoba.cs.comp2450.stack.impl.BadStack4;
import ca.umanitoba.cs.comp2450.stack.impl.BadStack5;
import ca.umanitoba.cs.comp2450.stack.Stack;


public class TestStack {

    private int successes = 0;
    private int failures = 0;

    public TestResults runTests() {
        testImplementation("BadStack1", new BadStack1<>());
        testImplementation("BadStack2", new BadStack2<>());
        testImplementation("BadStack3", new BadStack3<>());
        testImplementation("BadStack4", new BadStack4<>());
        testImplementation("BadStack5", new BadStack5<>());
        return new TestResults(successes, failures);
    }

    private void testImplementation(String name, Stack<Integer> s) {
        testPushPop(name, s);
        testPeek(name, s);
        testIsEmpty(name, s);
        testSize(name, s);
    }

    private void testPushPop(String name, Stack<Integer> s) {
        try {
            s.push(1);
            s.push(2);
            s.push(3);
            if (s.pop() != 3) {
                fail(name + ": LIFO violated");
            }
            else if (s.pop() != 2) {
                fail(name + ": LIFO violated");
            }
            else if (s.pop() != 1) {
                fail(name + ": LIFO violated");
            }
            else {
                pass(name + ": push/pop OK");
            }
        } catch (Exception e) {
            fail(name + ": crash in push/pop");
        }
    }

    private void testPeek(String name, Stack<Integer> s) {
        try {
            s.push(99);
            if (s.peek() != 99) {
                fail(name + ": peek wrong");
            }
            else if (s.pop() != 99) {
                fail(name + ": peek removed element");
            }
            else {
                pass(name + ": peek OK");
            }
        } catch (Exception e) {
            fail(name + ": crash in peek");
        }
    }

    private void testIsEmpty(String name, Stack<Integer> s) {
        try {
            if (!s.isEmpty()) {
                fail(name + ": empty stack not empty");
            }
            s.push(8);
            if (s.isEmpty()) {
                fail(name + ": non-empty stack reports empty");
            }
            s.pop();
            if (!s.isEmpty()) {
                fail(name + ": empty after pop but says non-empty");
            }
            else {
                pass(name + ": isEmpty OK");
            }
        } catch (Exception e) {
            fail(name + ": crash in isEmpty");
        }
    }

    private void testSize(String name, Stack<Integer> s) {
        try {
            if (s.size() != 0) {
                fail(name + ": size wrong initially");
            }
            s.push(1);
            s.push(2);
            if (s.size() != 2) {
                fail(name + ": wrong size after pushes");
            }
            s.pop();
            if (s.size() != 1) {
                fail(name + ": wrong size after pop");
            }
            else {
                pass(name + ": size OK");
            }
        } catch (Exception e) {
            fail(name + ": crash in size");
        }
    }

    private void pass(String m) {
        successes++;
        System.out.println("PASS: " + m);
    }

    private void fail(String m) {
        failures++;
        System.out.println("FAIL: " + m);
    }
}
