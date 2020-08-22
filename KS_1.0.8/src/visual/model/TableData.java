package visual.model;

import bean.QuackResults;
import javafx.beans.property.SimpleStringProperty;

/**
 * 	TableView表格的数据结构
 * 
 * @author Sunny
 *
 */
public class TableData {
	// 目前我们在JavaFX中的属性一般均采用Property,这样可以方便其他地方的数据绑定使用
	private SimpleStringProperty eventIndex = new SimpleStringProperty();
	private SimpleStringProperty eventTime = new SimpleStringProperty();
	private SimpleStringProperty eventLoca = new SimpleStringProperty();
	private SimpleStringProperty eventPos = new SimpleStringProperty();
	private SimpleStringProperty energy = new SimpleStringProperty();
	private SimpleStringProperty grade = new SimpleStringProperty();
	private QuackResults quackResults=null;

	/***
	 * 
	 * @param eventIndex 事件序号
	 * @param eventTime  触发时间
	 * @param eventLoca  触发台站
	 * @param eventPos   定位坐标
	 * @param energy     能量
	 * @param grade      震级
	 */
	public TableData(String eventIndex, String eventTime, String eventLoca, String eventPos, String energy,
			String grade,QuackResults dbresults) {
		super();
		this.eventIndex.set(eventIndex);
		this.eventTime.set(eventTime);
		this.eventLoca.set(eventLoca);
		this.eventPos.set(eventPos);
		this.energy.set(energy);
		this.grade.set(grade);
		this.quackResults=dbresults;
//		System.out.println(dbresults.toString());
	}

	public String getEventIndex() {
		return eventIndex.get();
	}

	public void setEventIndex(String eventIndex) {
		this.eventIndex.set(eventIndex);
		;
	}

	public String getEventTime() {
		return eventTime.get();
	}

	public void setEventTime(String eventTime) {
		this.eventTime.set(eventTime);
		;
	}

	public String getEventLoca() {
		return eventLoca.get();
	}

	public void setEventLoca(String eventLoca) {
		this.eventLoca.set(eventLoca);
		;
	}

	public String getEventPos() {
		return eventPos.get();
	}

	public void setEventPos(String eventPos) {
		this.eventPos.set(eventPos);
		;
	}

	public String getEnergy() {
		return energy.get();
	}

	public void setEnergy(String energy) {
		this.energy.set(energy);
	}

	public String getGrade() {
		return grade.get();
	}

	public void setGrade(String grade) {
		this.grade.set(grade);
		;
	}
//	public QuackResults getQuackResults() {
//		return quackResults;
//	}

	public void setQuackResults(QuackResults quackResults) {
		this.quackResults = quackResults;
	}

	public QuackResults getQuackResults() {
		// TODO Auto-generated method stub
		return quackResults;
	}
}
