/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mario
 */
public class Profiler {

    private static final int THEORETICAL_MAX_NAME_LENGTH = 50;

    private static Profiler singletonInstance = null;

    private Map<String, Profile> profiles; // Fast access to profiles by name
    private List<Profile> profilesStack; // Profiles as created chronologically

    /**
     * Get access to the singleton instance (create it if necessary)
     */
    public static Profiler getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new Profiler();
        }
        return singletonInstance;
    }

    /**
     * Protected constructor for singleton
     */
    protected Profiler() {
        profiles = new HashMap<String, Profiler.Profile>();
        profilesStack = new ArrayList<Profile>();
    }

    /**
     * Start a profile. If the profile does not exist, it will be created. If it exists, a new round of measure is
     * taken.
     * 
     * @param name
     *            The name of the profile. If possible, less than Profiler.THEORETICAL_MAX_NAME_LENGTH characters
     * 
     * @see Profiler.THEORETICAL_MAX_NAME_LENGTH
     */
    public void start(String name) {
        Profile p = profiles.get(name);
        if (p == null) {
            p = new Profile(name);
            profiles.put(name, p);
            profilesStack.add(p);
        }
        p.start();
    }

    /**
     * Stop a profile and compute some statistics about it.
     * 
     * @param name
     *            The name of the profile as declared in the corresponding start method
     */
    public void stop(String name) {
        Profile p = profiles.get(name);
        if (p == null) {
            throw new RuntimeException("The profile " + name + " has not been created by a call to the start() method!");
        }
        p.stop();
    }

    /**
     * Clear all the current measures. Not to be called within any start/stop pair.
     */
    public void reset() {
        profiles.clear();
    }

    /**
     * Build a string containing all the information about the measures we have taken so far.
     */
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        for (Profile p : profilesStack) {
            sb.append(p.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Output the measures to an output string
     */
    public void toCsvFile(OutputStream os) throws IOException {
        Profile.writeCsvHeader(os);
        for (Profile p : profilesStack) {
            p.writeCsvLine(os);
        }
    }

    /**
     * Profile information. It stores statistics per named profile.
     * 
     * @author Vincent Prat @ MarvinLabs
     */
    private static class Profile {
        private static final String CSV_HEADERS = "Name, Call Count, Total Time (ms), Average Time (ms), Min Time (ms), Max Time (ms), Delta Time (ms), Delta Ratio (%)\n";

        private static final String FORMAT_STRING = "%-" + THEORETICAL_MAX_NAME_LENGTH + "."
                + THEORETICAL_MAX_NAME_LENGTH
                + "s: %3d calls, total %5d ms, avg %5d ms, min %5d ms, max %5d ms, delta %5d ms (%d%%)";

        private static final String CSV_FORMAT_STRING = "%s,%d,%d,%d,%d,%d,%d,%d\n";

        private String name;
        private long startTime;
        private long callCount;
        private long totalTime;
        private long minTime;
        private long maxTime;

        public Profile(String name) {
            this.name = name;
            this.callCount = 0;
            this.totalTime = 0;
            this.startTime = 0;
            this.minTime = Long.MAX_VALUE;
            this.maxTime = Long.MIN_VALUE;
        }

        public void start() {
            startTime = System.currentTimeMillis();
        }

        public void stop() {
            final long elapsed = (System.currentTimeMillis() - startTime);
            if (elapsed < minTime) minTime = elapsed;
            if (elapsed > maxTime) maxTime = elapsed;
            totalTime += elapsed;
            callCount++;
        }

        private String getFormattedStats(String format) {
            final long avgTime = callCount == 0 ? 0 : (long) totalTime / callCount;
            final long delta = maxTime - minTime;
            final double deltaRatio = avgTime == 0 ? 0 : 100.0 * ((double) 0.5 * delta / (double) avgTime);

            return String
                    .format(format, name, callCount, totalTime, avgTime, minTime, maxTime, delta, (int) deltaRatio);
        }

        @Override
        public String toString() {
            return getFormattedStats(FORMAT_STRING);
        }

        public static void writeCsvHeader(OutputStream os) throws IOException {
            os.write(CSV_HEADERS.getBytes());
        }

        public void writeCsvLine(OutputStream os) throws IOException {
            os.write(getFormattedStats(CSV_FORMAT_STRING).getBytes());
        }
    }
}
