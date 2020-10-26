package ci.miage.ayenon.db.query;


public class WhereClause {


    String field ;
    String operator ;
    String value ;
    boolean withQuotes; 

    public WhereClause(String field , String value)
    {
        this.field = field;
        this.operator = "=";
        this.value = value;
        this.withQuotes = true;
    }

    public WhereClause(String field , String value, boolean withQuotes)
    {
        this.field = field;
        this.operator = "=";
        this.value = value;
        this.withQuotes = withQuotes;
    }

    
    public WhereClause(String field, String operator , String value)
    {
        this.field = field;
        this.operator = operator;
        this.value = value;
        this.withQuotes = true; 
    }
    
    

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString()
    {
    	if(withQuotes) {
            return this.field + this.operator + "'"+this.value+"'";	
    	}else {
    		return this.field + this.operator + this.value;	
    	}
    }
    
}
