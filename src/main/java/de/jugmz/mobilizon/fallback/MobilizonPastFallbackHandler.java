package de.jugmz.mobilizon.fallback;

import de.jugmz.meetup.EventDto;
import de.jugmz.mobilizon.MobilizonMapper;
import de.jugmz.mobilizon.MobilizonService;
import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;

import jakarta.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MobilizonPastFallbackHandler implements FallbackHandler<List<EventDto>> {

    Logger LOGGER = Logger.getLogger(MobilizonPastFallbackHandler.class.getName());

    private MobilizonService mobilizonService;

    private MobilizonMapper mobilizonMapper;

    @Inject
    public MobilizonPastFallbackHandler(MobilizonService mobilizonService, MobilizonMapper mobilizonMapper) {
        this.mobilizonService = mobilizonService;
        this.mobilizonMapper = mobilizonMapper;
    }

    @Override
    public List<EventDto> handle(ExecutionContext executionContext) {
        LOGGER.log(Level.INFO, "Serving 'Past Events' Request from Fallback Handler");
        return mobilizonService.getPastFromCache()
                .stream()
                .map(event -> mobilizonMapper.toDto(event, "JUG Mainz")) // FIXME
                .collect(Collectors.toList());
    }
}
