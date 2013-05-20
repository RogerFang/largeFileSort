package merge;

import java.util.Comparator;

/**
 * 
 * 文件比较器
 * 
 * @author jia.hej
 *
 * @version $Id: FileInfoComparator.java, v 0.1 2009-8-7 下午01:42:05 jia.hej Exp $
 */
public class FileInfoComparator implements Comparator<FileInfo> {

    @Override
    public int compare(final FileInfo o1, final FileInfo o2) {
        if (Integer.parseInt(o1.getValue()) != Integer.parseInt(o2.getValue())) {
            return Integer.parseInt(o1.getValue()) - Integer.parseInt(o2.getValue());
        }
        //如果存在重复值则使用文件号比较
        else {
            return o1.getFileNum() - o2.getFileNum();
        }
    }

}
