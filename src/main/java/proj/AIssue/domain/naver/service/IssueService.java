package proj.AIssue.domain.naver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import proj.AIssue.domain.naver.dto.IssueDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueService {

    @Value("${naver.api.clientId}")
    private String clientId;

    @Value("${naver.api.clientSecret}")
    private String clientSecret;

    @Value("${naver.api.url}")
    private String apiUrl;

    private final ObjectMapper objectMapper;

    /**
     * 네이버API를 사용해서 뉴스 기사를 검색
     */
    public List<IssueDTO> searchNews(String query) {
        String urlStr = apiUrl + "?query=" + URLEncoder.encode(query, StandardCharsets.UTF_8);

        HttpURLConnection urlConnection = null;
        InputStream stream = null;
        String result = null;

        try {
            URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection(); //네이버 API에 HTTP요청
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("X-Naver-Client-Id", clientId); //요청헤더에 ID,Secret
            urlConnection.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            urlConnection.setConnectTimeout(3000); //연결 및 읽기 시간 3초
            urlConnection.setReadTimeout(3000);

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = urlConnection.getInputStream();
                result = readStreamToString(stream);
            } else {
                throw new IOException("HTTP error code : " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) try { stream.close(); } catch (IOException e) { e.printStackTrace(); }
            if (urlConnection != null) urlConnection.disconnect();
        }

        // JSON 응답을 IssueDTO 리스트로 변환
        List<IssueDTO> issueDTOs = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(result); //JSON파싱 -> 트리구조로 변환
            JsonNode items = root.path("items"); // JSON 데이터에서 값을 가져옴 -> 뉴스 기사 목록

            for (JsonNode item : items) {
                String title = item.path("title").asText();
                String sourceUrl = item.path("originallink").asText();
                String description = item.path("description").asText();

                IssueDTO issueDTO = new IssueDTO(title, sourceUrl, description);
                issueDTOs.add(issueDTO);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return issueDTOs;
    }

    private String readStreamToString(InputStream stream) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        String readLine;
        while ((readLine = br.readLine()) != null) {
            result.append(readLine).append("\n");
        }
        br.close();
        return result.toString();
    }
}
