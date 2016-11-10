package pl.danielkumor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static org.springframework.util.StringUtils.isEmpty;

@Configuration
@ControllerAdvice
public class AppConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfiguration.class);

    @Autowired
    public ApplicationContext applicationContext;

    @Bean
    public DirectRouteRepository createDirectBusRouteRepository() throws IOException {

        LOGGER.debug("Create DirectRouteRepository");

        String[] args = applicationContext.getEnvironment().getProperty("nonOptionArgs", String[].class);
        checkArgument(args != null && args.length != 1 || !isEmpty(args[0]),
                "Incorrect number of attributes, expected 1");

        return BusRoutesDataFileParser.parse(args[0]);
    }


    // Configure request param validation
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handle(ConstraintViolationException exception) {
        return error(exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()));
    }

    private Map error(Object message) {
        return Collections.singletonMap("error", message);
    }


}

