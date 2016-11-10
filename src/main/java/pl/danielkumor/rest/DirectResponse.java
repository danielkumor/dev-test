package pl.danielkumor.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DirectResponse {

    public DirectResponse(Integer departureSID, Integer arrivalSID, boolean directBusRoute) {
        this.departureSID = departureSID;
        this.arrivalSID = arrivalSID;
        this.directBusRoute = directBusRoute;
    }

    @JsonProperty("dep_sid")
    private final Integer departureSID;

    @JsonProperty("arr_sid")
    private final Integer arrivalSID;

    @JsonProperty("direct_bus_route")
    private final boolean directBusRoute;
}
