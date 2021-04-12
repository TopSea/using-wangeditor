package top.topsea.wangeditor.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;
import top.topsea.wangeditor.config.UploadImagesConfig;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Receiver {

    //这个注入配置文件，主要是因为本地的路径和服务器url路径需要动态配置，可以自己写死，也可以动态获取
    @Resource
    UploadImagesConfig wang;

    @PostMapping("/send-content")
    public void getContent(String content){
        System.out.println(content);
    }

    @RequestMapping("/upload")
    @ResponseBody
    public Object editor(@RequestParam("file") MultipartFile file) {
        String fileName ="";
        if(!file.isEmpty()){
            //返回的是字节长度,1M=1024k=1048576字节 也就是if(fileSize<5*1048576)
            if(file.getSize()>(1048576*20)){return new WangEditorResponse("1", "文件太大，请上传小于20MB的");}
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            if(StringUtils.isEmpty(suffix)){return new WangEditorResponse("1", "上传文件没有后缀，无法识别");}

            fileName = System.currentTimeMillis()+suffix;
            String saveFileName = wang.getFilepath()+"\\"+fileName;
            System.out.println(saveFileName);
            File dest = new File(saveFileName);
            System.out.println(dest.getParentFile().getPath());
            if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }
            try {
                file.transferTo(dest); //保存文件
            } catch (Exception e) {
                e.printStackTrace();
                return new WangEditorResponse("1", "上传失败" + e.getMessage());
                //return ApiReturnUtil.error("上传失败"+e.getMessage());
            }
        }else {
            return new WangEditorResponse("1", "上传出错");
        }
        String imgUrl=wang.getUrlpath()+fileName;
        return new WangEditorResponse("0", imgUrl);
    }
    @Data
    private class WangEditorResponse{
        String errno;
        List<String> data;
        public WangEditorResponse(String errno, List<String> data){
            this.errno = errno;
            this.data=data;
        }
        public WangEditorResponse(String errno, String data){
            this.errno = errno;
            this.data=new ArrayList<>();
            this.data.add(data);
        }
    }
}
