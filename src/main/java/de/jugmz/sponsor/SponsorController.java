package de.jugmz.sponsor;

import de.jugmz.general.comparators.NoOpComparator;
import de.jugmz.general.comparators.RandomComparator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/api/sponsor")
@Produces(MediaType.APPLICATION_JSON)
public class SponsorController {

    private final static String SORT_BY_PARAM = "sortBy";
    private final static String SORT_BY_RANDOM_PARAM = "random";

    private final Comparator<SponsorDto> randomComparator = new RandomComparator<SponsorDto>();
    private final Comparator<SponsorDto> noOpComparator = new NoOpComparator<SponsorDto>();

    @GET
    public List<SponsorDto> getAll(@QueryParam(SORT_BY_PARAM) String sortBy) {
        return buildSponsors()
                .sorted(selectComparatorForParameter(sortBy))
                .collect(Collectors.toList());
    }

    private Stream<SponsorDto> buildSponsors() {
        return Stream.of(
                new SponsorDto("ilume", "ilum:e informatik ag", "https://www.ilume.de", "ilume.png"),
                new SponsorDto("triona", "Triona GmbH", "https://www.triona.de", "triona.png"),
                new SponsorDto("qaware", "QAware", "https://www.qaware.de", "qaware.svg"),
                new SponsorDto("hsmz", "Hochschule Mainz", "https://www.hs-mainz.de/", "hsmz.png"));
    }

    private Comparator<SponsorDto> selectComparatorForParameter(String sortBy) {
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
