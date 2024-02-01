package de.jugmz.partner;

import de.jugmz.general.comparators.NoOpComparator;
import de.jugmz.general.comparators.RandomComparator;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/api/partner")
@Produces(MediaType.APPLICATION_JSON)
public class PartnerController {

    private final static String SORT_BY_PARAM = "sortBy";
    private final static String SORT_BY_RANDOM_PARAM = "random";

    private final Comparator<PartnerDto> randomComparator = new RandomComparator<PartnerDto>();
    private final Comparator<PartnerDto> noOpComparator = new NoOpComparator<PartnerDto>();

    @GET
    public List<PartnerDto> getAll(@QueryParam(SORT_BY_PARAM) String sortBy) {
        return buildSponsors()
                .sorted(selectComparatorForParameter(sortBy))
                .collect(Collectors.toList());
    }

    private Stream<PartnerDto> buildSponsors() {
        return Stream.of(
                new PartnerDto("jax", "JAX", "https://www.jax.de", "jax.jpg"),
                new PartnerDto("javaland", "JavaLand 2020", "https://www.javaland.eu/de/home/", "javaland.png"));
    }

    private Comparator<PartnerDto> selectComparatorForParameter(String sortBy) {
        if (null == sortBy) {
            return noOpComparator;
        }

        switch (sortBy) {
            case SORT_BY_RANDOM_PARAM:
                return randomComparator;
            default:
                return noOpComparator;
        }
    }
}
