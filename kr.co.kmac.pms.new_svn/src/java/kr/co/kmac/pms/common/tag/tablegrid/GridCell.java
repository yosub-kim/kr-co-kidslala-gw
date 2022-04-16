package kr.co.kmac.pms.common.tag.tablegrid;



public class GridCell {

	public String cellData;
	public String xKey;
	public String yKey;
	public GridCell(String s){
		cellData = s;
	}
	
	public String getCellData(){
		return this.cellData;
	}
	public GridCell setCellData(String s){
		cellData = s;
		return this;
	}
	public GridCell setXKey(String s){
		this.xKey=s;
		return this;
	}
	public GridCell setYKey(String s){
		this.yKey=s;
		return this;
	}
	public String getXKey(){
		return this.xKey;
	}
	public String getYKey(){
		return this.yKey;
	}
}
