package utils;

import java.io.File;
import java.io.FilenameFilter;

public class HFMEDAccept implements FilenameFilter {

    private String extendName;

    public void setExtendName(String extendName) {
        this.extendName = "." + extendName;
    }

    public boolean accept(File dir, String name) {//重写接口的方法
        return name.endsWith(extendName);//是否是以某个格式结尾。是否是某个类型文件
    }
}

