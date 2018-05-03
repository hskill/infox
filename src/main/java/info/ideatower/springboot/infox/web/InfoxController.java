package info.ideatower.springboot.infox.web;

import info.ideatower.springboot.infox.Infox;
import info.ideatower.springboot.infox.web.json.JsonParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/infox")
public class InfoxController {

    @GetMapping("")
    public String all() {
        return JsonParser.ok(Infox.getAll());
    }

    @GetMapping("/{resource}")
    public String resource(@PathVariable("resource") String resource) {
        if (Infox.getAll().containsKey(resource)) {
            return JsonParser.ok(Infox.get(resource));
        }
        return JsonParser.error("404", "Not Found");
    }
}
