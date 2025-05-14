package com.example.demo2;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import com.example.demo2.Sql.DbUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DbUtilTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetConnection() {
		try {
			assertInstanceOf(Connection.class, DbUtil.getConnection());
		} catch (SQLException e) {
			fail("Connection misslyckades", e);
		}
	}

}
