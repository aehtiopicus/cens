package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.aehtiopicus.cens.enumeration.MaterialDidacticoUbicacionType;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;

@Entity
@Table(name="CENS_FILE_INFO")
public class FileCensInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private MaterialDidacticoUbicacionType fileLocation;
	
	@Column(name="path", length=1000)
	private String fileLocationPath;
	
	@Column(name="name")
	private String fileName;
	
	@Column(name="size")
	private Long fileSize;
	
	@Temporal(TemporalType.DATE)
	private Date fileLastModify;
	
	@Column(name="creator_id")
	private Long creatorId;
	
	@Enumerated(EnumType.STRING)
	private PerfilTrabajadorCensType creatorType;
	
	@Temporal(TemporalType.DATE)
	private Date creationDate;
	
	@Column(name="updater_id")
	private Long updaterId;	
	
	@Enumerated(EnumType.STRING)
	private PerfilTrabajadorCensType updaterType;
	
	@Temporal(TemporalType.DATE)
	private Date updateDate;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
	
	
	
}
