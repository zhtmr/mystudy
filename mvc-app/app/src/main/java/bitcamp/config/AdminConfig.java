package bitcamp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(value = "bitcamp.web", excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "bitcamp.web.app.*"))
public class AdminConfig {
}
