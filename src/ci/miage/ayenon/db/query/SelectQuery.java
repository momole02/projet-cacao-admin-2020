package ci.miage.ayenon.db.query;

import java.util.ArrayList;
import java.util.List;

public class SelectQuery {

    String table ;
    int limit;
    List<String> fields;
    List<String> andOrs;
    List<WhereClause> wheres;
    String joinClause ;

    public SelectQuery(String table)
    {
        this.fields = new ArrayList<>();
        this.andOrs = new ArrayList<>();
        this.wheres = new ArrayList<>();
        this.limit = 0 ;
        this.table = table ;
        this.joinClause = "";
    }


    public SelectQuery(String table, int limit)
    {
        this.fields = new ArrayList<>();
        this.andOrs = new ArrayList<>();
        this.wheres = new ArrayList<>();
        this.limit = limit;
        this.table = table ;
        this.joinClause = "";
    }

    public SelectQuery addField(String string)
    {
        fields.add(string);
        return this ;
    }

    public SelectQuery where(WhereClause where)
    {
        if(wheres.size() > 0){
            andOrs.add("AND");
        }
        wheres.add(where);
        return this;
    }

    public SelectQuery where(String field , String value)
    {
        return where(new WhereClause(field , value));
    }
    

    public SelectQuery where(String field , String value, boolean withQuotes)
    {
        return where(new WhereClause(field , value , withQuotes));
    }

    public SelectQuery where(String field, String operator , String value )
    {
        return where(new WhereClause(field, operator,  value));
    }
    
    

    public SelectQuery orWhere(WhereClause where)
    {

        if(wheres.size() > 0){
            andOrs.add("OR");
        }
        wheres.add(where);
        return this;
    }

    public SelectQuery join(String table , String field1, String op , String field2)
    {
        joinClause = "INNER JOIN  "+table+" ON  "+field1+" "+op+" "+field2+" ";
        return this;
    }

    public String toString()
    {
        String query = "SELECT ";
        if(fields.isEmpty()) query += "*";
        else{
            int i=0;
            for(String f : fields){
                query += f;
                if(i != fields.size()-1)
                    query += ",";
                ++i;
            }
        }
        query += " FROM "+table+" "+joinClause+" ";
        if(wheres.size() > 0)
            query += "WHERE ";
        for(int i=0; i < wheres.size() ; ++i){
            if(0 != i){
                query += " "+ andOrs.get(i-1) + " ";
            }
            query += wheres.get(i).toString() + " ";
        }
        if(limit > 0){
            query +=" LIMIT "+limit;
        }
        return query;
    }
}
