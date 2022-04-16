package kr.co.kmac.pms.common.tag.tablegrid;

import java.util.HashMap;

public class Cell {

	public String value ;
	int rowspan;
	int Colspan;
	public String attribute;
	boolean isHidden ;
	boolean isCompare;
	Cell upperCell; // »ó¼Ó ¼¿.
	Cell parentCell; // ÁÂÃø¼¿
	Cell inheritedParentCell; // ÁÂÃø¼¿·Î ÀÎÇØ¼­, »ó¼Ó¹Þ´Â ¼¿
	public HashMap<String,String> hmAttr;

	public Cell(){
		rowspan = 1;
		Colspan = 1;
		isHidden = false;
		isCompare = true ;
	}

	public Cell( String s ){
	
		value = checkNull(s);
		rowspan = 1;
		Colspan = 1;
		isHidden = false;
		isCompare = true ;
	}

	public Cell setNotCompare(){
		this.isCompare = false;
		return this;
	
	}
	public boolean getCompare(){
		return this.isCompare;
	}	
	public Cell setAttribute( String s){
		attribute = s;
		return this;
	}
	public Cell addAttribute( String s){
		attribute = attribute + s;
		return this;
	}	
	public String getAttribute(){
		return this.attribute ;
	}
		
	public String getValue(){
		String tempValue = "";
		if ( "".equals(this.value) ) { tempValue="&nbsp;"; }
		else { tempValue = this.value; }
		return tempValue;
	}
	public void setValue(String s){
		this.value = s;
	}
	public void addRowSpanPlus(){
		if ( upperCell == null ) {
			this.rowspan = this.rowspan + 1;
		} else {
			upperCell.addRowSpanPlus();
		}
	}
	public int getRowSpan(){
		return this.rowspan;
	}

	public void addColSpanPlus(){
		this.Colspan = this.Colspan + 1;
	}
	public int getColSpan(){
		return this.Colspan;
	}

	public void setHidden(){
		this.isHidden = true;
	}
	public boolean getHidden(){
		return this.isHidden;
	}	
	
	public Cell getUpperCell(){
		return upperCell;
	}
	public void setUpperCell(Cell s){
		upperCell = s;
	}	
	public Cell getParentCell(){
		return parentCell;
	}
	public void setParentCell(Cell s){
		parentCell = s;
	}	
	public Cell getInheritedParentCell(){
		return inheritedParentCell;
	}
	public void setInheritedParentCell(Cell s){
		inheritedParentCell = s;
	}

	/// SUM ROW CELL TYPE
	String sumCellType="";
	public Cell setSumCellType(String s){
		this.sumCellType = s; // [ UPPER_COPY | ±×³É  VALUE  ]
		return this;
	}
	public String getSumCellType(){
		return this.sumCellType;
	}

	public String checkNull(String s) {
       return (s==null || "null".equals(s)) ? "" : s;
	}

	
	// smrs
	public String xKey;
	public String yKey;

	public Cell setXKey(String s){
		this.xKey=s;
		return this;
	}
	public Cell setYKey(String s){
		this.yKey=s;
		return this;
	}
	public String getXKey(){
		return this.xKey;
	}
	public String getYKey(){
		return this.yKey;
	}
	
	public Cell setAttr(String key,String value){
		if (hmAttr==null){
			hmAttr = new HashMap<String,String>();
		}
		hmAttr.put(key, value);
		return this;
	}
	public String getAttr(String key){
		if (hmAttr==null){
			return "";
		} else {
			return (String)hmAttr.get(key);
		}
	}
}