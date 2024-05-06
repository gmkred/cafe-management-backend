package com.gmkr.cafe.restImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.gmkr.cafe.rest.DashboardRest;
import com.gmkr.cafe.service.DashboardService;

@RestController
public class DashboardRestImpl implements DashboardRest {
	@Autowired
	DashboardService dashboardService;

	@Override
	public ResponseEntity<Map<String, Object>> getCount() {

		return dashboardService.getCount();

	}

}
