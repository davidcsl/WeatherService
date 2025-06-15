package com.david.weather_service.interceptor;

import com.david.weather_service.model.dto.ApiKey;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class ApiKeyInterceptor implements HandlerInterceptor {

  public ApiKeyInterceptor() {
    keyMap.put("abcd", new ApiKey("abcd"));
    keyMap.put("efgh", new ApiKey("efgh"));
    keyMap.put("ijkl", new ApiKey("ijkl"));
    keyMap.put("mnop", new ApiKey("mnop"));
    keyMap.put("qrst", new ApiKey("qrst"));
  }

  private static final ConcurrentHashMap<String, ApiKey> keyMap = new ConcurrentHashMap<>();

  @Value("${app.apikey.interval-ms}")
  private long maxInterval;

  @Override
  public boolean preHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler) throws Exception {

    String incomingApiKey = request.getParameter("apiKey");

    if (Objects.isNull(incomingApiKey) || !keyMap.containsKey(incomingApiKey)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Invalid API Key");
      return false;
    } else {
      ApiKey apiKey = keyMap.get(incomingApiKey);
      long currentTimestamp = Instant.now().toEpochMilli();

      if (apiKey.getLives() == 0 && currentTimestamp - apiKey.getTimestamp() < maxInterval) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Invalid API Key");
        return false;
      } else if (apiKey.getLives() == 0 && currentTimestamp - apiKey.getTimestamp() >= maxInterval){
        apiKey.setLives(4);
        apiKey.setTimestamp(currentTimestamp);
        keyMap.put(apiKey.getApikey(), apiKey);
      } else {
        apiKey.setLives(apiKey.getLives() - 1);
        keyMap.put(apiKey.getApikey(), apiKey);
      }
      log.info("Current API object is :{}", apiKey);
      return true;
    }
  }
}
