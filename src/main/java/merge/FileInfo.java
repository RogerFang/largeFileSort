package merge;

import java.io.BufferedReader;

/**
 * 
 * 文件信息
 * 
 * @author jia.hej
 *
 * @version $Id: FileInfo.java, v 0.1 2009-8-1 上午02:11:30 jia.hej Exp $
 */
public class FileInfo {

    /**
     * 文件号
     */
    private int            fileNum;

    /**
     * 当前行号
     */
    private int            lineNum = 0;

    /**
     * 当前值
     */
    private String         value;

    /**
     * BufferedReader引用
     */
    private BufferedReader reader;

    public boolean readNextValue() throws Exception {
        String value;
        if ((value = this.reader.readLine()) != null) {
            this.value = value;
            this.lineNum++;
            return true;
        } else {
            this.reader.close();
            return false;
        }
    }

    public int getFileNum() {
        return fileNum;
    }

    public void setFileNum(final int fileNum) {
        this.fileNum = fileNum;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(final int lineNum) {
        this.lineNum = lineNum;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(final BufferedReader reader) {
        this.reader = reader;
    }
}
