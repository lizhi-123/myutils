package poi.multiTitleBar.domain;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 
 * @author liuming
 */
public class CellProperty {

	
	/**标题*/
	private String title;
	
	/**单元格宽度*/
	private Integer width;

	/**合并单元格四大参数--起始行*/
	private Integer startRow;
	/**合并单元格四大参数--结束行*/
	private Integer endRow;
	/**合并单元格四大参数--起始列*/
	private Integer startCol;
	/**合并单元格四大参数--结束列*/
	private Integer endCol;
	/**合并单元格附加属性--是否需要合并，默认false*/
	private boolean mergeCell = false;

	/**做为表头时的样式*/
	private String titleStyle;

	/**批注*/
	private String comment;

	private CellStyle cellStyle;

	public CellProperty(String title) {
		this.title = title;
	}

	public CellProperty(String title, Integer startRow, Integer endRow, Integer startCol, Integer endCol) {
		this.title = title;
		this.startRow = startRow;
		this.endRow = endRow;
		this.startCol = startCol;
		this.endCol = endCol;
		// 合并属性设置为true
		this.mergeCell = true;
	}

	public boolean isMergeCell() {
		return mergeCell;
	}

	public void setMergeCell(boolean mergeCell) {
		this.mergeCell = mergeCell;
	}

	public CellStyle getCellStyle() {
		return cellStyle;
	}

	public void setCellStyle(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getEndRow() {
		return endRow;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}

	public Integer getStartCol() {
		return startCol;
	}

	public void setStartCol(Integer startCol) {
		this.startCol = startCol;
	}

	public Integer getEndCol() {
		return endCol;
	}

	public void setEndCol(Integer endCol) {
		this.endCol = endCol;
	}

	public String getTitleStyle() {
		return titleStyle;
	}

	public void setTitleStyle(String titleStyle) {
		this.titleStyle = titleStyle;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
