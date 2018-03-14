package com.jxduhai.manager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jxduhai.common.PicUploadResult;
import org.apache.log4j.Logger;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/****
 *@author yxw
 *@date 2018/3/14
 */
@Controller
@RequestMapping("/pic/upload")
public class PicUploadController {

    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    private Logger logger = Logger.getLogger(PicUploadController.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    String[] TYPE = {"jpg","jpeg","bmp","gif","png"};

    /**		filePostName  : "uploadFile",  //上传的文件名
            uploadJson : '/rest/pic/upload', //上传的路径
            dir : "image"   //上传的文件类型
     * 文件上传
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST , produces = MediaType.TEXT_HTML_VALUE)
    public String upload(MultipartFile uploadFile) throws JsonProcessingException {
        //设置返回数据,默认为上传失败
        PicUploadResult result = new PicUploadResult();
        result.setError(1);
        boolean flag = false;

        //获取上传文件的名
        String oldName = uploadFile.getOriginalFilename();
        String etx = oldName.substring(oldName.lastIndexOf(".")+1);
        //后缀验证
        for (String s : TYPE) {
            if (s.equals(etx));
            flag = true;
            break;
        }

        //后缀验证不通过,直接返回
        if (!flag){
            String value = MAPPER.writeValueAsString(result);
            return value;
        }

        //验证上传的文件是否是真的图片,如不是图片,将抛出异常,是图片,则更加读出的图片设置宽和高
        try {
            BufferedImage image = ImageIO.read(uploadFile.getInputStream());
            if (image != null){
                result.setHeight(image.getHeight()+"");
                result.setWidth((image.getWidth()+""));
            }
        } catch (IOException e) {
            e.printStackTrace();
            String value = MAPPER.writeValueAsString(result);
            return value;
        }

        //至此,图片已经全部验证完成,开始上传图片给fastDFS
        /*
        *   1、创建tracker.conf配置文件，内容就是tracker服务的地址。配置文件内容：tracker_server=192.168.37.161:22122，然后加载配置文件(ClientGlobal.init方法加载)
            2、创建一个TrackerClient对象。直接new一个。
            3、使用TrackerClient对象创建连接，getConnection获得一个TrackerServer对象。
            4、创建一个StorageServer的引用，值为null，为接下来创建StorageClient使用
            5、创建一个StorageClient对象，直接new一个，需要两个参数TrackerServer对象、StorageServer的引用
            6、使用StorageClient对象upload_file方法上传图片。
            7、返回数组。包含组名和图片的路径，打印结果。
        * */

        try {
            String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//            String confurl = System.getProperty("user.dir") + "\\src\\main\\resources\\tracker.conf";
            ClientGlobal.init(path+"\\tracker.conf");
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageServer storageServer = null;
            StorageClient storageClient = new StorageClient(trackerServer,storageServer);
            String[] arr = storageClient.upload_file(uploadFile.getBytes(), etx, null);

            String url = IMAGE_SERVER_URL + "/" + arr[0] + "/" + arr[1];
            result.setError(0);
            result.setUrl(url);

        } catch (Exception e) {
            logger.error("上传图片发生了异常",e);
        }
        String value = MAPPER.writeValueAsString(result);
        return value;
//        return result;
    }
}
