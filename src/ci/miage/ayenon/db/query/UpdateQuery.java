package ci.miage.ayenon.db.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class UpdateQuery {

    String table ;
    List<String> andOrs;
    List<WhereClause> wheres;
    HashMap<String, String> sets;

    public UpdateQuery(String table)
    {
        this.table = table ;
        this.wheres = new ArrayList<>();
        this.sets = new HashMap<>();
        andOrs = new ArrayList<String>();
    }

    public UpdateQuery set(String field, String value )
    {
        return this.set(field, value , true);
    }
    

    public UpdateQuery set(String field, String value , boolean withQuotes)
    {
    	if(withQuotes)
    		this.sets.put(field, "'"+value+"'");
    	else 
    		this.sets.put(field, value);
        return this ;
    }


    public UpdateQuery where(WhereClause where)
    {
        if(wheres.size() > 0){
            andOrs.add("AND");
        }
        wheres.add(where);
        return this;
    }

    public UpdateQuery where(String field , String value)
    {
        return where(new WhereClause(field , value));
    }
    

    public UpdateQuery where(String field , String value, boolean withQuotes)
    {
        return where(new WhereClause(field , value, withQuotes));
    }

    public UpdateQuery where(String field, String operator , String value )
    {
        return where(new WhereClause(field, operator,  value));
    }

    public String toString()
    {
        String query = "UPDATE " + this.table + " SET ";
        int i=0 ;
        for(String field  : sets.keySet()){
            String value = sets.get(field);
            query += field+" = "+value;
            if(i!=sets.size() - 1)
                query += ",";
            ++i;
        }

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
