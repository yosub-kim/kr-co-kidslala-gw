package kr.co.kmac.pms.common.tag.tablegrid;

import java.util.List;

public class GridUtil{

	public GridUtil(){

	}
	
	public boolean isEqual (Cell handlingCell , Cell upperCell ){
	
	
		if ( !handlingCell.getCompare()) {return false;}

		Cell inheritedUpperCell =null ;
		boolean blEqual ;
		// 위셀과 비교해서 같은여부를 판단.
		if ( upperCell.getUpperCell() == null ) {
	
			if ( (handlingCell.getValue()).equals( upperCell.getValue() ) ) {
				inheritedUpperCell = upperCell;
				blEqual = true;
			} else {
				blEqual = false;
			}
			
		} else {
	
			Cell superUpperCell = upperCell.getUpperCell();

			if ( (handlingCell.getValue()).equals( superUpperCell.getValue() ) ) {
				inheritedUpperCell = superUpperCell;
				blEqual = true;
			} else {
				blEqual = false;
			}
		}

		if ( blEqual ) {
		
			Cell parentCell = handlingCell.getParentCell();
			if ( parentCell != null ) {
				if ( ! parentCell.getHidden() ) {
					blEqual = false;
				}
			}
		}
			
		if ( blEqual) { 
			handlingCell.setUpperCell(inheritedUpperCell); // 위 셀과 병합된다.
		}
		
		return blEqual;
		
	}


	public boolean isEqualHead (Cell handlingCell , Cell upperCell ){
	
		if ( !handlingCell.getCompare()) {return false;}
		if ( upperCell==null ) {return false;}

		Cell inheritedUpperCell =null ;
		boolean blEqual ;
		// 위셀과 비교해서 같은여부를 판단.
		if ( upperCell.getUpperCell() == null ) {
	
			if ( (handlingCell.getValue()).equals( upperCell.getValue() ) ) {
				inheritedUpperCell = upperCell;
				blEqual = true;
			} else {
				blEqual = false;
			}
			
		} else {
		
			Cell superUpperCell = upperCell.getUpperCell();

			if ( (handlingCell.getValue()).equals( superUpperCell.getValue() ) ) {
				inheritedUpperCell = superUpperCell;
				blEqual = true;
			} else {
				blEqual = false;
			}
		}

		// 위셀하고만 비고하고, end 
		if ( blEqual) { 
			handlingCell.setUpperCell(inheritedUpperCell); // 위 셀과 병합된다.
		}
		return blEqual;
	}

	public boolean isEqualHead2 (Cell handlingCell , Cell parentCell ){
	
		if ( !handlingCell.getCompare()) {return false;}
		if ( parentCell == null ) {return false;}

		Cell inheritedParnetCell =null ;
		boolean blEqual  ;
		
		if ( (handlingCell.getValue()).equals( parentCell.getValue() ) ) {
		
			if ( parentCell.getInheritedParentCell() == null ) {
				inheritedParnetCell = parentCell;
			} else {
				inheritedParnetCell = parentCell.getInheritedParentCell();
			}
			blEqual = true;			
		} else {
			blEqual = false;					
		}
		

		if ( blEqual) { 
			handlingCell.setInheritedParentCell(inheritedParnetCell); 
		}
		return blEqual;
	}
	
	
	// 소계 row를 만들어 반환.
	public Row makeSumRow(Row copyTarget,Row sumTypeRow ) {
		
		Row row = new Row();
		row.setAttribute(copyTarget.getAttribute());
		row.setOrgIndex("SUM-"+copyTarget.getOrgIndex());
		
		
		List<Cell> listCell =copyTarget.getListCell();
		if ( listCell != null && listCell.size()>0 ){
			for ( int i=0;i<listCell.size();i++){
				Cell cell = (Cell)listCell.get(i);
				Cell cellType = sumTypeRow.getCell(i);
				String copyType = cellType.getSumCellType();
				if ( "UPPER_COPY".equals(copyType) ) {
					row.addCell(new Cell(cell.getValue()).setAttribute(cell.getAttribute()));
				} else if ( "".equals(copyType) ) {
					row.addCell(new Cell("").setAttribute(cell.getAttribute()));
				} else if ( ! "".equals(copyType) ) {
					row.addCell(new Cell(copyType).setAttribute(cell.getAttribute()));
				}
				
			}
		}
		
		return row;
		
	}

	// 소계 row를 만들어 반환.
	public Row makeTotalSumRow(Row totalSumTypeRow ) {
		
		Row row = new Row();
		row.setAttribute(totalSumTypeRow.getAttribute());
		row.setOrgIndex("TOTAL");
		
		
		List<Cell> listCell =totalSumTypeRow.getListCell();
		if ( listCell != null && listCell.size()>0 ){
			for ( int i=0;i<listCell.size();i++){
				Cell cell = (Cell)listCell.get(i);
				Cell cellType = totalSumTypeRow.getCell(i);
				String copyType = cellType.getSumCellType();
				if ( "UPPER_COPY".equals(copyType) ) {
					row.addCell(new Cell(cell.getValue()).setAttribute(cell.getAttribute()));
				} else if ( "".equals(copyType) ) {
					row.addCell(new Cell("").setAttribute(cell.getAttribute()));
				} else if ( ! "".equals(copyType) ) {
					row.addCell(new Cell(copyType).setAttribute(cell.getAttribute()));
				}
				
			}
		}
		
		return row;
		
	}	
}

