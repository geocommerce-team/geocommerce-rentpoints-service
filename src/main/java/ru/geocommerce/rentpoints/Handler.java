package ru.geocommerce.rentpoints;

import com.microsoft.playwright.*;

import java.util.HashMap;
import java.util.Map;

public class Handler {
    private static final string baseUrl = "https://www.avito.ru/web/1/map/markers?filters=";
    private static final string partFiltersBeforeMaps = """
            {"categoryId":42,"correctorMode":0,"directionId":[],"disabledFilters":{"ids":["byTitle"],"slugs":["bt"]},"districtId":[],"from":"","localPriority":0,"locationId":641780,"map":"
            """;
    private static final string partFiltersAfterMaps = """
            ","metroId":[],"page":1,"params":{"110799":472643,"1209":13968,"178133":1,"536":5545},"rootCategoryId":4,
            """;

    private static final string partFiltersLast = """
            ,"subscription":{"isAuthenticated":false,"isErrorSaved":false,"isShowSavedTooltip":false,"visible":true},"verticalCategoryId":1,"viewPort":{"height":432,"width":726}}
            """;
    private static final string endUrl = "&offsetView%5Bleft%5D=500&z=1"

    public static String CreateRequest(Rash.Coordinates leftTop, Rash.Coordinates rightBottom) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(true) // Headless режим
            );

            // Заголовки
            Map<String, String> headers = new HashMap<>();
            headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
            headers.put("Accept-Language", "ru,en-US;q=0.9,en;q=0.8,zh-CN;q=0.7,zh;q=0.6,nl;q=0.5");
            headers.put("Cache-Control", "max-age=0");
            headers.put("Priority", "u=0, i");
            headers.put("Sec-CH-UA", "\"Chromium\";v=\"140\", \"Not=A?Brand\";v=\"24\", \"Opera\";v=\"124\"");
            headers.put("Sec-CH-UA-Mobile", "?0");
            headers.put("Sec-CH-UA-Platform", "Windows");
            headers.put("Sec-Fetch-Dest", "document");
            headers.put("Sec-Fetch-Mode", "navigate");
            headers.put("Sec-Fetch-Site", "none");
            headers.put("Sec-Fetch-User", "?1");
            headers.put("Upgrade-Insecure-Requests", "1");
            headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36 OPR/124.0.0.0");

            string searchArea = """
                    "searchArea":{"latBottom":54.123456789751346,"latTop":55.12345678975136,"lonLeft":82.12345678975136,"lonRight":83.12345678975136}
                    """;

            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    .setExtraHTTPHeaders(headers)
            );

            Page page = context.newPage();



            Response response = page.navigate("https://www.avito.ru/web/1/map/markers?filters=eyJjYXRlZ29yeUlkIjo0MiwiY29ycmVjdG9yTW9kZSI6MCwiZGlyZWN0aW9uSWQiOltdLCJkaXNhYmxlZEZpbHRlcnMiOnsiaWRzIjpbImJ5VGl0bGUiXSwic2x1Z3MiOlsiYnQiXX0sImRpc3RyaWN0SWQiOltdLCJmcm9tIjoiIiwibG9jYWxQcmlvcml0eSI6MCwibG9jYXRpb25JZCI6NjQxNzgwLCJtYXAiOiJleUp6WldGeVkyaEJjbVZoSWpwN0lteGhkRUp2ZEhSdmJTSTZOVFF1T0RVM09ERXhNamt5T0Rjd01USTBMQ0pzWVhSVWIzQWlPalUxTGpBeU9ERTVNekE0TkRJd09UZzRMQ0pzYjI1TVpXWjBJam80TWk0NE5qVXhNRGd4TkRFek5qVTBNU3dpYkc5dVVtbG5hSFFpT2pnekxqTTJNell4TWpjNE1EQXpOekk1ZlN3aWVtOXZiU0k2TVRGOSIsIm1ldHJvSWQiOltdLCJwYWdlIjoxLCJwYXJhbXMiOnsiMTEwNzk5Ijo0NzI2NDMsIjEyMDkiOjEzOTY4LCIxNzgxMzMiOjEsIjUzNiI6NTU0NX0sInJvb3RDYXRlZ29yeUlkIjo0LCJzZWFyY2hBcmVhIjp7ImxhdEJvdHRvbSI6NTQuODU3ODExMjkyODcwMTI0LCJsYXRUb3AiOjU1LjAyODE5MzA4NDIwOTg4LCJsb25MZWZ0Ijo4Mi44NjUxMDgxNDEzNjU0MSwibG9uUmlnaHQiOjgzLjM2MzYxMjc4MDAzNzI5fSwic3Vic2NyaXB0aW9uIjp7ImlzQXV0aGVudGljYXRlZCI6ZmFsc2UsImlzRXJyb3JTYXZlZCI6ZmFsc2UsImlzU2hvd1NhdmVkVG9vbHRpcCI6ZmFsc2UsInZpc2libGUiOnRydWV9LCJ2ZXJ0aWNhbENhdGVnb3J5SWQiOjEsInZpZXdQb3J0Ijp7ImhlaWdodCI6NDMyLCJ3aWR0aCI6NzI2fX0%3D&offsetView%5Bleft%5D=500&z=11&isMiniMap=false");

            String html = page.content();

            browser.close();
            return html;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        CreateRequest()
    }
}
