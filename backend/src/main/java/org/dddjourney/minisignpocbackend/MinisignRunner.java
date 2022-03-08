package org.dddjourney.minisignpocbackend;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MinisignRunner {

    @SneakyThrows
    public String version() {
        String[] args = new String[]{"minisign", "-v"};
        Process process = new ProcessBuilder(args).start();
        return IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
    }

    @SneakyThrows
    public String verify() {

        File outputLogFile = File.createTempFile("output", "log");
        File inputLogFile = File.createTempFile("input", "log");
        File errorLogFile = File.createTempFile("error", "log");

        String[] args = new String[]{
                "minisign",
                "-Vm",
                "src/test/resources/minisign/test_payload_file.txt",
                "-p",
                "src/test/resources/minisign/minisign_public_key.pub"
        };

        ProcessBuilder processBuilder = new ProcessBuilder(args)
                .redirectOutput(outputLogFile)
                .redirectInput(inputLogFile)
                .redirectError(errorLogFile);
        Process process = processBuilder.start();
        process.waitFor(5, TimeUnit.SECONDS);

        return FileUtils.readFileToString(outputLogFile, StandardCharsets.UTF_8);
    }

    @SneakyThrows
    public String sign(String password) {

        String[] args = new String[]{
                "minisign",
                "-Sm",
                "src/test/resources/minisign/test_payload_file.txt",
                "-s",
                "src/test/resources/minisign/minisign_secret_key.key"
        };

        ProcessBuilder processBuilder = new ProcessBuilder(args);

        processBuilder.redirectInput(ProcessBuilder.Redirect.INHERIT);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);

        Process process = processBuilder.start();

        InputStream errStream = process.getErrorStream();
        InputStream inStream = process.getInputStream();
        OutputStream outStream = process.getOutputStream();

        PrintStream out = new PrintStream(new BufferedOutputStream(outStream));

        new Thread(new StreamGobbler("err", out, errStream)).start();

        Callback cb = line -> {
            if (line.contains("Password:")) {
                out.println(password);
                out.flush();
            }
        };
        new Thread(new StreamGobbler("in", out, inStream, cb)).start();
        boolean exited = process.waitFor(5, TimeUnit.SECONDS);
        System.out.println("Process exited normal: " + exited);

        return "";
    }

    interface Callback {
        void onNextLine(String line);
    }

    static class StreamGobbler implements Runnable {
        private PrintStream out;
        private Scanner inScanner;
        private String name;
        private Callback cb;

        public StreamGobbler(String name, PrintStream out, InputStream inStream) {
            this.name = name;
            this.out = out;
            inScanner = new Scanner(new BufferedInputStream(inStream));
        }

        public StreamGobbler(String name, PrintStream out, InputStream inStream, Callback cb) {
            this.name = name;
            this.out = out;
            inScanner = new Scanner(new BufferedInputStream(inStream));
            this.cb = cb;
        }

        @Override
        public void run() {
            while (inScanner.hasNextLine()) {
                String line = inScanner.nextLine();
                if (cb != null)
                    cb.onNextLine(line);
                System.out.printf("%s: %s%n", name, line);
            }
        }
    }
}
