package info.ideatower.springboot.infox;

import info.ideatower.infra.support.web.http.JsonResult;
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
        return JsonResult.ok(Infox.get(resource));
    }
}
