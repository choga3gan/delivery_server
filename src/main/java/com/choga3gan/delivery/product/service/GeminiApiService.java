/**
 * @package     com.choga3gan.delivery.product.service
 * @class       GeminiApiService
 * @description gemini api 호출을 위한 서비스
 *
 * @author      jinnk0
 * @since       2025. 11. 6.
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025. 11. 6.        jinnk0       최초 생성
 * </pre>
 */
package com.choga3gan.delivery.product.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

@Service
public class GeminiApiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    public String generateText(String prompt) {
        Client client = Client.builder().apiKey(apiKey).build();

        prompt += "이 상품에 대한 소개 문구를 작성해줘. 답변은 최대한 간결하게 50자 이하로";

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.5-flash",
                        prompt,
                        null);
        return response.text();
    }
}
