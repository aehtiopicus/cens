package com.aehtiopicus.cens.service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.List;

public interface CompresorService {

	public ByteArrayOutputStream comprimirPagos(List<String> filesNames) throws FileNotFoundException, IOException;
}
