package zhwb.product.largesort;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import zhwb.product.largesort.util.StoreType;
import zhwb.product.largesort.util.StoreType.StoreUnit;

public class Test
{
    public static void main(final String[] args)
        throws IOException
    {
        /*test 500M data, in 50M Memory*/

        generateData("C:\\LargeFileSort\\large_data.csv", StoreType.getInstance(StoreUnit.MB, 100).getRealSize());

        new LargeSortService().sortLargeFile("C:\\LargeFileSort\\large_data.csv", StoreType.getInstance(StoreUnit.MB, 10)
                .getRealSize(), false);


        //        new LargeSortService().sortLargeFile(null, 0);
    }

    public static void generateData(final String filePath, final long memSize)
        throws IOException
    {
        long start = System.currentTimeMillis();
        File file = new File(filePath);
        if (!file.exists())
        {
            file.getParentFile().mkdirs();
        }
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        String header = "#ID,NAME,AGE,GENDER,COUNTRY,PROVINCE,CITY,STREET";
        out.write(header);
        Random random = new Random();

        for (long i = 0; i < memSize; i = file.length())
        {
            for (int j = 0; j < 20; j++)
            {
                out.write("1111,jack," + random.nextInt(150) + ",Male,US,NJ,Jersey City,Watchington Blv");
                out.newLine();
            }

        }
        out.flush();
        out.close();
        out = null;
        System.out.println("file size:" + (double)(file.length() / 1024 / 1024) + "M");
        System.out.println("generate time:" + (System.currentTimeMillis() - start));
    }

}
