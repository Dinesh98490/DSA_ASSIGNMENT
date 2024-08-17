// This scenario presents another sample Java GUI application using multithreading and an asynchronous framework
// (SwingWorker) to demonstrate asynchronous progress updates and batch processing.
// Features:
// File selection dialog: Choose multiple files for conversion.
// Conversion options: Select the desired output format (e.g., PDF to Docx, image resize).
// Start button: Initiates conversion of selected files concurrently.
// Progress bar: Shows overall conversion progress with individual file indicators.
// Status bar: Displays information about each file being processed (name, conversion type, progress).
// Cancel button: Allows stopping the entire conversion process or individual files.
// Completion notification: Provides a message when all conversions are finished.
// Challenges:
// Efficiently manage multiple file conversions using separate threads.
// Update the GUI asynchronously to show individual file progress without blocking the main thread.
// Handle potential errors during file access or conversion and provide informative feedback.
// Allow cancelling specific files or the entire process gracefully.
// Implementation:
// Swing GUI: Design a graphical interface using Swing components for file selection, buttons, progress bars, and
// status messages.
// Multithreading: Use a thread pool to manage multiple conversion threads efficiently


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class q_no_6FileConversion extends JFrame {

    private JFileChooser fileChooser;
    private JComboBox<String> formatComboBox;
    private JButton startButton, cancelButton;
    private JProgressBar progressBar;
    private JTextArea statusArea;
    private ExecutorService executorService;
    private int totalFiles;
    private int completedFiles;

    public q_no_6FileConversion() {
        setTitle("File Converter");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        // Initialize components
        fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setControlButtonsAreShown(false); // Hide default control buttons

        formatComboBox = new JComboBox<>(new String[]{"PDF to Docx", "Image Resize"});

        startButton = new JButton("Start");
        cancelButton = new JButton("Cancel");

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        statusArea = new JTextArea();
        statusArea.setEditable(false);

        // Set up the control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());

        // Add file chooser at the top of the control panel
        controlPanel.add(fileChooser, BorderLayout.NORTH);

        // Add conversion options and buttons in a separate panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.add(new JLabel("Conversion Type:"));
        optionsPanel.add(formatComboBox);
        optionsPanel.add(startButton);
        optionsPanel.add(cancelButton);

        // Add options panel to the center of the control panel
        controlPanel.add(optionsPanel, BorderLayout.CENTER);

        // Add all components to the frame
        getContentPane().add(controlPanel, BorderLayout.NORTH);
        getContentPane().add(progressBar, BorderLayout.SOUTH);
        getContentPane().add(new JScrollPane(statusArea), BorderLayout.CENTER);

        // Add action listeners to the buttons
        startButton.addActionListener(this::startConversion);
        cancelButton.addActionListener(this::cancelConversion);
    }

    // Method to start file conversion
    private void startConversion(ActionEvent event) {
        File[] files = fileChooser.getSelectedFiles();
        String conversionType = (String) formatComboBox.getSelectedItem();

        if (files.length == 0) {
            statusArea.append("Please select files to convert.\n");
            return;
        }

        totalFiles = files.length;
        completedFiles = 0;

        executorService = Executors.newFixedThreadPool(4); // Limit the number of threads
        progressBar.setValue(0);  // Reset progress bar

        for (File file : files) {
            ConversionTask task = new ConversionTask(file, conversionType);
            task.addPropertyChangeListener(new ProgressListener());
            executorService.submit(task);
        }
    }

    // Method to cancel conversion
    private void cancelConversion(ActionEvent event) {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();  // Immediately shut down the executor
            statusArea.append("Conversion canceled.\n");
        }
    }

    // Task class for file conversion
    private class ConversionTask extends SwingWorker<Void, FileConversionStatus> {
        private final File file;
        private final String conversionType;

        public ConversionTask(File file, String conversionType) {
            this.file = file;
            this.conversionType = conversionType;
        }

        @Override
        protected Void doInBackground() {
            try {
                // Simulate file conversion process
                for (int i = 0; i <= 100; i += 10) {
                    Thread.sleep(100); // Simulate time-consuming task
                    setProgress(i);
                    publish(new FileConversionStatus(file.getName(), i));
                }
            } catch (InterruptedException e) {
                publish(new FileConversionStatus(file.getName(), -1, "Conversion canceled."));
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                publish(new FileConversionStatus(file.getName(), -1, "Conversion failed."));
            }
            return null;
        }

        @Override
        protected void process(List<FileConversionStatus> chunks) {
            for (FileConversionStatus status : chunks) {
                statusArea.append(status + "\n");
            }
        }

        @Override
        protected void done() {
            completedFiles++;
            statusArea.append("Conversion of " + file.getName() + " completed.\n");
            updateProgressBar();
        }
    }

    // Listener to track progress updates
    private class ProgressListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if ("progress".equals(evt.getPropertyName())) {
                int progress = (Integer) evt.getNewValue();
                progressBar.setValue((completedFiles * 100 + progress) / totalFiles);
            }
        }
    }

    // Status class to hold conversion status info
    private static class FileConversionStatus {
        private final String fileName;
        private final int progress;
        private final String message;

        public FileConversionStatus(String fileName, int progress) {
            this(fileName, progress, null);
        }

        public FileConversionStatus(String fileName, int progress, String message) {
            this.fileName = fileName;
            this.progress = progress;
            this.message = message;
        }

        @Override
        public String toString() {
            return fileName + ": " + (message != null ? message : "Progress: " + progress + "%");
        }
    }

    // Main method to start the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            q_no_6FileConversion app = new q_no_6FileConversion();
            app.setVisible(true);
        });
    }

    // Method to update the overall progress bar based on completed files
    private void updateProgressBar() {
        int progress = (completedFiles * 100) / totalFiles;
        progressBar.setValue(progress);
    }
}
