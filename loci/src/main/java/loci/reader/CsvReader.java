package loci.reader;


import loci.exception.CustomException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import loci.validator.FileValidator;

public class CsvReader {

    public List<String> readStringsFromFile(final String filePath)
            throws CustomException {

        try (Stream<String> input = Files.lines(Paths.get(filePath))){
            FileValidator validator = new FileValidator();
            if (!validator.fileIsEmpty(filePath)) {
                return  input.collect(Collectors.toList());
            } else {
                throw new CustomException();
            }
        } catch (IOException e) {
            throw new CustomException(e);
        }
    }
}
