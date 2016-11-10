package pl.danielkumor;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class DirectRouteRepositoryTest {

    @Test
    public void repositoryForExistingDirectRouteShouldReturnTrue() {
        //given
        DirectRouteRepository repo = new DirectRouteRepository();

        //when
        repo.addPath(1, 2);
        repo.addPath(2, 4);
        repo.addPath(2, 7);

        //then
        assertThat(repo.checkIfDirectionExists(1, 2)).isTrue();
        assertThat(repo.checkIfDirectionExists(2, 4)).isTrue();
        assertThat(repo.checkIfDirectionExists(2, 7)).isTrue();
    }

    @Test
    public void repositoryForNotExistingDirectRouteShouldReturnFalse() {
        //given
        DirectRouteRepository repo = new DirectRouteRepository();

        //when
        repo.addPath(1, 2);
        repo.addPath(2, 4);
        repo.addPath(2, 7);

        //then
        assertThat(repo.checkIfDirectionExists(1, 4)).isFalse();
        assertThat(repo.checkIfDirectionExists(2, 1)).isFalse();
        assertThat(repo.checkIfDirectionExists(7, 2)).isFalse();
    }

}
