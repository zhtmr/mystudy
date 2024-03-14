package bitcamp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import static org.springframework.context.annotation.ComponentScan.*;

@ComponentScan(value = "bitcamp.web", excludeFilters = @Filter(type = FilterType.REGEX, pattern = "bitcamp.web.admin.*"))
public class AppConfig {

}
