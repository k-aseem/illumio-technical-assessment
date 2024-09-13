# Flow Log Processor

## Assumptions

- The program only supports the default log format specified in the `flow_logs.txt` file.
- Only Amazon VPC Flow Logs version 2 is supported.
- The lookup table is expected to be in a CSV format with a header row in the `lookup_table.csv` file.
- The output will be written to `output.txt`.

## Instructions

### How to Compile

1. Ensure you have Java Development Kit (JDK) installed.
2. Clone the git repository:
   ```sh
   git clone <repository-url>
   ```
3. Open a terminal and navigate to the project directory:
   ```sh
   cd <repository-directory>
   ```
4. Run the following command to compile the program:
   ```sh
   javac FlowLogProcessor.java
   ```

### How to Run

- After compiling, you can run the program with the following command:

  ```sh
  java FlowLogProcessor
  ```

- Alternatively, use any IDE like Visual Studio Code to open the `FlowLogProcessor` file and click Run at the top right.

## Tests

- Unit Tests: Individual methods such as `getProtocol`, `readLookupTable`, `parseFlowLogs`, and `writeOutput` were tested to ensure they perform as expected.
- Integration Tests: The entire flow from reading the lookup table, parsing flow logs, and writing the output was tested to ensure the components work together correctly.

## Analysis

The program reads a lookup table from `lookup_table.csv` and flow logs from `flow_logs.txt`.
It processes the logs to count tags and port/protocol combinations.
The results are written to `output.txt`.
The program is designed to handle the default log format efficiently.
