package org.apache.ignite.thick.models.datatypes;

import java.io.Serializable;

/**
 * Superclass with timestamp to track entry creation time
 */
public abstract class Timestamped implements Serializable {
    /** Current time */
    private long timestamp = System.currentTimeMillis();

    /**
     *
     * @return getter
     */
    public long getTimestamp() {
        return timestamp;
    }
}
