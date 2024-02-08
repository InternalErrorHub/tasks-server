package net.internalerror.rest.security;

import jakarta.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.internalerror.data.entity.SecurityToken;
import net.internalerror.data.entity.User;
import net.internalerror.data.repository.SecurityTokenRepository;
import net.internalerror.data.repository.UserRepository;
import net.internalerror.rest.Messages;
import net.internalerror.rest.exception.ValidationException;
import net.internalerror.rest.request.SecuredRequestBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityService {

  private final Random random = new Random();

  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  private final SecurityTokenRepository securityTokenRepository;

  @Value("${security.token_lifetime_in_seconds}")
  private long tokenLifetime;

  private String secret;

  @PostConstruct
  public void setupSecret() {
    this.secret = generateSecret();
  }

  public void checkToken(SecuredRequestBase securedRequest) {
    if (!isAuthorized(securedRequest.getToken())) {
      throw new ValidationException(Messages.UNAUTHORIZED_REQUEST);
    }
  }

  public boolean isAuthorized(String token) {
    return securityTokenExists(token) && isTokenAlive(token);
  }

  public User getUser(SecuredRequestBase securedRequest) {
    return securityTokenRepository.findByToken(securedRequest.getToken())
                                  .getUser();
  }

  public SecurityToken getSecurityToken(User user) {
    if (securityTokenRepository.existsByUser(user)) {
      SecurityToken securityToken = securityTokenRepository.findByUser(user);
      if (isTokenAlive(securityToken.getToken())) {
        return securityToken;
      } else {
        securityTokenRepository.delete(securityToken);
      }
    }
    return createSecurityToken(user);
  }

  public boolean isTokenAlive(String token) {
    SecurityToken securityToken = securityTokenRepository.findByToken(token);
    return securityToken.getValidUntil()
                        .isAfter(Instant.now());
  }

  public SecurityToken createSecurityToken(User user) {
    SecurityToken securityToken = new SecurityToken();
    securityToken.setToken(createToken(user.getEmail()));
    securityToken.setValidUntil(Instant.now()
                                       .plus(tokenLifetime, ChronoUnit.SECONDS));
    securityToken.setUser(user);
    securityTokenRepository.save(securityToken);
    return securityToken;
  }

  public boolean securityTokenExists(String token) {
    return securityTokenRepository.existsByToken(token);
  }

  private String createToken(String email) {
    String uuid = UUID.randomUUID()
                      .toString();
    String time = LocalDateTime.now()
                               .toString();
    return passwordEncoder.encode(String.format("%s-%s-%s-%s", uuid, email, time, secret));
  }

  public boolean passwordMatches(String email, String rawPassword) {
    User user = userRepository.findByEmail(email);
    return passwordEncoder.matches(rawPassword, user.getPassword());
  }

  public String encodePassword(String rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }

  public String generateSecret() {
    String charset = "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";
    int length = random.nextInt(20, 45);
    StringBuilder secretBuilder = new StringBuilder();
    for (int i = 0; i < length; i++) {
      int chrIndex = random.nextInt(charset.length());
      char chr = charset.toCharArray()[chrIndex];
      secretBuilder.append(chr);
    }
    return secretBuilder.toString();
  }

}
