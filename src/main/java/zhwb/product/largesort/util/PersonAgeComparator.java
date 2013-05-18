package zhwb.product.largesort.util;

import java.util.Comparator;

import zhwb.product.largesort.bean.Person;

/**
 * The Class PersonAgeComparator.
 */
public class PersonAgeComparator implements Comparator<Person>
{
    /** 
     * if any of the compare element is null, return is Header.
     * {@inheritDoc}
     *  @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(final Person o1, final Person o2)
    {
        if (o1 == null || o2 == null)
        {
            return 0;
        }
        return o1.getAge() - o2.getAge();
    }

}
