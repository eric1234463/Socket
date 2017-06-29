package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class FileList {
	private SimpleStringProperty fileName = new SimpleStringProperty("");
	private SimpleStringProperty filePath = new SimpleStringProperty("");
	private SimpleStringProperty fileSize = new SimpleStringProperty("");
	public FileList(FileList filelist){
		
	}
	public FileList(String fileName,String filePath,String fileSize){
		setFileName(fileName);
		setFilePath(filePath);
		setFileSize(fileSize);
	}
	public void setFileName(String fileName){
		this.fileName = new SimpleStringProperty(fileName);	
	}
	public String getFileName() {
		return fileName.get();
	}
	public void setFilePath(String filePath){
		this.filePath = new SimpleStringProperty(filePath);	
	}
	public String getFilePath() {
		return filePath.get();
	}
	public void setFileSize(String fileSize){
		this.fileSize = new SimpleStringProperty(fileSize);	
	}
	public String getFileSize() {
		return fileSize.get();
	}
	
	
}
