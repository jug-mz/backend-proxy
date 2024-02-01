package de.jugmz.meetup.fallback;

import de.jugmz.meetup.EventDto;
import de.jugmz.meetup.MeetupMapper;
import de.jugmz.meetup.MeetupService;
import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;

import jakarta.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MeetupUpcomingFallbackHandler implements FallbackHandler<List<EventDto>> {

    Logger LOGGER = Logger.getLogger(MeetupUpcomingFallbackHandler.class.getName());

    private MeetupService meetupService;

    private MeetupMapper meetupMapper;

    public MeetupUpcomingFallbackHandler() {

    }

    @Inject
    public MeetupUpcomingFallbackHandler(MeetupService meetupService, MeetupMapper meetupMapper) {
        this.meetupService = meetupService;
        this.meetupMapper = meetupMapper;
    }

    @Override
    public List<EventDto> handle(ExecutionContext executionContext) {
        LOGGER.log(Level.INFO, "Serving 'Past Events' Request from Fallback Handler");
        return meetupService.getUpcomingFromCache()
                .stream()
                .map(meetupMapper::toDto)
                .collect(Collectors.toList());
    }
}
