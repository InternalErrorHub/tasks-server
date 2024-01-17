package net.internalerror;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.internalerror.data.entity.SecurityToken;
import net.internalerror.data.repository.SecurityTokenRepository;
import net.internalerror.rest.security.SecurityService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ScheduledTasks {

  private final SecurityTokenRepository securityTokenRepository;

  private final SecurityService securityService;

  @Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
  public void removeExpiredTokens() {
    log.info("Scanning for expired tokens");
    Iterable<SecurityToken> securityTokens = securityTokenRepository.findAll();
    List<SecurityToken> expiredTokens = new ArrayList<>();

    securityTokens.forEach(securityToken -> {
      if (securityService.isTokenAlive(securityToken.getToken())) {
        expiredTokens.add(securityToken);
      }
    });

    log.info("Found {} expired tokens", expiredTokens.size());

    if (!expiredTokens.isEmpty()) {
      log.info("Removing expired tokens");

      securityTokenRepository.deleteAll(expiredTokens);

      log.info("Removed {} expired tokens", expiredTokens.size());
    }

  }

}
