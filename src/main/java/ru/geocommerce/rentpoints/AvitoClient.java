package ru.geocommerce.rentpoints;

import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class AvitoClient {

    private static final Logger log = LoggerFactory.getLogger(AvitoClient.class);

    private static final String BASE_URL = "https://www.avito.ru/web/1/map/markers?filters=";
    private static final String PART_BEFORE_MAP = "{\"categoryId\":42,\"correctorMode\":0,\"directionId\":[],\"disabledFilters\":{\"ids\":[\"byTitle\"],\"slugs\":[\"bt\"]},\"districtId\":[],\"from\":\"\",\"localPriority\":0,\"locationId\":641780,\"map\":\"";
    private static final String PART_AFTER_MAP = "\",\"metroId\":[],\"page\":1,\"params\":{\"110799\":472643,\"1209\":13968,\"178133\":1,\"536\":5545},\"rootCategoryId\":4,";
    private static final String PART_LAST = ",\"subscription\":{\"isAuthenticated\":false,\"isErrorSaved\":false,\"isShowSavedTooltip\":false,\"visible\":true},\"verticalCategoryId\":1,\"viewPort\":{\"height\":432,\"width\":726}}";
    private static final String END_URL = "&offsetView%5Bleft%5D=500&z=1";


    public String fetchMarkers(Rash.Coordinates leftTop, Rash.Coordinates rightBottom) {
        String searchArea = String.format(
                "\"searchArea\":{\"latBottom\":%f,\"latTop\":%f,\"lonLeft\":%f,\"lonRight\":%f}",
                rightBottom.getLat(), leftTop.getLat(), leftTop.getLng(), rightBottom.getLng()
        );

        String mapJson = "{" + searchArea + ",\"zoom\":11}";
        String mapBase64 = Base64.getEncoder().encodeToString(mapJson.getBytes(StandardCharsets.UTF_8));

        String filtersJson = PART_BEFORE_MAP + mapBase64 + PART_AFTER_MAP + searchArea + PART_LAST;
        String filtersBase64 = Base64.getEncoder().encodeToString(filtersJson.getBytes(StandardCharsets.UTF_8));
        String filtersEncoded = URLEncoder.encode(filtersBase64, StandardCharsets.UTF_8);

        String url = BASE_URL + filtersEncoded + END_URL;

        log.debug("Requesting Avito URL: {}", url);

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));

            Map<String, String> headers = new HashMap<>();
            headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36");
            headers.put("Accept", "application/json");
            // остальные заголовки можно упростить

            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    .setExtraHTTPHeaders(headers)
            );

            Page page = context.newPage();
            Response response = page.navigate(url);

            if (response == null || response.status() != 200) {
                log.warn("Avito returned status: {}", response != null ? response.status() : "null");
                return null;
            }

            String content = page.textContent("body"); // Avito возвращает чистый JSON, не HTML!
            browser.close();
            return content;
        } catch (Exception e) {
            log.error("Error fetching Avito markers", e);
            return null;
        }
    }
}