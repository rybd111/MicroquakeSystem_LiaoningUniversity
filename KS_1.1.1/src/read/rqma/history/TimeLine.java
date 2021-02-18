package read.rqma.history;

import java.io.File;

/**
 * @Description:
 * @Auther: RQMA
 * @Date: 4/26/2019 1:19 PM
 */
public class TimeLine {
    int id;
    String filename;
    String filepath;//文件夹
    int position;//记录位置
    File file;//记录文件。

    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }

    public String getFilepath() {
        return filepath;
    }
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public File getFile() {
    	return file;
    }
    public void setFile(File file) {
    	this.file = file;
    }
}
