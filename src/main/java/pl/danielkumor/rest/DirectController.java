package pl.danielkumor.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.danielkumor.DirectRouteRepository;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Collections.EMPTY_SET;


@RestController
@RequestMapping("/api")
@Validated
public class DirectController {

    @Autowired
    private DirectRouteRepository directRouteRepository;

    @RequestMapping("/direct")
    public DirectResponse isDirect(@NotNull(message = "Required Integer parameter 'dep_sid' is not present")
                                   @RequestParam(name = "dep_sid") final Integer departureSID,
                                   @NotNull(message = "Required Integer parameter 'arr_sid' is not present")
                                   @RequestParam(name = "arr_sid") final Integer arrivalSID) {

        checkParameters(departureSID, arrivalSID);


        return new DirectResponse(departureSID, arrivalSID,
                directRouteRepository.checkIfDirectionExists(departureSID, arrivalSID));
    }

    private void checkParameters(final Integer departureSID, final Integer arrivalSID) {
        checkArgument(departureSID != null);

        if (departureSID.equals(arrivalSID)) {
            throw new ConstraintViolationException("'dep_sid' can not be the same as 'arr_sid'", EMPTY_SET);
        }
    }

}
