package pl.danielkumor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.isRegularFile;
import static org.springframework.util.StringUtils.isEmpty;

public final class DataFileParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataFileParser.class);

    private DataFileParser() {
    }

    public static DirectRouteRepository parse(String pathString) throws IOException {

        LOGGER.debug("Parse file from given location '{}'", pathString);

        checkArgument(!isEmpty(pathString));

        Path path = Paths.get(pathString);
        checkArgument(exists(path), "Specified file '%s' does not exists.", path);
        checkArgument(isRegularFile(path), "Specified path '%s' is not a file.", path);

        DirectRouteRepository index = new DirectRouteRepository();

        Stream<String> stream = Files.lines(path);

        stream.skip(1).map(line -> line.split("\\s+")).forEach(stringArray -> {

            for (int i = 1; i < stringArray.length; i++) {
                for (int j = i + 1; j < stringArray.length; j++) {
                    index.addPath(Integer.parseInt(stringArray[i]), Integer.parseInt(stringArray[j]));
                }
            }
        });

        return index;
    }

}
