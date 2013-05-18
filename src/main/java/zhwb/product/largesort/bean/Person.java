package zhwb.product.largesort.bean;

/**
 * The Class Person.
 */
public class Person
{

    /** The id. */
    private int id;

    /** The name. */
    private String name;

    /** The age. */
    private int age;

    /** The gender. */
    private String gender;

    /** The country. */
    private String country;

    /** The province. */
    private String province;

    /** The city. */
    private String city;

    /** The street. */
    private String street;

    /**
     * Get the property id.
     *
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Set the property id.
     *
     * @param id the id to set
     */
    public void setId(final int id)
    {
        this.id = id;
    }

    /**
     * Get the property name.
     *
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Set the property name.
     *
     * @param name the name to set
     */
    public void setName(final String name)
    {
        this.name = name;
    }

    /**
     * Get the property age.
     *
     * @return the age
     */
    public int getAge()
    {
        return age;
    }

    /**
     * Set the property age.
     *
     * @param age the age to set
     */
    public void setAge(final int age)
    {
        this.age = age;
    }

    /**
     * Get the property gender.
     *
     * @return the gender
     */
    public String getGender()
    {
        return gender;
    }

    /**
     * Set the property gender.
     *
     * @param gender the gender to set
     */
    public void setGender(final String gender)
    {
        this.gender = gender;
    }

    /**
     * Get the property country.
     *
     * @return the country
     */
    public String getCountry()
    {
        return country;
    }

    /**
     * Set the property country.
     *
     * @param country the country to set
     */
    public void setCountry(final String country)
    {
        this.country = country;
    }

    /**
     * Get the property province.
     *
     * @return the province
     */
    public String getProvince()
    {
        return province;
    }

    /**
     * Set the property province.
     *
     * @param province the province to set
     */
    public void setProvince(final String province)
    {
        this.province = province;
    }

    /**
     * Get the property city.
     *
     * @return the city
     */
    public String getCity()
    {
        return city;
    }

    /**
     * Set the property city.
     *
     * @param city the city to set
     */
    public void setCity(final String city)
    {
        this.city = city;
    }

    /**
     * Get the property street.
     *
     * @return the street
     */
    public String getStreet()
    {
        return street;
    }

    /**
     * Set the property street.
     *
     * @param street the street to set
     */
    public void setStreet(final String street)
    {
        this.street = street;
    }

    /** {@inheritDoc}
     *  @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s", id, name,
                age, gender, country, province, city, street);
    }

    /**
     * Value of.
     *
     * @param str the str
     * @return the person
     */
    public static Person valueOf(final String str)
    {
        try
        {
            return new Person(str.split(","));
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    /**
     * Instantiates a new person.
     *
     * @param split the split
     */
    public Person(final String[] split)
    {
        super();
        this.id = Integer.valueOf(split[0]);
        this.name = split[1];
        this.age = Integer.valueOf(split[2]);
        this.gender = split[3];
        this.country = split[4];
        this.province = split[5];
        this.city = split[6];
        this.street = split[7];
    }
}
