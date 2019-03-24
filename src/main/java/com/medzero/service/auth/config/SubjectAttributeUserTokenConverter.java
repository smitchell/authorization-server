package com.medzero.service.auth.config;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

/**
 * Legacy Authorization Server does not support a custom name for the user parameter, so we'll need
 * to extend the default. By default, it uses the attribute {@code user_name}, though it would be
 * better to adhere to the {@code sub} property defined in the
 * <a target="_blank" href="https://tools.ietf.org/html/rfc7519">JWT Specification</a>.
 */
public class SubjectAttributeUserTokenConverter extends DefaultUserAuthenticationConverter {
  @Override
  public Map<String, ?> convertUserAuthentication(Authentication authentication) {
    Map<String, Object> response = new LinkedHashMap<String, Object>();
    response.put("sub", authentication.getName());
    if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
      response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
    }
    return response;
  }
}
