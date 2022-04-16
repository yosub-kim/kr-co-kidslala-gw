package kr.co.kmac.pms.common.tag.tablegrid;

public class TableHeaderFixedUtil {

	public static String getDivStartStyle(String height){
		return " <TABLE id='basisResize' width='95%' ><TR height=1>	<TD></TD></TR></TABLE>\n<DIV style='width:expression(document.all.basisResize.offsetWidth);height:expression(document.body.clientHeight-"+height+"-30);overflow:auto;'> ";
	}
	public static String getDivEndStyle(){
		return " </DIV> ";
	}
	
	public static String tableStyle = " class='tableGridStyle' cellpadding='0' cellspacing='0' border='1' bordercolor='#cccccc'  ";	
	public static String headerTrStyle = " class='tr_header_grid' style='position:relative;top:expression(this.offsetParent.scrollTop);' ";
	public static String headerTrStyle2 = " class='tr_header_grid2' style='position:relative;top:expression(this.offsetParent.scrollTop);z-index:10;' ";
	public static String headerFirstTdStyle = " class='firstHeader' height='30' ";
	
	public static String dataTrStyle = " class='td_data_grid' ";
	public static String dataTrStyle2 = " class='td_data_grid' "; // 첫번째 td가 ellipsis인경우
	public static String dataFirstTdStyle = " class='firstData' ";

	
	public static String dataEllipsisStyle = " class='td_data_ellipsis_grid' ";
	
}

