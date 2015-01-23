package com.aehtiopicus.cens.dto.cens;

import java.util.Date;

import com.aehtiopicus.cens.enumeration.MaterialDidacticoUbicacionType;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;

public class FileCensInfoDto {

	private Long id;
	private MaterialDidacticoUbicacionType fileLocation;
	private String fileLocationPath;
	private String fileName;
	private Long fileSize;
	private Date fileLastModify;
	private Long creatorId;
	private PerfilTrabajadorCensType creatorType;
	private Date creationDate;
	private Long updaterId;	
	private PerfilTrabajadorCensType updaterType;
	private Date updateDate;
	
	public MaterialDidacticoUbicacionType getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(MaterialDidacticoUbicacionType fileLocation) {
		this.fileLocation = fileLocation;
	}
	public String getFileLocationPath() {
		return fileLocationPath;
	}
	public void setFileLocationPath(String fileLocationPath) {
		this.fileLocationPath = fileLocationPath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public Date getFileLastModify() {
		return fileLastModify;
	}
	public void setFileLastModify(Date fileLastModify) {
		this.fileLastModify = fileLastModify;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public PerfilTrabajadorCensType getCreatorType() {
		return creatorType;
	}
	public void setCreatorType(PerfilTrabajadorCensType creatorType) {
		this.creatorType = creatorType;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Long getUpdaterId() {
		return updaterId;
	}
	public void setUpdaterId(Long updaterId) {
		this.updaterId = updaterId;
	}
	public PerfilTrabajadorCensType getUpdaterType() {
		return updaterType;
	}
	public void setUpdaterType(PerfilTrabajadorCensType updaterType) {
		this.updaterType = updaterType;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
}
