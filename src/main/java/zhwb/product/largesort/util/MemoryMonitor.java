package zhwb.product.largesort.util;

import org.slf4j.Logger;

/**
 * The Class MemoryMonitor.
 */
public final class MemoryMonitor
{

    /**
     * Instantiates a new memory monitor.
     */
    private MemoryMonitor()
    {

    }

    /**
     * Log memory info.
     *
     * @param logger the logger
     */
    public static void logMemoryInfo(final Logger logger)
    {
        Runtime envRuntime = Runtime.getRuntime();
        envRuntime.gc();

        // Check the heap memory of the JVM
        Long committedMemory = envRuntime.totalMemory();
        Long freeMemory = envRuntime.freeMemory();
        Long usedMemory = committedMemory - freeMemory;
        double usagePercentage = ((double)usedMemory / (double)committedMemory) * 100.00;

        logger.info("Memory info: free memeory: {}, total memory: {}, used memory: {}, usage percentage: {}", new Object[] {
                freeMemory,
                committedMemory, usedMemory, usagePercentage});
    }
}
