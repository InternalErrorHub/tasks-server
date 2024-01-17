package net.internalerror.rest.request.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import net.internalerror.rest.Messages;
import net.internalerror.rest.request.SecuredRequestBase;

@Getter
@Setter
public class UpdateUserRequest extends SecuredRequestBase {

  @NotNull(message = Messages.FIRSTNAME_IS_NULL)
  @NotEmpty(message = Messages.FIRSTNAME_IS_EMPTY)
  private String firstname;

  @NotNull(message = Messages.LASTNAME_IS_NULL)
  @NotEmpty(message = Messages.LASTNAME_IS_EMPTY)
  private String lastname;

}
