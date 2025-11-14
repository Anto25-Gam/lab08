package it.unibo.deathnote;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestDeathNote {
    private static final int TIME_CAUSE_LIMIT = 40;
    private static final int TEST_TIME_CAUSE_LIMIT = 100;
    private static final int TIME_DETAIL_LIMIT = 6000;
    private static final String MARCO = "Marco";
    private static final String FILIPPO = "Filippo";
    private static final String CAUSE_DEATH1 = "investito";

    private DeathNote deathnote;

    @BeforeEach
    public void setUp() {
        this.deathnote = new DeathNoteImpl(); 
    }

    @Test
    void testRuleNumber() {
        final int[] input = {-1, 0};
        for (final int i : input) {
            assertThrowsExactly(IllegalArgumentException.class, new Executable() {
                @Override
                public void execute() throws Throwable {
                    deathnote.getRule(i);
                }
            });
        }
    }

    @Test
    void testEmptyOrNullRules() {
        for (int i = 1; i < DeathNote.RULES.size(); i++) {
            final String rule = deathnote.getRule(i);
            assertNotNull(rule);
            assertFalse(rule.isBlank());
        }
    }

    @Test
    void testHumanNotWritten() {
        assertFalse(deathnote.isNameWritten(MARCO));
        deathnote.writeName(MARCO);
        assertTrue(deathnote.isNameWritten(MARCO));
        assertFalse(deathnote.isNameWritten(FILIPPO));
        assertFalse(deathnote.isNameWritten(""));
    }

    @Test
    void testCauseOfDeath() throws InterruptedException {
        assertThrowsExactly(IllegalStateException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                deathnote.writeDeathCause(CAUSE_DEATH1);
            }
        });

        deathnote.writeName(MARCO);
        assertEquals(deathnote.getDeathCause(MARCO), "heart attack");

        deathnote.writeName(FILIPPO);
        deathnote.writeDeathCause("karting accident");

        assertEquals("karting accident", deathnote.getDeathCause(FILIPPO));

        Thread.sleep(TEST_TIME_CAUSE_LIMIT);

        assertFalse(deathnote.writeDeathCause(CAUSE_DEATH1));
        assertNotEquals(deathnote.getDeathCause(FILIPPO), CAUSE_DEATH1);
    }

    @Test
    void testDetails() throws InterruptedException {
        assertThrowsExactly(IllegalStateException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                deathnote.writeDetails(CAUSE_DEATH1);
            }
        });

        deathnote.writeName(MARCO);

        assertTrue(deathnote.getDeathDetails(MARCO).isEmpty());

        Thread.sleep(TIME_CAUSE_LIMIT + 1);
        assertTrue(deathnote.writeDetails("ran for too long"));
        assertEquals("ran for too long", deathnote.getDeathDetails(MARCO));

        deathnote.writeName(FILIPPO);

        Thread.sleep(TIME_DETAIL_LIMIT + 100);

        assertFalse(deathnote.writeDetails(CAUSE_DEATH1));
        assertNotEquals(deathnote.getDeathDetails(FILIPPO), CAUSE_DEATH1);
    }
}
