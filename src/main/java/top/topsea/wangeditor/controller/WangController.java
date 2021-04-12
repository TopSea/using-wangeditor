package top.topsea.wangeditor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class WangController {

    @GetMapping("/wang")
    public String WangEditorPage(){
        return "editor";
    }

}
