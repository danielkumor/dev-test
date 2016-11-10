package pl.danielkumor.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.danielkumor.DirectRouteRepository;

import static java.lang.String.format;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DirectController.class)
public class DirectControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DirectRouteRepository directRouteRepository;

    @Test
    public void controllerForGivenRepositoryAndRequestParametersShouldReturnProperResponse() throws Exception {

        int depSID = 1;
        int arrSID = 2;
        given(directRouteRepository.checkIfDirectionExists(anyInt(), anyInt())).willReturn(true);

        mvc.perform(get(format("/api/direct?dep_sid=%s &arr_sid=%s", depSID, arrSID))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(format("{\"dep_sid\":%s,\"arr_sid\":%s,\"direct_bus_route\":true}", depSID, arrSID )));
    }

    @Test
    public void controllerForGivenIncorrectRequestParametersShouldReturnBadRequest() throws Exception {
        mvc.perform(get(format("/api/direct?dep_sid=%s &arr_sid=%s", 1, "string"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void controllerForSameDepartureSIDAndArrivalSIDShouldReturnBadRequest() throws Exception {
        mvc.perform(get(format("/api/direct?dep_sid=%s &arr_sid=%s", 1, 1))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

}
