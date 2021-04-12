package top.topsea.wangeditor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "wang") // 接收application.yml中的myProps下面的属性
public class UploadImagesConfig {
    public String filepath;
    public String urlpath;
}
