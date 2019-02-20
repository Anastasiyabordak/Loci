package loci.validator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileValidator {

    /** checks if file is empty.
     * @param filePath path to checked file.
     * @return true if file is empty.
     * @throws IOException if something is wrong with filePath.
     */
    public boolean fileIsEmpty(final String filePath) throws IOException {
        return Files.size(Paths.get(filePath)) == 0;
    }

    /** checks if file exists.
     * @param filePath path to checked file.
     * @return true if file exists.
     */
    public boolean fileExist(final String filePath) {
        return Paths.get(filePath).toFile().exists();
    }
}
