package pl.danielkumor;

import org.junit.Test;
import org.springframework.util.ResourceUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class DataFileParserTest {

    @Test
    public void parserForGivenPathShouldCreateCorrectRepository() throws Exception {

        //given
        String path = ResourceUtils.getFile(this.getClass().getResource("/example")).getAbsolutePath();

        //when
        DirectRouteRepository repo = DataFileParser.parse(path);

        //then
        assertThat(repo.index.keySet().toArray()).contains(2, 3, 4, 8);
    }

    @Test
    public void parserForGivenPathToNotExistingFileShouldThrowException() throws Exception {
        //given
        String incorrectPath = "/home/daniel/incorrectFileName";

        //when
        Throwable thrown = catchThrowable(() -> DataFileParser.parse(incorrectPath));

        //then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("not exists");
    }

    @Test
    public void parserForGivenPathToDirectoryShouldThrowException() throws Exception {
        //given
        String directoryPath = ResourceUtils.getFile(this.getClass().getResource("/")).getAbsolutePath();

        //when
        Throwable thrown = catchThrowable(() -> DataFileParser.parse(directoryPath));

        //then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("is not a file");
    }
}
