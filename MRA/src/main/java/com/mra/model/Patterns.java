package com.mra.model;

import java.time.LocalDateTime;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "patterns")
public class Patterns {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Long id;
	private String pattern_name;
	private String pattern_file;
	private int file_size;
	@CreationTimestamp
	private LocalDateTime created;
	@UpdateTimestamp
	private LocalDateTime updated;
	@Lob
	@Type(type="org.hibernate.type.BinaryType")
	private byte[] data;
	private String file_type;
	
	@Override
	public String toString() {
		return "Patterns [id=" + id + ", pattern_name=" + pattern_name + ", pattern_file=" + pattern_file
				+ ", file_size=" + file_size + ", created=" + created + ", updated=" + updated + ", data="
				+ Arrays.toString(data) + ", file_type=" + file_type + "]";
	}
	
	

}
