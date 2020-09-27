package api;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.Image;
import dao.ImageDao;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ImageServlet extends HttpServlet {
    /**
     * 查看图片属性: 既能查看所有, 也能查看指定
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        //查看图片，可以查看一个，也可以查看所有
        // 查看指定图片时，用 imageId 可以定位到图片

        //解析请求
        String imageId = req.getParameter("imageId");
        if(imageId==null||imageId.equals("")){
            //查看所有图片属性
            selectAll(req,resp);
        }else{
            //查看imageId对应的图片属性
            selectOne(imageId,resp);
        }
   }

    private void selectAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //使用json数据格式，ContentType就是application/json
        resp.setContentType("application/json; charset=utf-8");
        //创建了ImageDao对象
        ImageDao imageDao = new ImageDao();
        //调用imageDao的selectAll方法，将数据库中的所有图片选取
        List<Image> images = imageDao.selectAll();
        //创建gson对象
        Gson gson = new GsonBuilder().create();
        //将gson对象利用toJson方法转为Json格式的字符串
        String jsonData = gson.toJson(images);
        //将Json格式的字符串写入到resp对象
        //Tomcat构造resp对象，生成HTTP报文，通过Socket写回浏览器
        resp.getWriter().write(jsonData);
    }

    private void selectOne(String imageId, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=utf-8");
        ImageDao imageDao = new ImageDao();
        Image image = imageDao.selectOne(Integer.parseInt(imageId));
        Gson gson = new GsonBuilder().create();
        String jsonData = gson.toJson(image);
        resp.getWriter().write(jsonData);
    }


    /**
     * 上传图片（在数据库、磁盘文件）
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取图片的属性信息，并且存入数据库
        //  a)需要创建一个 factory 对象和 upload 对象，这是为了获取到图片属性做的准备工作
        //    固定的逻辑
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        //  b)通过 upload 对象进一步解析请求
        //    FileItem 就代表一个上传的文件对象
        //    理论上说，HTTP 支持一个请求中同时上传多个文件，所以用List
        List<FileItem> items = null;
        try {
            items = upload.parseRequest(req);
        } catch (FileUploadException e) {
            //出现异常说明解析出错！
            e.printStackTrace();

            //告诉客户端出现的错误是是什么
            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().write("{\"ok\": false,\"reason\":\"请求解析失败\"}");
            return;
        }
        //  c)把 FileItem 中的属性提取出来，转换成 Image 对象，才能存到数据库
        //    当前只考虑一张图片的情况
        FileItem fileItem = items.get(0);
        Image image = new Image();
        image.setImageName(fileItem.getName());
        image.setSize((int) fileItem.getSize());
        //手动获取当前日期，并转成格式化日期-uploadTime
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        image.setUploadTime(simpleDateFormat.format(new Date()));
        image.setContentType(fileItem.getContentType());
        image.setMd5(DigestUtils.md5Hex(fileItem.get()));
        //引入时间戳是为了文件路径唯一
        //image.setPath("./image/"+System.currentTimeMillis()+image.getImageName());
        image.setPath("./image/"+image.getMd5());
        //存到数据库中
        ImageDao imageDao = new ImageDao();
        //看数据库中是否存在相同的md5的图片，不存在，返回null
        Image existImage = imageDao.selectByMd5(image.getMd5());
        imageDao.insert(image);
        //2. 获取图片的内容信息，并写入磁盘文件

        if (existImage == null) {
            File file = new File(image.getPath());
            try {
                fileItem.write(file);
            } catch (Exception e) {
                e.printStackTrace();
                resp.setContentType("application/json; charset=utf-8");
                resp.getWriter().write("{\"ok\": false,\"reason\":\"写磁盘失败\"}");
                return;
            }
        }
        //3. 给客户端返回一个结果数据
        resp.sendRedirect("index.html");
    }

    /**
     * 删除图片（同时也要删除数据库和磁盘上的图片）
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        // 1. 先获取到请求中的 imageId
        String imageId = req.getParameter("imageId");
        if (imageId == null || imageId.equals("")) {
            resp.setStatus(200);
            resp.getWriter().write("{ \"ok\": false, \"reason\": \"解析请求失败\" }");
            return;
        }
        // 2. 创建 ImageDao 对象, 查看到该图片对象对应的相关属性(这是为了知道这个图片对应的文件路径)
        ImageDao imageDao = new ImageDao();
        Image image = imageDao.selectOne(Integer.parseInt(imageId));
        if (image == null) {
            // 此时请求中传入的 id 在数据库中不存在.
            resp.setStatus(200);
            resp.getWriter().write("{ \"ok\": false, \"reason\": \"imageId 在数据库中不存在\" }");
            return;
        }
        // 3. 删除数据库中的记录
        imageDao.delete(Integer.parseInt(imageId));
        // 4. 删除本地磁盘文件
        File file = new File(image.getPath());
        file.delete();
        resp.setStatus(200);
        resp.getWriter().write("{ \"ok\": true }");
    }
}

