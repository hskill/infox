package info.ideatower.springboot.infox.web;

import info.ideatower.infra.support.web.http.JsonResult;
import info.ideatower.springboot.infox.Infox;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/infox")
public class InfoxController {

    @GetMapping("")
    public String all() {
        return JsonResult.ok(Infox.getAll());
    }

    @GetMapping("/{resource}")
    public String resource(@PathVariable("resource") String resource) {
        if (Infox.getAll().containsKey(resource)) {
            return JsonResult.ok(Infox.get(resource));
        }
        return JsonResult.error("404", "Not Found");
    }
}
