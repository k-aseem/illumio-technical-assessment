import java.io.*;
import java.util.*;

public class FlowLogProcessor {
    private static final String FLOW_LOG_FILE = "flow_logs.txt";
    private static final String LOOKUP_FILE = "lookup_table.csv";
    private static final String OUTPUT_FILE = "output.txt";

    private static Map<String, String> lookupTable = new HashMap<>();
    private static Map<String, Integer> tagCounts = new HashMap<>();
    private static Map<String, Integer> portProtocolCounts = new HashMap<>();

    private static String getProtocol(String protocolNum) {
        switch (protocolNum) {
            case "6":
                return "tcp";
            case "17":
                return "udp";
            case "1":
                return "icmp";
            default:
                return "unknown";
        }
    }

    private static void readLookupTable() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOOKUP_FILE))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 3) {
                    System.err.println("Invalid line in lookup table: " + line);
                    continue;
                }
                String key = parts[0] + "," + parts[1].toLowerCase();
                lookupTable.put(key, parts[2].toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseFlowLogs() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FLOW_LOG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String parts[] = line.split(" ");
                if (parts.length < 14) {
                    System.err.println("Invalid line in flow logs: " + line);
                    continue;
                }

                String key = parts[6] + "," + getProtocol(parts[7]);

                // Count tags
                String tag = lookupTable.getOrDefault(key, "Untagged");
                tagCounts.put(tag, tagCounts.getOrDefault(tag, 0) + 1);

                // Count port/protocol combinations
                portProtocolCounts.put(key, portProtocolCounts.getOrDefault(key, 0) + 1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeOutput() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(OUTPUT_FILE))) {

            // Write tag counts
            writer.println("Tag Counts:");
            writer.println("Tag,Count");
            for (Map.Entry<String, Integer> entry : tagCounts.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }

            writer.println(); // Add a blank line

            // Write port/protocol counts
            writer.println("Port/Protocol Combination Counts:");
            writer.println("Port,Protocol,Count");
            for (Map.Entry<String, Integer> entry : portProtocolCounts.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        readLookupTable();
        parseFlowLogs();
        writeOutput();
    }
}