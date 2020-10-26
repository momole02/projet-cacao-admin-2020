package ci.miage.ayenon.db.query;

import java.util.ArrayList;
import java.util.List;

public class DeleteQuery {

    String table ;
    List<WhereClause> wheres;
    List<String> andOrs;

    public DeleteQuery(String table)
    {
        this.table = table ;
        wheres = new ArrayList<>();
    }


    public DeleteQuery where(WhereClause where)
    {
        if(wheres.size() > 0){
            andOrs.add("AND");
        }
        wheres.add(where);
        return this;
    }

    public DeleteQuery where(String field , String value)
    {
        return where(new WhereClause(field , value));
    }
    
    public DeleteQuery where(String field , String value, boolean withQuotes)
    {
        return where(new WhereClause(field , value, withQuotes));
    }

    public DeleteQuery where(String field, String operator , String value )
    {
        return where(new WhereClause(field, operator,  value));
    }

    public String toString()
    {
        String query = "DELETE FROM "+this.table;

        int i ;
        if(wheres.size() > 0)
            query += " WHERE ";
        for( i=0; i < wheres.size() ; ++i){
            if(0 != i){
                query += " "+ andOrs.get(i-1) + " ";
            }
            query += wheres.get(i).toString() + " ";
        }
        return query;
    }

}
