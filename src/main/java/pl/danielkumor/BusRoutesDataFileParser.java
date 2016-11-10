package pl.danielkumor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;

public final class BusRoutesDataFileParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusRoutesDataFileParser.class);

    private BusRoutesDataFileParser() {
    }

    public static DirectRouteRepository parse(String pathString) throws IOException {

        LOGGER.debug("Parse file from given location '{}'", pathString);

        Path path = Paths.get(pathString);
        checkArgument(Files.exists(path), "Specified file '%s' does not exists.", path);
        checkArgument(Files.isRegularFile(path), "Specified path '%s' is not a file.", path);

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
