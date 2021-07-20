package com.mra.business;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mra.logger.ConfigProperties;
import com.mra.logger.MraLogger;
import com.mra.model.Category;
import com.mra.model.Collections;
import com.mra.model.Patterns;
import com.mra.model.Product;
import com.mra.model.ProductModelMapper;
import com.mra.model.ProductRequest;
import com.mra.model.Response;
import com.mra.service.CategoryService;
import com.mra.service.CollectionService;
import com.mra.service.ProductService;

@Component
public class ProductBusiness {

	@Autowired
	Environment env;
	
	@Autowired
	MraLogger LOGGER;
	
	@Autowired
	ConfigProperties configProp;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	Response response;
	
	
	public Object getAllProduct() {
		List<Product> productList = productService.getAllProduct();
		if(productList!=null)
		{
			
			LOGGER.info(getClass(), "PRODUCTS RETRIEVED SUCCESSFULLY");
			response.setData(ProductModelMapper.prepareResponseList(productList));
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO RETRIEVE PRODUCTS");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object getProduct(int id) {
		LOGGER.debug(getClass(), "PRODUCT ID IN GET "+id);
		Optional<Product> products = productService.getProduct(id);
		if(products.isPresent())
		{
			LOGGER.info(getClass(), "PRODUCT BY ID RETRIEVED SUCCESSFULLY");
			response.setData(ProductModelMapper.prepareResponse(products.get()));
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO RETRIEVE PRODUCT FOR GIVEN ID");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public Object addProduct(ProductRequest product) throws Exception {
		
		Product productDto =  ProductModelMapper.productModelDto(product);
		if(product.getFile().getSize()>0)
		{
			String fileName = storeFileDB(product.getFile());	
			productDto.setImage_path(fileName);
			productDto.setData(product.getFile().getBytes());
			productDto.setFile_type(product.getFile().getContentType());

	     }
		else
		{
			Product oldProduct = productService.getProduct(product.getId().intValue()).get();
			productDto.setImage_path(oldProduct.getImage_path());
			productDto.setData(product.getFile().getBytes());
			productDto.setFile_type(product.getFile().getContentType());
		}   
		if(productService.addProduct(productDto))
		{
			LOGGER.info(getClass(), "PRODUCT ADDED SUCCESSFULLY");
			response.setData(null);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO ADD PRODUCT");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	

	public Object deleteProduct(int id) {
		LOGGER.debug(getClass(), "PRODUCT ID IN DELETE "+id);
		if(productService.deleteProduct(id))
		{
			LOGGER.info(getClass(), "PRODUCT DELETED SUCCESSFULLY");
			response.setData(null);
			response.setStatus(HttpStatus.OK);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO DELETE PRODUCT");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setData(null);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
	
//	public String storeFile(MultipartFile file) throws Exception {
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        Path filepath = Paths.get(env.getProperty("file.upload.pattern"))
//        .toAbsolutePath().normalize();
//        Files.createDirectories(filepath);
//        try {
//            if(fileName.contains("..")) {
//                throw new Exception("Sorry! Filename is invalid " + fileName);
//            }
//            Path targetLocation = filepath.resolve(fileName);
//            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//
//            return fileName;
//        } catch (IOException ex) {
//            throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
//        }
//    }
//
//    public Resource loadFileAsResource(String fileName) throws Exception {
//    	 Path filepath = Paths.get(env.getProperty("file.upload.pattern"))
//    		        .toAbsolutePath().normalize();
//        try {
//            Path filePath = filepath.resolve(fileName).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//            if(resource.exists()) {
//                return resource;
//            } else {
//                throw new Exception("File Not found " + fileName);
//            }
//        } catch (MalformedURLException ex) {
//            throw new Exception("File Not found " + fileName, ex);
//        }
//    }

//	public ResponseEntity<Resource> downloadProduct(String fileName, HttpServletRequest request) throws Exception {
//        Resource resource = loadFileAsResource(fileName);
//        String contentType = null;
//        try {
//            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//        } catch (IOException ex) {
//        }
//        if(contentType == null) {
//            contentType = "application/octet-stream";
//        }
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
//		
//	}
	
//	public String getDownloadUri(String fileName)
//	{
//		return ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/product/download/")
//                .path(fileName)
//                .toUriString();
//	}
//    
	
	public String storeFileDB(MultipartFile file) throws Exception {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
            }
       return fileName;
        } catch (Exception ex) {
            throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
	
	public ResponseEntity<Resource> downloadProduct(int fileId) throws Exception {
		 
		Optional<Product> products = productService.getProduct(fileId);
		if(products.isPresent())
		{
			LOGGER.info(getClass(), "PRODUCT FILE FOUND ");
			Product product = products.get();
			 return ResponseEntity.ok()
		                .contentType(MediaType.parseMediaType(product.getFile_type()))
		                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + product.getImage_path()+ "\"")
		                .body(new ByteArrayResource(product.getData()));
		}
		else
		{
			LOGGER.error(getClass(), "UNABLE TO FIND PATTERN");
			return ResponseEntity.notFound().build();
		}

	       
		
	}
    

    
    
}
