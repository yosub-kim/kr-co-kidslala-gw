package kr.co.kmac.pms.common.tag.tablegrid;

import java.util.List;

public class GridUtil{

	public GridUtil(){

	}
	
	public boolean isEqual (Cell handlingCell , Cell upperCell ){
	
	
		if ( !handlingCell.getCompare()) {return false;}

		Cell inheritedUpperCell =null ;
		boolean blEqual ;
		// ������ ���ؼ� �������θ� �Ǵ�.
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
			handlingCell.setUpperCell(inheritedUpperCell); // �� ���� ���յȴ�.
		}
		
		return blEqual;
		
	}


	public boolean isEqualHead (Cell handlingCell , Cell upperCell ){
	
		if ( !handlingCell.getCompare()) {return false;}
		if ( upperCell==null ) {return false;}

		Cell inheritedUpperCell =null ;
		boolean blEqual ;
		// ������ ���ؼ� �������θ� �Ǵ�.
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

		// �����ϰ� ����ϰ�, end 
		if ( blEqual) { 
			handlingCell.setUpperCell(inheritedUpperCell); // �� ���� ���յȴ�.
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
	
	
	// �Ұ� row�� ����� ��ȯ.
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

	// �Ұ� row�� ����� ��ȯ.
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

