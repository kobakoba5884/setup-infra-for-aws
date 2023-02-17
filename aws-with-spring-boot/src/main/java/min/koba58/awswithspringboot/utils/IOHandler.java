package min.koba58.awswithspringboot.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

@Component
public class IOHandler {
    // discriminate whether folder is empty
    public boolean folderIsEmpty(Path path) {
        if (Files.isDirectory(path)) {
            try (Stream<Path> entries = Files.list(path)) {
                return !entries.findFirst().isPresent();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        return false;
    }
}
