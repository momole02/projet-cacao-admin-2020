package ci.miage.ayenon.db.query;

import java.util.HashMap;
import java.util.List;

public class InsertQuery {

    String table ;
    HashMap<String,String> fields;

    public InsertQuery(String table)
    {
        this.table = table;
        this.fields = new HashMap<>();
    }

    public InsertQuery field(String field, String value , boolean withQuotes)
    {
    	if(withQuotes) {
            this.fields.put(field, "'"+value+"'");
    	}else {
    		this.fields.put(field, value);
    	}
        return this;
    }
    

    public InsertQuery field(String field, String value )
    {
    	return field(field, value , true);
    }

    public String toString()
    {
        String query = "INSERT INTO "+table;
        int i = 0;
        query += "(";
        for(String field : fields.keySet()){
            query += field;
            if(i!=fields.size()-1)
                query += ",";
            ++i;
        }
        query += ") VALUES (";
        i=0;
        for(String field : fields.keySet()){
            query += fields.get(field);
            if(i!=fields.size()-1)
                query +=  ",";
            ++i;
        }
        query += ")";
        return query ;
    }
}
