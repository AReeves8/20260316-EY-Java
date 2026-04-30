package com.skillstorm.spring_boot_actuator.actuator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.skillstorm.spring_boot_actuator.repositories.MovieRepository;


/**
 * implementing HelathIndicator will add new metrics to the /actuator/health endpoint
 * 
 *      Health states:
 *          Health.up() - everything is all  good
 *          Health.down() - something is not working correctly, attention is needed
 *          Health.unknown() - status cannot be determined
 *          Health.outOfService() - component is initentionally offline
 * 
 */
@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseHealthIndicator.class);

    private final MovieRepository movieRepository;
    
    public DatabaseHealthIndicator(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    @Override
    public Health health() {
        
        try {
            long movieCount = movieRepository.count();
            logger.debug("DatabaseHealthIndicator found {} records in database.", movieCount);

            if(movieCount > 0) {
                return Health.up()
                    .status("UP")
                    .withDetail("message", "Database is responsive and contains data.")
                    .withDetail("movieCount", movieCount)
                    .withDetail("dataIntegrity", "OK")
                    .build();
            }
            else {
                return Health.down()
                    .status("DOWN")
                    .withDetail("message", "Database is empty. Seed data not found.")
                    .withDetail("movieCount", movieCount)
                    .withDetail("dataIntegrity", "BAD")
                    .build();
            }
        }
        catch(Exception e) {

            logger.debug("DatabaseHealthIndicator: Health Check Failed. \nERROR: {}", e.getMessage());
            return Health.down()
                .status("DOWN")
                .withDetail("message", "Database could not be connected to. Database query failed.")
                .withDetail("error", e.getMessage())
                .build();
        }
    }
}
