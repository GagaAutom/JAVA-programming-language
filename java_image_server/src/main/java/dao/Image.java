package dao;
//用Image类封装一个image对象
//这里设置了image对象的属性信息，和数据库的表一一对应
public class Image {
    private int imageId;
    private String imageName;
    private int size;
    private String uploadTime;
    private String contentType;
    private String path;
    private String md5;

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public int getImageId() {
        return imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public int getSize() {
        return size;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public String getContentType() {
        return contentType;
    }

    public String getPath() {
        return path;
    }

    public String getMd5() {
        return md5;
    }

    @Override
    public String toString() {
        return "Image{" +
                "imageId=" + imageId +
                ", imageName='" + imageName + '\'' +
                ", size=" + size +
                ", uploadTime='" + uploadTime + '\'' +
                ", contentType='" + contentType + '\'' +
                ", path='" + path + '\'' +
                ", md5='" + md5 + '\'' +
                '}';
    }
}
