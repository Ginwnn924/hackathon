package com.example.hackathon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OverpassService {
//
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    public OverpassResponse queryOverpass(String lat, String lon, int radius, Map<String, List<String>> tags) {
//        String query = buildQuery(lat, lon, radius, tags);
//        String url = "https://overpass-api.de/api/interpreter";
//        log.info("query url: {}", query);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("data", query); // KHÔNG ENCODE
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
//        String json = restTemplate.postForObject(url, request, String.class);
//        // Chỉ lấy tag elements thôi
//
//
//        // parse JSON về OverpassResponse
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            OverpassResponse response = mapper.readValue(json, OverpassResponse.class);
//
//            // filter chỉ giữ element có tag "name"
//            List<Place> filtered = response.getElements().stream()
//                    .filter(e -> e.getTags() != null && e.getTags().containsKey("name"))
//                    .map(e -> {
//                        // nếu type là way hoặc relation, lấy center nếu có
//                        if ((e.getType().equals("way") || e.getType().equals("relation")) && e.getCenter() != null) {
//                            e.setLat(e.getCenter().getLat());
//                            e.setLon(e.getCenter().getLon());
//                        }
//                        return e;
//                    })
//                    .toList();
//
//            response.setElements(filtered);
//            return response;
//        } catch (Exception e) {
//            log.error("Parse Overpass JSON failed", e);
//            return new OverpassResponse(); // trả rỗng nếu lỗi
//        }
//    }
//
//    private String buildQuery(String lat, String lon, int radius, Map<String, List<String>> tags) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("[out:json][timeout:25];\n(\n");
//
//        // vòng lặp tag
//        for (Map.Entry<String, List<String>> entry : tags.entrySet()) {
//            String key = entry.getKey();
//            List<String> values = entry.getValue();
//
//            // Nếu list rỗng, chỉ lọc key
//            if (values.isEmpty()) {
//                sb.append("  node[\"").append(key).append("\"](around:")
//                        .append(radius).append(",").append(lat).append(",").append(lon).append(");\n");
//                sb.append("  way[\"").append(key).append("\"](around:")
//                        .append(radius).append(",").append(lat).append(",").append(lon).append(");\n");
//                sb.append("  relation[\"").append(key).append("\"](around:")
//                        .append(radius).append(",").append(lat).append(",").append(lon).append(");\n");
//            } else {
//                for (String value : values) {
//                    sb.append("  node[\"").append(key).append("\"=\"").append(value).append("\"](around:")
//                            .append(radius).append(",").append(lat).append(",").append(lon).append(");\n");
//                    sb.append("  way[\"").append(key).append("\"=\"").append(value).append("\"](around:")
//                            .append(radius).append(",").append(lat).append(",").append(lon).append(");\n");
//                    sb.append("  relation[\"").append(key).append("\"=\"").append(value).append("\"](around:")
//                            .append(radius).append(",").append(lat).append(",").append(lon).append(");\n");
//                }
//            }
//        }
//
//        sb.append(");\nout center tags;\n");
//        return sb.toString();
//    }

}
