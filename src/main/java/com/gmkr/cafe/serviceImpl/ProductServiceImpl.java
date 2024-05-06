package com.gmkr.cafe.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gmkr.cafe.contants.CafeContants;
import com.gmkr.cafe.dao.ProductDao;
import com.gmkr.cafe.jwt.JwtFilter;
import com.gmkr.cafe.pojo.Category;
import com.gmkr.cafe.pojo.Product;
import com.gmkr.cafe.service.ProductService;
import com.gmkr.cafe.utils.CafeUtils;
import com.gmkr.cafe.wrapper.ProductWrapper;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	JwtFilter jwtFilter;
	@Autowired
	ProductDao productDao;

	@Override
	public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
		try {
			if (jwtFilter.isAdmin()) {
				if (validateProductMap(requestMap, false)) {
					productDao.save(getProductFromMap(requestMap, false));
					return CafeUtils.getResponseEntity("Product Added Successfully", HttpStatus.OK);
				} else {
					return CafeUtils.getResponseEntity(CafeContants.INVALID_DATA, HttpStatus.BAD_REQUEST);
				}
			} else {
				return CafeUtils.getResponseEntity(CafeContants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return CafeUtils.getResponseEntity(CafeContants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
		if (requestMap.containsKey("name")) {
			if (requestMap.containsKey("id") && validateId) {
				return true;
			} else if (!validateId) {
				return true;
			}
		}
		return false;
	}

	private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
		Category category = new Category();
		category.setId(Integer.parseInt(requestMap.get("categoryId")));
		Product product = new Product();

		if (isAdd) {
			product.setId(Integer.parseInt(requestMap.get("id")));
		} else {
			product.setStatus("true");
		}
		product.setCategory(category);
		product.setName(requestMap.get("name"));
		product.setDescription(requestMap.get("description"));
		product.setPrice(Integer.parseInt(requestMap.get("price")));

		return product;
	}

	@Override
	public ResponseEntity<List<ProductWrapper>> getAllProduct() {
		try {
			return new ResponseEntity<List<ProductWrapper>>(productDao.getAllProduct(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<List<ProductWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
		try {
			if (jwtFilter.isAdmin()) {
				if (validateProductMap(requestMap, true)) {

					Optional<Product> optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
					if (!optional.isEmpty()) {
						Product product = getProductFromMap(requestMap, true);
						product.setStatus(optional.get().getStatus());
						productDao.save(product);
						return CafeUtils.getResponseEntity("Product Updated successfully", HttpStatus.OK);
					} else {
						return CafeUtils.getResponseEntity("Product is not exist", HttpStatus.OK);
					}
				} else {
					return CafeUtils.getResponseEntity(CafeContants.INVALID_DATA, HttpStatus.BAD_REQUEST);
				}
			} else {
				return CafeUtils.getResponseEntity(CafeContants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return CafeUtils.getResponseEntity(CafeContants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> deleteProduct(Integer id) {
		try {
			if (jwtFilter.isAdmin()) {
				Optional<Product> optional = productDao.findById(id);
				if (!optional.isEmpty()) {
					productDao.deleteById(id);
					return CafeUtils.getResponseEntity("Product deleted successfully", HttpStatus.OK);
				} else {
					return CafeUtils.getResponseEntity("Product does not exist", HttpStatus.OK);
				}
			} else {
				return CafeUtils.getResponseEntity(CafeContants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return CafeUtils.getResponseEntity(CafeContants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateStatus(Map<String, String> requstMap) {
		try {

			if (jwtFilter.isAdmin()) {
				Optional<Product> optional = productDao.findById(Integer.parseInt(requstMap.get("id")));
				if (!optional.isEmpty()) {
					productDao.updateProductStatus(requstMap.get("status"), Integer.parseInt(requstMap.get("id")));
					return CafeUtils.getResponseEntity("Product Status updated successfully", HttpStatus.OK);
				} else {
					return CafeUtils.getResponseEntity("Product id does not exist", HttpStatus.OK);
				}
			} else {
				return CafeUtils.getResponseEntity(CafeContants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return CafeUtils.getResponseEntity(CafeContants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {

		try {

			return new ResponseEntity<List<ProductWrapper>>(productDao.getProductByCategory(id), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<ProductWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<ProductWrapper> getProductById(Integer id) {
		try {
			return new ResponseEntity<ProductWrapper>(productDao.getProductById(id), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ProductWrapper>(new ProductWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
