package likelion_backend.OnSiL.domain.healthnews.client;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class JsoupCrawling {
    public Optional<Elements> getJsoupElements(String url, String query) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select(query);
            return Optional.of(elements);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
