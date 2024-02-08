package net.internalerror;

import java.util.List;
import lombok.Generated;
import lombok.experimental.UtilityClass;
import net.internalerror.rest.exception.ValidationException;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.EnglishSequenceData;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

@Generated
@UtilityClass
public class ServerUtil {

  private static final PasswordValidator passwordValidator;

  static {
    passwordValidator = new PasswordValidator(new LengthRule(8, 16), new CharacterRule(EnglishCharacterData.UpperCase, 1), new CharacterRule(EnglishCharacterData.LowerCase, 1), new CharacterRule(EnglishCharacterData.Digit, 1), new CharacterRule(EnglishCharacterData.Special, 1), new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false), new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false), new IllegalSequenceRule(EnglishSequenceData.USQwerty, 5, false), new WhitespaceRule());
  }

  public static void checkPasswordStrength(String rawPassword) {
    RuleResult result = passwordValidator.validate(new PasswordData(rawPassword));
    if (!result.isValid()) {
      StringBuilder builder = new StringBuilder();
      List<String> messages = passwordValidator.getMessages(result);
      for (int i = 0; i < messages.size(); i++) {
        String message = messages.get(i);
        builder.append("PASSWORD_")
               .append(message);
        if (i != messages.size() - 1) {
          builder.append(",");
        }
      }
      throw new ValidationException(builder.toString());
    }
  }

}

