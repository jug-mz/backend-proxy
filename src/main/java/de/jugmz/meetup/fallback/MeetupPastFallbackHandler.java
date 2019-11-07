package de.jugmz.meetup.fallback;

import de.jugmz.SimplestCache;
import de.jugmz.meetup.EventDto;
import de.jugmz.meetup.MeetupMapper;
import de.jugmz.meetup.MeetupService;
import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MeetupPastFallbackHandler implements FallbackHandler<List<EventDto>> {

    Logger LOGGER = Logger.getLogger(MeetupPastFallbackHandler.class.getName());

    private MeetupService meetupService;

    private MeetupMapper meetupMapper;

    public MeetupPastFallbackHandler() {

    }

    @Inject
    public MeetupPastFallbackHandler(MeetupService meetupService, MeetupMapper meetupMapper) {
        this.meetupService = meetupService;
        this.meetupMapper = meetupMapper;
    }

    @Override
    public List<EventDto> handle(ExecutionContext executionContext) {
        LOGGER.log(Level.INFO, "Serving 'Past Events' Request from Fallback Handler");
        return meetupService.getPastFromCache()
                .stream()
                .map(meetupMapper::toDto)
                .collect(Collectors.toList());
    }
}
