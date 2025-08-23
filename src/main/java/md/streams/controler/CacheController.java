package md.streams.controler;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Контроллер для управления кешем
@RestController
@RequestMapping("/api/cache")
public class CacheController {

    @PostMapping("/clear")
    public String clearCache() {
        // логика очистки кеша
        return "Cache cleared successfully";
    }
}
