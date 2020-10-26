package ci.miage.ayenon.db.query;

public class Q {

    public static String quo(String s)
    {
        return "'"+s+"'";
    }


    public static String bs(String s)
    {
        return "`"+s+"`";
    }


    public static String quo2(String s)
    {
        return "\""+s+"\"";
    }

}
