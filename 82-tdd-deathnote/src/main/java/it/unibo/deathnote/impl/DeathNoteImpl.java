package it.unibo.deathnote.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import it.unibo.deathnote.api.DeathNote;

/**
 * implementation of {@link DeathNote}. 
 */
public class DeathNoteImpl implements DeathNote {

    private static final long LIMITCAUSE = 40;
    private static final int LIMITDETAILS = 6000;
    private static final int INDEXCAUSE = 0;
    private static final int INDEXDETAIL = 1;

    private final Map<String, ArrayList<String>> dethnote = new HashMap<>(); //NOPMD
    private String currentName;
    private long startTime;


    /**
     * {@inheritDoc}
     */
    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber > 0) {
            return RULES.get(ruleNumber);
        }
        throw new IllegalArgumentException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeName(final String name) {
        if (name == null) {
            throw new NullPointerException(); //NOPMD
        }
        dethnote.put(name, new ArrayList<>(Arrays.asList("", "")));
        currentName = name;
        startTime = System.currentTimeMillis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeDeathCause(final String cause) {

        if (isNameWritten(currentName)) {
            if (System.currentTimeMillis() - startTime < LIMITCAUSE + 1) {
                dethnote.get(currentName).add(INDEXCAUSE, cause);
                return true;
            }
            return false;
        }
        throw new IllegalStateException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeDetails(final String details) {
        if (isNameWritten(currentName)) {
            if (System.currentTimeMillis() - startTime > LIMITCAUSE && System.currentTimeMillis() - startTime < LIMITDETAILS) {
                dethnote.get(currentName).add(INDEXDETAIL, details);
                return true;
            }
            return false;
        }
        throw new IllegalStateException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDeathCause(final String name) {
        if (isNameWritten(name)) {
            final String cause = dethnote.get(name).get(INDEXCAUSE);
            if (cause.isBlank()) {
                return new String("heart attack"); //NOPMD
            }
            return cause;
        }
        throw new IllegalArgumentException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDeathDetails(final String name) {
        if (isNameWritten(name)) {
            return dethnote.get(name).get(INDEXDETAIL);
        }
        throw new IllegalArgumentException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNameWritten(final String name) {
        return name != null && dethnote.containsKey(name);
    }
}
