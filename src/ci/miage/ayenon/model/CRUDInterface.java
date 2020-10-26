package ci.miage.ayenon.model;

import java.util.List;

public interface CRUDInterface {

	public void insert() ; 
	public void update() ; 
	public List<? extends CRUDInterface> selectAll(int limit);
	public void delete(); 
	public List<? extends CRUDInterface> search(String filter);
	public void retrieve();
}
