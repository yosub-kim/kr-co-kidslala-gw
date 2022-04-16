package kr.co.kmac.pms.common.tag.tablegrid;

import java.util.ArrayList;
import java.util.List;

public class Row {

	public String attribute;
	public String orgIndex;
	List<Cell> listCell;

	public Row() {
		listCell = new ArrayList<Cell>();

	}

	public void addCell(Cell cell) {
		listCell.add(cell);
	}

	public Cell getCell(int i) {
		return (Cell) listCell.get(i);
	}

	public Row setAttribute(String s) {
		attribute = s;
		return this;

	}

	public String getAttribute() {
		return this.attribute;

	}

	public int getCellCount() {
		return listCell.size();
	}

	public List<Cell> getListCell() {
		return listCell;
	}

	public String getOrgIndex() {
		return orgIndex;
	}

	public Row setOrgIndex(String orgIndex) {
		this.orgIndex = orgIndex;
		return this;
	}

}
