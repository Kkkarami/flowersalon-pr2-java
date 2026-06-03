package com.fedelesh.flowersalon.infrastructure.payment;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;

public class LiqPayService {

  private static final String CHECKOUT_URL = "https://www.liqpay.ua/api/3/checkout";
  private static final String PUBLIC_KEY = "";
  private static final String PRIVATE_KEY = "";

  public String generatePaymentUrl(BigDecimal amount, String description, String orderId) {
    try {
      Map<String, String> params = new LinkedHashMap<>();

      params.put("public_key", PUBLIC_KEY);
      params.put("version", "3");
      params.put("action", "pay");
      params.put("amount", amount.toString());
      params.put("currency", "UAH");
      params.put("description", description);
      params.put("order_id", orderId);
      params.put("sandbox", "1");

      String data = encodeData(params);
      String signature = sign(data);

      return CHECKOUT_URL
          + "?data="
          + URLEncoder.encode(data, StandardCharsets.UTF_8)
          + "&signature="
          + URLEncoder.encode(signature, StandardCharsets.UTF_8);

    } catch (Exception e) {
      throw new RuntimeException("Помилка створення LiqPay платежу", e);
    }
  }

  private String encodeData(Map<String, String> params) {
    String json = new JSONObject(params).toString();
    return Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
  }

  private String sign(String data) throws Exception {
    MessageDigest digest = MessageDigest.getInstance("SHA-1");
    byte[] hash =
        digest.digest((PRIVATE_KEY + data + PRIVATE_KEY).getBytes(StandardCharsets.UTF_8));
    return Base64.getEncoder().encodeToString(hash);
  }
}
