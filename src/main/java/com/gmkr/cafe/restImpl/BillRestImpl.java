package com.gmkr.cafe.restImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.gmkr.cafe.contants.CafeContants;
import com.gmkr.cafe.pojo.Bill;
import com.gmkr.cafe.rest.BillRest;
import com.gmkr.cafe.service.BillService;
import com.gmkr.cafe.utils.CafeUtils;

@RestController
public class BillRestImpl implements BillRest {
	@Autowired
	private BillService billService;

	@Override
	public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
		try {
			return billService.generateReport(requestMap);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return CafeUtils.getResponseEntity(CafeContants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Bill>> getBills() {

		try {

			return billService.getBills();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Bill>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {

		try {
			return billService.getPdf(requestMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResponseEntity<String> deleteBill(Integer id) {

		try {
			return billService.deleteBill(id);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeContants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
