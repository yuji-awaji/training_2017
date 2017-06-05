package jp.com.xpower.app2017.controller.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * トップページ用コントローラーです。
 */
@Controller
public class IndexController {
    /**
     * トップページを表示します。
     * @return テンプレートのパス
     */
    @RequestMapping("/websocket")
    public String showTopPage() {
        return "websocket";
    }
}