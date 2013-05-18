package zhwb.product.largesort;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import zhwb.product.largesort.bean.Person;

public class Test
{
    static Random rn = new Random();

    public static void main(final String[] args)
        throws IOException
    {
        /*test 500M data, in 50M Memory*/

        //        generateData("C:\\LargeFileSort\\large_data.csv", 100000000);

        new LargeSortService().sortLargeFile("C:\\LargeFileSort\\large_data.csv", 100000000 / 10, true);


        //        new LargeSortService().sortLargeFile(null, 0);
    }

    public static void generateData(final String filePath, final int memSize)
        throws IOException
    {
        File file = new File(filePath);
        if (!file.exists())
        {
            file.getParentFile().mkdirs();
        }
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        String header = "#ID,NAME,AGE,GENDER,COUNTRY,PROVINCE,CITY,STREET";
        out.write(header);
        while (file.length() < memSize)
        {
            out.write(Person.valueOf(generateOneline()).toString());
            out.newLine();
            out.flush();
        }
        out.close();
        out = null;
        System.out.println("file size:" + (double)(file.length() / 1024 / 1024) + "M");
    }

    private static String generateOneline()
    {
        String s = "1111,jack," + rn.nextInt(150) + ",Male,US,NJ,Jersey City,Watchington Blv";
        return s;
    }
}
