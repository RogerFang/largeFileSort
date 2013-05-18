package zhwb.product.largesort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zhwb.product.largesort.bean.Person;
import zhwb.product.largesort.util.MemoryMonitor;
import zhwb.product.largesort.util.PersonAgeComparator;

/**
 * The Class LargeSortService.
 */
public class LargeSortService
{

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(LargeSortService.class);

    /** The split folder name. */
    private static final String SPILT_FOLDER = "splitFiles";

    /** The header. */
    private String header;

    /** The Constant AGE_COMPARATOR. */
    private static final Comparator<Person> AGE_COMPARATOR = new PersonAgeComparator();

    /**
     * Sort large file, do a copy of the origin file.
     *
     * @param from the from
     * @param memoryLimit the memory limit
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void sortLargeFile(final String from, final long memoryLimit, final boolean makeCopy)
        throws IOException
    {
        LOG.info("Sort Large File start, fromFile {}, memoryLimit {}, make a copy {}", new Object[] {from, memoryLimit, makeCopy});
        long start = System.currentTimeMillis();
        if (from == null || from.trim().length() == 0 || memoryLimit <= 0)
        {
            LOG.error("Sort Large File stop, input parameter can not be null or empty.");
            return;
        }
        /*check and obtain the file*/
        File file = new File(from);
        if (file.exists() && file.isFile())
        {
            /*split files into parts*/
            splitFile(file, memoryLimit);
            /*sort each files in split folder*/
            sortSplitFile(file.getParent());
            /*merge sorted files back and copy to new location*/
            mergeFiles(from, makeCopy);

            LOG.info("Sort Large File end, whole time is {}ms.", System.currentTimeMillis() - start);
        }
        else
        {
            LOG.error("Sort stop, file not exist or not a normal file, please check your input parameter.");
        }
    }

    /**
     * Merge files.
     *
     * @param from the from
     * @param makeCopy 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void mergeFiles(final String from, final boolean makeCopy)
        throws IOException
    {
        LOG.info("Merge File start, fromFile {}", from);
        long start = System.currentTimeMillis();
        File originFile = new File(from);
        File outDir = new File(originFile.getParentFile(), SPILT_FOLDER);

        if (outDir.exists())
        {
            String extension = originFile.getName().split("\\.")[1];
            File result = new File(originFile.getParentFile(), new StringBuilder("resultFile.").append(extension).toString());
            if (!result.exists())
            {
                result.createNewFile();
            }
            File temp = new File(originFile.getParentFile(), new StringBuilder("tempFile.").append(extension).toString());
            extension = null;
            if (!temp.exists())
            {
                temp.createNewFile();
            }

            boolean flag = false;
            BufferedReader smallIn = null;
            BufferedReader largeIn = null;
            String smallLine = null;
            String largeLine = null;
            BufferedWriter out = null;

            /*merger sort begin*/
            File[] files = outDir.listFiles();
            for (File smallFile : files)
            {
                smallIn = new BufferedReader(new FileReader(smallFile));

                if (flag)
                {
                    largeIn = new BufferedReader(new FileReader(temp));
                    out = new BufferedWriter(new FileWriter(result));
                    flag = false;
                }
                else
                {
                    largeIn = new BufferedReader(new FileReader(result));
                    out = new BufferedWriter(new FileWriter(temp));
                    flag = true;
                }
                if (header != null)
                {
                    out.write(header);
                    header = null;
                    out.newLine();
                }

                smallLine = smallIn.readLine();
                largeLine = largeIn.readLine();
                while (smallLine != null && largeLine != null)
                {
                    int rt = AGE_COMPARATOR.compare(Person.valueOf(smallLine), Person.valueOf(largeLine));
                    if (rt < 0)
                    {
                        out.write(smallLine);
                        smallLine = smallIn.readLine();
                    }
                    else
                    {
                        out.write(largeLine);
                        largeLine = largeIn.readLine();
                    }
                    out.newLine();
                }

                while (smallLine != null)
                {
                    out.write(smallLine);
                    out.newLine();
                    smallLine = smallIn.readLine();
                }

                while (largeLine != null)
                {
                    out.write(largeLine);
                    out.newLine();
                    largeLine = largeIn.readLine();
                }

                MemoryMonitor.logMemoryInfo(LOG);

                out.flush();
                out.close();
                out = null;
                smallIn.close();
                smallIn = null;
                largeIn.close();
                largeIn = null;
                smallFile.delete();
                smallFile = null;
            }

            files = null;
            outDir.delete();
            outDir = null;

            File copyFile = null;
            if (makeCopy)
            {
                copyFile = new File(originFile.getParentFile(), new StringBuilder("sortedFile.").append(extension)
                        .toString());
            }
            else
            {
                /*delete the origin file is all success*/
                originFile.delete();
                copyFile = originFile;
            }
            if (flag)
            {
                temp.renameTo(copyFile);
            }
            else
            {
                result.renameTo(copyFile);
            }
            originFile = null;
            result.delete();
            result = null;
            temp.delete();
            temp = null;

        }
        LOG.info("Merge File end, whole time is {}ms.", System.currentTimeMillis() - start);
    }

    /**
     * Split file.
     *
     * @param file the file
     * @param memoryLimit the memory limit
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void splitFile(final File file, long memoryLimit)
        throws IOException
    {
        LOG.info("Split start...");
        long start = System.currentTimeMillis();

        /*90% limit to avoid memory leak*/
        memoryLimit = (long)(memoryLimit * 0.95);

        String extension = file.getName().split("\\.")[1];
        File outDir = new File(file.getParentFile(), SPILT_FOLDER);
        if (!outDir.exists())
        {
            outDir.mkdir();
        }

        BufferedReader in = new BufferedReader(new FileReader(file));
        String line = null;
        int n = 0;
        String fileName = new StringBuilder(String.valueOf(n)).append(".").append(extension).toString();
        extension = null;

        File f = new File(outDir, fileName);
        BufferedWriter out = new BufferedWriter(new FileWriter(f));
        /*store header here*/
        header = in.readLine();
        while ((line = in.readLine()) != null)
        {
            if (f.length() >= memoryLimit)
            {
                fileName = fileName.replaceFirst("^\\d+", String.valueOf(++n));
                out.flush();
                out.close();
                out = null;
                f = new File(outDir, fileName);
                out = new BufferedWriter(new FileWriter(f));
            }
            out.write(line);
            out.newLine();
            out.flush();
        }

        outDir = null;
        fileName = null;
        if (out != null)
        {
            out.flush();
            out.close();
            out = null;
        }
        in.close();
        in = null;
        LOG.info("Split end, split time is {}ms.", System.currentTimeMillis() - start);
    }

    /**
     * Sort split file.
     *
     * @param filePath the file path
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void sortSplitFile(final String filePath)
        throws IOException
    {
        LOG.info("Sort split start...");
        long start = System.currentTimeMillis();

        /*create a temp folder under the file path*/
        File file = new File(filePath, SPILT_FOLDER);
        if (file.exists())
        {
            BufferedReader in = null;
            BufferedWriter out = null;
            String line = null;
            List<Person> dataList = new LinkedList<Person>();
            File[] files = file.listFiles();
            file = null;

            int size = 0;
            int i = 0;
            for (File smallFile : files)
            {
                in = new BufferedReader(new FileReader(smallFile));
                dataList.clear();

                while ((line = in.readLine()) != null)
                {
                    dataList.add(Person.valueOf(line));
                }

                in.close();
                in = null;

                Collections.sort(dataList, AGE_COMPARATOR);

                out = new BufferedWriter(new FileWriter(smallFile));
                smallFile = null;

                size = dataList.size();
                while (i < size)
                {
                    if (dataList.get(i) != null)
                    {
                        out.write(dataList.get(i).toString());
                        out.newLine();
                    }
                    dataList.remove(i);
                    size = dataList.size();

                }
                out.flush();
                out.close();
                out = null;
            }
            dataList.clear();
            dataList = null;
            files = null;
        }
        else
        {
            LOG.error("sort splits stop, file not exist or not a normal file, please check your application.");
        }
        LOG.info("Sort splits end, split time is {}ms.", System.currentTimeMillis() - start);
    }
}
