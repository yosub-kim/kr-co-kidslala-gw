package kr.co.kmac.pms.common.tag.tablegrid;

import java.util.ArrayList;
import java.util.List;

public class TableGrid {

	List<Row> listRow;
	List<Row> listHeadRow;
	GridUtil gridUtil;
	String attribute;
	String totalHtml;
	boolean divUse;
	String divAttribute;
	boolean isAnnex;
	boolean isHeadAnnex;
	String userLastTrHtml;
	String tableId;

	public TableGrid() {

		listRow = new ArrayList<Row>();
		listHeadRow = new ArrayList<Row>();
		gridUtil = new GridUtil();
		divUse = false;
		isAnnex = false;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getTableId() {
		return this.tableId;
	}

	public void addRow(Row row) {
		listRow.add(row);
	}

	public void addRow(int i, Row row) {
		listRow.add(i, row);
	}

	public Row getRow(int i) {
		return (Row) listRow.get(i);
	}

	public void setHeadAnnex(boolean blHeadAnnex) {
		this.isHeadAnnex = blHeadAnnex;
	}

	public boolean getHeadAnnex() {
		return this.isHeadAnnex;
	}

	public void setAnnex(boolean blAnnex) {
		this.isAnnex = blAnnex;
	}

	public boolean getAnnex() {
		return this.isAnnex;
	}

	public void addHeadRow(Row row) {
		listHeadRow.add(row);
	}

	public void setDiv(String s) {
		this.divUse = true;
		this.divAttribute = s;
	}

	public boolean getDivUse() {
		return this.divUse;
	}

	public String getDivAttribute() {
		return this.divAttribute;
	}

	public void setAttribute(String s) {
		this.attribute = s;
	}

	public String getAttribute() {
		return this.attribute;
	}

	public void setTotalHtml(String s) {
		this.totalHtml = s;
	}

	public String getTotalHtml() {
		return this.totalHtml;
	}

	// / summary 관련 함수---------------------------
	Row sumTypeRow; // 소계를 만드는 Row의 Cell별 생성 타입
	Row totalSumTypeRow; // 합계를 만드는 Row의 Cell별 생성 타입

	public void setSumTypeRow(Row row) {
		this.sumTypeRow = row;
	}

	public Row getSumTypeRow() {
		return sumTypeRow;
	}

	public void setTotalSumTypeRow(Row row) {
		this.totalSumTypeRow = row;
	}

	public Row getTotalSumTypeRow() {
		return totalSumTypeRow;
	}

	// --------------------------------------------

	// 데이터가 없음을 알리는 표시----------------------------
	boolean isUseNoData = false;

	public void setUseNoData() {
		isUseNoData = true;
	}

	String noData = "<table width='100%' border='0' cellpadding='2' cellspacing='1' bgcolor='#CCCCCC' ><tr><td align=center class='td_data_center'>데이터가 없습니다.</td></tr></table>";

	public String getNoData() {
		return noData;
	}

	public void setNoData(String noData) {
		this.noData = noData;
	}

	// --------------------------------------------------

	// 사용자가 tr html을 만들어 마지막줄에 삽입.
	public void setUserLastTrHtml(String html) {
		this.userLastTrHtml = html;
	}

	public String getUserLastTrHtml() {
		return this.userLastTrHtml;
	}

	public void tableCheck() {

		// ////////////////////////////// head 처리 ////////////////////////////////
		if (listHeadRow != null && listHeadRow.size() > 0) {
			Row headRow = (Row) listHeadRow.get(0);
			int tdHeadCount = headRow.getCellCount();
			int trHeadCount = listHeadRow.size();

			CellData[][] cellHeadData = new CellData[trHeadCount][tdHeadCount];

			for (int i = 0; i < trHeadCount; i++) {

				for (int j = 0; j < tdHeadCount; j++) {
					Row tempRow = (Row) listHeadRow.get(i);
					List<Cell> listCell = tempRow.getListCell();
					cellHeadData[i][j] = new CellData((Cell) listCell.get(j));

					if (j == 0) {
						cellHeadData[i][j].getCell().setParentCell(null);
					} else {
						cellHeadData[i][j].getCell().setParentCell(cellHeadData[i][j - 1].getCell());
					}
				}
			}
			Cell handlingHeadCell = null;
			Cell upperHeadCell = null;
			Cell parentHeadCell = null;

			if (this.getHeadAnnex()) {

				for (int i = 0; i < trHeadCount; i++) {

					for (int j = 0; j < tdHeadCount; j++) {

						handlingHeadCell = (Cell) cellHeadData[i][j].getCell();
						upperHeadCell = (i == 0) ? null : ((Cell) cellHeadData[i - 1][j].getCell());
						parentHeadCell = (Cell) handlingHeadCell.getParentCell();

						if (gridUtil.isEqualHead(handlingHeadCell, upperHeadCell)) {
							upperHeadCell.addRowSpanPlus();
							handlingHeadCell.setHidden();
						}
						if (gridUtil.isEqualHead2(handlingHeadCell, parentHeadCell)) {
							Cell inheritedParentCell = handlingHeadCell.getInheritedParentCell();
							inheritedParentCell.addColSpanPlus();
							handlingHeadCell.setHidden();
						}
					}

				} // for
			}
		}
		// /////////////////////////////////////////////////////////////////////////

		// ////////////////////////////// griddata 처리 ////////////////////////////////
		if (listRow == null || listRow.size() == 0) {
			if (isUseNoData) {
				this.setTotalHtml(getNoData());
			} else {
				this.setTotalHtml("");
			}
			return;
		}

		Row row = (Row) listRow.get(0);

		int tdCount = row.getCellCount();
		int trCount = listRow.size();

		CellData[][] cellData = new CellData[trCount][tdCount];

		for (int i = 0; i < trCount; i++) {
			for (int j = 0; j < tdCount; j++) {
				Row tempRow = (Row) listRow.get(i);
				List<Cell> listCell = tempRow.getListCell();
				if (listCell.size() <= j)
					break;
				cellData[i][j] = new CellData((Cell) listCell.get(j));

				if (j == 0) {
					cellData[i][j].getCell().setParentCell(null);
				} else {
					cellData[i][j].getCell().setParentCell(cellData[i][j - 1].getCell());
				}

			}
		}

		Cell handlingCell = null;
		Cell upperCell = null;

		if (this.getAnnex()) {

			for (int i = 0; i < trCount; i++) {
				for (int j = 0; j < tdCount; j++) {

					if (i > 0) {
						if (cellData[i][j] == null || cellData[i - 1][j] == null)
							break;
						handlingCell = (Cell) cellData[i][j].getCell();
						upperCell = (Cell) cellData[i - 1][j].getCell();

						if (gridUtil.isEqual(handlingCell, upperCell)) {
							upperCell.addRowSpanPlus();
							handlingCell.setHidden();
						}

					}

				}

			} // for
		}

		// makeHtml
		Cell printCell = null;
		StringBuffer strTable = new StringBuffer();

		if (this.getDivUse()) {
			strTable.append("<DIV " + this.getDivAttribute() + ">");
		}

		strTable.append("<TABLE " + this.getAttribute() + ">");

		// ////////////////////// Header Row 만들기 start ////////////////////////

		List<Row> listHeadRow = this.listHeadRow;

		strTable.append("<THEAD>");

		for (int h = 0; h < listHeadRow.size(); h++) {

			// strTable.append("<TR style='position:relative;top:expression(this.offsetParent.scrollTop);background:#ADADAD;' align='left'>");
			Row printHeadRow = (Row) this.listHeadRow.get(h);
			strTable.append("<TR " + printHeadRow.getAttribute() + ">");

			List<Cell> listHeadCell = printHeadRow.getListCell();

			for (int h2 = 0; h2 < listHeadCell.size(); h2++) {
				Cell printHeadCell = (Cell) listHeadCell.get(h2);

				if (!printHeadCell.getHidden()) {
					strTable.append("<TD rowspan='" + printHeadCell.getRowSpan() + "' Colspan='" + printHeadCell.getColSpan() + "' "
							+ printHeadCell.getAttribute() + ">");
					strTable.append(printHeadCell.getValue() + "</TD>");
				}
			}
			strTable.append("</TR>");
		}

		strTable.append("</THEAD>");
		// ////////////////////// Header Row 만들기 end ////////////////////////

		// ////////////////////// Grid Data Row 만들기 end ////////////////////////
		strTable.append("<TBODY  style='width:100%;max-height:100px;overflow:auto;'>");

		for (int i = 0; i < trCount; i++) {

			Row printRow = (Row) listRow.get(i);
			strTable.append("<TR " + printRow.getAttribute() + " >");

			for (int j = 0; j < tdCount; j++) {
				if (cellData[i][j] == null)
					break;
				printCell = (Cell) cellData[i][j].getCell();

				if (!printCell.getHidden()) {
					strTable
							.append("<TD rowspan='" + printCell.getRowSpan() + "' " + printCell.getAttribute() + ">" + printCell.getValue() + "</TD>");
				}

			}
			strTable.append("</TR>");
		}

		// 사용자 임의의 TR을 마지막줄에 삽입.
		strTable.append((getUserLastTrHtml() == null) ? "" : getUserLastTrHtml());

		strTable.append("</TBODY>");
		strTable.append("</TABLE>");

		if (this.getDivUse()) {
			strTable.append("</DIV>");
		}

		this.setTotalHtml(strTable.toString());

	}

	// //////////// 부분합, 전체 합 등 sum 처리를 위한 추가 작업 //////////////

	public class SumInfo {

		public SumInfo(int i, int j) {
			this.fromTrIndex = i;
			this.toTrIndex = j;
		}

		int fromTrIndex;
		int toTrIndex;

		public int getFromTrIndex() {
			return this.fromTrIndex;
		}

		public int getToTrIndex() {
			return this.toTrIndex;
		}

	}

	boolean isCalculateSummary = false;
	boolean isTotalSummary = false;
	int groupColumnForSummary = -1; // 0 부터 시작 . 컬럼순번.
	List<Integer> listSummaryColumn = null;
	List<SumInfo> listSumInfo = null;

	public void setSummary() {
		this.isCalculateSummary = true;
	}

	public void setTotalSummary() {
		this.isTotalSummary = true;
	}

	public void setGroupColumnForSummary(int colIndex) {
		this.groupColumnForSummary = colIndex;
		// 0 부터 시작..
	}

	public void setSummaryColumn(List<Integer> listCol) {
		this.listSummaryColumn = listCol;

	}

	public void tableSummary() {

		if (!isCalculateSummary) {
			return;
		}

		if (listRow == null || listRow.size() == 0) {
			return;
		}

		listSumInfo = new ArrayList<SumInfo>();

		Row row = (Row) listRow.get(0);

		int tdCount = row.getCellCount();
		int trCount = listRow.size();

		CellData[][] cellData = new CellData[trCount][tdCount];

		for (int i = 0; i < trCount; i++) {

			for (int j = 0; j < tdCount; j++) {
				Row tempRow = (Row) listRow.get(i);
				List<Cell> listCell = tempRow.getListCell();
				cellData[i][j] = new CellData((Cell) listCell.get(j));

				if (j == 0) {
					cellData[i][j].getCell().setParentCell(null);
				} else {
					cellData[i][j].getCell().setParentCell(cellData[i][j - 1].getCell());
				}

			}
		}

		Cell handlingCell = null;
		Cell upperCell = null;
		int fromTrIndexSum = 0;
		int toTrIndexSum = -1;
		List<Row> listNewRow = new ArrayList<Row>();
		for (int i = 0; i <= trCount; i++) {

			if (i == 0) {
				listNewRow.add(this.getRow(0).setOrgIndex("0"));
			}

			if (i > 0 && i < trCount) {

				handlingCell = (Cell) cellData[i][groupColumnForSummary].getCell();
				upperCell = (Cell) cellData[i - 1][groupColumnForSummary].getCell();

				System.out.println("handlingCell.getValue()=" + handlingCell.getValue() + "=" + upperCell.getValue());
				if ((handlingCell.getValue()).equals(upperCell.getValue())) {

					System.out.println("equals add");
					listNewRow.add(this.getRow(i).setOrgIndex(i + ""));

				} else {
					toTrIndexSum = (i - 1);
					listSumInfo.add(new SumInfo(fromTrIndexSum, toTrIndexSum));
					fromTrIndexSum = i;

					System.out.println("not equals add");
					listNewRow.add(gridUtil.makeSumRow(this.getRow(i - 1).setOrgIndex((i - 1) + ""), this.getSumTypeRow()));
					listNewRow.add(this.getRow(i).setOrgIndex(i + ""));

				}

			}

			if (i == trCount) {
				listSumInfo.add(new SumInfo(fromTrIndexSum, (i - 1)));
				listNewRow.add(gridUtil.makeSumRow(this.getRow(i - 1).setOrgIndex((i - 1) + ""), this.getSumTypeRow()));
			}
		}
		if (isTotalSummary) {
			listNewRow.add(gridUtil.makeTotalSumRow(this.getTotalSumTypeRow()));
		}

		// sum 계산하기.
		if (listSummaryColumn != null && listSummaryColumn.size() > 0) {
			for (int j = 0; j < listSummaryColumn.size(); j++) {
				int colIndex = ((Integer) listSummaryColumn.get(j)).intValue();
				// System.out.println("colIndex="+colIndex);
				if (listSumInfo != null && listSumInfo.size() > 0) {
					for (int k = 0; k < listSumInfo.size(); k++) {
						SumInfo sumInfo = (SumInfo) listSumInfo.get(k);

						int from = sumInfo.getFromTrIndex();
						int to = sumInfo.getToTrIndex();

						// System.out.println("from="+from);
						// System.out.println("to="+to);

						double colValue = new Double("0").doubleValue();
						for (int l = from; l <= to; l++) {

							handlingCell = (Cell) cellData[l][colIndex].getCell();
							try {
								colValue = colValue + new Double(handlingCell.getValue()).doubleValue();
							} catch (NumberFormatException e) {

							}

						}
						// System.out.println("colValue="+colValue);

						// summary replace
						for (int m = 0; m < listNewRow.size(); m++) {
							Row rowTemp = (Row) listNewRow.get(m);
							if (rowTemp.getOrgIndex().equals("SUM-" + to)) {
								Cell cell = rowTemp.getCell(colIndex);
								cell.setValue(colValue + "");
							}
						}

					}
				}

			}
		} // if

		// total summary
		if (isTotalSummary) {

			if (listSummaryColumn != null && listSummaryColumn.size() > 0) {
				for (int j = 0; j < listSummaryColumn.size(); j++) {
					int colIndex = ((Integer) listSummaryColumn.get(j)).intValue();
					// System.out.println("colIndex="+colIndex);

					double colValue = new Double("0").doubleValue();
					for (int l = 0; l < trCount; l++) {

						handlingCell = (Cell) cellData[l][colIndex].getCell();
						try {
							colValue = colValue + new Double(handlingCell.getValue()).doubleValue();
						} catch (NumberFormatException e) {

						}

					}
					// System.out.println("colValue="+colValue);

					// summary replace
					for (int m = 0; m < listNewRow.size(); m++) {
						Row rowTemp = (Row) listNewRow.get(m);
						if (rowTemp.getOrgIndex().equals("TOTAL")) {
							Cell cell = rowTemp.getCell(colIndex);
							cell.setValue(colValue + "");
						}
					}

				}
			} // if

		}

		this.listRow = null;
		this.listRow = listNewRow;

	}

	// SMRS 추가
	public Cell[][] getTotalCell() {

		Row row = (Row) listRow.get(0);

		int tdCount = row.getCellCount();
		int trCount = listRow.size();

		Cell[][] cell = new Cell[trCount][tdCount];

		for (int i = 0; i < trCount; i++) {

			for (int j = 0; j < tdCount; j++) {
				Row tempRow = (Row) listRow.get(i);
				List<Cell> listCell = tempRow.getListCell();
				cell[i][j] = (Cell) listCell.get(j);

			}
		}

		return cell;
	}

}
